// 예제 7.1  문서의 종류에 상관없이 텍스트를 추출해 루씬으로 색인하는 코드

public class TikaIndexer extends Indexer {

  private boolean DEBUG = false;

  static Set<String> textualMetadataFields
      = new HashSet<String>();
  static {
    textualMetadataFields.add(Metadata.TITLE);
    textualMetadataFields.add(Metadata.AUTHOR);
    textualMetadataFields.add(Metadata.COMMENTS);
    textualMetadataFields.add(Metadata.KEYWORDS);
    textualMetadataFields.add(Metadata.DESCRIPTION);
    textualMetadataFields.add(Metadata.SUBJECT);
  }

  public static void main(String[] args) thorws Exception {
    if (args.length != 2) {
      throw new IllegalArgumentException("Usage: java " +
	   TikaIndexer.class.getName()
	   + " <index dir> <data dir>");
    }

    TikaConfig config = TikaConfig.getDefaultConfig();
    List<String> parsers = new ArrayList<String>(config.getParsers()
				     .keySet());
    Collections.sort(parsers);
    Iterator<String> it = parsers.iterator();
    System.out.println("Mime type parsers:");
    while(it.hasNext()) {
      System.out.println(" " + it.next());
    }
    System.out.println();

    String indexDir = args[0];
    String dataDir = args[1];

    long start = new Date().getTime();
    TikaIndexer indexer = new TikaIndexer(indexDir);
    int numIndexed = indexer.index(dataDir, null);
    indexer.close();
    long end = new Date().getTime();

    System.out.println("Indexing " + numIndexed + " files took "
        + (end - start) + " milliseconds");
  }

  public TikaIndexer(String indexDir) throws IOException {
    super(indexDir);
  }

  protected Document getDocument(File f) throws Exception {

    Metadata metadata = new Metadata();
    metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());

    // 문서를 HTTP 서버에서 받아왔기 때문에 HTTP 헤더에
    // 담겨있는 문서의 종류를 Metadata.CONTENT_TYPE
    // 값으로 지정한다.

    // 뿐만 아니라 HTTP 헤더를 통해 문서의 글자 인코딩도
    // 알아낼 수 있다면 글자 인코딩은 Metadata.CONTENT_ENCODING
    // 값으로 지정하자.

    InputStream is = new FileInputStream(f);
    Parser parser = new AutoDetectParser();
    Conotenthandler handler = new BodyContentHandler();
    ParseContext context = new ParseContext();
    context.set(Parser.class, parser);

    try {
      parser.parse(is, handler, matadata,
		   new ParseContext());
    } finally {
      is.close();
    }

    Document doc = new Document();
    doc.add(new Field("contents", handler.toString(),
	        Field.Store.NO, Filed.Index.ANALYZED));
    
    if (DEBUG) {
      System.out.println("  all text: " + handler.toString());
    }

    for(String name : metadata.names()) {
      String value = metadata.get(name);

      if (textualMetadataFields.contains(name)) {
	doc.add(new Field("contents", value,
	       Field.Store.NO, Field.Index.ANALYZED));
      }

      doc.add(new Field(name, value,
	  Field.Store.YES, Field.Index.NO));

      if (DEBUG) {
	System.out.println("  " + name + ": " + value);
      }
    }

    if (DEBUG) {
      System.out.println();
    }

    doc.add(new Field("filename", f.getCanonicalPath(),
        Field.Store.YES, Field.Index.NOT_ANALYZED));

    return doc;
  }
}
