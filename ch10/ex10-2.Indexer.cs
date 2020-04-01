// 예제 10.2  루씬닷넷을 사용해 *.txt 파일을 색인하는 C# 코드

System.String indexDir = args[0];
System.String dataDir = args[1];

private IndexWriter writer;

class Indexer {

  public void Indexer(System.String indexDir) {
    Directory dir = FSDirectory.Open(new System.IO.FileInfo(indexDir));

    IndexWriter writer = new IndexWriter(
		       FSDirectory.Open(INDEX_DIR),
		       new StandardAnalyzer(Version.LUCENE_CURRENT),
		       true, IndexWriter.MaxFieldLength.LIMITED);
  }

  public void Close() {
    writer.Close();
  }

  public int Index(System.String dataDir) {
    System.String[] files =
                System.IO.Directory.GetFileSystemEntries(file.FullName);
    for (int i = 0; i < files.Length; i++) {
      IndexFile(new System.IO.FileInfo(files[i]));
    }
    return writer.NumDocs();
  }

  protected Document GetDocument(System.IO.FileInfo file) {
    Document doc = new Document();
    doc.Add(new Field("contents",
		      new System.IO.StreamReader(file.FullName,
		      System.Text.Encoding.Default)));
    doc.Add(new Field("filename",
		      file.Name,
		      Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.Add(new Field("fullpath",
		      file.FullName,
		      Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    return doc;
  }

  private void IndexFile(System.IO.FileInfo file) {
    Document doc = GetDocument(file);
    writer.AddDocument(doc);
  }
}
