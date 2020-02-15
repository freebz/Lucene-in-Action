// 예제 5.19  분류별 대표 벡터 생성

private void buildCategoryVectors() throws IOException {
  IndexReader reader = IndexReader.open(TestUtil.getBookIndexDirectory());

  int maxDoc = reader.maxDoc();

  for (int i = 0; i < maxDoc; i++) {
    if (!reader.isDeleted(i)) {
      Document doc = reader.document(i);
      String category = doc.get("category");

      Map vectorMap = (Map) categoryMap.get(category);
      if (vectorMap == null) {
	vectorMap = new TreeMap();
	categoryMap.put(category, vectorMap);
      }

      TermFreqVector termFreqVector =
 	   reader.getTermFreqVector(i, "subject");

      addTermFreqToMap(vectorMap, termFreqVector);
    }
  }
}
