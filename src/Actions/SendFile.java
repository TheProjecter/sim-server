package Actions;

import java.util.regex.Matcher;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SendFile  extends Actionnable{

	

	public SendFile(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	public void start(ListenerClient plc, Matcher m) {
		 String file;
		try {
			file = ValParam(getParamAction(0), plc);
			System.out.println("SEND!");
			new PipeFromFile(plc.os,file);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
	}	

}
