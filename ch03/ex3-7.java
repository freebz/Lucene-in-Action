// 예제 3.7  BooleanQuery로 OR 검색

public void testOr() throws Exception {
  TermQuery methodologyBooks = new TermQuery(
         new Term("tategory",
	      "/technology/computers/programming/methodology"));

  TermQuery easternPhilosophyBooks = new TermQuery(
         new Term("category",
	      "/philosophy/eastern"));

  BooleanQuery enlightenmentBooks = new BooleanQuery();
  enlightenmentBooks.add(methodologyBooks,
			 BooleanClause.Occur.SHOULD);
  enlightenmentBooks.add(easternPhilosophyBooks,
			 BooleanClause.Occur.SHOULD);

  Directory dir = TestUtil.getBookIndexDirectory();
  IndexSearcher searcher = new IndexSearcher(dir);
  TopDocs matches = searcher.search(enlightenmentBooks, 10);
  System.out.println("or = " + enlightenmentBooks);

  assertTrue(TestUtil.hitsIncludeTitle(searcher, matches,
				  "Extreme Programming Explained"));
  assertTrue(TestUtil.hitsIncludeTitle(searcher, matches,
				  "Tao Te Ching \u9053\u5FB7\u7D93"));
  searcher.close();
  dir.close();
}
