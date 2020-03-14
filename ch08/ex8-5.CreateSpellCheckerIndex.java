// 예제 8.5  검색어 추천 색인 생성

public class CreateSpellCheckerIndex {

  public static void main(String[] args) throws IOException {

    if (args.length != 3) {
      System.out.println("Usage: java lia.tools.SpellCheckerTest " +
			 "SpellCheckerIndexDir IndexDir IndexField");
      System.exit(1);
    }

    String spellCheckDir = args[0];
    String indexDir = args[1];
    String indexField = args[2];

    System.out.println("Now build SpellChecker index...");
    Directory dir = FSDirectory.open(new File(spellCheckDir));
    SpellChecker spell = new SpellChecker(dir);
    long startTime = System.currentTimeMillis();

    Directory dir2 = FSDirectory.open(new File(indexDir));
    IndexReader r = IndexReader.open(dir2);
    try {
      spell.indexDictionary(new LuceneDictionary(r, indexField));
    } finally {
      r.close();
    }
    dir.close();
    dir2.close();
    long endTime = System.currentTimeMillis();
    System.out.println("  took " + (endTime-startTime) + " milliseconds");
  }
}
