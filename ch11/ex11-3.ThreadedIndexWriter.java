// 예제 11.3  색인 작업을 병렬로 처리하는 ThreadedIndexWriter

public class ThreadedIndexWriter extends IndexWriter {

  private ExecutorService threadPool;
  private Analyzer defaultAnalyzer;

  private class Job implements Runnable {
    Document doc;
    Analyzer analyzer;
    Term delTerm;
    public Job(Document doc, Term delTerm, Analyzer analyzer) {
      this.doc = doc;
      this.analyzer = analyzer;
      this.delTerm = delTerm;
    }

    public void run() {
      try {
	if (delTerm != null) {
	  ThreadedIndexWriter.super.updateDocument(delTerm, doc, analyzer);
	} else {
	  ThreadedIndexWriter.super.addDocument(doc, analyzer);
	}
      } catch (IOException ioe) {
	throw new RuntimeException(ioe);
      }
    }
  }

  public ThreadedIndexWriter(Directory dir, Analyzer a,
			 boolean create, int numThreads,
			 int maxQueueSize, IndexWriter.MaxFieldLength mfl)
           throws CorruptIndexException, IOException {
    super(dir, a, create, mfl);
    defaultAnalyzer = a;
    threadPool = new ThreadPoolExecutor(
	   numThreads, numThreads,
	   0, TimeUnit.SECONDS,
	   new ArrayBlockingQueue<RUnnable>(maxQueueSize, false),
	   new ThreadPoolExecutor.CallerRunsPolicy());
  }

  public void addDocument(Document doc) {
    threadPool.execute(new Job(doc, null, defaultAnalyzer));
  }

  public void addDocument(Document doc, Analyzer a) {
    threadPool.execute(new Job(doc, term, defaultAnalyzer));
  }

  public void updateDocument(Term term, Document doc, Analyzer a) {
    threadPool.execute(new Job(doc, term, a));
  }

  public void close()
         throws CorruptIndexException, IOException {
    finish();
    super.close();
  }

  public void close(boolean doWait)
         throws CorruptIndexException, IOException {
    finish();
    super.close(doWait);
  }

  public void rollback()
         throws CorruptIndexException, IOException {
    finish();
    super.rollback();
  }

  private void finish() {
    threadPool.shutdown();
    while(true) {
      try {
	if (threadPool.awaitTermination(Long.MAX_VALUE,
	       TimeUnit.SECONDS)) {
	  break;
	}
      } catch (InterruptedException ie) {
	Thread.currentThread().interrupt();
	throw new RuntimeException(ie);
      }
    }
  }
}
