// 예제 5.21  가장 근접한 벡터를 찾아 분류를 결정

private String getCategory(String subject) {
  String[] words = subject.split(" ");

  Iterator categoryIterator = categoryMap.keySet().iterator();
  double bestAngle = Double.MAX_VALUE;
  String bestCategory = null;

  while (categoryIterator.hasNext()) {
    String category = (String) categoryIterator.next();
    //   System.out.println(category);

    double angle = computeAngle(words, category);
    //   System.out.println(" -> angle = " + angle + " (" +
    //         Math.toDegrees(angle) + ")");
    if (angle < bestAngle) {
      bestAngle = angle;
      bestCategory = category;
    }
  }

  return bestCategory;
}
