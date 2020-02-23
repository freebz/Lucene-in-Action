// 예제 6.9  숫자 필드를 올바로 다루게 QueryParser 확장

class NumericRangeQueryParser extends QueryParser {
  public NumericRangeQueryParser(Version matchVersion,
				 String field, Analyzer a) {
    super(matchVersion, field, a);
  }

  public Query getRangeQuery(String field,
			     String part1,
			     String part2,
			     boolean inclusive)
      throws ParseException {
    TermRangeQuery query = (TermRangeQuery)
      super.getRangeQuery(field, part1, part2,
			  inclusive);
    if ("price".equals(field)) {
      return NumericRangeQuery.newDoubleRange(
		    "price",
		    Double.parseDouble(
			query.getLowerTerm()),
		    Double.parseDouble(
			query.getUpperTerm()),
		    query.includesLower(),
		    query.includesUpper());
    } else {
      return query;
    }
  }
}
