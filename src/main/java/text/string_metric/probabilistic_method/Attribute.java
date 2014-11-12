package text.string_metric.probabilistic_method;

import java.util.Collections;
import java.util.Iterator;

import org.apache.commons.collections.iterators.IteratorChain;


public class Attribute {

	private final String label;
	private String value;
	private final AttributeValues<String> values = new AttributeValues<String>();
	
	public Attribute(String label) {
        this.label = label;
    }
	
	public Attribute(String label, String value) {
        this.label = label;
        this.value = value;
    }
	
	/**
     * @return The column name.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return The column's value (always as string, never typed).
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Set the merged column value.
     * 
     * @param value A string value for the column. For custom types, provide a consistent representation of the data
     * since the string is used for match.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * @return All the values that lead to the merged value (i.e. the value returned by {@link #getValue()}).
     */
    public AttributeValues<String> getValues() {
        return values;
    }

    @SuppressWarnings("unchecked")
	public Iterator<String> allValues() {
        return new IteratorChain(new Iterator[] { Collections.singleton(value).iterator(), values.iterator() });
    }
	
}
