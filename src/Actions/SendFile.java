package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;
import Model.Actionnable;

public class SendFile  extends Actionnable{

	public SendFile(Actions pactions) {
		super(pactions);

	}


	public void start(ListenerClient plc, Matcher m) {
		 String file;
		try {
			file = ValParam( m, 0,plc,actions);
			System.out.println("ENVOI!");
			new PipeFromFile(plc.os,file);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	
	}	

}
