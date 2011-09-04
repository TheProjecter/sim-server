package Actions;

import java.io.InputStream;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;

public class ExecutionFile  extends Actionnable{




	public ExecutionFile(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	public void start(ListenerClient plc) {

		String cmd;
		try {
			cmd = ValParam( getParamAction(0), plc);

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
