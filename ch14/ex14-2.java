// 예제 14.2  조이 시스템에서 모든 문서 관련 작업은 색인 요청을 거쳐 처리한다.

class DataIndexable implements ZoieIndexable {
  private Data _data;
  public DataIndexable(Data data) {
    _data = data;
  }

  public long getUID() {
    return _data.id;
  }

  public IndexingReq[] buildIndexingReqs() {
    Document doc = new Document();
    doc.add(new Field("content",
		      _data.content,
		      Store.NO,
		      Index.ANALYZED));
    return new IndexingReq[]{new IndexingReq(doc)};
  }

  public boolean isDeleted() {
    return
      "_MARKED_FOR_DELETE".equals(_data.content);
  }

  public boolean isSkip() {
    return "_MARKED_FOR_SKIP".equals(_data.content);
  }
}

class DataIndexableInterpreter implements ZoieIndexableInterpreter<Data> {
  public ZoieIndexable interpret(Data src) {
    return new DataIndexable(src);
  }
}
