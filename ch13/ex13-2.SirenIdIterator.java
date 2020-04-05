// 예제 13.2  SirenIdIterator 인터페이스

public interface SirenIdIterator {
  public boolean skipTo(int entityID);
  public boolean skipTo(int entityID, int tupleID);
  public boolean skipTo(int entityID, int tupleID, int cellID);

  public int dataset();
  public int entity();
  public int tuple();
  public int cell();
  public int pos();
}
