// 예제 10.1  CLucene의 IndexWriter와 IndexSearcher API

#include "CLucene.h"

using namespace lucene::analysis;
using namespace lucene::index;
using namespace lucene::document;
using namespace lucene::queryParser;
using namespace lucene::search;
using namespace lucene::store;

const TCHAR* docs[] = {
  _T("a b c d e"),
  _T("a b c d e a b c d e"),
  _T("a b c d e f g h i j"),
  _T("a c e"),
  _T("e c a"),
  _T("a c e a c e"),
  _T("a c e a b c"),
  NULL
};

const TCHAR* queries[] = {
  _T("a b"),
  _T("\"a b\""),
  _T("a c"),
  _T("\"a c\""),
  _T("\"a c e\""),
  NULL
};

int main( int32_t, char** argv )
{
  SimpleAnalyzer analyzer;

  try {

    Directory* dir = new RAMDirectory();

    IndexWriter* writer = new IndexWriter(dir, &analyzer, true);

    Document doc;

    for (int j = 0; docs[j] != NULL; ++j) {
      doc.add( *_CLNEW Field(_T("contents"),
			     docs[j],
			     Field::STORE_YES |
			     Field::INDEX_TOKENIZED) );
      writer->addDocument(&doc);
      doc.clear();
    }

    writer->close();
    delete writer;

    IndexReader* reader = IndexReader::open(dir);
    IndexSearcher searcher(reader);

    QueryParser parser(_T("contents"), &analyzer);
    parser.setPhraseSlop(4);

    Hits* hits = NULL;

    for (int j = 0; queries[j] != NULL; ++j)
    {
      Query* query = parser.parse(queries[j]);

      const wchar_t* qryInfo = query->toString(_T("contents"));
      _tprintf(_T("Query: %s\n"), qryInfo);
      delete[] qryInfo;

      hits = searcher.search(query);
      _tprintf(_T("%d total results\n"),
	       hits->length());
      for (size_t i=0; i < hits->length() && i<10; i++) {
	Document* d = &hits->doc(i);
	_tprintf(_T("#%d. %s (score: %f)\n"),
		 i, d->get(_T("contents")),
		 hits->score(i));
      }
      delete hits;
      delete query;
    }

    searcher.close(); reader->close(); delete reader;
    dir->close(); delete dir;

  } catch (CLuceneError& e) {
    _tprintf(_T(" caught a exception: %s\n"), e.twhat());

  } catch (...){
    _tprintf(_T(" caught an unknown exception\n"));
  }
}
