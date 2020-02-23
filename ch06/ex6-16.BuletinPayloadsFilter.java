// 예제 6.16  특보 문서 안에 담긴 warning 텀의 중요도를 적재하는 BulletinPayloadsFilter 클래스

public class BulletinPayloadsFilter extends TokenFilter {

  private TermAttribute termAtt;
  private PayloadAttribute payloadAttr;
  private boolean isBulletin;
  private Payload boostPayload;

  BulletinPayloadsFilter(TokenStream in, float warningBoost) {
    super(in);
    payloadAttr = addAttribute(PayloadAttribute.class);
    termAtt = addAttribute(TermAttribute.class);
    boostPayload = new Payload(PayloadHelper.encodeFloat(warningBoost));
  }

  void setIsBulletin(boolean v) {
    isBulletin = v;
  }

  public final boolean incrementToken() throws IOException {
    if (input.incrementToken()) {
      if (isBulletin && termAtt.term().equals("warning")) {
	payloadAttr.setPayload(boostPayload);
      } else {
	payloadAttr.setPayload(null);
      }
      return true;
    } else {
      return false;
    }
  }
}

public class BulletinPayloadsAnalyzer extends Analyzer {
  private boolean isBulletin;
  private float boost;

  BulletinPayloadsAnalyzer(float boost) {
    this.boost = boost;
  }

  void setIsBulletin(boolean v) {
    isBulletin = v;
  }

  public TokenStream tokenStream(String fieldName, Reader reader) {
    BulletinPayloadsFilter stream = new BulletinPayloadsFilter(
							       new StandardAnalyzer(Version.LUCENE_30)
							       .tokenStream(fieldName,
									    reader), boost);
    stream.setIsBulletin(isBulletin);
    return stream;
  }
}
