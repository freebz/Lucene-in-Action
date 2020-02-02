// 예제 4.3  각 토큰의 텀, 텍스트에서의 위치, 종류, 직전 토큰과의 위치를 화면에 출력하는 메소드

public static void displayTokensWithFullDetails(Analyzer analyzer,
						String text)
       throws IOException {

  TokenStream stream = analyzer.tokenStream("contents",
					    new StringReader(text));

  TermAttribute term = stream.addAttribute(TermAttribute.class);
  PositionIncrementAttribute posIncr =
       stream.addAttribute(PositionIncrementAttribute.class);
  OffsetAttribute offset =
       stream.addAttribute(OffsetAttribute.class);
  TypeAttribute type = stream.addAttribute(TYpeAttribute.class);

  int position = 0;

  while(stream.incrementToken()) {

    int increment = posIncr.getPositionIncrement();

    if (increment > 0) {
      position = position + increment;
      System.out.println();
      System.out.print(position + ": ");
    }

    System.out.print("[" +
		     term.term() + ":" +
		     offset.startOffset() + "->" +
		     offset.endOffset() + ":" +
		     type.type() + "] ");
  }
  System.out.println();
}
