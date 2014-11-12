package text.string_metric.edit_distance;

import org.apache.commons.lang.Validate;

import ru.fuzzysearch.DamerauLevensteinMetric;
import text.string_metric.EditDistance;

/**
 * Damerau-Levenshtein distance.
 * 
 * @author Bruno P. Kinoshita
 * @see http://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance
 */
public class DamerauLevenshteinDistance implements EditDistance<Integer, String> {

	private final DamerauLevensteinMetric metric;

	public DamerauLevenshteinDistance() {
		metric = new DamerauLevensteinMetric();
	}

	public Integer distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return metric.getDistance(from, to);
	}
}
