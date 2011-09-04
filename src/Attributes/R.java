package Attributes;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Attributable;

public class R extends Attributable{

	public String Get(String param,Condition c,ListenerClient plc){
		
		return""+((int)(Math.random()*10000));
	}
	
}
