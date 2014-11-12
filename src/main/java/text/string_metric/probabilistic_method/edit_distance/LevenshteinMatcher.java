package text.string_metric.probabilistic_method.edit_distance;

import text.string_metric.EditDistance;
import text.string_metric.edit_distance.LevenshteinDistance;
import text.string_metric.probabilistic_method.AbstractAttributeMatcher;
import text.string_metric.probabilistic_method.AttributeMatcherType;

public class LevenshteinMatcher extends AbstractAttributeMatcher {

	private final EditDistance<Integer, String> wrapped;
	
	public LevenshteinMatcher(double threshold, double weight) {
		super(threshold, weight);
		wrapped = new LevenshteinDistance();
	}
	
	public AttributeMatcherType getMatchType() {
		return AttributeMatcherType.JARO;
	}

	@Override
	protected double getWeight(String record1, String record2) {
		int maxLen = Math.max(record1.length(), record2.length());
		
		// check for 0 maxLen
		if (maxLen == 0) {
            return 1.0; // as both strings identically zero length
        } else {
            final int levenshteinDistance = wrapped.distance(record1, record2);
            // return actual / possible levenstein distance to get 0-1 range
            return 1.0 - ((double) levenshteinDistance / maxLen);
        }
	}
	
}
