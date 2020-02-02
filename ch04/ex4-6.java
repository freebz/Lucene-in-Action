// 예제 4.6  유사어 분석기 테스트 메소드

public void testJumps() throws Exception {
  TokenStream stream =
      synonymAnalyzer.tokenStream("contents",
				  new StringReader("jumps"));
  TermAttribute term = stream.addAttribute(TermAttribute.class);
  PositionIncrementAttribute posIncr =
      stream.addAttribute(PositionIncrementAttribute.class);

  int i = 0;
  String[] expected = new String[]{"jumps",
				   "hops",
				   "leaps"};
  while(stream.incrementToken()) {
    assertEquals(expected[i], term.term());

    int expectedPos;
    if (i == 0) {
      expectedPos = 1;
    } else {
      expectedPos = 0;
    }
    assertEquals(expectedPos,
        posIncr.getPositionIncrement());
    i++;
  }
  assertEquals(3, i);
}
