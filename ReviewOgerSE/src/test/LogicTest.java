package test;

import static org.junit.Assert.*;
import logic.Matcher;

import org.junit.Test;

public class LogicTest {

	@Test
	public void testCalculateResults() {

		// scribe != author
		//moderator != reviewed group
		Matcher matcher = new Matcher("manueller Abbruch", false,
			true, 2);

		// Tests
		//assertEquals(0, matcher.c);
		//assertEquals("0 x 10 must be 0", 0, tester.multiply(0, 10));
	}

}
