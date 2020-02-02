// 예제 4.10  QueryParser로 생성한 질의로 SynonymAnalyzer 테스트

public void testWithQueryParser() throws Exception {
  Query query = new QueryParser(Version.LUCENE_30,
			    "content",
			    synonymAnalyzer).parse("\"fox jumps\"");
  assertEquals(1, TestUtil.hitCount(searcher, query));
  System.out.println("With SynonymAnalyzer, \"fox jumps\" parses to " +
		     query.toString("content"));

  query = new QueryParser(Version.LUCENE_30,
			  "content",
			  new StandardAnalyzer(Version.LUCENE_30))
                     .parse("\"fox jumps\"");
  assertEquals(1, TestUtil.hitCount(searcher, query));
  System.out.println("With StandardAnalyzer, \"fox jumps\" parses to " +
		       query.toString("content"));
}
