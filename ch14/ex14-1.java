// 예제 14.1  조이에 의한 인덱싱 데이터 이벤트

class Data {
  long id;
  String content;
}

class NoNullDataConsumer implements
    DataConsumer<Data>, DataProvider {
  private DataConsumer<Data> _subConsumer;
  NoNullDataConsumer(DataConsumer<Data> subConsumer) {
    _subConsumer = subConsumer;
  }

  public void consume(Collection<DataEvent<Data>> data) throws ZoieException
  {
    List<DataEvent<Data>> events = new LinkedList<DataEvent<Data>>();
    for (DataEvent<Data> evt : data) {
      if (evt.content != null) {
	events.add(evt);
      }
    }
    _subConsumer.consume(events);
  }
}

ZoieSystem indexingSystem ... ;
indexingSystem.start();

ZoieSystemAdminMBean adminMBean =
     indexingSystem.getAdminMBean();
long lastVersion = adminMBean.getCurrentDiskVersion();

JDBCDataProvier dataProvider = new JDBCDataProvier(
     "SELECT id,content,tmstmp FROM newstable WHERE tmstmp >= "+lastVersion);
Collection<DataEvent<Data>> indexingRequests =
     dataProvider.getIndexingRequests();

NoNullDataConsumer consumer = new NoNullDataConsumer(indexingSystem);
consumer.consume(indexingSystem);
