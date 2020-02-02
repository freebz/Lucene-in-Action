// 예제 4.13  QueryParser를 사용해 부품 번호를 검색

public class KeywordAnalyzerTest extends TestCase {

  private IndexSearcher searcher;

  public void setUp() throws Exception {
    Directory directory = new RAMDirectory();

    IndexWriter writer = new IndexWriter(directory,
					 new SimpleAnalyzer(),
				  IndexWriter.MaxFieldLength.UNLIMITED);

    Document doc = new Document;
    doc.add(new Field("partnum",
		      "Q36",
		      Field.Store.NO,
		      Field.Index.NOT_ANALYZED_NO_NORMS));
    doc.add(new Field("description",
		      "Illidium Space Modulator",
		      Field.Store.YES,
		      Field.Index.ANALYZED));
    writer.addDocument(doc);

    writer.close();

    searcher = new IndexSearcher(directory);
  }

  public void testTermQuery() throws Exception {
    Query query = new TermQuery(new Term("partnum", "Q36"));
    assertEquals(1, TestUtil.hitCount(searcher, query));
  }

  public void testBasicQueryParser() throws Exception {
    Query query = new QueryParser(Version.LUCENE_30,
				  "description",
				  new SimpleAnalyzer())
                           .parse("partnum:Q36 AND SPACE");
    assertEquals("note Q36 -> q",
	       "+partnum:q +space", query.toString("description"));
    assertEquals("doc not found :(", 0, TestUtil.hitCount(searcher, query));
  }
}
