// 예제 3.8  PhraseQuery

public class PhraseQueryTest extends TestCase {
  private Directory dir;
  private IndexSearcher searcher;
  protected void setUp() throws IOException {
    dir = new RAMDirectory();
    IndexWriter writer = new IndexWriter(dir,
			      new WhitespaceAnalyzer(),
			      IndexWriter.MaxFieldLength.UNLIMITED);
    Document doc = new Document();
    doc.add(new Field("field",
	"the quick brown fox jumped over the lazy dog",
	Field.Store.YES,
	Field.Index.ANALYZED));
    writer.addDocument(doc);
    writer.close();
    searcher = new IndexSearcher(dir);
  }

  protected void tearDown() throws IOException {
    searcher.close();
    dir.close();
  }

  private boolean matched(String[] phrase, int slop)
      throws IOException {
    PhraseQuery query = new PhraseQuery();
    query.setSlop(slop);

    for (String word : phrase) {
      query.add(new Term("field", word));
    }

    TopDocs matches = searcher.search(query, 10);
    return matches.totalHits > 0;
  }
}
