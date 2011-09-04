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
	
	
//	private LinkedList<String> params = new LinkedList<String>(); //params of condition
	private LinkedList<Actions>  actions= new LinkedList<Actions>();//action to todo, if condition is true
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
		setActions(pactions);
	}

	public void testCondition(ListenerClient plc){

		if(plc.getEtat()==etat){
			//conditionable type ==type action?
			//System.out.println("etat="+etat);
			setM(getPattern().matcher(plc.getBuffer()));//conditionable	
			//System.out.println("buffer:"+plc.getBuffer());
			//attributes depend of action which depend of condition// @G=1= could be misunderstood => context
			if(type.compareTo("compareValue")==0){/// move in conditionable
				
				
			}else if(type.compareTo("regex")==0){
			//	System.out.println("regex condition"+getM());

			if(getM().matches() ){
				System.out.println("entered");
				for (int i=0;i<getActions().size();i++){
					
					Actions actionTodo =getActions().get(i);
					String action = actionTodo.type.toString();
					System.out.println("action= " +action);
					
					try {
						Constructor classToRun =   Class.forName("Actions."+action).getConstructor(Condition.class,int.class);
						Actionnable cl = (Actionnable) classToRun.newInstance(this,i);
						cl.start(plc);
		
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
				plc.setBuffer("");
			}
			
			
			}
			if(plc.getEtat()!=futuretat) {
				plc.setEtat(futuretat);
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


	public void setActions(LinkedList<Actions> actions) {
		this.actions = actions;
	}


	public LinkedList<Actions> getActions() {
		return actions;
	}




	
}
