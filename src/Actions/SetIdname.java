package Actions;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;
import Model.Actionnable;

public class SetIdname  extends Actionnable{

	public SetIdname(Actions pactions) {
		super(pactions);

	}


	public void start(ListenerClient plc, Matcher m) {
	
		try {
		
			 String id_candidat = ValParam( m, 0,plc,actions);
			if(plc.ls.is_IDlibre(id_candidat))plc.setIdname(id_candidat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	}	

}
