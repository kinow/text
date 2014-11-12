package text.string_metric.probabilistic_method.edit_distance;

import org.apache.commons.lang.StringUtils;

import text.string_metric.probabilistic_method.AbstractAttributeMatcher;
import text.string_metric.probabilistic_method.AttributeMatcherType;

public class ExactMatcher extends AbstractAttributeMatcher {

	public ExactMatcher(double threshold, double weight) {
		super(threshold, weight);
	}

	public AttributeMatcherType getMatchType() {
		return AttributeMatcherType.EXACT;
	}

	@Override
	protected double getWeight(String record1, String record2) {
		return StringUtils.equals(record1, record2) ? 1.0 : 0.0;
	}

}
