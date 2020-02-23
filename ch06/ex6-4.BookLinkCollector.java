// 예제 6.4  모든 검색 결과의 링크와 제목을 확보하게 직접 구현한 Collector

public class BookLinkCollector extends Collector {
  private Map<String,String> documents = new HashMap<String,String>();
  private Scorer scorer;
  private String[] urls;
  private String[] titles;

  public boolean acceptsDocsOutOfOrder() {
    return true;
  }

  public void setScorer(Scorer scorer) {
    this.scorer = scorer;
  }

  public void setNextReader(IndexReader reader, int docBase) throws
         IOException {
    urls = FieldCache.DEFAULT.getStrings(reader, "url");
    titles = FieldCache.DEFAULT.getStrings(reader, "title2");
  }

  public void collect(int docID) {
    try {
      String url = urls[docID];
      String title = titles[docID];
      documents.put(url, title);
      System.out.println(title + ":" + scorer.score());
    } catch (IOException e) {
      // ignore
    }
  }

  public Map<String,String> getLInks() {
    return Collections.unmodifiableMap(documents);
  }
}
