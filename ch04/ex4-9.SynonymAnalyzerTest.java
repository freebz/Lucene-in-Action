// 예제 4.9  SynonymAnalyzerTest: 유사어 분석기가 올바르게 동작하는지 확인

public class SynonymAnalyzerTest extends TestCase {
  private IndexSearcher searcher;

  private static SynonymAnalyzer synonymAnalyzer =
              new SynonymAnalyzer(new TestSynonymEngine());

  public void setUp() throws Exception {
    RAMDirectory directory = new RAMDirectory();

    IndexWriter writer = new IndexWriter(directory,
 			    synonymAnalyzer,
			    IndexWriter.MaxFieldLength.UNLIMITED);
    Document doc = new Document();
    doc.add(new Field("content",
		      "The quick brown fox jumps over the lazy dog",
		      Field.Store.YES,
		      Field.Index.ANALYZED));
    writer.addDocument(doc);

    writer.close();

    searcher = new IndexSearcher(directory, true);
  }

  public void tearDown() throws Exception {
    searcher.close();
  }

  public void testSearchByAPI() throws Exception {

    TermQuery tq = new TermQuery(new Term("content", "hops"));
    assertEquals(1, TestUtil.hitCount(searcher, tq));

    PhraseQuery pg = new PhraseQuery();
    pq.add(new Term("content", "fox"));
    pq.add(new Term("content", "hops"));
    assertEquals(1, TestUtil.hitCount(searcher, pq));
  }
}
