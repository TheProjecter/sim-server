package Actions;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class CloseConnection extends Actionnable{

	
	public CloseConnection(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
	
	}

	@Override
	public void start(ListenerClient plc) {

			plc.ConnectionClose();
	
	}
	
	
}
