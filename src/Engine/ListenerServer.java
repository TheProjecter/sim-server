package Engine;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import Engine.SimpleContentHandler.TYPE;
import Event.ServerListener;
import GUI.UIPushState;
import Model.Conditionnable;
import Model.GUI;



public class ListenerServer extends JFrame{

	private final EventListenerList listeners = new EventListenerList();
	private boolean listen=true;
	public LinkedList<ListenerClient> clients= new LinkedList<ListenerClient>();
	LinkedList<Condition> conditions;
//(jung implementation?)


	public ListenerServer(int port, LinkedList<Condition> protocol, String GUIName,TYPE type) {
		System.out.println(type);
		if(GUIName!=""){
			try{
			Constructor classUI =   Class.forName("GUI."+GUIName).getConstructor();
			GUI objectUI = (GUI)classUI.newInstance();
			
			objectUI.start(this);
			}catch( Exception e){
				System.out.println("invalid UI" + GUIName +" exception:"+e);
				
			}
		
			
		}
		
		conditions=protocol;
		if(type==TYPE.SERVER){
		ServerSocket sp;
		try {
			sp = new ServerSocket(port);
			while(this.listen){// serveur multi-user
				//System.out.println(clients.size());
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
		}else{
			try{
			ListenerClient c = new ListenerClient(new Socket("http://google.fr", 80),this);
			}catch (IOException e){
				
			}
		}

	}

	
	
	
	 protected void fireStateChanged(String id,String  newState) {
	        for(ServerListener listener : getServerListeners()) {
	        		listener.stateChanged( id,newState);
	        } 
}
	 protected	void fireConnectedSocket(String id) {
	        for(ServerListener listener : getServerListeners()) {
        		listener.connectedSocket( id);
        } 
}
	 protected	void fireDisconnectedSocket(String id) {
	        for(ServerListener listener : getServerListeners()) {
        		listener.disconnectedSocket( id);
        } 
}
	 protected		void fireActionPerformed(String id,String actionName) {
	        for(ServerListener listener : getServerListeners()) {
        		listener.ActionPerformed( id,actionName);
        } 
}

public ServerListener[] getServerListeners() {
     return listeners.getListeners(ServerListener.class);
 }
public void addServerListener(ServerListener listener) {
     listeners.add(ServerListener.class, listener);
 }
 
 public void removeServerListener(ServerListener listener) {
     listeners.remove(ServerListener.class, listener);
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
