package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;
import Model.Actionnable;

public class SetHashVar extends Actionnable {



	public SetHashVar(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc,Matcher m) {
		try {
			
			plc.variables.put(ValParam( m, 0,plc,actions),""+ ValParam( m, 1,plc,actions).hashCode())	;
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}