// 예제 9.3  워드넷 유의어 색인에서 유의어 찾아오기

public static void main(String[] args) throws IOException {
  if (args.length != 2) {
    System.out.println(
        "java org.apache.lucene.wordnet.SynLookup <index path> <word>");
  }

  FSDirectory directory = FSDirectory.open(new File(args[0]));
  IndexSearcher searcher = new IndexSearcher(directory, true);

  String word = args[1];
  Query query = new TermQuery(new Term(Syns2Index.F_WORD, word));
  CountingCollector countingCollector = new CountingCollector();
  searcher.search(query, countingCollector);

  if (countingCollector.numHits == 0) {
    System.out.println("No synonyms found for " + word);
  } else {
    System.out.println("Synonyms found for \"" + word + "\":");
  }

  ScoreDoc[] hits = searcher.search(query,
      countingCollector.numHits).scoreDocs;

  for (int i = 0; i < hits.length; i++) {
    Document doc = searcher.doc(hits[i].doc);

    String[] values = doc.getValues(Syns2Index.F_SYN);

    for (int j = 0; j < values.length; j++) {
      System.out.println(values[j]);
    }
  }

  searcher.close();
  directory.close();
}
