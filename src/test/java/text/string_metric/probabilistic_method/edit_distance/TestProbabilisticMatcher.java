package text.string_metric.probabilistic_method.edit_distance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import text.string_metric.probabilistic_method.Attribute;
import text.string_metric.probabilistic_method.IAttributeMatcher;
import text.string_metric.probabilistic_method.MatchResult;
import text.string_metric.probabilistic_method.ProbabilisticMatcher;
import text.string_metric.probabilistic_method.Record;
import text.string_metric.probabilistic_method.edit_distance.JaroWrinklerMatcher;
import text.string_metric.probabilistic_method.edit_distance.LevenshteinMatcher;

public class TestProbabilisticMatcher {

	@Test
	public void testProbabilisticMatcher() {
		ProbabilisticMatcher m = new ProbabilisticMatcher(/* min confidence */ 0.5);
		m.setRecordSize(1); // just name
		m.setAttributeMatchers(new IAttributeMatcher[] { new JaroWrinklerMatcher(0.5, 1.0) });
		m.setAttributeMatchers(new IAttributeMatcher[] { new LevenshteinMatcher(0.1, 1.0) });
		//m.setAttributeMatchers(new IAttributeMatcher[] { new ExactMatcher(1.0, 1.0) });
		Record bruno = Record.from("1");
		bruno.getAttributes().add(new Attribute("name", "casa"));
		Record nuno = Record.from("2");
		nuno.getAttributes().add(new Attribute("name", "cassa"));
		MatchResult mr = m.measure(bruno, nuno);
		double normalizedConfidence = mr.getNormalizedConfidence();
		assertEquals(Double.valueOf(0.8), Double.valueOf(normalizedConfidence));
	}
	
}
