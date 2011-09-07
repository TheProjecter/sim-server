package Engine;


import java.lang.reflect.Constructor;


import java.util.LinkedList;
import java.util.regex.Matcher;

import javax.swing.event.EventListenerList;

import Event.ServerListener;

import Model.Actionnable;
import Model.Attributable;
import Model.Conditionnable;

public class Condition {
	
	String state="0";		//context

	private Matcher m;//context of condition, can be use for actions...

	Conditionnable testClass;

	private LinkedList<Actions>  actions= new LinkedList<Actions>();//action to todo, if condition is true
	String futureState="0"; //future state
	
	public Condition(String strpattern,LinkedList<String> paramsCondition,LinkedList<Actions> actions,String petat) {
		this(strpattern,paramsCondition,actions,petat,petat);
	}
	

	public Condition(String function,LinkedList<String> PparamsCondition, LinkedList<Actions> pactions,String petat,String pfuturetat) {
		
		
		state=petat;
		futureState=pfuturetat;
		try {
			Constructor classForTest =   Class.forName("Conditions."+function).getConstructor(LinkedList.class);
			testClass = (Conditionnable) classForTest.newInstance(PparamsCondition);
			setActions(pactions);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

	public void testCondition(ListenerClient plc){
		if(plc.getEtat().compareTo(state)==0){
			//conditionable type ==type action?
			//attributes depend of action which depend of condition// @G=1= could be misunderstood => context			
			if(testClass.Test(this, plc)){
				System.out.println("entered");
				for (int i=0;i<getActions().size();i++){
					
					Actions actionTodo =getActions().get(i);
					String action = actionTodo.type.toString();
					System.out.println("action= " +action);
					plc.ls.fireActionPerformed(plc.getIdname(), action);
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
					if(plc.getEtat().compareTo(futureState)!=0) {
						plc.setEtat(futureState);
						//System.out.println("fire!"getListenerCount());
						plc.ls.fireStateChanged(plc.getIdname(),futureState);
					}
				}
				plc.setBuffer("");
			}
			
			
			}
			
			
		
	}


	public String ValParam(String paramToParse, ListenerClient plc ){
		//String paramToParse= actions.getParam(numparam);
		//condition contain context, plc for evaluate  native argument, paramToParse is the input


		String test= paramToParse; //actions.getParam(numparam);
		test=   test.replaceAll("\\\\n", ""+'\n');//   \\n -> \n 
		String attributetype="";
		String attributeParam="";
		int state=1;

		for(int i=0;i<test.length();i++){
			try {
		
			if(state==1){
					
				if(test.charAt(i)=='@'){
					attributetype="";
					state=2;
				}
				
			}else if(state==2){
				
				if(test.charAt(i)=='@'){ 
					state=1;
					
				}else if(test.charAt(i)=='='){ 
					state=3;
					attributeParam="";	
					
				}else{
					attributetype+=test.charAt(i);
				}
			}else{

				if(test.charAt(i)=='='){ 
			//		System.out.println("attribute:"+attributetype+"  param:"+attributeParam);

					Constructor<Attributable> classToRun;
					
						classToRun = (Constructor<Attributable>) Class.forName("Attributes."+attributetype).getConstructor();
						Attributable pr = (Attributable) classToRun.newInstance();
					//	System.out.println("before:"+test);
						try{
						test=test.replaceFirst("@"+attributetype+"="+attributeParam+"=", pr.Get(attributeParam,this,plc));
						}catch (Exception e) {
							test=test.replaceFirst("@"+attributetype+"="+attributeParam+"=", "N/A");
						}
						//System.out.println("after:"+test);
						i=0;  //optimizable TODO

						attributetype="";
						attributeParam="";						
						state=1;	
				}else{

					attributeParam+=test.charAt(i);
				}
			}

			} catch (Exception e) {
				
		//		e.printStackTrace();
				break;
				//return "problem on class or XML format";
			}
			
		}


		return test;
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
