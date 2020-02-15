// 예제 5.6  QueryParser에서 MultiPhraseQuery 질의를 생성하는 모습

public void testQueryParser() throws Exception {
  SynonymEngine engine = new SynonymEngine() {
    public String[] getSynonyms(String s) {
      if (s.equals("quick"))
	return new String[] {"fast"};
      else
	return null;
    }
  };

  Query q = new QueryParser(Version.LUCENE_30,
			    "field",
			    new SynonymAnalyzer(engine))
                            .parse("\"quick fox\"");
  assertEquals("analyzed",
       "field:\"(quick fast) fox\"", q.toString());
  assertTrue("parsed as MultiPhraseQuery", q instanceof MultiPhraseQuery);
}
