package Actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


import Engine.Condition;
import Engine.ListenerClient;
import Model.Actionnable;


public class PipeWithServer extends Actionnable{


	public PipeWithServer(Condition pcondition, int pNumaction) {
		super(pcondition, pNumaction);
		// TODO Auto-generated constructor stub
	}

	public void start(final ListenerClient plc){



		try {
			String url = ValParam( getParamAction(0), plc);
			int port = Integer.parseInt(ValParam(getParamAction(0), plc));
			Socket so = new Socket(url,port);
			final InputStream inServ = so.getInputStream();

			final OutputStream outServ = so.getOutputStream();
			plc.outs.add(outServ);
			System.out.println("connection to server!!!");

			new Thread(
					new Runnable(){
						public void run(){
							try {
								int t;
								while(true){
									t=inServ.read();
									plc.os.write(t);
									//	System.out.print((char)t);
								}

								//inServ.write(os.read());
								//outServ.write(is.read());
							} catch (IOException e) {
								
								e.printStackTrace();
							}
						}
					}
					).start();

		}catch(Exception ex){
			
		}
	}
}


