package GUI;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import Engine.Condition;
import Engine.ListenerClient;
import Engine.ListenerServer;
import Event.ServerListener;
import Model.Actionnable;
import Model.BroadcastingAction;
import Model.GUI;

public class UIPushState extends GUI{

	JTabbedPane haut;
	
	HashMap<String,UIClient> hashMapUI ;
	JLabel allTODO = new JLabel("todo");
	JButton sendALL = new JButton("SendALL");
	//disconnect all..
	//graph?
	//loadProtocol ?
	ListenerServer theServer;
	public UIPushState() {
		
	}

	public void provideInformation(String i){
		System.out.println(i);	
	}
	
	public void start (ListenerServer ls) {	
	
		
		theServer = ls;
		hashMapUI = new HashMap<String, UIClient>();	
			System.out.println("addListener");
			ls.setVisible(true);
			
			haut = new JTabbedPane();
			haut.add("all", allTODO);
			ls.add(haut);
			
			ls.setSize(800, 600);
			ls.addServerListener(new ServerListener() {
			
			@Override
			public void stateChanged(String id,String newState) {
				provideInformation("newState:"+newState +"id:"+id);
				hashMapUI.get(id).infoAppend("State change to "+newState);
			}
			
			@Override
			public void disconnectedSocket(String id) {
				provideInformation("disconnect:"+id);
				
				haut.setTitleAt((hashMapUI.get(id).tabPosition), id+" disconnected");
	
			}
			
			@Override
			public void connectedSocket(String id) {
				provideInformation("connect:"+id);
				
				
				UIClient cherche = hashMapUI.get(id);
				int identifiantposition;
				if(cherche!=null){
					identifiantposition = cherche.tabPosition;
					haut.setTitleAt((hashMapUI.get(id).tabPosition), id+" reconnected");
					 
						
					String ip = theServer.GetClient(id).getSock().getInetAddress().toString();
					cherche.infoAppend("reconnected with "+ip);
				}else{
					
					identifiantposition = hashMapUI.size();
					System.out.println("newUI");
					UIClient newUI = new UIClient( identifiantposition, id, theServer.GetClient(id) );
					String ip = theServer.GetClient(id).getSock().getInetAddress().toString();
					newUI.infoAppend("connected with "+ip);
					haut.insertTab(id, null, newUI.infoGlobal, "no tip", identifiantposition);
					System.out.println("newHMap");
					hashMapUI.put(id, newUI);
					
						
				}
				
				
				
				
				
			}
			
			@Override
			public void ActionPerformed(String id,String actionName) {
				provideInformation("action:"+actionName +"id:"+id);
				hashMapUI.get(id).infoAppend(actionName);
				String buffer = theServer.GetClient(id).getBuffer();
				if(buffer.length()>100){
					hashMapUI.get(id).infoAppend(buffer.substring(0, 100)+"..."); //too large for displaying
				}else{
					hashMapUI.get(id).infoAppend(buffer);	
				}
			}
		});
	
		
		}
		
	
			
			
		}
class UIClient{
	JPanel infoGlobal;
	JTextArea log;
	JPanel bas= new JPanel();
	int tabPosition;
	String Title;
	
	//disconnect...
	JTextArea sendText = new JTextArea(1,50);
	JButton send = new JButton("Send");
	
	ListenerClient lc;
	public UIClient(int pTabPosition, String pTitle, ListenerClient plc) {
		lc=plc;
		Title = pTitle;
		tabPosition = pTabPosition;
		log =  new JTextArea();
		infoGlobal = new JPanel();
		log = new JTextArea("New id",100,10);
		JScrollPane scrollPane = new JScrollPane(log);
		log.setAutoscrolls(true);
		scrollPane.setAutoscrolls(true);
		infoGlobal.setLayout(new BorderLayout());
		infoGlobal.add(scrollPane,BorderLayout.CENTER);
		bas.add(sendText);
		bas.add(send);
		
		
		infoGlobal.add(bas,BorderLayout.SOUTH);
		
		
		
	send.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			BroadcastingAction bra = new BroadcastingAction();
			
			bra.SendString(sendText.getText(), lc);
		}
	});
		
	}	
	public void infoAppend(String info){
		log.setText(log.getText()+"\n"+info);
	}
	
	
	}

