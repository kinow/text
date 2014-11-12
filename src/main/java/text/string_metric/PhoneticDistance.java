package text.string_metric;

/**
 * A phonetic algorithm.
 * 
 * @author Bruno P. Kinoshita
 * @since 0.1
 * @param <T> distance value (double or int)
 * @param <S> type compared (string)
 */
public interface PhoneticDistance<T extends Number, S extends CharSequence> extends EditDistance<T, S> {

	/**
	 * Calculates an edit distance from 'from' to 'to' tokens.
	 * @param from from token
	 * @param to to token
	 * @return edit distance
	 */
	T distance(S from, S to);
	
}
