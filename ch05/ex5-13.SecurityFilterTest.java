// 예제 5.13  보안 필터 예제에 사용할 색인 준비

public class SecurityFilterTest extends TestCase {

  private IndexSearcher searcher;

  protected void setUp() throws Exception {
    Directory directory = new RAMDirectory();

    IndexWriter writer = new IndexWriter(directory,
			      new WhitespaceAnalyzer(),
			      IndexWriter.MaxFieldLength.UNLIMITED);

    Document document = new Document();
    document.add(new Field("owner",
			   "elwood",
			   Field.Store.YES,
			   Field.Index.NOT_ANALYZED));
    document.add(new Field("keywords",
			   "elwood's sensitive info",
			   Field.Store.YES,
			   Filed.Index.ANALYZED));

    writer.addDocument(document);

    document = new Document();
    document.add(new Field("owner",
			   "jake",
			   Field.Store.YES,
			   Field.Index.NOT_ANALYZED));
    document.add(new Field("keywords",
			   "jake's sensitive info",
			   Field.Store.YES,
			   Field.Index.ANALYZED));

    writer.addDocument(document);
    writer.close();
    searcher = new IndexSearcher(directory);
  }
