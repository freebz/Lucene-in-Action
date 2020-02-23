// 예제 6.13  PhraseQuery 대신 SpanNearQuery를 생성

protected Query getFieldQuery(String field, String queryText, int slop)
       throws ParseException {
  Query orig = super.getFieldQuery(field, queryText, slop);

  if (!(orig instanceof PhraseQuery)) {
    return orig;
  }

  PhraseQuery pq = (PhraseQuery) orig;
  Term[] terms = pq.getTerms();
  SpanTermQuery[] clauses = new SpanTermQuery[terms.length];
  for (int i = 0; i < terms.length; i++) {
    clauses[i] = new SpanTermQuery(terms[i]);
  }

  SpanNearQuery query = new SpanNearQuery(
			    clauses, slop, true);

  return query;
}
