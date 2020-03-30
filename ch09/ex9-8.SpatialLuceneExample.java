// 예제 9.8  지리적인 위치 기반으로 검색하기 위한 색인 작업

public class SpatialLuceneExample {

  String latField = "lat";
  String lngField = "lon";
  String tierPrefix = "_localTier";

  private Directory directory;
  private IndexWriter writer;

  SpatialLuceneExample() throws IOException {
    directory = new RAMDirectory();
    writer = new IndexWriter(directory, new WhitespaceAnalyzer(),
	   MaxFieldLength.UNLIMITED);
  }

  private void addLocation(IndexWriter writer, String name, double lat,
	   double lng) throws IOException {

    Document doc = new Document();
    doc.add(new Field("name", name, Field.Store.YES,
	    Field.Index.ANALYZED));

    doc.add(new Field(latField,
	   NumericUtils.doubleToPrefixCoded(lat),
	   Field.Store.YES, Field.Index.NOT_ANALYZED));
    doc.add(new Field(lngField,
	   NumericUtils.doubleToPrefixCoded(lng),
	   Field.Store.YES, Field.Index.NOT_ANALYZED));

    doc.add(new Field("metafile", "doc", Field.Store.YES,
	    Field.Index.ANALYZED));

    IProjector projector = new SinusoidalProjector();

    int startTier = 5;
    int endTier = 15;

    for (; startTier <= endTier; startTier++) {
      CartesianTierPlotter ctp;
      ctp = new CartesianTierPlotter(startTier,
		  projector, tierPrefix);
      double boxId = ctp.getTierBoxId(lat, lng);
      System.out.println("Adding field " + ctp.getTierFieldName()
	     + ":" + boxId);
      doc.add(new Field(ctp.getTierFieldName(), NumericUtils
	     .doubleToPrefixCoded(boxId), Field.Store.YES,
	     Field.Index.NOT_ANALYZED_NO_NORMS));
    }

    writer.addDocument(doc);
    System.out.println("===== Added Doc to index =====");
  }
}
