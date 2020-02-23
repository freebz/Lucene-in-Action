// 예제 6.17  적재된 값으로 특정 텀의 중요도를 추가로 지정

public class PayloadsTest extends TestCase {

  Directory dir;
  IndexWriter writer;
  BulletinPayloadsAnalyzer analyzer;

  protected void setUp() throws Exception {
    super.setUp();
    dir = new RAMDirectory();
    analyzer = new BulletinPayloadsAnalyzer(5.0F);
    writer = new IndexWriter(dir, analyzer,
	        IndexWriter.MaxFieldLength.UNLIMITED);
  }

  protected void tearDown() throws Exception {

    super.tearDown();
    writer.close();
  }

  void addDoc(String title, String contents) throws IOException {
    Document doc = new Document();
    doc.add(new Field("title",
		      title,
		      Field.Store.YES,
		      Field.Index.NO));
    doc.add(new Field("contents",
		      contents,
		      Field.Store.NO,
		      Field.Index.ANALYZED));
    analyzer.setIsBulletin(contents.startsWith("Bulletin:"));
    writer.addDocument(doc);
  }

  public void testPayloadTermQuery() throws Throwable {
    addDoc("Hurricane warning",
	   "Bulletin: A hurricane warning was issued at " +
	   "6 AM for the outer great banks");
    addDoc("Warning label maker",
	   "The warning label maker is a delightful toy for " +
	   "your precocious seven year old's warning needs");
    addDoc("Tornado warning",
	   "Bulletin: There is a tornado warning for " +
	   "Worcester county until 6 PM today");

    IndexReader r = writer.getReader();
    writer.close();

    IndexSearcher searcher = new IndexSearcher(r);

    searcher.setSimilarity(new BoostingSimilarity());

    Term warning = new Term("contents", "warning");

    Query query1 = new TermQuery(warning);

    System.out.println("\nTermQuery results:");
    TopDocs hits = search.search(query1, 10);
    TestUtil.dumpHits(searcher, hits);

    assertEquals("Warning label maker",
		 searcher.doc(hits.scoreDocs[0].doc).get("title"));

    Query query2 = new PayloadTermQuery(warning,
		         new AveragePayloadFunction());
    System.out.println("\nPayloadTermQuery results:");
    hits = searcher.search(query2, 10);
    TestUtil.dumpHits(searcher, hits);

    assertEquals("Warning label maker",
        searcher.doc(hits.scoreDocs[2].doc).get("title"));
    r.close();
    searcher.close();
  }
}
