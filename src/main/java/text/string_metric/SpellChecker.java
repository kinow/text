package text.string_metric;

import java.util.List;

/**
 * A spell checker.
 * 
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public interface SpellChecker extends StringMetric {

	List<String> getSuggestions(String word);
	
}
