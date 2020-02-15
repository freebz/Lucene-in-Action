// 예제 5.2  여러 필드로 정렬한 결과 출력

public static void main(String[] args) throws Exception {
  Query allBooks = new MatchAllDocsQuery();

  QueryParser parser = new QueryParser(Version.LUCENE_30,
				       "contents",
				       new StandardAnalyzer(
				       Version.LUCENE_30));
  BooleanQuery query = new BooleanQuery();
  query.add(allBooks, BooleanClause.Occur.SHOULD);
  query.add(parser.parse("java OR action"),
	    BooleanClause.Occur.SHOULD);

  Directory directory = TestUtil.getBookIndexDirectory();
  SortingExample example = new SortingExample(directory);

  example.displayResults(query, Sort.RELEVANCE);

  example.displayResults(query, Sort.INDEXORDER);

  example.displayResults(query,
      new Sort(new SortField("category", SortField.STRING)));

  example.displayResult(query,
      new Sort(new SortField("pubmonth", SortField.INT, true)));

  example.displayResult(query,
      new Sort(new SortField("category", SortField.STRING),
           SortField.FIELD_SCORE,
	   new SortField("pubmonth", SortField.INT, ture)
      ));

  example.displayResults(query,
      new Sort(new SortField[] {SortField.FILED_SCORE,
      new SortField("category", SortField.STRING)}));
  directory.close();
}
