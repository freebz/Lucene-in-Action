// 예제 9.10  Spatial Lucene을 사용해 가장 가까운 식당을 검색하는 코드

public static void main(String[] args) throws IOException {
  SpatialLuceneExample spatial = new SpatialLuceneExample();
  spatial.addData();
  spatial.findNear("Restaurant", 38.8725000, -77.3829000, 8);
}

private void addData() throws IOException {
  addLocation(writer, "McCormick & Schmick's Seafood Restaurant",
	      38.9579000, -77.3572000);
  addLocation(writer, "Jimmy's Old Town Tavern", 38.9690000, -77.3862000);
  addLocation(writer, "Ned Devine's", 38.9510000, -77.4107000);
  addLocation(writer, "Old Brogue Irish Pub", 38.9955000, -77.2884000);
  addLocation(writer, "Alf Laylah Wa Laylah", 38.8956000, -77.4258000);
  addLocation(writer, "Sully's Restaurant & Supper", 38.9003000, -77.4467000);
  addLocation(writer, "TGIFriday", 38.8725000, -77.3829000);
  addLocation(writer, "Potomac Swing Dance Club", 38.9027000, -77.2639000);
  addLocation(writer, "White Tiger Restaurant", 38.9027000, -77.2638000);
  addLocation(writer, "Jammin' Java", 38.9039000, -77.2622000);
  addLocation(writer, "Potomac Swing Dance Club", 38.9027000, -77.2639000);
  addLocation(writer, "WiseAcres Comedy Club", 38.9248000, -77.2344000);
  addLocation(writer, "Glen Echo Spanish Ballroom", 38.9691000, -77.1400000);
  addLocation(writer, "Whitlow's on Wilson", 38.8889000, -77.0926000);
  addLocation(writer, "Iota Club and Cafe", 38.8890000, -77.0923000);
  addLocation(writer, "Hilton Washington Embassy Row", 38.9103000,
	      -77.0451000);
  addLocation(writer, "HorseFeathers, Bar & Grill", 39.01220000000001,
	      -77.3942);
  writer.close();
}
