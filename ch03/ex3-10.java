// 예제 3.10  QueryParser로 TermRangeQuery 생성

public void testTermRangeQuery() throws Exception {
  Query query = new QueryParser(Version.LUCENE_30,
				"subject", analyzer)
                                .parse("title2:[Q TO V]");
  assertTrue(query instanceof TermRangeQuery);

  TopDocs matches = searcher.search(query, 10);
  assertTrue(TestUtil.hitsIncludeTitle(searcher, matches,
	     "Tapestry in Action"));

  query = new QueryParser(Version.LUCENE_30,
		"subject",
		analyzer)
           .parse("title2:{Q TO \"Tapestry in Action\"}");
  matches = searcher.search(query, 10);
  assertFalse(TestUtil.hitsIncludeTitle(searcher, matches,
	      "Tapestry in Action"));
}
