package Engine;

import java.util.LinkedList;


import javax.swing.JFrame;




public class Moteur extends JFrame{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		//generer Conditions
		LinkedList<Condition> protocol;
		try {
			GenerateProtocol gp= new GenerateProtocol("HttpCatchCrawler.xml");
			protocol = gp.getCondition();
			
			System.out.println("nombre de regle:"+protocol.size());
			new ListenerServer(gp.getport(),protocol);	
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	

		


	}

	public Moteur() {
		super();
	}
}
