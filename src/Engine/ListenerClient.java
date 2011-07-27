package Engine;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;




public class ListenerClient implements Runnable{


	/**Socket for the current connection*/
	private Socket sock;
	/**input flow*/
	InputStream is;
	public LinkedList<OutputStream> outs =new LinkedList<OutputStream>();
	/**output flow*/
	public OutputStream os;
	public ListenerServer ls;
	String buffer="";
	private int etat=0;    //for protocol
	private String idname=""; //could be a variable, it is an unique identifier of this client
	public Map<String, String> variables = new HashMap<String,String>();//variable

	public ListenerClient(Socket psock,ListenerServer pls) {
		sock= psock;
		ls=pls;
		setIdname(ls.get_Idlibre());
	}

	public void run() {

		try {
			os = this.sock.getOutputStream();
			is = this.sock.getInputStream();

			boolean fin=false;
			char k;

			setIdname(ls.get_Idlibre());
			System.out.println("id:"+getIdname());
			ls.perform_protocol(this);
			while(!fin&& (k=(char)is.read())>0 ){//blocked

				buffer=buffer+k; //carreful with big buffer
				if(k>33 && k<126)System.out.print(k); 

				if(k==65535)fin =true; // when a socket is closed on the client side

				for (int i=0;i<outs.size();i++){
					System.out.print(k);
					outs.get(i).write(k) ;
				}
				ls.perform_protocol(this);
				if (k=='\n')buffer="";//discutable
			}

		} catch (IOException e) {
     		e.printStackTrace();
		}finally{
			System.out.println("end of connexion");
			this.ConnectionClose();
		}



	}

	
	//should not be here:
	public void pipeWithServer(String URI,int port) {

		try {
			Socket so = new Socket(URI,port);
			final InputStream inServ = so.getInputStream();

			final OutputStream outServ = so.getOutputStream();
			outs.add(outServ);
			System.out.println("connection to server!!!");
			new Thread(
					new Runnable(){
						public void run(){
							try {
								int t;
								while(true){
									t=inServ.read();
									os.write(t);
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
			/*

			new Thread(
					new Runnable(){
						public void run(){
							try {while(true)
								out.write(in.read());
							} catch (IOException e) {
									e.printStackTrace();
							}
						}
					}
			).start();
			 * 
			 */
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public Socket getSock() {//should not be use
		return sock;
	}

	public String getIdname() {
		return idname;
	}

	public void setIdname(String idname) {
		this.idname = idname;
	}



	public void ConnectionClose() {
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			this.ls.remove_clients(this);
		}
		
	}



	public int getEtat() {
		return etat;
	}


	public void setEtat(int etat) {
		this.etat = etat;
	}

	public Map<String, String> getVariables() {
		return variables;
	}


	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
}
