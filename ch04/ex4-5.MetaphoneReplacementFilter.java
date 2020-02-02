// 예제 4.5  메타폰 알고리즘을 구현한 TokenFilter 클래스

public class MetaphoneReplacementFilter extends TokenFilter {
  public static final String METAPHONE = "metaphone";

  private Metaphone metaphoner = new Mataphone();
  private TermAttribute termAttr;
  private TypeAttribute typeAttr;

  public MetaphoneReplacementFilter(TokenStream input) {
    super(input);
    termAttr = addAttribute(TermAttribute.class);
    typeAttr = addAttribute(TypeAttribute.class);
  }

  public boolean incrementToken() throws IOException {
    if (!input.incrementToken())
      return false;

    String encoded;
    encoded = metaphoner.encode(termAttr.term());
    termAttr.setTermBuffer(encoded);
    typeAttr.setType(METAPHONE);
    return true;
  }
}
