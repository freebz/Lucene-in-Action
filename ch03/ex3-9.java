// 예제 3.9  WildcardQuery

private void indexSingleFieldDocs(Field[] fields) throws Exception {
  IndexWriter writer = new IndexWriter(directory,
       new WhitespaceAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
  for (Field f : fields) {
    Document doc = new Document();
    doc.add(f);
    writer.addDocument(doc);
  }
  writer.optimize();
  writer.close();
}

public void testWildcard() throws Exception {
  indexSingleFieldDocs(new Field[] {
      new Field("contents", "wild", Field.Store.YES,
	     Field.Index.ANALYZED),
      new Field("contents", "child", Field.Store.YES,
	     Field.Index.ANALYZED),
      new Field("contents", "mild", Field.Store.YES,
	     Field.Index.ANALYZED),
      new Field("contents", "mildew", Field.Store.YES,
	     Field.Index.ANALYZED) });

  IndexSearcher searcher = new IndexSearcher(directory);
  Query query = new WildcardQuery(new Term("contents", "?ild*"));
  TopDocs matches = searcher.search(query, 10);

  assertEquals("child no match", 3, matches.totalHits);

  assertEquals("score the same", matches.scoreDocs[0].score,
	       matches.scoreDocs[1].score, 0.0);
  assertEquals("score the same", matches.scoreDocs[1].score,
	       matches.scoreDocs[2].score, 0.0);
  searcher.close();
}
