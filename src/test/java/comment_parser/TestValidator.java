package comment_parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestValidator {
	@Test
	public void testIsSupportedFile() {
		Validator validator = new Validator();

		String accepted = "Java.java";
		assertTrue(validator.isSupportedFile(accepted));

		String noExtension = "Java";
		assertFalse(validator.isSupportedFile(noExtension));

		String unsupportedExtension = "Java.txt";
		assertFalse(validator.isSupportedFile(unsupportedExtension));

		String wrongStart = ".Java.java";
		assertFalse(validator.isSupportedFile(wrongStart));
	}

}