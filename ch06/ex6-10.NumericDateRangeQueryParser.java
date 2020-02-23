// 예제 6.10  날짜 필드를 처리하게 확장한 QueryParser

class NumericDateRangeQueryParser extends QueryParser {
  public NumericDateRangeQueryParser(Version matchVersion,
		  String field, Analyzer a) {
    super(matchVersion, field, a);
  }

  public Query getRangeQuery(String field,
			     String part1,
			     String part2,
			     boolean inclusive)
    throws ParseException {
    TermRangeQuery query = (TermRangeQuery)
      super.getRangeQuery(field, part1, part2, inclusive);

    if ("pubmonth".equals(field)) {
      return NumericRangeQuery.newIntRange(
	     "pubmonth",
	     Integer.parseInt(query.getLowerTerm()),
	     Integer.parseInt(query.getUpperTerm()),
	     query.includesLower(),
	     query.includesUpper());
    } else {
      return query;
    }
  }
}
