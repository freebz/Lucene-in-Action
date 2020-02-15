// 예제 5.12  TermRangeFilter를 사용해 제목에 필터 적용

public class FilterTest extends TestCase {
  private Query allBooks;
  private IndexSearcher searcher;
  private Directory dir;

  protected void setUp() throws Exception {
    allBooks = new MatchAllDocsQuery();
    dir = TestUtil.getBookIndexDirectory();
    searcher = new IndexSearcher(dir);
  }

  protected void tearDown() throws Exception {
    searcher.close();
    dir.close();
  }

  public void testTermRangeFilter() throws Exception {
    Filter filter = new TermRangeFilter("title2", "d", "j", true, true);
    assertEquals(3, TestUtil.hitCount(searcher, allBooks, filter));
  }
}
