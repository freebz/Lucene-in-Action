// 예제 4.12  PositionalPorterStopAnalyzer: 불용어를 제거하고 기본형을 찾아내는 분석기

public class PositionalPorterStopAnalyzer extends Analyzer {
  private Set stopWords;

  public PositionalPorterStopAnalyzer() {
    this(StopAnalyer.ENGLISH_STOP_WORDS_SET);
  }

  public PositionalPorterStopAnalyzer(Set stopWords) {
    this.stopWords = stopWords;
  }

  public TokenStream tokenStream(String fieldName, Reader reader) {
    StopFilter stopFilter = new StopFilter(true,
					 new LowerCaseTokenizer(reader),
					 stopWords);
    stopFilter.setEnablePositionIncrements(true);
    return new PorterStemFilter(stopFilter);
  }
}
