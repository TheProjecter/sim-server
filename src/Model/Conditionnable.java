package Model;

import Engine.Condition;
import Engine.ListenerClient;

public abstract class  Conditionnable {

	public Conditionnable() {
		
	}

	public boolean Test(Condition c,ListenerClient plc){
		
		return true;
	}

}