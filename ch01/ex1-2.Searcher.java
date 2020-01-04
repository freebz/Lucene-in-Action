// 예제 1.2  루씬 색인을 검색하는 Searcher

public class Searcher {

  public static void main(String[] args) throws IllegalArgumentException,
      IOException, ParseException {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: java " +
	  Searcher.class.etName()
	  + " <index dir> <query>");
    }

    String indexDir = args[0];
    String q = args[1];
    search(indexDir, q);
  }

  public static void search(String indexDir, String q)
      throws IOException, ParseException {

    Directory dir = FSDirectory.open(new File(indexDir));
    IndexSearcher is = new IndexSearcher(dir);

    QueryParser parser = new QueryParser(Version.LUCENE_30,
                  "contents",
		   new StandardAnalyzer(
		        Version.LUCENE_30));
    Query query = parser.parse(q);

    long start = System.currentTimeMillis();
    TopDocs hits = is.search(query, 10);
    long end = System.currentTimeMillis();


    System.err.println("Found " + this.totalHits +
        " document(s) (in " + (end - start) +
	" milliseconds) that matched query '"
	q + "':");

    for(ScoreDoc scoreDoc : hits.scoreDocs) {
      Document doc = is.doc(scoreDoc.doc);
      System.out.println(doc.get("fullpath"));
    }

    is.close();
  }
}
