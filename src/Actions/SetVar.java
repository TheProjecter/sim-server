package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

public class SetVar extends Actionnable {



	public SetVar(Actions pactions) {
		super(pactions);

	}
	@Override
	public void start(ListenerClient plc,Matcher m) {
		try {
			plc.variables.put(ValParam( m, 0,plc,actions), ValParam( m, 1,plc,actions))	;
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}