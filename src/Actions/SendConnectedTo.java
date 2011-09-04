package Actions;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class SendConnectedTo extends Actionnable{

	


	public SendConnectedTo(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	public void start(ListenerClient plc) {


		String messageWrapper;
		try {
			messageWrapper = ValParam(getParamAction(0), plc);
			setDestination(ValParam(getParamAction(0), plc));
			
			for(int i=0;i<plc.ls.clients.size();i++){
				
				String connectetd =plc.ls.clients.get(i).getIdname();
				ma.SendString(messageWrapper.replace("@HERE@", connectetd), plc);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}



	}	

}
