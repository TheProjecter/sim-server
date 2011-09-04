package Attributes;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Attributable;

public class S extends Attributable{

	public String Get(String param,Condition c,ListenerClient plc){
		
		return ""+plc.getEtat();
	}
	
}
