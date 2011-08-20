package Engine;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;



public class ListenerServer {


	private boolean listen=true;
	public LinkedList<ListenerClient> clients= new LinkedList<ListenerClient>();
	LinkedList<Condition> conditions;
//(jung implementation?)


	public ListenerServer(int port, LinkedList<Condition> protocol) {
		conditions=protocol;

		ServerSocket sp;
		try {
			sp = new ServerSocket(port);
			while(this.listen){// serveur multi-user
				System.out.println(clients.size());
				System.out.println("listen on port : "+sp.getLocalPort());
				Socket sa;
				sa = sp.accept();
				System.out.println("connected : "+sa);
				ListenerClient c = new ListenerClient(sa,this);
				if(this.listen){
					clients.add(c);
					Thread t = new Thread(c);
					t.start();
				}
				else
				{
					
					c.ConnectionClose();
					System.out.println("socket close");
				}
			}
		} catch (IOException e) {		
			e.printStackTrace();
		
		}

	}


	public void perform_protocol(ListenerClient listenerClient){
		for (int i=0;i<conditions.size();i++){
			conditions.get(i).testCondition(listenerClient);
		}
		
	}
	public void remove_clients(ListenerClient listenerClient) {
		this.clients.remove(listenerClient);
	}

	public boolean is_IDlibre(String id){
		for (int i=0;i<clients.size();i++){
		
			if(clients.get(i).getIdname().equals(id))return false;
		}
		return true;
	}
	
	public String get_Idlibre(){
		int i;
		for (i=0;i<clients.size();i++){
		if(is_IDlibre(""+i))break;
		}
		return ""+i;
	}
	
	public ListenerClient GetClient(String id){
		for(int i=0;i<clients.size();i++){
			if (clients.get(i).getIdname().compareTo(id)==0){
				return clients.get(i);	
			}
		}
		return null; //not found
	}
}
