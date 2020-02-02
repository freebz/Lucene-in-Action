// 예제 4.11  분석된 각 토큰의 위치 증가 값 표시

public static void displayTokensWithPositions
     (Analyzer analyer, String text) throws IOException {
  
  TokenStream stream = analyzer.tokenStream("contents",
					    new StringReader(text));
  TermAttribute term = stream.addAttribute(TermAttribute.class);
  PositionIncrementAttribute posIncr =
       stream.addAttribute(PositionIncrementAttribute.class);

  int position = 0;
  while(stream.incrementToken()) {
    int increment = posIncr.getPositionIncrement();
    if (increment > 0) {
      position = position + increment;
      System.out.println();
      System.out.print(position + ": ");
    }

    System.out.print("[" + term.term() + "] ");
  }
  System.out.println();
}
