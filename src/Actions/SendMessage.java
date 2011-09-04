package Actions;



import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SendMessage extends Actionnable {



	public SendMessage(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(ListenerClient plc) {
		String pmes;
		try {
			
			pmes = ValParam(getParamAction(0), plc);
			System.out.println("Sending: "+pmes);
			
			System.out.println("Sending: "+plc.getIdname());
			ma.SendString(pmes,plc);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
