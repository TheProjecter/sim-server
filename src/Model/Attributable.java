package Model;

import java.util.regex.Matcher;

import Engine.ListenerClient;

public abstract class Attributable {
	/**NOT IMPLEMENTED*/
	
	String attribute;
	
	public  Attributable() {
		
		

	}
	
	public String Get(String param,Matcher m,ListenerClient plc){
		
		return "";
	}
}
