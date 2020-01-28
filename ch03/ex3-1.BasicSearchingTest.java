// 예제 3.1  TermQuery로 검색

public class BasicSearchingTest extends TestCase {
  public void testTerm() throws Exception {
    Directory dir = TestUtil.getBookIndexDirectory();
    IndexSearcher searcher = new IndexSearcher(dir);

    Term t = new Term("subject", "ant");
    Query query = new TermQuery(t);
    TopDocs docs = searcher.search(query, 10);
    assertEquals("Ant in Action",
		 1, docs.totalHits);

    t = new Term("subject", "junit");
    docs = searcher.search(new TermQuery(t), 10);
    assertEquals("Ant in Action, " +
		 "Junit in Action, Second Edition",
		 2, docs.totalHits);

    searcher.close();
    dir.close();
  }
}
