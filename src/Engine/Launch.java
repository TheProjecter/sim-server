package Engine;

import java.util.LinkedList;


import javax.swing.JFrame;




public class Launch {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		//generate Conditions
		LinkedList<Condition> protocol;
		try {
			GenerateProtocol gp= new GenerateProtocol("IRC.xml");
			protocol = gp.getCondition();
			
		//	System.out.println("nombre de regle:"+protocol.size());
			new ListenerServer(gp.getport(),protocol,gp.getGUIName(),gp.getType());	
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	

		


	}

	public Launch() {
		super();
	}
}
