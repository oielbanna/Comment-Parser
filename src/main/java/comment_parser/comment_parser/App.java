package comment_parser.comment_parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App {
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Must have only one argument with filename");
		}

		String filename = args[0];
		Validator validator = new Validator();
		if (validator.isSupportedFile(filename)) {
			FileCounter parser = new JavaCounter();
			FileInputStream input = new FileInputStream(filename);
			System.out.println(parser.parse(input));
		}
	}

}
