package Actions;

import java.io.FileOutputStream;

import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;

//client --> file
public class StoreToFile extends Actionnable{

	public StoreToFile(Actions pactions) {
		super(pactions);
		
	}

	
	@Override
	public void start(ListenerClient plc,Matcher m) {

		try {
			String file= ValParam( m, 0,plc,actions);
			String message= ValParam( m, 1,plc,actions);
			FileOutputStream so = new FileOutputStream(file,true);
			so.write(message.getBytes());
			so.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
