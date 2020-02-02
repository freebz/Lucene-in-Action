// 예제 4.4  발음이 비슷한 단어 검색

public void testKoolKat() throws Exception {
  RAMDirectory directory = new RAMDirectory();
  Analyzer analyzer = new MetaphoneReplacementAnalyzer();

  IndexWriter writer = new IndexWriter(directory, analyzer, true,
		            IndexWriter.MaxFieldLength.UNLIMITED);

  Document doc = new Document();
  doc.add(new Field("contents",
		    "cool cat",
		    Field.Store.YES,
		    Field.Index.ANALYZED));
  writer.addDocument(doc);
  writer.close();

  IndexSearcher searcher = new IndexSearcher(directory);

  Query query = new QueryParser(Version.LUCENE_30,
				"contents", analyzer)
                                .parse("kool kat");

  TopDocs hits = searcher.search(query, 1);
  assertEquals(1, hits.totalHits);
  int docID = hits.scoreDocs[0].doc;
  doc = searcher.doc(docID);
  assertEquals("cool cat", doc.get("contents"));

  searcher.close();
}
