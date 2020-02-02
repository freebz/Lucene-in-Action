// 예제 4.8  SynonymFilter: 유사어를 찾아 버퍼에 담아두고 하나씩 알려준다.

public class SynonymFilter extends TokenFilter {
  public static final String TOKEN_TYPE_SYNONYM = "SYNONYM";

  private Stack<String> synonymStack;
  private SynonymEngine engine;
  private AttributeSource.State current;

  private final TermAttribute termAtt;
  private final PositionIncrementAttribute posIncrAtt;

  public SynonymFilter(TokenStream in, SynonymEngine engine) {
    super(in);
    synonymStack = new Stack<String>();
    this.engine = engine;

    this.termAtt = addAttribute(TermAttribute.class);
    this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);
  }

  public boolean incrementToken() throws IOException {
    if (synonymStack.size() > 0) {
      String syn = synonymStack.pop();
      restoreState(current);
      termAtt.setTermBuffer(syn);
      posIncrAtt.setPositionIncrement(0);
      return true;
    }

    if (!input.incrementToken())
      return false;

    if (addAliasesToStack()) {
      current = captureState();
    }

    return true;
  }

  private boolean addAliasesToStack() throws IOException {
    String[] synonyms = engine.getSynonyms(termAtt.term());
    if (synonyms == null) {
      return false;
    }
    for (String synonym : synonyms) {
      synonymStack.push(synonym);
    }
    return true;
  }
}
