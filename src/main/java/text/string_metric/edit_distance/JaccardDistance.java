package text.string_metric.edit_distance;

import org.apache.commons.lang.Validate;

import text.string_metric.EditDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.JaccardSimilarity;

public class JaccardDistance implements EditDistance<Double, String> {

	private final JaccardSimilarity jaccardMetric;
	
	public JaccardDistance() {
		jaccardMetric = new JaccardSimilarity();
	}
	
	public Double distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return (double) jaccardMetric.getSimilarity(from, to);
	}

}
