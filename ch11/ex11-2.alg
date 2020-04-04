예제 11.2  스레드를 사용하고, 통합 색인 형식을 사용하지 않고, 메모리를 더 많이 활용하고, mergeFactor 값을 높게 지정한 성능 측정 알고리즘

analyzer=org.apache.lucene.analysis.standard.StandardAnalyzer
content.source=org.apache.lucene.benchmark.byTask.feeds.LineDocSource
directory=FSDirectory
docs.file=/x/lucene/enwiki-20090306-lines.txt

doc.stored = true
doc.term.vectors = true
ram.flush.mb = 128
compound = false
merge.factor = 30

log.step=1000

{ "Rounds"
  ResetSystemErase
  { "BuildIndex"
    -CreateIndex
    [ { "AddDocs" AddDoc > : 40000 ] : 5
    -CloseIndex
  }
  NewRound
} : 3

RepSumByPrefRound BuildIndex
