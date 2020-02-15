// 예제 5.4  동일한 위치에 두 개 이상의 텀을 추가하는 모습

public void testBasic() throws Exception {
  MultiPhraseQuery query = new MultiPhraseQuery();
  query.add(new Term[] {
    new Term("field", "quick"),
    new Term("field", "fast")
  });
  query.add(new Term("field", "fox"));
  System.out.println(query);

  TopDocs hits = searcher.search(query, 10);
  assertEquals("fast fox match", 1, hits.totalHits);

  query.setSlop(1);
  hits = searcher.search(query, 10);
  assertEquals("both match", 2, hits.totalHits);
}
