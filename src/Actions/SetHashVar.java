package Actions;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SetHashVar extends Actionnable {




	public SetHashVar(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(ListenerClient plc) {
		try {
			
			plc.variables.put(ValParam( getParamAction(0), plc),""+ ValParam( getParamAction(1), plc).hashCode())	;
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}