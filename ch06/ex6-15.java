// 예제 6.15  FilteredQuery 활용

public void testFilteredQuery() throws Exception {
  String[] isbns = new String[] {"9780880105118"};

  SpecialsAccessor accessor = new TestSpecialsAccessor(isbns);
  Filter filter = new SpecialsFilter(accessor);

  WildcardQuery educationBooks =
       new WildcardQuery(new Term("category", "education*"));
  FilteredQuery edBooksOnSpecial =
       new FilteredQuery(educationBooks, filter);

  TermQuery logoBooks =
       new TermQuery(new Term("subject", "logo"));

  BooleanQuery logoOrEdBooks = new BooleanQuery();
  logoOrEdBooks.add(logoBooks, BooleanClause.Occur.SHOULD);
  logoOrEdBooks.add(edBooksOnSpecial, BooleanClause.Occur.SHOULD);

  TopDocs hits = searcher.search(logoOrEdBooks, 10);
  System.out.println(logoOrEdBooks.toString());
  assertEquals("Papert and Steiner", 2, hits.totalHits);
}
