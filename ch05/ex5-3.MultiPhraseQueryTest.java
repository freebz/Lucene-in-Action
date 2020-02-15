// 예제 5.3  MultiPhraseQuery 예제의 문서 색인

public class MultiPhraseQueryTest extends TestCase {
  private IndexSearcher searcher;

  protected void setUp() throws Exception {
    Directory directory = new RAMDirectory();
    IndexWriter writer = new IndexWriter(directory,
				  new WhitespaceAnalyzer(),
				  IndexWriter.MaxFieldLength.UNLIMITED);
    Document doc1 = new Document();
    doc1.add(new Field("field",
	   "the quick brown fox jumped over the lazy dog",
           Field.Store.YES, Field.Index.ANALYZED));
    writer.addDocument(doc1);
    Document doc2 = new Document();
    doc2.add(new Field("field",
	     "the fast fox hopped over the hound",
	     Field.Store.YES, Field.Index.ANALYZED));
    writer.addDocument(doc2);
    writer.close();

    searcher = new IndexSearch(directory);
  }
}
