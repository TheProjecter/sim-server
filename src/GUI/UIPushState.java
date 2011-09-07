package GUI;



import javax.swing.JLabel;

import Engine.Condition;
import Engine.ListenerClient;
import Engine.ListenerServer;
import Event.ServerListener;
import Model.Actionnable;
import Model.GUI;

public class UIPushState extends GUI{

	
	JLabel jpState = new JLabel();
	public UIPushState() {
		
	}

	public void provideInformation(String i){
		System.out.println(i);
		jpState.setText(i);
		
	}
	
	public void start (ListenerServer ls) {	
	
			System.out.println("addListener");
			ls.setVisible(true);
			ls.add(jpState);
			ls.setSize(800, 600);
		ls.addServerListener(new ServerListener() {
			
			@Override
			public void stateChanged(String id,String newState) {
				provideInformation("newState:"+newState +"id:"+id);
				
			}
			
			@Override
			public void disconnectedSocket(String id) {
				provideInformation("disconnect:"+id);
		
			}
			
			@Override
			public void connectedSocket(String id) {
				provideInformation("connect:"+id);
				
			}
			
			@Override
			public void ActionPerformed(String id,String actionName) {
				provideInformation("action:"+actionName +"id:"+id);
				
			}
		});
	
		
		}
		
		
		
	
	}

