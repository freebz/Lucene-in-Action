// 예제 8.3  검색 결과 하이라이팅

public void testHits() throws Exception {
  IndexSearcher searcher = new
       IndexSearcher(TestUtil.getBookIndexDirectory());
  TermQuery query = new TermQuery(new Term("title", "action"));
  TopDocs hits = searcher.search(query, 10);

  QueryScorer scorer = new QueryScorer(query, "title");
  Highlighter highlighter = new Highlighter(scorer);
  highlighter.setTextFragmenter(
       new SimpleSpanFragmenter(scorer));

  Analyzer analyer = new SimpleAnalyzer();

  for (ScoreDoc sd : hits.scoreDocs) {
    Document doc = searcher.doc(sd.doc);
    String title = doc.get("title");

    TokenStream stream =
         TokenSources.getAnyTokenStream(searcher.getIndexReader(),
					sd.doc,
					"title",
					doc,
					analyzer);
    String fragment =
         highlighter.getBestFragment(stream, title);

    System.out.println(fragment);
  }
}
