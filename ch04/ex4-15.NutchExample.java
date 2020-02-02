// 예제 4.15  NutchExample: 너치의 분석기와 질의 분석 예제

public class NutchExample {

  public static void main(String[] args) throws IOException {
    Configuration conf = new Configuration();
    conf.addResource("nutch-default.xml");
    NutchDocumentAnalyzer analyzer.tokenStream("content",
				  new StringReader("The quick brown fox..."));
    int position = 0;
    while(true) {
      Token token = ts.next();
      if (token == null) {
	break;
      }
      int increment = token.getPositionIncrement();

      if (increment > 0) {
	position = position + increment;
	System.out.println();
	System.out.print(position + ": ");
      }

      System.out.print("[" +
		       token.termText() + ":" +
		       token.startOffset() + "->" +
		       token.endOffset() + ":" +
		       token.type() + "] ");
    }
    System.out.println();

    Query nutchQuery = Query.parse("\"the quick brown\"", conf);
    org.apache.lucene.search.Query luceneQuery;
    luceneQuery = new QueryFilters(conf).filter(nutchQuery);
    System.out.println("Translated: " + luceneQuery);
  }
}
