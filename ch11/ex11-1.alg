예제 11.1  위키피디아 문서를 색인하는 성능 측정 알고리즘

analyzer=org.apache.lucene.analysis.standard.StandardAnalyzer
content.source=org.apache.lucene.benchmark.byTask.feeds.LineDocSource
directory=FSDirectory
doc.stored = true
doc.term.vectors = true
docs.file=/x/lucene/enwiki-20090306-lines.txt

{ "Rounds"
  ResetSystemErase
  { "BuildIndex"
    -CreateIndex
    { "AddDocs" AddDoc > : 200000
      -CloseIndex
    }
  NewRound
} : 3

RepSumByPrefRound BuildIndex
