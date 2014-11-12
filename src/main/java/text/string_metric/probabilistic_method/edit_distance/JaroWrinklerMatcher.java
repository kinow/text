package text.string_metric.probabilistic_method.edit_distance;

import text.string_metric.EditDistance;
import text.string_metric.edit_distance.JaroWrinklerDistance;
import text.string_metric.probabilistic_method.AbstractAttributeMatcher;
import text.string_metric.probabilistic_method.AttributeMatcherType;
import text.string_metric.util.StringComparisonUtil;

public class JaroWrinklerMatcher extends AbstractAttributeMatcher {

	/**
     * prefix adjustment scale.
     */
    private static final double PREFIXADUSTMENTSCALE = 0.1;
    
	private final EditDistance<Double, String> wrapped;
	
	public JaroWrinklerMatcher(double threshold, double weight) {
		super(threshold, weight);
		wrapped = new JaroWrinklerDistance();
	}
	
	public AttributeMatcherType getMatchType() {
		return AttributeMatcherType.JARO;
	}

	@Override
	protected double getWeight(String record1, String record2) {
		double dist = wrapped.distance(record1, record2);
		final int prefixLength = StringComparisonUtil.getPrefixLength(record1, record2);
        return dist + (prefixLength * PREFIXADUSTMENTSCALE * (1 - dist));
	}

}
