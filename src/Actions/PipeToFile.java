package Actions;

import java.io.FileOutputStream;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

//client --> file
public class PipeToFile extends Actionnable{

	public PipeToFile(Actions pactions) {
		super(pactions);
		
	}

	
	@Override
	public void start(ListenerClient plc,Matcher m) {

		try {
			String file= ValParam( m, 0,plc,actions);
			FileOutputStream so = new FileOutputStream(file,true);
			plc.outs.add(so);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
