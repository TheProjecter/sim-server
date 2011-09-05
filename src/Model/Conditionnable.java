package Model;

import java.util.LinkedList;

import Engine.Condition;
import Engine.ListenerClient;

public abstract class  Conditionnable {

	private LinkedList<String> param;
	public Conditionnable(LinkedList<String> Pparam) {
		param =Pparam;
	}

	public boolean Test(Condition c,ListenerClient plc){
		
		return true;
	}
	public String ValParam(Condition condition,String paramToParse, ListenerClient plc ){

		return condition.ValParam(paramToParse, plc);
	}

	

	public String getParam(int i) {
		return param.get(i);
	}

}