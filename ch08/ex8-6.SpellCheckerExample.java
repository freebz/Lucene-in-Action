// 예제 8.6  검색어 추천 색인을 바탕으로 추천 단어 후보 목록을 뽑아낸다.

public class SpellCheckerExample {

  public static void main(String[] args) throws IOException {

    if (args.length != 2) {
      System.out.println("Usage: java lia.tools.SpellCheckerTest " +
			 "SpellCheckerIndexDir wordToRespell");
      System.exit(1);
    }

    String spellCheckDir = args[0];
    String wordToRespell = args[1];

    Directory dir = FSDirectory.open(new File(spellCheckDir));
    if (!IndexReader.indexExists(dir)) {
      System.out.println("\nERROR: No spellchecker index at path \"" +
		    spellCheckDir +
		    "\"; please run CreateSpellCheckerIndex first\n");
      System.exit(1);
    }
    SpellChecker spell = new SpellChecker(dir);

    spell.setStringDistance(new LevensteinDistance());
    //spell.setStringDistance(new JaroWinklerDistance());

    String[] suggestions = spell.suggestSimilar(
				wordToRespell, 5);
    System.out.println(suggestions.length + " suggestions for '" +
	   wordToRespell + "':");
    for (String suggestion : suggestions)
      System.out.println(" " + suggestion);
  }
}
