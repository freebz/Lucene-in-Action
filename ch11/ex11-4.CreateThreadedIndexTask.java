// 예제 11.4  성능 측정 모듈에 ThreadedIndexWriter를 사용하는 색인 작업 클래스 추가

public class CreateThreadedIndexTask extends CreateIndexTask {

  public CreateThreadedIndexTask(PerfRunData runData) {
    super(runData);
  }

  public int doLogic() throws IOException {
    PerfRunData runData = getRunData();
    Config config = runData.getConfig();
    IndexWriter writer = new ThreadedIndexWriter(
			 runData.getDirectory(),
			 runData.getAnalyzer(),
			 true,
			 config.get("writer.num.threads", 4),
			 config.get("writer.max.thread.queue.size", 20),
			 IndexWriter.MaxFieldLength.UNLIMITED);
    CreateIndexTask.setIndexWriterConfig(writer, config);
    runData.setIndexWriter(writer);
    return 1;
  }
}
