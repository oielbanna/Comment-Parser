package comment_parser;

import java.util.Arrays;
import java.util.List;

/**
 * Main class that checks if file is valid and can be counted
 * 
 * @author Omar Ibrahim
 *
 */
public class Validator {
	// holds valid file extensions that counter supports
	private final static List<String> SUPPORTED_EXTENSIONS = Arrays.asList("js", "java");

	/**
	 * Check if file is supported by the program. Assumes file is error free
	 * 
	 * @return True if file does not start with "." character, has an extension and
	 *         is one of the supported file extensions.
	 */
	public boolean isSupportedFile(String filename) {
		// starts with . character
		if (filename.startsWith("."))
			return false;
		int extensionIdx;
		if ((extensionIdx = filename.lastIndexOf(".")) == -1) // has no extension
			return false;
		String extension = filename.substring(extensionIdx + 1);
		return Validator.SUPPORTED_EXTENSIONS.contains(extension); // extension is supported
	}
}
