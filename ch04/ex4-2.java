// 예제 4.2  AnalyzerUtils: 분석기 호출

public static void displayTokens(Analyzer analyzer,
				 String text) throws IOException {
  displayTokens(analyzer.tokenStream("contents",
		       new StringReader(text)));
}

public static void displayTokens(TokenStream stream)
  throws IOException {

  TermAttribute term = stream.addAttribute(TermAttribute.class);
  while(stream.incrementToken()) {
    System.out.print("[" + term.term() + "] ");
  }
}
