// 예제 6.5  BookLinkCollector 테스트

public void testCollecting() throws Exception {
  Directory dir = TestUtil.getBookIndexDirectory();
  TermQuery query = new TermQuery(new Term("contents", "junit"));
  IndexSearcher searcher = new IndexSearcher(dir);

  BookLinkCollector collector = new BookLinkCollector();
  searcher.search(query, collector);

  Map<String,String> linkMap = collector.getLinks();
  assertEquals("ant in action",
	       linkMap.get("http://www.manning.com/loughran"));

  TopDocs hits = searcher.search(query, 10);
  TestUtil.dumpHits(searcher, hits);

  searcher.close();
  dir.close();
}
