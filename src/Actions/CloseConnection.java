package Actions;

import java.io.IOException;
import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

public class CloseConnection extends Actionnable{

	public CloseConnection(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc, Matcher m) {
	

		try {
			plc.getSock().close();
		} catch (IOException e) {
			System.out.println("Socket closed or already closed");
		}
	}
	
	
}
