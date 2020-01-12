// 예제 2.5  색인 하나를 단 하나의 IndexWriter만 사용할 수 있게 파일 기반의 락을 사용하는 모습

public class LockTest extends TestCase {

  private Directory dir;
  private File indexDir;

  protected void setUp() throws IOException {
    indexDir = new File(
	System.getProperty("java.io.tempdir", "tmp") +
	System.getProperty("file.separator") + "index");
    dir = FSDirectory.open(indexDir);
  }

  public void testWriteLock() throws IOException {

    IndexWriter writer1 = new IndexWriter(dir, new SimpleAnalyzer(),
				IndexWriter.MaxFieldLength.UNLIMITED);
    IndexWriter writer2 = null;

    try {
      writer2 = new IndexWriter(dir, new SimpleAnalyzer(),
			   IndexWriter.MaxFieldLength.UNLIMITED);
      fail("We should never reach this point");
    }
    catch (LockObtainFailedException e) {
      e.printStackTrace();
    }
    finally {
      writer1.close();
      assertNull(writer2);
      TestUtil.rmDir(indexDir);
    }
  }
}
