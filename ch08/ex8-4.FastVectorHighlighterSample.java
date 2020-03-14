// 예제 8.4  FastVectorHighlighter를 사용한 하이라이팅

public class FastVectorHighlighterSample {

  static final String[] DOCS = {
    "the quick brown fox jumps over the lazy dog",
    "the quick gold fox jumped over the lazy black dog",
    "the quick fox jumps over the black dog",
    "the red fox jumped over the lazy dark gray dog"
  };
  static final String QUERY = "quick OR fox OR \"lazy dog\"~1";
  static final String F = "f";
  static Directory dir = new RAMDirectory();
  static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: FastVectorHighlighterSample <filename>");
      System.exit(-1);
    }
    makeIndex();
    searchIndex(args[0]);
  }

  static void makeIndex() throws IOException {
    IndexWriter writer = new IndexWriter(dir, analyzer,
			      true, MaxFieldLength.UNLIMITED);
    for(String d : DOCS) {
      Document doc = new Document();
      doc.add(new Field(F, d, Store.YES, Index.ANALYZED,
			TermVector.WITH_POSITIONS_OFFSETS));
      writer.addDocument(doc);
    }
    writer.close();
  }

  static void searchIndex(String filename) throws Exception {
    QueryParser parser = new QueryParser(Version.LUCENE_30,
					 F, analyzer);
    Query query = parser.parse(QUERY);
    FastVectorHighlighter highlighter = getHighlighter();
    FieldQuery fieldQuery = highlighter.getFieldQuery(query);
    IndexSearcher searcher = new IndexSearcher(dir);
    TopDocs docs = searcher.search(query, 10);

    FileWriter writer = new fileWriter(filename);
    writer.write("<html>");
    writer.write("<body>");
    writer.write("<p>QUERY : " + QUERY + "</p>");
    for(ScoreDoc scoreDoc : docsscoreDocs) {
      String snippet = highlighter.getBestFragment(
           fieldQuery, searcher.getIndexReader(),
	   scoreDoc.doc, F, 100 );
      if (snippet != null) {
	writer.write(scoreDoc.doc + " : " + snippet + "<br/>");
      }
    }
    writer.write("</body></html>");
    writer.close();
    searcher.close();
  }

  static FastVectorHighlighter getHighlighter() {
    FragListBuilder fragListBuilder = new SimpleFragListBuilder();
    FragmentsBuilder fragmentBuilder =
         new ScoreOrderFragmentsBuilder(
	   BaseFragmentsBuilder.COLORED_PRE_TAGS,
	   BaseFragmentsBuilder.COLORED_POST_TAGS);
    return new FastVectorHighlighter(true, true,
         fragListBuilder, fragmentBuilder);
  }
}
