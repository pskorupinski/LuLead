package uk.co.flax.luwak.queryrepresentation;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.spans.SpanMultiTermQueryWrapper;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;

public class ComplexNameQueryRepresentation extends QueryRepresentation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7885994673970890321L;

	private String [] keywords;
	private int nonMatchingWords;
	private int nonMatchingChars;
	private int distanceBetweenWords;
	private boolean inOrder;
	
	public ComplexNameQueryRepresentation(String [] keywords, 
			int nonMatchingWords,
			int nonMatchingChars,
			int distanceBetweenWords,
			boolean inOrder) {
		this.keywords = keywords;
		this.nonMatchingChars = nonMatchingChars;
		this.nonMatchingWords = nonMatchingWords;
		this.distanceBetweenWords = distanceBetweenWords;
		this.inOrder = inOrder;
	}
	
	@Override
	public Query getQuery() {
		BooleanQuery query = new BooleanQuery();

		// Construct the terms since they will be used more than once
		SpanQuery[] clauses = new SpanQuery[keywords.length];
		for(int i=0; i<keywords.length; i++) {
			Term term = new Term("text", keywords[i]);
			FuzzyQuery fQuery = new FuzzyQuery(term,nonMatchingChars);
			//
			clauses[i] = new SpanMultiTermQueryWrapper<FuzzyQuery>(fQuery);
			query.add(fQuery, Occur.SHOULD);
		}
		SpanNearQuery spanQuery = new SpanNearQuery(clauses, distanceBetweenWords, inOrder);
		spanQuery.setBoost(5f);
		query.add(spanQuery, Occur.SHOULD);
		
		query.setMinimumNumberShouldMatch(keywords.length-nonMatchingWords);	
		
		return query;
	}

}
