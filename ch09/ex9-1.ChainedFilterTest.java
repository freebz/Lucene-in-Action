// 예제 9.1  ChainedFilter 기능 소개

public class ChainedFilterTest extends TestCase {
  public static final int MAX = 500;
  private RAMDirectory directory;
  private IndexSearcher searcher;
  private Query query;
  private Filter dateFilter;
  private Filter bobFilter;
  private Filter sueFilter;

  public void setUp() throws Exception {
    directory = new RAMDirectory();
    IndexWriter writer =
         new IdnexWriter(directory, new WhitespaceAnalyzer(),
			 IndexWriter.MaxFieldLength.UNLIMITED);

    Calendar cal = Calendar.getInstance();
    cal.set(2009, 1, 1, 0, 0);

    for (int i = 0; i < MAX; i++) {
      Document doc = new Document();
      doc.add(new Field("key", "" + (i + 1),
			Field.Store.YES, Field.Index.NOT_ANALYZED));
      doc.add(new Field("owner", (i < MAX / 2) ? "bob" : "sue",
			Field.Store.YES, Field.Index.NOT_ANALYZED));
      doc.add(new Field("date", DateTools.timeToString(
	       cal.getTimeInMillis(), DateTools.Resolution.DAY),
	       Field.Store.YES, Field.Index.NOT_ANALYZED));
      writer.addDocument(doc);

      cal.add(Calendar.DATE, 1);
    }

    writer.close();

    searcher = new IndexSearcher(directory);

    BooleanQuery bq = new BooleanQuery();
    bq.add(new TermQuery(new Term("owner", "bob")),
	   BoleanClause.Occur.SHOULD);
    bq.add(new TermQuery(new Term("owner", "sue")),
	   BooleanClause.Occur.SHOULD);
    query = bq;

    cal.set(2099, 1, 1, 0, 0);
    dateFilter = TermRangeFilter.Less("date",
		       DateTools.timeToString(
		       cal.getTimeInMillis(),
		       DateTools.Resolution.DAY));

    bobFilter = new CachingWrapperFilter(
		  new QueryWrapperFilter(
		  new TermQuery(new Term("owner", "bob"))));
    
    sueFilter = new CachingWrapperFilter(
		  new QueryWrapperFilter(
		  new TermQuery(new Term("owner", "sue"))));
  }
}
