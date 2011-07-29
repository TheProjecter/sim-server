package Actions;

import java.io.InputStream;
import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;
import Model.Actionnable;

public class ExecutionFile  extends Actionnable{

	public ExecutionFile(Actions pactions) {
		super(pactions);

	}


	public void start(ListenerClient plc, Matcher m) {

		String cmd;
		try {
			cmd = ValParam( m, 0,plc,actions);

			System.out.println("cmd:"+cmd);
			try {
				System.out.println("runtime");
				Runtime r = Runtime.getRuntime();
				Process p = r.exec(cmd);
				System.out.println("wait");
				//p.waitFor();//si l'application doit attendre a ce que ce process fini
				boolean returnOutput=false;


				InputStream in = p.getInputStream();
				int c = 0;
				while ((c= in.read()) !=-1)	if (returnOutput)plc.os.write(c);

			}catch(Exception e) {
				System.out.println("erreur d'execution " + cmd + e.toString());

			}
			System.out.println("fin de l'execution");




		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
	}
}
