package Engine;

public class TestRegex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test="bonjour @V@@Vbouh";
		test=test.replace("@@", "@@_");
		test=test.replaceAll("@V","bla");

		
		test=test.replace("@@_", "@");
		
		System.out.println(test);
	}

}
