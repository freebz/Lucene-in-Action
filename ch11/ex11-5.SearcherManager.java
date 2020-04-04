// 예제 11.5  다중 스레드 환경에서 IndexSearcher를 관리해주는 SearcherManager 클래스

public class SearcherManager {

  private IndexSearcher currentSearcher;
  private IndexWriter writer;

  public SearcherManager(Directory dir) throws IOException {
    currentSearcher = new IndexSearcher(
			      IndexReader.open(dir));
    warm(currentSearcher);
  }

  public SearcherManager(IndexWriter writer) throws IOException {
    this.writer = writer;
    currentSearcher = new IndexSearcher(
			      writer.getReader());
    warm(currentSearcher);

    writer.setMergedSegmentWarmer(
      new IndexWriter.IndexReaderWarmer() {
	public void warm(IndexReader reader) throws IOException {
	  SearcherManager.this.warm(new IndexSearcher(reader));
	}
    });
  }

  public void warm(IndexSearcher searcher)
       throws IOException
  {}

  private boolean reopening;

  private synchronized void startReopen()
       throws InterruptedException {
    while (reopening) {
      wait();
    }
    reopening = true;
  }

  private synchronized void doneReopen() {
    reopening = false;
    notifyAll();
  }

  public void maybeReopen()
       throws InterruptedException,
       IOException {

    startReopen();

    try {
      final IndexSearcher searcher = get();
      try {
	IndexReader newReader =
	       currentSearcher.getIndexReader().reopen();
	if (newReader != currentSearcher.getIndexReader()) {
	  IndexSearcher newSearcher = new IndexSearcher(newReader);
	  if (writer == null) {
	    warm(newSearcher);
	  }
	  swapSearcher(newSearcher);
	}
      } finally {
	release(searcher);
      }
    } finally {
      doneReopen();
    }
  }

  public synchronized IndexSearcher get() {
    currentSearcher.getIndexReader().incRef();
    return currentSearcher;
  }

  public synchronized void release(
 	      IndexSearcher searcher)
       throws IOException {
    searcher.getIndexREader().decRef();
  }

  private synchronized void swapSearcher(IndexSearcher newSearcher)
       throws IOException {
    release(currentSearcher);
    currentSearcher = newSearcher;
  }

  public void close() throws IOException {
    swapSearcher(null);
  }
}
