package Engine;

public class TestRegex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test=":@ID==!~Guest@IP== JOIN :@G=1=";

System.out.println(test);

		String attributetype="";
		String attributeParam="";
		int state=1;

		for(int i=0;i<test.length();i++){
	//	System.out.println(test.charAt(i) +":"+state);
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
					test=test.replace("@"+attributetype+"="+attributeParam+"=", "ok");
					attributetype="";
					attributeParam="";
					i=0;
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
