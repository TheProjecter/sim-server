package Engine;

import java.util.LinkedList;


import org.xml.sax.XMLReader;

import org.xml.sax.helpers.XMLReaderFactory;


public class GenerateProtocol {

	private LinkedList<Condition> condition= new LinkedList<Condition>();
	private int port;
	public GenerateProtocol() {
		// TODO Auto-generated constructor stub
	}
	public GenerateProtocol(String fic) {
		   XMLReader saxReader;
			try {

				saxReader = XMLReaderFactory.createXMLReader();
				SimpleContentHandler sch= new SimpleContentHandler();
				  saxReader.setContentHandler(sch);
			        saxReader.parse(fic);
			        condition=sch.getCondition();
			        port=sch.port;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public LinkedList<Condition> getCondition() {
		return condition;
	}
	public int getport(){
		return port;
	}

}
