package Model;


import Engine.Condition;
import Engine.ListenerClient;

public abstract class Attributable {
	
	
	String attribute;
	
	public  Attributable() {
		
		

	}
	
	public String Get(String param,Condition c,ListenerClient plc){
		
		return "not implemented attributes";
	}
}
