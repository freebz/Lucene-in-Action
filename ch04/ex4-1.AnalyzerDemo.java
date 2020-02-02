// 예제 4.1  AnalyzerDemo: 분석 과정의 결과물 확인

public class AnalyzerDemo {
  private static final String[] examples = {
    "The quick brown fox jumped over the lazy dog",
    "XY&Z Corporation - xyz@example.com"
  };

  private static final Analyzer[] analyzers = new Analyzer[] {
    new WhitespaceAnalyzer(),
    new SimpleAnalyzer(),
    new StopAnalyzer(Version.LUCENE_30),
    new StandardAnalyzer(Version.LUCENE_30)
  };

  public static void main(String[] args) throws IOException {

    String[] strings = examples;
    if (args.length > 0) {
      strings = args;
    }

    for (String text : strings) {
      analyze(text);
    }
  }

  private static void analyze(String text) throws IOException {
    System.out.println("Analyzing \"" + text + "\"");
    for (Analyzer analyzer : analyzers) {
      String name = analyzer.getClass().getSimpleName();
      System.out.println(" " + name + ":");
      System.out.print("   ");
      AnalyzerUtils.displayTokens(analyzer, text);
      System.out.println("\n");
    }
  }
}
