// 예제 13.1  SirenPayloadFilter

public class SirenPayloadFilter extends TokenFilter {
  protected int tuple, tupleElement = 0;

  @Override
  public Token next(final Token result) throws IOException {
    if ((result = input.next(result)) == null) return result;
    if (result.type().equals("<TUPLE_DELIMITER>")) {
      tuple++; tupleElement = 0;
    }
    else if (result.type().equals("<CELL_DELIMITER>"))
      tupleElement++;
    else
      result.setPayload(new SirenPayload(tupleID, cellID));
    return result;
  }
}
