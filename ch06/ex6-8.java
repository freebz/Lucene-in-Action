// 예제 6.8  직접 작성한 QueryParser 활용

public void testCustomQueryParser() {
  CustomQueryParser parser =
       new CustomQueryParser(Version.LUCENE_30,
			    "field", analyzer);
  try {
    parser.parse("a?t");
    fail("Wildcard queries should not be allowed");
  } catch (ParseException expected) {
  }

  try {
    parser.parse("xunit~");
    fail("Fuzzy queries should not be allowed");
  } catch (ParseException expected) {
  }
}
