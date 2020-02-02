// 예제 4.14  ChineseDemo: 중국어 원문 분석 예제

public class ChineseDemo {
  private static String[] strings = {"道德經"};

  private static Analyzer[] analyzers = {
    new SimpleAnalyzer(),
    new StandardAnalyzer(Version.LUCENE_30),
    new ChineseAnalyzer(),
    new CJKAnalyzer(Version.LUCENE_30),
    new SmartChineseAnalyzer(Version.LUCENE_30)
  };

  public static void main(String args[]) throws Exception {

    for (String string : strings) {
      for (Analyzer analyzer : analyzers) {
	analyze(string, analyzer);
      }
    }
  }

  private static void analyze(String string, Analyzer analyzer)
       throws IOException {
    StringBuffer buffer = new StringBuffer();

    TokenStream stream = analyzer.tokenStream("contents",
					      new StringReader(string));
    TermAttribute term = stream.addAttribute(TermAttribute.class);

    while(stream.incrementToken()) {
      buffer.append("[");
      buffer.append(term.term());
      buffer.append("] ");
    }

    String output = buffer.toString();

    Frame f = new Frame();
    f.setTitle(analyzer.getClass().getSimpleName() + " : " + string);
    f.setResizable(true);

    Font font = new Font(null, Font.PLAIN, 36);
    int width = getWidth(f.getFontMetrics(font), output);

    f.setSize((width < 250) ? 250 : width + 50, 75);

    // 참고: Label 클래스로 화면에 중국어 글자가 제대로 나타나지 않는다면
    // Label 클래스 대신 javax.swing.JLabel 클래스를 사용해보자.
    Label label = new Label(output);
    label.setSize(width, 75);
    label.setAlignment(Label.CENTER);
    label.setFont(font);
    f.add(label);

    f.setVisible(true);
  }

  private static int getWidth(FontMetrics metrics, String s) {
    int size = 0;
    int length = s.length();
    for (int i = 0; i < length; i++) {
      size += metrics.charWidth(s.charAt(i));
    }

    return size;
  }
}
