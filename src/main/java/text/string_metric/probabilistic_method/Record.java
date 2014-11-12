package text.string_metric.probabilistic_method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A record represents input data for the match & merge.
 * 
 * @author Talend, Bruno P. Kinoshita
 * @since 0.1
 */
public class Record {

	private static final double MAX_CONFIDENCE = 1.0;

	private double confidence = MAX_CONFIDENCE;

	private final List<Attribute> attributes;
	
	private final String id;

    private final long timestamp;

    private final String source;
    
    private Set<String> relatedIds = new HashSet<String>();

	/**
	 * Creates a empty record (no {@link Attribute attributes}).
	 */
	public Record(String id, long timestamp, String source) {
		this.id = id;
        this.timestamp = timestamp;
        this.source = source;
		this.attributes = new ArrayList<Attribute>();
	}
	
	/**
     * Creates a record with {@link org.talend.dataquality.matchmerge.Attribute attributes}.
     * 
     * @param attributes Attributes for the new record.
     * @param id Id of the record in the source system.
     * @param timestamp Last modification time (in milliseconds).
     * @param source A source name to indicate where the values come from.
     */
    public Record(List<Attribute> attributes, String id, long timestamp, String source) {
        this.attributes = attributes;
        this.id = id;
        this.timestamp = timestamp;
        this.source = source;
    }
	
    /**
     * @return The id as it is in the source system. It is <b>always</b> a string (i.e. not dependent on the actual id
     * type).
     */
    public String getId() {
        return id;
    }

    /**
     * @return The {@link org.talend.dataquality.matchmerge.Attribute attributes} for this record.
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * @return A set of ids this record was built with.
     * @see #setRelatedIds(java.util.Set)
     */
    public Set<String> getRelatedIds() {
        return relatedIds;
    }

    /**
     * @param relatedIds A set of ids this record is linked to.
     * @see #getRelatedIds()
     */
    public void setRelatedIds(Set<String> relatedIds) {
        this.relatedIds = relatedIds;
    }
	
    /**
     * @return The "confidence" of the record. Confidence is always a double between 0 and 1. 1 indicates a high
     * confidence (a certain match) and 0 indicates a very unreliable match.
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * @param confidence The new confidence for this record. Confidence is always a double between 0 and 1. 1 indicates
     * a high confidence (a certain match) and 0 indicates a very unreliable match.
     * @throws java.lang.IllegalArgumentException If confidence > {@link #MAX_CONFIDENCE}.
     */
    public void setConfidence(double confidence) {
        if (confidence > MAX_CONFIDENCE) {
            throw new IllegalArgumentException("Confidence value '" + confidence + "' is incorrect (>" + MAX_CONFIDENCE + ".");
        }
        this.confidence = confidence;
    }

    /**
     * @return The last modification time for this record.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return The source associated with this record.
     */
    public String getSource() {
        return source;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        return !(id != null ? !id.equals(record.id) : record.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String relatedId : relatedIds) {
            builder.append(relatedId).append(' ');
        }
        return id + " ( " + builder + ")";
    }
    
    public static Record from(String value) {
    	return new Record(value, 0, null);
    }

}
