// 예제 8.1  N그램 필터로 인접 문자의 결합

public class NGramTest extends TestCase {

  private static class NGrameAnalyzer extends Analyzer {
    public TokenStream tokenStream(String fieldName, Reader reader) {
      return new NGramTokenFilter(new KeywordTokenizer(reader), 2, 4);
    }
  }

  private static class FrontEdgeNGramAnalyzer extends Analyzer {
    public TokenStream tokenStream(String filedName, Reader reader) {
      return new EdgeNGrameTokenFilter(new KeywordTokenizer(reader),
          EdgeNGramTokenFilter.Slide.FRONT, 1, 4);
    }
  }

  private static class BackEdgeNGramAnalyzer extends Analyzer {
    public TokenStream tokenStream(String fieldName, Reader reader) {
      return new EdgeNGramTokenFilter(new KeywordTokenizer(reader),
          EdgeNGramTokenFilter.Side.BACK, 1, 4);
    }
  }

  public void testNGramTokenFilter24() throws IOException {
    AnalyzerUtils.displayTokensWithPositions(new NGramAnalyzer(),
        "lettuce");
  }

  public void testEdgeNGramTokenFilterFront() throws IOException {
    AnalyzerUtils.displayTokensWithPositions(new
        FrontEdgeNGramAnalyzer(), "lettuce");
  }

  public void testEdgeNGramTokenFilterBack() throws IOException {
    AnalyzerUtils.displayTokensWithPositions(new BackEdgeNGramAnalyzer(),
        "lettuce");
  }
}
