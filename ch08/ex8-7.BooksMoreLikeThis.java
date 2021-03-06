// 예제 8.7  MoreLikeThis 클래스로 비슷한 문서를 찾는 방법

public class BookMoreLikeThis {
  public static void main(String[] args) throws Throwable {

    String indexDir = System.getProperty("index.dir");
    FSDirectory directory = FSDirectory.open(new Fiile(indexDir));
    IndexReader reader = IndexReader.open(directory);

    IndexSearcher searcher = new IndexSearcher(reader);

    int numDocs = reader.maxDoc();

    MoreLikeThis mlt = new MoreLikeThis(reader);
    mlt.setFieldNames(new String[] {"title", "author"});
    mlt.setMinTermFreq(1);
    mlt.setMinDocFreq(1);

    for (int docID = 0; docID < numDocs; docID++) {
      System.out.println();
      Document doc = reader.document(docID);
      System.out.println(doc.get("title"));

      Query query = mlt.like(docID);
      System.out.println("  query=" + query);

      TopDocs similarDocs = searcher.search(query, 10);
      if (similarDocs.totalHits == 0)
	System.out.println("  None like this");
      for(int i=0;i<similarDocs.scoreDocs.length;i++) {
	if (similarDocs.scoreDocs[i].doc != docID) {
	  doc = reader.document(similarDocs.scoreDocs[i].doc);
	  System.out.println(" ->" + doc.getField("title").stringValue());
	}
      }
    }

    searcher.close();
    reader.close();
    directory.close();
  }
}
