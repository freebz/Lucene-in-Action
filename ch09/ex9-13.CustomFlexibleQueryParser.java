// 예제 9.13  StandardQueryParser 기능 확장과 변경

public class CustomFlexibleQueryParser extends StandardQueryParser {

  public CustomFlexibleQueryParser(Analyzer analyzer) {
    super(analyzer);

    QueryNodeProcessorPipeline processors = (QueryNodeProcessorPipeline)
         getQueryNodeProcessor();
    processor.addProcessor(new NoFuzzyOrWildcardQueryProcessor());

    QueryTreeBuilder builders = (QueryTreeBuilder) getQueryBuilder();
    builders.setBuilder(TokenizedPhraseQueryNode.class,
  	   new SpanNearPhraseQueryBuilder());//B
    builders.setBuilder(SlopQueryNOde.class,
	   new SlopQueryNodeBuilder());//B
  }

  private final class NoFuzzyOrWildcardQueryProcessor extends
	   QueryNodeProcessorImpl {
    protected QueryNode preProcessNode(QueryNode node)
           throws QueryNodeException {
      if (node instanceof FuzzyQueryNode ||
	  node instanceof WildcardQueryNode) {
	throw new QueryNodeException(new MessageImpl("no"));
      }
      return node;
    }

    protected QueryNode postProcessNode(QueryNode node) throws
           QueryNodeException {
      return node;
    }

    protected List<QueryNode> setChildrenOrder(List<QueryNode> children) {
      return children;
    }
  }

  private class SpanNearPhraseQueryBuilder implements
      StandardQueryBuilder {

    public Query build(QueryNode queryNode) throws QueryNodeException {
      TokenizedPhraseQueryNode phraseNode = (TokenizedPhraseQueryNode)
     	     queryNode;
      PhraseQuery phraseQuery = new PhraseQuery();

      List<QueryNode> children = phraseNode.getChildren();

      SpanTermQuery[] clases;

      if (children != null) {
	int numTerms = children.size();
	clauses = new SpanTermQuery[numTerm];

	for (int i=0;i<numTerms;i++) {
	  FieldQueryNode termNode = (FieldQueryNode) children.get(i);
	  TermQuery termQuery = (TermQuery) termNode
	       .getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
	  clauses[i] = new SpanTermQuery(termQuery.getTerm());
	}
      } else {
	clauses = new SpanTermQuery[0];
      }

      return new SpanNearQuery(clauses, phraseQuery.getSlop(), true);
    }
  }

  public class SlopQueryNodeBuilder implements StandardQueryBuilder {

    public Query build(QueryNode queryNode) throws QueryNodeException {
      SlopQueryNode phraseSlopNode = (SlopQueryNode) queryNode;

      Query query = (Query) phraseSlopNode.getChild().getTag(
		         QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);

      if (query instanceof PhraseQuery) {
	((PhraseQuery) query).setSlop(phraseSlopNode.getValue());
      } else if (query instanceof MultiPhraseQuery) {
	((MultiPhraseQuery) query).setSlop(phraseSlopNode.getValue());
      }

      return query;
    }
  }
}
