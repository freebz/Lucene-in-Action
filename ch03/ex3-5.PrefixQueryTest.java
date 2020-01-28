// 예제 3.5  PrefixQuery

public class PrefixQueryTest extends TestCase {
  public void testPrefix() throws Exception {
    Directory dir = TestUtil.getBookIndexDirectory();
    IndexSearcher searcher = new IndexSearcher(dir);

    Term term = new Term("category",
		"/technology/computers/programming");
    PrefixQuery query = new PrefixQuery(term);

    TopDocs matches = searcher.search(query, 10);
    int programmingAndBelow = matches.totalHits;

    matches = searcher.search(new TermQuery(term), 10);
    int programmingAndBelow = matches.totalHits;

    matches = searcher.search(new TermQuery(term), 10);
    int justProgramming = matches.totalHits;

    assertTrue(programmingAndBelow > justProgramming);
    searcher.close();
    dir.close();
  }
}
