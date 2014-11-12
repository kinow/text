package text.string_metric.edit_distance;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import text.string_metric.edit_distance.LevenshteinDistance;

public class TestLevenshteinDistance {
	
	private LevenshteinDistance distance = null;
	
	@Before
	public void setUp() {
		distance = new LevenshteinDistance();
	}

	@Test
	public void testLevenshteinDistance() {
		String from = "Maria";
		String to = "Marta";
		assertEquals("Wrong edit distance", Integer.valueOf(1), distance.distance(from, to));
		from = "Maria";
		to = "Mariana";
		assertEquals("Wrong edit distance", Integer.valueOf(2), distance.distance(from, to));
		
		from = "Maria Ana";
		to = "Mariana";
		assertEquals("Wrong edit distance", Integer.valueOf(2), distance.distance(from, to));
	}
	
}
