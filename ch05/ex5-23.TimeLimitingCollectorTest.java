// 예제 5.23  TimeLimitingCollector를 사용해 검색 시간을 제한하는 방법

public class TimeLimitingCollectorTest extends TestCase {
  public void testTimeLimitingCollector() throws Exception {
    Directory dir = TestUtil.getBookIndexDirectory();
    IndexSearcher searcher = new IndexSearcher(dir);
    Query q = new MatchAllDocsQuery();
    int numAllBooks = TestUtil.hitCount(searcher, q);

    TopScoreDocCollector topDocs = TopScoreDocCollector.create(10, false);
    Collector collector = new TimeLimitingCollector(topDocs,
						    1000);
    try {
      searcher.search(q, collector);
      assertEquals(numAllBooks, topDocs.getTotalHits());
    } catch (TimeExceededException tee) {
      System.out.println("Too much time taken.");
    }
    searcher.close();
    dir.close();
  }
}
