package comment_parser.comment_parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;


public class TestFileCounter {
	FileCounter counter = new JavaCounter();

	@Test
	public void testLineComment() {
		// block comment and string within line comment should be counted as line
		// comment only
		String string = "// there /* */ \"is one\" comment here";

		InputStream inputStream = new ByteArrayInputStream(string.getBytes());
		counter.parse(inputStream);

		assertEquals(counter.getLineCommentCount(), 1);
		assertEquals(counter.getBlockCommentCount(), 0);
	}

	@Test
	public void testBlockComment() {
		// string and line comment inside multiline block comment should be counted as
		// block comment
		String string = "random stuff /* there \"is\"  // one comment here \n */";

		InputStream inputStream = new ByteArrayInputStream(string.getBytes());
		counter.parse(inputStream);
		assertEquals(counter.getLineCommentCount(), 0);
		assertEquals(counter.getBlockCommentCount(), 1);
		assertEquals(counter.getBlockCommentLinesCount(), 2);
	}

	@Test
	public void testString() {
		// Any comments inside strings should not be counted
		String string = "\" this is a string that contains /* a block comment */ and // a line comment \"";

		InputStream inputStream = new ByteArrayInputStream(string.getBytes());
		counter.parse(inputStream);
		assertEquals(counter.getLineCommentCount(), 0);
		assertEquals(counter.getBlockCommentCount(), 0);
		assertEquals(counter.getBlockCommentLinesCount(), 0);
	}

	@Test
	public void testEmptyFile() {
		// Any comments inside strings should not be counted
		String string = "";

		InputStream inputStream = new ByteArrayInputStream(string.getBytes());
		counter.parse(inputStream);
		assertEquals(counter.getLineCount(), 0);
		assertEquals(counter.getLineCommentCount(), 0);
		assertEquals(counter.getBlockCommentCount(), 0);
		assertEquals(counter.getBlockCommentLinesCount(), 0);
	}

	@Test
	public void testCompleteFileOne() {
		String string = "Test.java";

		InputStream inputStream;
		try {

			inputStream = new FileInputStream(string);
			counter.parse(inputStream);
			assertEquals(counter.getLineCount(), 60);
			assertEquals(counter.getTotalCommentLinesCount(), 28);
			assertEquals(counter.getLineCommentCount(), 6);
			assertEquals(counter.getBlockCommentLinesCount(), 22);
			assertEquals(counter.getBlockCommentCount(), 2);
			assertEquals(counter.getTodoCount(), 1);

		} catch (FileNotFoundException e) {
			fail("Java.java file not found");
		}
	}

	@Test
	public void testCompleteFileTwo() {
		String string = "Test.js";

		InputStream inputStream;
		try {

			inputStream = new FileInputStream(string);
			counter.parse(inputStream);
			assertEquals(counter.getLineCount(), 40);
			assertEquals(counter.getTotalCommentLinesCount(), 23);
			assertEquals(counter.getLineCommentCount(), 5);
			assertEquals(counter.getBlockCommentLinesCount(), 18);
			assertEquals(counter.getBlockCommentCount(), 4);
			assertEquals(counter.getTodoCount(), 1);

		} catch (FileNotFoundException e) {
			fail("Js.js file not found");
		}
	}
}