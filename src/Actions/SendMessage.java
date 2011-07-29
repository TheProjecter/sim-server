package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

public class SendMessage extends Actionnable {



	public SendMessage(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc,Matcher m) {
		String pmes;
		try {
			pmes = ValParam( m, 0,plc,actions);
			System.out.println("Sending: "+pmes);
			System.out.println("Sending: "+plc.getIdname());
			ma.SendString(pmes,plc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
