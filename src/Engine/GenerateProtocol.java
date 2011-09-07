package Engine;

import java.util.LinkedList;


import org.xml.sax.XMLReader;

import org.xml.sax.helpers.XMLReaderFactory;

import Engine.SimpleContentHandler.TYPE;


public class GenerateProtocol {

	private LinkedList<Condition> condition= new LinkedList<Condition>();
	private int port;
	private String GUIName="";
	private TYPE type = TYPE.SERVER;
	
	public String getGUIName() {
		return GUIName;
	}

	public GenerateProtocol() {
	
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
			        GUIName = sch.GUIname;
			        type = sch.type;
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	}


	public LinkedList<Condition> getCondition() {
		return condition;
	}
	public int getport(){
		return port;
	}

	public TYPE getType() {
		
		return type;
	}

}
