package prop.assignment0;
public class TestParser {
	public static void main(String[] args) {
		String inputFileName = "program1.txt";
		Parser p = new Parser();

		try {
		    p.open(inputFileName);
		    INode root = p.parse();
		    StringBuilder builder = new StringBuilder();
		    root.buildString(builder, 0);
		    System.out.println(builder);
		    p.close();
		}
		catch (Exception exception) {
		    System.out.println("EXCEPTION: " + exception);
		}
	}
}


