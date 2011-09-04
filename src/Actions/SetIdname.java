package Actions;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SetIdname  extends Actionnable{



	public SetIdname(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	public void start(ListenerClient plc) {
	
		try {
		
			 String id_candidat = ValParam( getParamAction(0), plc);
			if(plc.ls.is_IDlibre(id_candidat))plc.setIdname(id_candidat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

	}	

}
