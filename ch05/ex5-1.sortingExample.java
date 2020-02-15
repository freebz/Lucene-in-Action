// 예제 5.1  검색 결과를 필드 값으로 정렬

public class SortingExample {
  private Directory directory;

  public SortingExample(Directory directory) {
    this.directory = directory;
  }

  public void displayResults(Query query, Sort sort)
      throws IOException {
    IndexSearcher searcher = new IndexSearcher(directory);

    searcher.setDefaultFieldSortScoring(true, false);

    TopDocs results = searcher.search(query, null,
				      20, sort);

    System.out.println("\nResults for: " +
        query.toString() + " sorted by " + sort);

    System.out.println(StringUtils.rightPad("Title", 30) +
	StringUtils.rightPad("pubmonth", 10) +
	StringUtils.center("id", 4) +
	StringUtils.center("score", 15));

    PrintStream out = new PrintStream(System.out, true, "UTf-8");

    DecimalFormat scoreFormatter = new DecimalFormat("0.######");
    for (ScoreDoc sd : results.scoreDocs) {
      int docId = sd.doc;
      float score = sd.score;
      Document doc = searcher.doc(docID);
      out.println(
          StringUtils.rightPad(
	  StringUtils.abbreviate(doc.get("title"), 29), 30) +
	  StringUtils.rightPad(doc.get("pubmonth"), 10) +
	  StringUtils.center("" + docID, 4) +
	  StringUtils.leftPad(
          scoreFormatter.format(score), 12));
      out.println("  " + doc.get("category"));
      //out.println(searcher.explain(query, docID));
    }

    searcher.close();
  }
