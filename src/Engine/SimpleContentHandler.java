package Engine;
import java.util.LinkedList;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;


public class SimpleContentHandler implements ContentHandler {
	int port=25;
	String regex="";
	int tostate=0;
	int currentstate=0;
	private LinkedList<Condition> condition= new LinkedList<Condition>();
	private LinkedList<Actions> ActionsToadd;


	/**
	 * Constructeur par defaut. 
	 */
	public SimpleContentHandler() {
		super();
		// On definit le locator par defaut.
		locator = new LocatorImpl();
	}

	/**
	 * Definition du locator qui permet a tout moment pendant l'analyse, de localiser
	 * le traitement dans le flux. Le locator par defaut indique, par exemple, le numero
	 * de ligne et le numero de caractere sur la ligne.
	 * @author smeric
	 * @param value le locator a utiliser.
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator value) {
		locator =  value;
	}

	/**
	 * Evenement envoye au demarrage du parse du flux xml.
	 * @throws SAXException en cas de probleme quelquonque ne permettant pas de
	 * se lancer dans l'analyse du document.
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
		// System.out.println("Debut de l'analyse du document");
	}

	/**
	 * Evenement envoye a la fin de l'analyse du flux xml.
	 * @throws SAXException en cas de probleme quelquonque ne permettant pas de
	 * considerer l'analyse du document comme etant complete.
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
		//  System.out.println("Fin de l'analyse du document" );
	}

	/**
	 * Debut de traitement dans un espace de nommage.
	 * @param prefixe utilise pour cet espace de nommage dans cette partie de l'arborescence.
	 * @param URI de l'espace de nommage.
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	public void startPrefixMapping(String prefix, String URI) throws SAXException {
		//      System.out.println("Traitement de l'espace de nommage : " + URI + ", prefixe choisi : " + prefix);
	}

	/**
	 * Fin de traitement de l'espace de nommage.
	 * @param prefixe le prefixe choisi a l'ouverture du traitement de l'espace nommage.
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String prefix) throws SAXException {
		//    System.out.println("Fin de traitement de l'espace de nommage : " + prefix);
	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre une balise xml ouvrante.
	 * @param nameSpaceURI l'url de l'espace de nommage.
	 * @param localName le nom local de la balise.
	 * @param rawName nom de la balise en version 1.0 <code>nameSpaceURI + ":" + localName</code>
	 * @throws SAXException si la balise ne correspond pas a ce qui est attendu,
	 * comme par exemple non respect d'une dtd.
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
		System.out.println("Ouverture de la balise : " + localName);

		if (localName.equals("rules")){
			for (int index = 0; index < attributs.getLength(); index++) {
				if(attributs.getLocalName(index).equals("port"))port=Integer.parseInt(attributs.getValue(index));	
			}
			System.out.println("port! = "+port);
		}

		if (localName.equals("condition")){
			ActionsToadd = new LinkedList<Actions>();	
			regex = attributs.getValue("regex");
			try {
				tostate= Integer.parseInt(attributs.getValue("tostate"));
			} catch (Exception e) {
				tostate=currentstate;
			}



		}
		if (localName.equals("action")){	
			String name = attributs.getValue("name");
			
			Actions act = new Actions(name);

			for (int index = 1; index < attributs.getLength(); index++) {
				//System.out.println(">>>"+attributs.getValue("arg"+index));
				act.addParams(attributs.getValue("arg"+index)); //on recupere les arguments
			}
			ActionsToadd.add(act);
		}
		if (localName.equals("state")){	
			currentstate=Integer.parseInt(attributs.getValue("id"));
		}



		if ( ! "".equals(nameSpaceURI)) { // espace de nommage particulier
			//System.out.println("  appartenant a l'espace de nom : "  + nameSpaceURI);
		}

		//System.out.println("  Attributs de la balise : ");

		for (int index = 0; index < attributs.getLength(); index++) { // on parcourt la liste des attributs
		//	System.out.println("     - " +  attributs.getLocalName(index) + " = " + attributs.getValue(index));
		}
	}

	public LinkedList<Condition> getCondition() {
		return condition;
	}

	/**
	 * Evenement recu a chaque fermeture de balise.
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
	//	System.out.print("Fermeture de la balise : " + localName);

		if (localName.equals("condition")){
			System.out.println("------------------condition added:");
			for (int i=0;i<ActionsToadd.size();i++)
			{
				//System.out.println("cond name:"+ActionsToadd.get(i).);
				for (int k=0;k<ActionsToadd.get(i).getParams().size();k++)
				  System.out.println(ActionsToadd.get(i).getParams().get(k));
			}
			
			condition.add(new Condition(regex,ActionsToadd, currentstate,tostate));
		}


		if ( ! "".equals(nameSpaceURI)) { // name space non null
			//      System.out.print("appartenant a l'espace de nommage : " + localName);
		}


	}

	/**
	 * Evenement recu a chaque fois que l'analyseur rencontre des caracteres (entre
	 * deux balises).
	 * @param ch les caracteres proprement dits.
	 * @param start le rang du premier caractere a traiter effectivement.
	 * @param end le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int end) throws SAXException {
		//  System.out.println("#PCDATA : " + new String(ch, start, end));
	}

	/**
	 * Recu chaque fois que des caracteres d'espacement peuvent etre ignores au sens de
	 * XML. C'est a dire que cet evenement est envoye pour plusieurs espaces se succedant,
	 * les tabulations, et les retours chariot se succedants ainsi que toute combinaison de ces
	 * trois types d'occurrence.
	 * @param ch les caracteres proprement dits.
	 * @param start le rang du premier caractere a traiter effectivement.
	 * @param end le rang du dernier caractere a traiter effectivement
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
		//    System.out.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
	}

	/**
	 * Rencontre une instruction de fonctionnement.
	 * @param target la cible de l'instruction de fonctionnement.
	 * @param data les valeurs associees a cette cible. En general, elle se presente sous la forme 
	 * d'une serie de paires nom/valeur.
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public void processingInstruction(String target, String data) throws SAXException {
		System.out.println("Instruction de fonctionnement : " + target);
		System.out.println("  dont les arguments sont : " + data);
	}

	/**
	 * Recu a chaque fois qu'une balise est evitee dans le traitement a cause d'un
	 * probleme non bloque par le parser. Pour ma part je ne pense pas que vous
	 * en ayez besoin dans vos traitements.
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
		// Je ne fais rien, ce qui se passe n'est pas franchement normal.
		// Pour eviter cet evenement, le mieux est quand meme de specifier une dtd pour vos
		// documents xml et de les faire valider par votre parser.              
	}

	private Locator locator;

}
