// 예제 9.4  워드넷에서 유의어를 불러오는 WordNetSynonymEngine 클래스

public class WordNetSynonymEngine implements SynonymEngine {
  IndexSearcher searcher;
  Directory fsDir;

  public WordNetSynonymEngine(File index) throws IOException {
    fsDir = FSDirectory.open(index);
    searcher = new IndexSearcher(fsDir);
  }

  public void close() throws IOException {
    searcher.close();
    fsDir.close();
  }

  public String[] getSynonyms(String word) throws IOException {

    List<String> synList = new ArrayList<String>();

    AllDocCollector collector = new AllDocCollector();

    searcher.search(new TermQuery(new Term("word", word)), collector);

    for(ScoreDoc hit : collector.getHits()) {
      Document doc = searcher.doc(hit.doc);

      String[] values = doc.getValues("syn");

      for (String syn : values) {
	synList.add(syn);
      }
    }
    return synList.toArray(new String[0]);
  }
}
