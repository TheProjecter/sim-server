package Attributes;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Attributable;

public class V extends Attributable{

	public String Get(String param,Condition c,ListenerClient plc){
		
		return plc.getVariables().get(param);
	}
	
}
