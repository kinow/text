package text.string_metric.edit_distance;

import org.apache.commons.lang.Validate;

import text.string_metric.EditDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Jaro;

public class JaroDistance implements EditDistance<Double, String> {

	private final Jaro jaroMetric;
	
	public JaroDistance() {
		jaroMetric = new Jaro();
	}
	
	public Double distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return (double) jaroMetric.getSimilarity(from, to);
	}

}
