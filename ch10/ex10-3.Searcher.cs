// 예제 10.3  루씬닷넷으로 색인의 내용 검색

class Searcher {
  String indexDir = args[0];
  String q = args[1];

  public static void search(String indexDir, String q) {
    Directory dir = FSDirectory.Open(new System.IO.FileInfo(indexDir));
    IndexSearcher searcher = new IndexSearcher(dir, true);
    QueryParser parser = new QueryParser("contents",
					 new
	     StandardAnalyzer(Version.LUCENE_CURRENT));
    Query query = parser.Parse(q);
    Lucene.Net.Search.TopDocs hits = searcher.Search(query, 10);
    System.Console.WriteLine("Found " +
			 hits.totalHits +
			     " document(s) that matched query '" + q + "':");
    for (int i = 0; i < hits.scoreDocs.Length; i++) {
      ScoreDoc scoreDoc = hits.ScoreDocs[i];
      Document doc = searcher.Doc(scoreDoc.doc);
      System.Console.WriteLine(doc.Get("filename"));
    }
    searcher.Close();
    dir.Close();
  }
}
