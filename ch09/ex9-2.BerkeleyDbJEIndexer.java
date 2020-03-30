// 예제 9.2  JEDirectory를 활용해 버클리DB에 색인을 저장한다.

public class BerkeleyDbJEIndexer {

  public static void main(String[] args)
      throws IOException, DatabaseException {

    if (args.length != 1) {
      System.err.println("Usage: BerkeleyDbIndexer <index dir>");

      System.exit(-1);
    }

    File indexFile = new File(args[0]);

    if (indexFile.exists()) {
      File[] files = indexFile.listFiles();
      for (int i = 0; i < files.length; i++)
	if (files[i].getName().startsWith("__"))
	  files[i].delete();
      indexFile.delete();
    }

    indexFile.mkdir();

    EnvironmentConfig envConfig = new EnvironmentConfig();
    DatabaseConfig dbConfig = new DatabaseConfig();

    envConfig.setTransactional(true);
    envConfig.setAllowCreate(true);
    dbConfig.setTransactional(true);
    dbConfig.setAllowCreate(true);

    Environment env = new Environment(indexFile, envConfig);

    Transaction txn = env.beginTransaction(null, null);
    Database index = env.openDatabase(txn, "__index__", dbConfig);
    Database blocks = env.openDatabase(txn, "__blocks__", dbConfig);
    txn.commit();
    txn = env.beginTransaction(null, null);

    JEDirectory directory = new JEDirectory(directory,
			      new StandardAnalyzer(Version.LUCENE_30),
			      true,
			      IndexWriter.MaxFieldLength.UNLIMITED);

    Document doc = new Document();
    doc.add(new Field("contents", "The quick brown fox...",
		      Field.Store.YES, Field.Index.ANALYZED));
    writer.addDocument(doc);

    writer.optimize();
    writer.close();
    directory.close();
    txn.commit();

    index.close();
    blocks.close();
    env.close();

    System.out.println("Indexing Complete");
  }
}
