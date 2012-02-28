package au.edu.anu.xmlview.fedora;

/**
 * Helper class for adding information to rels-ext
 * 
 *
 */
public class FedoraReference {
	private String predicate_;
	private String object_;
	private Boolean isLiteral_;
	
	public FedoraReference(String predicate, String object, boolean isLiteral){
		predicate_ = predicate;
		object_ = object;
		isLiteral_ = isLiteral;
	}

	public String getPredicate_() {
		return predicate_;
	}

	public void setPredicate_(String predicate) {
		this.predicate_ = predicate;
	}

	public String getObject_() {
		return object_;
	}

	public void setObject_(String object) {
		this.object_ = object;
	}

	public Boolean isLiteral_() {
		return isLiteral_;
	}

	public void setLiteral_(Boolean isLiteral) {
		this.isLiteral_ = isLiteral;
	}
}
