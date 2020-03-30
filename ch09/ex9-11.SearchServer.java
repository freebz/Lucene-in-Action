// 예제 9.11  RMI를 활용하는 원격 검색 서버 SearchServer

public class SearchServer {
  private static final String ALPHABET =
    "abcdefghijklmnopqrstuvwxyz";

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: SearchServer <basedir>");
      System.exit(-1);
    }

    String basedir = args[0];
    Directory[] dirs = new Directory[ALPHABET.length()];
    Searchable[] searchables = new Searchable[ALPHABET.length()];
    for (int i = 0; i < ALPHABET.length(); i++) {
      dirs[i] = FSDirectory.open(new File(basedir,
	  ""+ALPHABET.charAt(i)));
      searchables[i] = new IndexSearcher(
      dirs[i]);
    }

    LocateRegistry.createRegistry(1099);

    Searcher multiSearcher = new MultiSearcher(searchables);
    RemoteSearchable multiImpl =
         new RemoteSearchable(multiSearcher);
    Naming.rebind("//localhost/LIA_Multi", multiImpl);

    Searcher parallelSearcher =
         new ParallelMultiSearcher(searchables);
    RemoteSearchable parallelImpl =
         new RemoteSearchable(parallelSearcher);
    Naming.rebind("//localhost/LIA_Parallel", parallelImpl);

    System.out.println("Server started");

    for (int i = 0; i < ALPHABET.length(); i++) {
      dirs[i].close();
    }
  }
}
