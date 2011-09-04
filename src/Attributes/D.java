package Attributes;

import java.util.Date;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Attributable;

public class D extends Attributable{

	public String Get(String param,Condition c,ListenerClient plc){
		
		return new Date().toString();
	}
	
}
