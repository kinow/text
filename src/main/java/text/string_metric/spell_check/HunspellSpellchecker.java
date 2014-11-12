package text.string_metric.spell_check;

import java.util.List;

import text.string_metric.SpellChecker;

import com.atlascopco.hunspell.Hunspell;

public final class HunspellSpellchecker implements SpellChecker {

	private final Hunspell hunspell;
	
	public HunspellSpellchecker() {
		hunspell = new Hunspell(
			getClass().getResource("/text/string_metric/spell_check/pt/pt_BR.dic").getFile(), 
			getClass().getResource("/text/string_metric/spell_check/pt/pt_BR.aff").getFile()
		);
	}
	
	public List<String> getSuggestions(String word) {
		List<String> result = hunspell.analyze(word);
		return result;
	}
	
	public static void main(String[] args) {
		HunspellSpellchecker spellChecker = new HunspellSpellchecker();
		List<String> results = spellChecker.getSuggestions("cassa");
		System.out.println(results);
	}
	
}
