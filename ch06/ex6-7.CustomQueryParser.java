// 예제 6.7  와일드카드와 퍼지 질의 제한

public class CustomQueryParser extends QueryParser {
  public CustomQueryParser(Version matchVersion,
      String field, Analyzer analyzer) {
    super(matchVersion, field, analyzer);
  }

  protected final Query getWildcardQuery(String field, String termStr)
      throws ParseException {
    throw new ParseException("Wildcard not allowed");
  }

  protected Query getFuzzyQuery(String field,
         String term, float minSimilarity) throws ParseException {
    throw new ParseException("Fuzzy queries not allowed");
  }
}
