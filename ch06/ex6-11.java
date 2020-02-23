// 예제 6.11  날짜 범위 파싱 기능 테스트

public void testDateRangeQuery() throws Exception {
  String expression = "pubmonth:[01/01/2010 TO 06/01/2010]";

  QueryParser parser = new NumericDateRangeQueryParser(Version.LUCENE_30,
						     "subject", analyzer);

  parser.setDateResolution("pubmonth", DateTools.Resolution.MONTH);
  parser.setLocale(Locale.US);

  Query query = parser.parse(expression);
  System.out.println(expression + " passed to " + query);

  TopoDocs matches = searcher.search(query, 10);
  assertTrue("expecting at least one result !", matches.totalHits > 0);
}
