// 예제 5.22  새로 추가한 책과 기존 분류의 대표 벡터 간의 각도 계산

private double computeAngle(String[] words, String category) {

  Map vectorMap = (Map) categoryMap.get(category);

  int dotProduct = 0;
  int sumOfSquares = 0;
  for (String word : words) {
    int categoryWordFreq = 0;

    if (vectorMap.containsKey(word)) {
      categoryWordFreq =
	   ((Integer) vectorMap.get(word)).intValue();
    }

    dotProduct += categoryWordFreq;
    sumOfSquares += categoryWordFreq * categoryWordFreq;
  }

  double denominator;
  if (sumOfSquares == words.length) {
    denominator = sumOfSquares;
  } else {
    denominator = Math.sqrt(sumOfSquares) *
           Math.sqrt(words.length);
  }

  double ratio = dotProduct / denominator;

  return Math.acos(ratio);
}
