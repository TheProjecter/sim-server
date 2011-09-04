package Actions;

import java.io.FileOutputStream;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

//client --> file
public class PipeToFile extends Actionnable{



	
	public PipeToFile(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(ListenerClient plc) {

		try {
			String file= ValParam(getParamAction(0), plc);
			FileOutputStream so = new FileOutputStream(file,true);
			plc.outs.add(so);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
