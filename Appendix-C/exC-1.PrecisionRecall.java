// 예제 C.1  IndexSearcher 인스턴스에 대한 정확도와 재현율 측정

public class PrecisionRecall {

  public static void main(String[] args) throws Throwable {

    File topicsFile = new File("src/lia/benchmark/topics.txt");
    File qrelsFile = new File("src/lia/benchmark/qrels.txt");

    Directory dir = FSDirectory.open(new File("indexes/MeetLucene"));
    Searcher searcher = new IndexSearcher(dir, true);

    String docNameField = "fillname";

    PrintWriter logger = new PrintWriter(System.out, true);

    TrecTopicsReader qReader = new TrecTopicsReader();
    QualityQuery qqs[] = qReader.readQueries(
        new BufferedReader(new FileReader(topicsFile)));

    Judge judge = new TrecJudge(new BufferedReader(
	new FileReader(qrelsFile)));

    judge.validateData(qqs, logger);

    QualityQueryParser qqParser = new SimpleQQParser(
		 	        "title", "contents");

    QualityBenchmark qrun = new QualityBenchmark(qqs,
		    qqParser, searcher, docNameFile);
    SubmissionReport submitLog = null;
    QualityStats stats[] = qrun.execute(judge,
	submitLog, logger);

    QualityStats avg =
        QualityStats.average(stats);
    avg.log("SUMMARY",2,logger,"  ");
    dir.close();
  }
}
