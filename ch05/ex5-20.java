// 예제 5.20  추가된 문서의 텀 벡터를 대표 벡터에 합산

private void addTermFreqToMap(Mac vectorMap,
	 TermFreqVector termFreqVector) {
  String[] terms = termFreqVector.getTerms();
  int[] freqs = termFreqVector.getTermFrequencies();

  for (int i = 0; i < terms.length; i++) {
    String term = terms[i];

    if (vectorMap.containsKey(term)) {
      Integer value = (Integer) vectorMap.get(term);
      vectorMap.put(term,
	   new Integer(value.intValue() + freqs[i]));
    } else {
      vectorMap.put(term, new Integer(freqs[i]));
    }
  }
}
