package Actions;

import java.util.regex.Matcher;

import Actions.ManageAction.destination;
import Engine.Actions;
import Engine.ListenerClient;

public class SendMessageTo extends Actionnable {



	public SendMessageTo(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc,Matcher m) {
		String pmes;
		String to;
		try {
			pmes = ValParam( m, 0,plc,actions);
			System.out.println("Sending: "+pmes);
			
			to = ValParam( m, 1,plc,actions);
			ma.id = to;
			if (ma.id.compareTo("all")==0)ma.dest = destination.ALL;
			else  ma.dest = destination.ID;
			
			ma.SendString(pmes,plc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
