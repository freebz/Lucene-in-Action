// 예제 13.3  엔티티 설명 질의 생성

CellQuery predicate = new CellQuery();
predicate.addClause(new TermQuery(new Term("name")),
		    Occur.SHOULD);
predicate.addClause(new TermQuery(new Term("fullname")),
		    Occur.SHOULD);
predicate.setConstraint(0);

PhraseQuery q = new PhraseQuery();
q.add(new Term("renaud")); q.add(new Term("delbru"));

CellQuery object = new CellQuery();
object.addClause(q, Occur.MUST);
object.setConstraint(1, Integer.MAX_VALUE);

TupleQuery tuple1 = new TupleQuery();
tuple1.addClause(predicate, Occur.MUST);
tuple1.addClause(object, Occur.MUST);

BooleanQuery query = new BooleanQuery();
query.addClause(tuple1, Occur.MUST);
query.addClause(new TermQuery(new Term("DERI")),
		Occur.MUST);
