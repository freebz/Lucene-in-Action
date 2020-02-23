// 예제 6.1  지리 정보 색인

public class DistanceSortingTest extends TestCase {
  private RAMDirectory directory;
  private IndexSearcher searcher;
  private Query query;

  protected void setUp() throws Exception {
    directory = new RAMDirectory();
    IndexWriter writer =
         new IndexWriter(directory, new WhitespaceAnalyzer(),
			 IndexWriter.MaxFieldLength.UNLIMITED);
    addPoint(writer, "El Charro", "restaurant", 1, 2);
    addPoint(writer, "Cafe Poca Cosa", "restaurant", 5, 9);
    addPoint(writer, "Los Betos", "restaurant", 9, 6);
    addPoint(writer, "Nico's Taco Shop", "restaurant", 3, 8);

    writer.close();

    searcher = new IndexSearcher(directory);

    query = new TermQuery(new Term("type", "restaurant"));
  }

  private void addPoint(IndexWriter writer,
	   String name, String type, int x, int y)
         throws IOException {
    Document doc = new Document();
    doc.add(new Field("name", name, Field.Store.YES,
         Field.Index.NOT_ANALYZED));
    doc.add(new Field("type", type, Field.Store.YES,
	 Field.Index.NOT_ANALYZED));
    doc.add(new Field("x", Integer.toString(x), Field.Store.YES,
	 Field.Index.NOT_ANALYZED_NO_NORMS));
    doc.add(new Field("y", Integer.toString(y), Field.Store.YES,
         Field.Integer.NOT_ANALYZED_NO_NORMS));
    writer.addDocument(doc);
  }
}
