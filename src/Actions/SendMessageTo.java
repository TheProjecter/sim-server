package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;
import Model.Actionnable;


public class SendMessageTo extends Actionnable {



	public SendMessageTo(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc,Matcher m) {
		String pmes;
		try {
			pmes = ValParam( m, 0,plc,actions);
			System.out.println("Sending: "+pmes);
			setDestination(ValParam( m, 1,plc,actions));
			ma.SendString(pmes,plc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	

}
