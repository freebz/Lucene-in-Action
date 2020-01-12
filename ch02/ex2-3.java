// 예제 2.3  색인된 문서를 변경

public void testUpdate() throws IOException {

    assertEquals(1, getHitCount("city", "Amsterdam"));

    IndexWriter writer = getWriter();

    Document doc = new Document();
    doc.add(new Field("id", "1",
		       Field.Store.YES,
		       Field.Index.NOT_ANALYZED));
    doc.add(new Field("country", "Netherlands",
		       Field.Store.YES,
		       Field.Index.NO));
    doc.add(new Field("contents",
		       "Den Haag has a lot of museums",
		       Field.Store.NO,
 		       Field.Index.ANALYZED));
    doc.add(new Field("city", "Den Haag",
		       Field.Store.YES,
		       Field.Index.ANALYZED));

    writer.updateDocument(new Term("id", "1"),
			  doc);

    writer.close();

    assertEquals(0, getHitCount("city", "Amsterdam"));
    assertEquals(1, getHitCount("city", "Haag"));

}
