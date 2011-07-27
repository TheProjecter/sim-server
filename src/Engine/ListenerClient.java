package Engine;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;




public class ListenerClient implements Runnable{


	/**Socket où la connection sera faite*/
	private Socket sock;
	/**Le flux d'entrée*/
	InputStream is;
	public LinkedList<OutputStream> outs =new LinkedList<OutputStream>();
	/**Le flux de sortie*/
	public OutputStream os;
	public ListenerServer ls;
	String buffer="";
	private int etat=0;    //pour les protocoles
	private String idname=""; //pour l'aspect session//pourrai être inclus dans les variables
	public Map<String, String> variables = new HashMap<String,String>();//pour l'aspect algo

	public ListenerClient(Socket psock,ListenerServer pls) {
		this.setSock(psock);
		ls=pls;
		setIdname(ls.get_Idlibre());
	}







	public void run() {

		try {
			os = this.getSock().getOutputStream();
			is = this.getSock().getInputStream();

			boolean fin=false;
			char k;

			setIdname(ls.get_Idlibre());
			System.out.println("id:"+getIdname());
			ls.perform_protocol(this);
			while(!fin&& (k=(char)is.read())>0 ){//bloquant

				buffer=buffer+k; //attention au buffer trop gros..	
				if(k>33 && k<126)System.out.print(k); //verfier pourquoi ya des valeur bizarre qui transit lors de seconde connection
				
				for (int i=0;i<outs.size();i++){
					System.out.print(k);
					outs.get(i).write(k) ;
				}
				ls.perform_protocol(this);
				if (k=='\n')buffer="";//discutable
			}
			getSock().close();

		} catch (IOException e) {

			System.out.println("fin de connexion");
			e.printStackTrace();
		}

		ls.remove_clients(this);//retirer de la liste des clients

	}

/*
	public void pipeWithFile(String file,String regexfin) {

		try {
			FileOutputStream so = new FileOutputStream(file,true);
			outs.add(so);

		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
*/
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
								// TODO Auto-generated catch block
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
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
			).start();
			 * 
			 */
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}







	public String getIdname() {
		return idname;
	}







	public void setIdname(String idname) {
		this.idname = idname;
	}







	public Socket getSock() {
		return sock;
	}







	public void setSock(Socket sock) {
		this.sock = sock;
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
