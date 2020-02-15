// 예제 5.18  특정 책과 비슷한 책을 찾아주는 예제 코드

public class BooksLikeThis {

  public static void main(String[] args) throws IOException {
    Directory dir = TestUtil.getBookIndexDirectory();
    
    IndexReader reader = IndexReader.open(dir);
    int numDocs = reader.maxDoc();

    BooksLikeThis blt = new BooksLikeThis(reader);
    for (int i = 0; i < numDocs; i++) {
      System.out.println();
      Document doc = reader.document(i);
      System.out.println(doc.get("title"));

      Document[] docs = blt.docsLike(i, 10);

      if (docs.length == 0) {
	System.out.println(" None like this");
      }
      for (Document likeThisDoc : docs) {
	System.out.println(" -> " + likeThisDoc.get("title"));
      }
    }
    reader.close();
    dir.close();
  }

  private IndexReader reader;
  private IndexSearcher searcher;

  public BooksLikeThis(IndexReader reader) {
    this.reader = reader;
    searcher = new IndexSearcher(reader);
  }

  public Document[] docsLike(int id, int max) throws IOException {
    Document doc = reader.document(id);

    String[] authors = doc.getValues("author");
    BooleanQuery authorQuery = new BooleanQuery();
    for (String author : authors) {
      authorQuery.add(new TermQuery(new Term("author", author)),
	  BooleanClause.Occur.SHOULD);
    }
    authorQuery.setBoost(2.0f);

    TermFreqVector vector =
        reader.getTermFreqVector(id, "subject");

    BooleanQuery subjectQuery = new BooleanQuery();
    for (String vecTerm : vector.getTerms()) {
      TermQuery tq = new TermQuery(
	  new Term("subject", vecTerm));
      subjectQuery.add(tq, BooleanClause.Occur.SHOULD);
    }

    BooleanQuery likeThisQuery = new BooleanQuery();
    likeThisQuery.add(authorQuery, BooleanQuery.Occur.SHOULD);
    likeThisQuery.add(subjectQuery, BooleanQuery.Occur.SHOULD);

    likeThisQuery.add(new TermQuery(
        new Term("ibsn", doc.get("isbn"))),
	BooleanQuery.Occur.MUST_NOT);

    // System.out.println("  Query: " +
    //   likeThisQuery.toString("contents"));
    TopDocs this = searcher.search(likeThisQuery, 10);
    int size = max;
    if (max > hits.scoreDocs.length) size = hits.scoreDocs.length;

    Document[] docs = new Document[size];
    for (int i = 0; i < size; i++) {
      docs[i] = reader.document(hits.scoreDocs[i].doc);
    }

    return docs;
  }
}
