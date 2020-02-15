// 예제 5.11  SpanOrQuery로 두 갱 ㅣ스프내 질의를 연결

public void testSpanOrQuery() throws Exception {
  SpanNearQuery quick_fox =
       new SpanNearQuery(new SpanQuery[]{quick, fox}, 1, true);

  SpanNearQuery lazy_dog =
       new SpanNearQuery(new SpanQuery[]{lazy, dog}, 0, true);
  
  SpanNearQuery sleepy_cat =
       new SpanNearQuery(new SpanQuery[]{sleepy, cat}, 0, true);

  SpanNearQuery qf_near_ld =
       new SpanNearQuery(
	   new SpanQuery[]{quick_fox, lazy_dog}, 3, true);
  assertOnlyBrownFox(qf_near_ld);
  dumpSpans(qf_near_ld);

  SpanNearQuery qf_near_sc =
       new SpanNearQuery(
           new SpanQuery[]{quick_fox, sleepy_cat}, 3, true);
  dumpSpans(qf_near_sc);

  SpanOrQuery or = new SpanOrQuery(
       new SpanQuery[]{qf_near_ld, qf_near_sc});
  assertBothFoxes(or);
  dumpSpans(or);
}
