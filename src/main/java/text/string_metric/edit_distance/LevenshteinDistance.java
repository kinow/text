package text.string_metric.edit_distance;

import org.apache.commons.lang.Validate;

import ru.fuzzysearch.LevensteinMetric;
import text.string_metric.EditDistance;

/**
 * Levenshtein distance.
 * 
 * @author Bruno P. Kinoshita
 * @see http://en.wikipedia.org/wiki/Levenshtein_distance
 */
public final class LevenshteinDistance implements EditDistance<Integer, String> {

	private final LevensteinMetric metric; 
	
	public LevenshteinDistance() {
		metric = new LevensteinMetric();
	}
	
	public LevenshteinDistance(int maxLength) {
		metric = new LevensteinMetric(maxLength);
	}
	
	public Integer distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return metric.getDistance(from, to);
	}

}
