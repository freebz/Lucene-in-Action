// 예제 6.6  검색 결과 문서와 연관도 점수를 List 객체에 모두 담는 Collector

public class AllDocCollector extends Collector {
  List<ScoreDoc> docs = new ArrayList<ScoreDoc>();
  private Scorer scorer;
  private int docBase;

  public boolean acceptsDocsOutOfOrder() {
    return true;
  }

  public void setScorer(Scorer scorer) {
    this.scorer = scorer;
  }

  public void setNextReader(IndexReader reader, int docBase) {
    this.docBase = docBase;
  }

  public void collect(int doc) throws IOException {
    docs.add(
        new ScoreDoc(doc+docBase,
		     scorer.score()));
  }

  public void reset() {
    docs.clear();
  }

  public List<ScoreDoc> getHits() {
    return docs;
  }
}
