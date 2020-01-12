// 예제 2.2  색인에서 문서 삭제

public void testDeleteBeforeOptimize() throws IOException {
    IndexWriter writer = getWriter();
    assertEquals(2, writer.numDocs());
    writer.deleteDocuments(new Term("id", "1"));
    writer.commit();
    assertTrue(writer.hasDeletions());
    assertEquals(2, writer.maxDoc());
    assertEquals(1, writer.numDocs());
    writer.close();
}

public void testDeleteAfterOptimize() throws IOException {
    IndexWriter writer = getWriter();
    assertEquals(2, writer.numDocs());
    writer.deleteDocuments(new Term("id", "1"));
    writer.optimize();
    writer.commit();
    assertFalse(writer.hasDeletions());
    assertEquals(1, writer.maxDoc());
    assertEquals(1, writer.numDocs());
    writer.close();
}
