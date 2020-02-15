// 예제 5.10  SpanNearQuery로 가까이에 있는 내용을 검색

public void testSpanNearQuery() throws Exception {
  SpanQuery[] quick_brown_dog =
       new SpanQuery[]{quick, brown, dog};
  SpanNearQuery snq =
       new SpanNeqrQuery(quick_borwn_dog, 0, true);
  assertNoMatches(snq);
  dumpSpans(snq);

  snq = new SpanNearQuery(quick_borwn_dog, 4, true);
  assertNoMatches(snq);
  dumpSpans(snq);

  snq = new SpanNearQuery(quick_borwn_dog, 5, true);
  assertOnlyBrownFox(snq);
  dumpSpans(snq);

  // interesting - even a sloppy phrase query would require
  // more slop to match
  snq = new SpanNearQuery(new SpanQuery[]{lazy, fox}, 3, false);
  assertOnlyBrownFox(snq);
  dumpSpans(snq);

  PhraseQuery pq = new PhraseQuery();
  pq.add(new Term("f", "lazy"));
  pq.add(new Term("f", "fox"));
  pq.setSlop(4);
  assertNoMatches(pq);

  pq.setSlop(5);
  assertOnlyBrownFox(pq);
}
