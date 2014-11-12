package text.string_metric;

/**
 * A probabilistic metric.
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public interface ProbabilisticMetric<T, S> extends StringMetric {

	T measure(S from, S to);
	
}
