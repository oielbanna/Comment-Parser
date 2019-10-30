# Comment-Parser
Java application that parses valid programs to extract information about line count, comments and TODOs.

## Explanation / Thought Process
First of all, this seems like a feature in a bigger application so I did not want to create a GUI or do a whole independent application of its own. My first instinct was to set up a github webhook for every new commit and have the parsing run then. However, since I can't really host the application anywhere and this is such a trivial part so I thought I'd get the main features down first. Thats why I opted for a simple command line application to showcase how it would run. 

I decided to support Java mainly because Java comments are very similar to a lot of other languages (JS, C, etc.) and additionally, from the design pattern I used, you can see that you can easily expand this to other languages as well. Also I remember talking to your engineers and they said you mostly use Spring boot and React so this implementation would work with that.

I did not want to create a whole compiler design because that would be too overboard for this. I'm mainly saying this to justify my implementation of getToken(). Ideally I would have all tokens and would have used some regex to match each one correctly but its much simpler in this case.

I decided against reading the whole file as a string fist, even though that would have made the parsing more generic and better, because I didn't want to use up too much memory. Instead, I read it into InputStream and scan it line by line. 

Using a template pattern seemed to be most fitting here because it drastically reduced the amount of repeated code for me (ie: reading from inputstream and counting) and because once each class (representing another language) extends FileCounter, that class only has to handle specifics for their language like getting Token and handling multiline block comments. So if you would like to support Python for example, you will simply extend FileCounter and handle python specific parsing since its comment style is different and you're done. In that case, I would also create a Factory that would dynamically return the correct object counter based on the file extension. Right now the example I show (and in my test cases) I only use Java so I create that class directly. 

At first, I had FileCounter store a FileInputStream but this logic seemed to be unrelated to a counting class so I removed it and had parse take an input stream to abstract that. This was also much better for testing purposes.

## Assumptions
  * Validator will take a filename, not a pathname.
  * Line count, comment count, etc. are not bigger than 2^31 - 1 since they are stored as int.
  * The parse(InputStream input) method assumes input has been validated using Validator first.
  * Programs passed to parse() in FileCounter don't have syntax errors.
  * Comments inside strings are not counted as comments but as normal lines.
  * If there's a line comment and block comment on the same line, whichever one comes first takes precedence.
     * I documented this in the tests so check there if its unclear.
