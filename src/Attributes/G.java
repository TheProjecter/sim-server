package Attributes;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Attributable;

public class G extends Attributable{

	public String Get(String param,Condition c,ListenerClient plc){
		
		return c.getM().group(param.charAt(0)-48);
	}
	
}
