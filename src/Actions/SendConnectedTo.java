package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

public class SendConnectedTo extends Actionnable{

	public SendConnectedTo(Actions pactions) {
		super(pactions);

	}


	public void start(ListenerClient plc, Matcher m) {


		String messageWrapper;
		try {
			messageWrapper = ValParam( m, 0,plc,actions);
			setDestination(ValParam( m, 1,plc,actions));
			
			for(int i=0;i<plc.ls.clients.size();i++){
				
				String connectetd =plc.ls.clients.get(i).getIdname();
				ma.SendString(messageWrapper.replace("@HERE@", connectetd), plc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}



	}	

}
