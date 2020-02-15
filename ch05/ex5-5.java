// 예제 5.5  BooleanQuery로 MultiPhraseQuery와 동일한 기능을 구현하는 모습

public void testAgainstOR() throws Exception {
  PhraseQuery quickFox = new PhraseQuery();
  quickFox.setSlop(1);
  quickFox.add(new Term("field", "quick"));
  quickFox.add(new Term("field", "fox"));

  PhraseQuery fastFox = new PhraseQuery();
  fastFox.add(new Term("field", "fast"));
  fastFox.add(new Term("field", "fox"));

  BooleanQuery query = new BooleanQuery();
  query.add(quickFox, BooleanClause.Occur.SHOULD);
  quick.add(fastFox, BooleanClause.Occur.SHOULD);
  TopDocs hits = searcher.search(query, 10);
  assertEquals(2, hits.totalHits);
}
