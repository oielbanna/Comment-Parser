package comment_parser;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Counter class for Java files
 * 
 * @author Omar Ibrahim
 *
 */
public class JavaCounter extends FileCounter {
	private final Pattern block = Pattern.compile(".*?(/*.*?\\*\\/).*");

	public JavaCounter() {
		super();
	}

	/**
	 * Finds the first occurrence of a valid token in TokenType and returns it.
	 * Assumes that the line type is equal to whichever valid token comes first. If
	 * a line has a string that contains a line comment, this line is not counted as
	 * a line comment
	 * 
	 * @param line
	 * @return returns TokenType of the first Token that appears in line
	 */
	protected TokenType getToken(String line) {
		final String stringToken = "\"";
		final String blockCommentToken = "/*";
		final String lineCommentToken = "//";

		int stringIdx = line.indexOf(stringToken);
		int blockCIdx = line.indexOf(blockCommentToken);
		int lineCIdx = line.indexOf(lineCommentToken);

		// finds minimum index of stringToken, blockCommentToken, lineCommentToken only
		// if token exists in line
		int min = -1;
		if (stringIdx != -1 && blockCIdx != -1 && lineCIdx != -1)
			min = Math.min(stringIdx, Math.min(blockCIdx, lineCIdx));
		else if (stringIdx != -1 && blockCIdx != -1)
			min = Math.min(stringIdx, blockCIdx);
		else if (stringIdx != -1 && lineCIdx != -1)
			min = Math.min(stringIdx, lineCIdx);
		else if (blockCIdx != -1 && lineCIdx != -1)
			min = Math.min(blockCIdx, lineCIdx);
		else if (stringIdx != -1)
			return TokenType.Other;
		else if (blockCIdx != -1)
			return TokenType.BlockComment;
		else if (lineCIdx != -1)
			return TokenType.LineComment;

		if (min == stringIdx)
			return TokenType.Other;
		if (min == blockCIdx)
			return TokenType.BlockComment;
		if (min == lineCIdx)
			return TokenType.LineComment;
		return TokenType.Other;

	}

	/**
	 * Starts with line, it loops on inputstream using scanner until it finds the
	 * closing token for block comment. Appends each line in stream to line until
	 * token is found
	 * 
	 * @param line    first line that has a block comment
	 * @param scanner
	 */
	protected void handleBlockComment(String line, Scanner scanner) {
		while (!block.matcher(line).matches() && scanner.hasNextLine()) {
			lineCount++;
			blockCommentLinesCount++;
			line += scanner.nextLine().trim();
		}
		incrementTodo(line);
	}

}
