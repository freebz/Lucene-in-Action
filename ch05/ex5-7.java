// 예제 5.7  MultiFieldQueryParser로 질의를 생성하는 방법

public void testDefaultOperator() throws Exception {
  Query query = new MultiFieldQueryParser(Version.LUCENE_30,
		     new String[]{"title", "subject"},
		     new SimpleAnalyzer()).parse("development");

  Directory dir = TestUtil.getBookIndexDirectory();
  IndexSearcher searcher = new IndexSearcher(
			      dir,
			      true);
  TopDocs hits = searcher.search(query, 10);

  assertTrue(TestUtil.hitsIncludeTitle(
	     searcher,
	     hits,
	     "Ant in Action"));

  assertTrue(TestUtil.hitsIncludeTitle(
	     searcher,
	     hits,
	     "Extreme Programming Explained"));
  searcher.close();
  dir.close();
}

public void testSpecifiedOperator() throws Exception {
  Query query = MultiFieldQueryParser.parse(Version.LUCENE_30,
	        "lucene",
	        new String[]{"title", "subject"},
	        new BooleanClause.Occur[]{BooleanClause.Occur.MUST,
	        BooleanClause.Occur.MUST},
		new SimpleAnalyzer());

  Directory dir = TestUtil.getBookIndexDirectory();
  IndexSearcher searcher = new IndexSearcher(
			      dir,
			      true);
  TopDocs hits = searcher.search(query, 10);

  assertTrue(TestUtil.hitsIncludeTitle(
	     searcher,
	     hits,
	     "Lucene in Action, SecondEdition"));
  assertEquals("one and only one", 1, hits.scoreDocs.length);
  searcher.close();
  dir.close();
}
