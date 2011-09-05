package Engine;
import java.util.LinkedList;


public class Actions {
 
 //fonction annexe: New 
 //
 String type;
 
 private LinkedList<String> params = new LinkedList<String>();
 public Actions(String tip) {
	type=tip;
}
 
 
 public String getParam(int i)  throws Exception{
	 //if (i>params.size())   throw new Exception("Parameter Missing"+this.name());
	 return getParams().get(i);
 }


public LinkedList<String> getParams() {
	return params;
}

public void addParams(String param) {
	//System.out.println("param:" +param);
	params.add(param);
}


}
