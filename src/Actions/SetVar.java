package Actions;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SetVar extends Actionnable {




	public SetVar(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		
	}

	@Override
	public void start(ListenerClient plc) {
		try {
			plc.variables.put(ValParam(getParamAction(0), plc), ValParam(getParamAction(1), plc))	;
			
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
}