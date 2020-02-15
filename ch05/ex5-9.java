// 예제 5.9  스팬 질의에 해당하는 모든 스팬을 화면에 출력하는 dumpSpans 메소드

private void dumpSpans(SpanQuery query) throws IOException {
  Spans spans = query.getSpans(reader);
  System.out.println(query + ":");
  int numSpans = 0;
  
  TopDocs hits = searcher.search(query, 10);
  float[] scores = new float[2];
  for (ScoreDoc sd : hits.scoreDocs) {
    scores[sd.doc] = sd.score;
  }

  while (spans.next()) {
    numSpans++;
    
    int id = spans.doc();
    Document doc = reader.document(id);
    
    TokenStream stream = analyzer.tokenStream("contents",
			 new StringReader(doc.get("f")));
    TermAttribute term = stream.addAttribute(TermAttribute.class);
    
    StringBuilder buffer = new StringBuilder();
    buffer.append("   ");
    int i = 0;
    while(stream.incrementToken()) {
      if (i == spans.start()) {
	buffer.append("<");
      }
      buffer.append(term.term());
      if (i + 1 == spans.end()) {
	buffer.append(">");
      }
      buffer.append(" ");
      i++;
    }
    buffer.append("(").append(scores[id]).append(")");
    System.out.println(buffer);
  }

  if (numSpans == 0) {
    System.out.println("   No spans");
  }
  System.out.println();
}
