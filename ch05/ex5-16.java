// 예제 5.16  최근 문서의 중요도를 높게 지정하는 기능 테스트

public void testRecency() throws Throwable {
  Directory dir = TestUtil.getBookIndexDirectory();
  IndexReader r = IndexReader.open(dir);
  IndeexSearcher s = new IndexSearcher(r);
  s.setDefaultFieldSortScoring(true, true);

  QueryParser parser = new QueryParser(
		         Version.LUCENE_30,
			 "contents",
			 new StandardAnalyzer(
			      Version.LUCENE_30));
  Query q = parser.parse("java in action");
  Query q2 = new RecencyBoostingQuery(q,
				      2.0, 2*365,
				      "pubmonthAsDay");
  Sort sort = new Sort(new SortField[] {
    SortField.FIELD_SCORE,
         new SortField("title2", SortField.STRING)});
  TopDocs hits = s.search(q2, null, 5, sort);

  for (int i = 0; i < hits.scoreDocs.length; i++) {
    Document doc = r.document(hits.scoreDocs[i].doc);
    System.out.println((1+i) + ": " +
	     doc.get("title") +
	     ": pubmonth=" +
	     doc.get("pubmonth") +
	     " score=" + hits.scoreDocs[i].score);
  }
  s.close();
  r.close();
  dir.close();
}
