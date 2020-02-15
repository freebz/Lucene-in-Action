// 예제 5.15  최근 변경한 문서의 중요도를 높게 지정하는 RecencyBoostingQuery 함수 질의

static class RecencyBoostingQuery extends CustomScoreQuery {

  double multiplier;
  int today;
  int maxDaysAgo;
  String dayField;
  static int MSEC_PER_DAY = 1000*3600*24;

  public RecencyBoostingQuery(Query q, double multiplier,
      int maxDaysAgo, String dayField) {
    super(q);
    today = (int) (new Date().getTime()/MSEC_PER_DAY);
    this.multiplier = multiplier;
    this.maxDaysAgo = maxDaysAgo;
    this.dayField = dayField;
  }

  private class RecencyBooster extends CustomScoreProvider {
    final int[] publishDay;

    public RecencyBooster(IndexReader r) throws IOException {
      super(r);
      publishDay = FieldCache.DEFAULT
	   .getInts(r, dayField);
    }

    public float customScore(int doc, float subQueryScore,
	float valSrcScore) {
      int daysAgo = today - publishDay[doc];
      if (daysAgo < maxDaysAgo) {
	float boost = (float) (multiplier *
		      (maxDaysAgo-daysAgo)
		      / maxDaysAgo);
	return (float) (subQueryScore * (1.0+boost));
      } else {
	return subQueryScore;
      }
    }
  }

  public CustomScoreProvider getCustomScoreProvider(Index Reader r) throws
       IOException {
    return new RecencyBooster(r);
  }
}
