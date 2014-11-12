package text.string_metric;

/**
 * An edit distance.
 * 
 * @author Bruno P. Kinoshita
 * @see http://en.wikipedia.org/wiki/Edit_distance
 * @since 0.1
 * @param <T> distance value (double or int)
 * @param <S> type compared (string)
 */
public interface EditDistance<T extends Number, S extends CharSequence> extends StringMetric {

	/**
	 * Calculates an edit distance from 'from' to 'to' tokens.
	 * @param from from token
	 * @param to to token
	 * @return edit distance
	 */
	T distance(S from, S to);
	
}
