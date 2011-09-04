package Model;

import Engine.Condition;
import Engine.ListenerClient;

public abstract class  Conditionnable {

	String type;
	public Conditionnable(String name) {
	type=name;// chack if it's correct for action..	
	}

	public boolean Test(Condition c,ListenerClient plc){
		
		return true;
	}

}