package Engine;


import java.lang.reflect.Constructor;


import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Model.Actionnable;

public class Condition {
	
	int etat=0;		//context
	private Pattern pattern; // a pattern must be in context => exception of model
	private Matcher m;
	String type = "regex";//context of this condition regex by default
	
	
	private LinkedList<String> params = new LinkedList<String>(); //params of condition
	LinkedList<Actions>  actions= new LinkedList<Actions>();//action to todo, if condition is true
	int futuretat=0; //future state
	
	/*
		TODO action

	 * STORE FOLLOWING DATA UNTIL IN (file or var) regex de fin (on server)  //pipe to file et pipe to var?
	 * EXEC File (with parameter) 								(on server)
	 * PLAY Sound(ok)       juste wav 							(on server)
	 * TUNNELING on port    ok    								(on server)  //Pipe with server

	 */
	//retirons les classes Action dont il n'y a pas de Thread



	public Condition(String strpattern,LinkedList<Actions> actions,int petat) {
		this(strpattern,actions,petat,petat);
	}


	public Condition(String strpattern,LinkedList<Actions> pactions,int petat,int pfuturetat) {
		if(strpattern.equals("")){
			setPattern(Pattern.compile(".*"));	
		}else{
			setPattern(Pattern.compile(strpattern));
		}
		etat=petat;
		futuretat=pfuturetat;
		actions=pactions;
	}

	public void testCondition(ListenerClient plc){

		if(plc.getEtat()==etat){
			setM(getPattern().matcher(plc.getBuffer()));//conditionable			
			//attributes depend of action which depend of condition// @G=1= could be misunderstood => context
			if(type.compareTo("compareValue")==0){/// move in conditionable
				
				
			}else if(type.compareTo("regex")==0){


			if(getM().matches() ){
				System.out.println("entered");
				for (int i=0;i<actions.size();i++){
					
					Actions actionTodo =actions.get(i);
					String action = actionTodo.type.toString();
					System.out.println("action= " +action);
					
					try {
						Constructor classToRun =   Class.forName("Actions."+action).getConstructor(Actions.class);
						Actionnable cl = (Actionnable) classToRun.newInstance(actionTodo);
						cl.start(plc, getM());
		
					} catch (Exception e) {
						System.out.println(action+".class  inexistant : "+e);
					}
					
					/*

					}else if (action=="PipeWith"){
						
						try {//si le cast ne passe pas
							plc.pipeWithServer(ValParam(i, m, 0,plc),Integer.parseInt(ValParam(i, m, 1,plc)));//rajouter ce que l'on doit envoyer en premier
						} catch (Exception e) {		}
	
					}
					*/
				}

			}
				plc.setEtat(futuretat);
				plc.setBuffer("");
				plc.ls.perform_protocol(plc);
			}
		}
	}


	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}


	public Pattern getPattern() {
		return pattern;
	}


	public void setM(Matcher m) {
		this.m = m;
	}


	public Matcher getM() {
		return m;
	}




	
}
