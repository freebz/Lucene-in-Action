// 예제 4.7  SynonymAnalyzer 클래스

public class SynonymAnalyzer extends Analyzer {
  private SynonymEngine engine;

  public SynonymAnalyzer(SynonymEngine engine) {
    this.engine = engine;
  }

  public TokenStream tokenStream(String fieldName, Reader reader) {
    TokenStream result = new SynonymFilter(
			   new StopFilter(true,
			     new LowerCaseFilter(
			       new StandardFilter(
				 new StandardTokenizer(
				   Version.LUCENE_30, reader))),
			     StopAnalyzer.ENGLISH_STOP_WORDS_SET),
			   engine
			 );
    return result;
  }
}
