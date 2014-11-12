package text.string_metric.edit_distance;

import org.apache.commons.lang.Validate;

import text.string_metric.EditDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.JaroWinkler;

public class JaroWrinklerDistance implements EditDistance<Double, String> {

	private final JaroWinkler jaroWinklerMetric;
	
	public JaroWrinklerDistance() {
		jaroWinklerMetric = new JaroWinkler();
	}
	
	public Double distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return (double) jaroWinklerMetric.getSimilarity(from, to);
	}

}
