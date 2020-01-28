// 예제 3.6  여러 질의를 BooleanQuery로 묶어서 질의하는 모습

public void testAnd() throws Exception {
  TermQuery searchingBooks =
      new TermQuery(new Term("subject","search"));

  Query books2010 =
    NumericRangeQuery.newIntRange("pubmonth", 201001,
				  201012,
				  true, true);

  BooleanQuery searchingBooks2010 = new BooleanQuery();
  searchingBooks2010.add(searchingBooks, BooleanClause.Occur.MUST);
  searchingBooks2010.add(books2010, BooleanClause.Occur.MUST);

  Directory dir = TestUtil.getBookIndexDirectory();
  IndexSearcher searcher = new IndexSearcher(dir);
  TopDocs matches = searcher.search(searchingBooks2010, 10);

  assertTrue(TestUtil.hitsIncludeTitle(searcher, matches,
				       "Lucene in Action, Second Edition"));
  searcher.close();
  dir.close();
}
