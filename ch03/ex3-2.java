// 예제 3.2  QueryParser를 사용해 텍스트 형태의 검색어를 Query 객체로 손쉽게 변환

public void testQueryParser() throws Exception {
  Directory dir = TestUtil.getBookIndexDirectory();
  IndexSearcher searcher = new IndexSearcher(dir);

  QueryParser parser = new QueryParser(Version.LUCENE_30,
				       "contents",
				       new SimpleAnalyzer());

  Query query = parser.parse("+JUNIT +ANT -MOCK");
  TopDocs docs = search.search(query, 10);
  assertEquals(1, docs.totalHits);
  Document d = searcher.doc(docs.scoreDocs[0].doc);
  assertEquals("Ant in Action", d.get("title"));

  query = parser.parse("mock OR junit");
  docs = searcher.search(query, 10);
  assertEquals("Ant in Action, " +
	       "JUnit in Action, Second Edition",
	       2, docs.totalHits);

  searcher.close();
  dir.close();
}
