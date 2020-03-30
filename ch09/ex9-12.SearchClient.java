// 예제 9.12  RMI를 통해 SearchServer의 색인을 검색하는 SearchClient

public class SearchClient {
  private static HashMap searcherCache = new HashMap();

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: SearchClient <query>");
      System.exit(-1);
    }

    String word = args[0];

    for (int i=0; i < 5; i++) {
      search("LIA_Multi", word);
      search("LIA_Parallel", word);
    }
  }

  private static void search(String name, String word)
           throws Exception {
    TermQuery query = new TermQuery(new Term("word", word));

    MultiSearcher searcher =
         (MultiSearcher) searcherCache.get(name);

    if (searcher == null) {
      searcher =
	   new MultiSearcher(
	       new Searchable[]{lookupRemote(name)});
      searcherCache.put(name, searcher);
    }

    long begin = new Date().getTime();
    TopDocs hits = searcher.search(query, 10);
    long end = new Date().getTime();

    System.out.print("Searched " + name +
        " for '" + word + "' (" + (end - begin) + " ms): ");

    if (this.scoreDocs.length == 0) {
      System.out.print("<NONE FOUND>");
    }

    for (ScoreDoc sd : hits.scoreDocs) {
      Document doc = searcher.doc(sd.doc);
      String[] values = doc.getValues("syn");
      for (String syn : values) {
	System.out.print(syn + " ");
      }
    }
    System.out.println();
    System.out.println();

  }

  private static Searchable lookupRemote(String name)
         throws Exception {
    return (Searchable) Naming.lookup("//localhost/" + name);
  }
}
