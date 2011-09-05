package Conditions;


import java.util.LinkedList;
import java.util.regex.Pattern;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Conditionnable;

public class regex extends Conditionnable{
	
	Pattern p;
	public regex(LinkedList<String> param) {
		super(param);
		String strpattern = param.get(0);
		if(strpattern.equals("")){
			p=(Pattern.compile(".*"));	
		}else{
			p=(Pattern.compile(strpattern));
		}
	}
	
	public boolean Test(Condition c,ListenerClient plc){
		c.setM(p.matcher(plc.getBuffer()));
		return c.getM().matches() ;
	}
}
