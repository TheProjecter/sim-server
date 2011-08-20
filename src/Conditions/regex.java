package Conditions;


import Engine.Condition;
import Engine.ListenerClient;

public class regex {

	
	public boolean Test(Condition c,ListenerClient plc){
		return c.getM().matches() ;
	}
}
