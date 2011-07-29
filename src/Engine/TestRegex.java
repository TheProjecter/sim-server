package Engine;

public class TestRegex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test="bonjour @@V=  @V=test=@V=test=@G= @ID== @S==";



		String attributetype="";
		String attributeParam="";
		int state=1;

		for(int i=0;i<test.length();i++){
		
			switch(state){
			case 1:
				if(test.charAt(i)=='@') state=2;
				break;
			case 2:
				if(test.charAt(i)=='@'){ 
					state=1;
					break;
				}
				if(test.charAt(i)=='='){ 
					state=3;
					break;
				}
				attributetype+=test.charAt(i);

				break;
			case 3:

				if(test.charAt(i)=='='){ 
					System.out.println("attribute:"+attributetype+"  param:"+attributeParam);
					attributetype="";
					attributeParam="";
					state=1;
					break;
				}

				attributeParam+=test.charAt(i);
				break;
			}
		}








		System.out.println(test);
	}

}
