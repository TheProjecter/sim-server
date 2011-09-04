package Actions;

import java.io.FileOutputStream;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

//client --> file
public class StoreToFile extends Actionnable{

	

	
	public StoreToFile(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(ListenerClient plc) {

		try {
			String file= ValParam( getParamAction(0), plc);
			String message= ValParam( getParamAction(1), plc);
			FileOutputStream so = new FileOutputStream(file,true);
			so.write(message.getBytes());
			so.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
