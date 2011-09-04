package Actions;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;


public class SendMessageTo extends Actionnable {




	public SendMessageTo(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		
	}

	@Override
	public void start(ListenerClient plc) {
		String pmes;
		try {
			pmes = ValParam(getParamAction(0), plc);
			System.out.println("Sending: "+pmes);
			setDestination(ValParam(getParamAction(1), plc));
			ma.SendString(pmes,plc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	

}
