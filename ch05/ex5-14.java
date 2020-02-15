// 예제 5.14  필터를 사용해 검색 대상을 제한

public void testSecrityFilter() throws Exception {
  TermQuery query = new TermQuery(
		       new Term("keywords", "info"));

  assertEquals("Both documents match",
	       2,
	       TestUtil.hitCount(searcher, query));

  Filter jakeFilter = new QueryWrapperFilter(
       new TermQuery(new Term("owner", "jake")));

  TopDocs hits = searcher.search(query, jakeFilter, 10);
  assertEquals(1, hits.totalHits);
  assertEquals("elwood is safe",
	       "jake's sensitive info",
	       searcher.doc(hits.scoreDocs[0].doc)
	       .get("keywords"));
}
