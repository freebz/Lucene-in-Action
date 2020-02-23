// 예제 6.3  정렬할 때 계산한 값 확보

public void testNeareastRestaurantToWork() throws Exception {
  Sort sort = new Sort(new SortField("unused",
       new DistanceComparatorSource(10, 10)));

  TopFieldDocs docs = searcher.search(query, null, 3, sort);

  assertEquals(4, docs.totalHits);
  assertEquals(3, docs.scoreDocs.length);

  FieldDoc fieldDoc = (FieldDoc) docs.scoreDocs[0];

  assertEquals("(10,10) -> (9,6) = sqrt(17)",
      new Float(Math.sqrt(17)),
      fieldDoc.fields[0]);

  Document document = searcher.doc(fieldDoc.doc);
  assertEquals("Los Betos", document.get("name"));

  //dumpDocs(sort, docs);
}
