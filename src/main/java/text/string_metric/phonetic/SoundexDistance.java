package text.string_metric.phonetic;

import org.apache.commons.lang.Validate;

import text.string_metric.PhoneticDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Soundex;

public class SoundexDistance implements PhoneticDistance<Double, String> {

	private final Soundex soundexMetric;
	
	protected SoundexDistance() {
		soundexMetric = new uk.ac.shef.wit.simmetrics.similaritymetrics.Soundex();
	}

	public Double distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return (double) soundexMetric.getSimilarity(from, to);
	}
	
	
}
