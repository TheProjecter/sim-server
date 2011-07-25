package Actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;

import Engine.Actions;
import Engine.ListenerClient;


public class PipeWithServer extends Actionnable{

	public PipeWithServer(Actions pactions) {
		super(pactions);
		// TODO Auto-generated constructor stub
	}

	public void start(final ListenerClient plc,Matcher m){



		try {
			String url = ValParam( m, 0,plc,actions);
			int port = Integer.parseInt(ValParam( m, 1,plc,actions));
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


