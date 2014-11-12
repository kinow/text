package text.string_metric.phonetic;

import org.apache.commons.lang.Validate;

import text.string_metric.PhoneticDistance;

/**
 * Edit distance using the Fonetica Portuguesa, proposed as a pull request to an Elastic
 * Search (MIT) plug-in in 2013.
 * 
 * @author Bruno P. KInoshita
 * @see https://github.com/elasticsearch/elasticsearch-analysis-phonetic/pull/9/files
 * @since 0.1
 */
public class FoneticaPortuguesaDistance implements PhoneticDistance<Double, String> {

	private final FoneticaPortuguesaMetric foneticaPortuguesaMetric;
	
	protected FoneticaPortuguesaDistance() {
		foneticaPortuguesaMetric = new FoneticaPortuguesaMetric();
	}
	
	public Double distance(String from, String to) {
		Validate.notEmpty(from, "Missing 'from' string");
		Validate.notEmpty(to, "Missing 'to' string");
		return foneticaPortuguesaMetric.getSimilarity(from, to);
	}

}
