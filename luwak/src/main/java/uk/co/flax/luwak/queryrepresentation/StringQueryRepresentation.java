package uk.co.flax.luwak.queryrepresentation;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.Query;

public class StringQueryRepresentation extends QueryRepresentation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435835072102163628L;
	
	private String queryString;
	private String defaultField;

	public StringQueryRepresentation(String queryString, String defaultField) {
		this.queryString = queryString;
		this.defaultField = defaultField;
	}
	
	@Override
	public Query getQuery() {
		try {
			return new StandardQueryParser().parse(queryString,defaultField);
		} catch (QueryNodeException e) {
			e.printStackTrace();
			return null;
		}
	}

}
