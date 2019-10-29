package comment_parser.comment_parser;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Main class container for counter class hierarchy of different languages
 * 
 * @author Omar Ibrahim
 * 
 */
public abstract class FileCounter {
	protected int lineCount;
	protected int todoCount;
	protected int lineCommentCount;
	protected int blockCommentCount;
	protected int blockCommentLinesCount;
	

	/**
	 * Constructs a newly allocated FileCounter object with the filename provided
	 * 
	 */
	public FileCounter() {
		lineCount = 0;
		todoCount = 0;
		lineCommentCount = 0;
		blockCommentCount = 0;
		blockCommentLinesCount = 0;
	}


	/**
	 * Main template method for parsing. Loops through input stream line by line and
	 * if it detects a recognized token (line comment, block comment), it increments
	 * the count for that token
	 * 
	 * @param input InputStream that method will loop through line by line
	 * @return string representation of line count, comment count, todo count
	 */
	public String parse(InputStream input) {

		Scanner scanner = new Scanner(input);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			lineCount++;
			TokenType type = getToken(line);
			// either a string or non-special token
			if (type == TokenType.Other) {
				continue;
			} else if (type == TokenType.LineComment) {
				lineCommentCount++;
				incrementTodo(line);
			} else if (type == TokenType.BlockComment) {
				blockCommentCount++;
				blockCommentLinesCount++;
				handleBlockComment(line, scanner);
			}
		}

		StringBuffer result = new StringBuffer();
		result.append("Total # of lines: " + lineCount + "\n");
		result.append("Total # of comment lines:  " + (lineCommentCount + blockCommentLinesCount) + "\n");
		result.append("Total # of single line comments: " + lineCommentCount + "\n");
		result.append("Total # of comment lines within block comments: " + blockCommentLinesCount + "\n");
		result.append("Total # of block line comments: " + blockCommentCount + "\n");
		result.append("Total # of TODO’s: " + todoCount + "\n");

		scanner.close();
		return result.toString();

	}

	/**
	 * Finds the first occurrence of a valid token in TokenType and returns it.
	 * Assumes that the line type is equal to whichever valid token comes first
	 * 
	 * @param line
	 * @return returns TokenType of the first Token that appears in line
	 */
	abstract TokenType getToken(String line);

	/**
	 * Starting with line, loops through inputstream using scanner until it finds
	 * the end token of block comment
	 * 
	 * @param line    first line that has a block comment
	 * @param scanner
	 */
	abstract void handleBlockComment(String line, Scanner scanner);

	/**
	 * increments todo count if line contains "TODO"
	 * 
	 * @param line
	 */
	protected void incrementTodo(String line) {
		if (line.contains("TODO"))
			todoCount++;
	}

	public int getLineCount() {
		return lineCount;
	}

	public int getTodoCount() {
		return todoCount;
	}

	public int getLineCommentCount() {
		return lineCommentCount;
	}

	public int getBlockCommentCount() {
		return blockCommentCount;
	}

	public int getBlockCommentLinesCount() {
		return blockCommentLinesCount;
	}

	public int getTotalCommentLinesCount() {
		return lineCommentCount + blockCommentLinesCount;
	}
}
