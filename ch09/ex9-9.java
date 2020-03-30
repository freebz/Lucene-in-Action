// 예제 9.9  위치 정보를 사용한 필터링과 정렬

public void findNear(String what, double latitude, double longitude,
	      double radius) throws CorruptIndexException, IOException {
  IndexSearcher searcher = new IndexSearcher(directory);

  DistanceQueryBuilder dq;
  dq = new DistanceQueryBuilder(latitude,
				longitude,
				radius,
				latField,
				lngField,
				tierPrefix,
				true);

  Query tq;
  if (what == null)
    tq = new TermQuery(new Term("metafile", "doc"));
  else
    tq = new TermQuery(new Term("name", what));

  DistanceFieldComparatorSource dsort;
  dsort = new DistanceFieldComparatorSource(
	        dq.getDistanceFilter());
  Sort sort = new Sort(new SortField("foo", dsort));

  TopDocs hits = searcher.search(tq, dq.getFilter(), 10, sort);

  Map<Integer,Double> distances =
           dq.getDistanceFilter().getDistances();

  System.out.println("Number of results: " + this.totalHits);
  System.out.println("Found:");
  for (ScoreDoc sd : hits.scoreDocs) {
    int docID = sd.doc;
    Document d = searcher.doc(docID);

    String name = d.get("name");
    double rsLat = NumericUtils.prefixCodedToDouble(d.get(latField));
    double rsLng = NumericUtils.prefixCodedToDouble(d.get(lngField));
    Double geo_distance = distances.get(docID);

    System.out.printf(name +": %.2f Miles\n", geo_distance);
    System.out.println("\t\t("+ rsLat +","+ rsLng +")");
  }
}
