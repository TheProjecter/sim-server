package Model;



import java.lang.reflect.Constructor;
import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Engine.Actions;
import Engine.Condition;
import Engine.ListenerClient;
import Model.BroadcastingAction.destination;

public abstract class  Actionnable extends Thread{
	//thread usage is not mandatory

	protected Condition condition;
	protected int numAction=0;
	protected BroadcastingAction ma= new BroadcastingAction();

	public Actionnable(Condition pcondition,int pNumaction) {
		condition= pcondition;
		numAction = pNumaction;

	}
	public String getParamAction(int i){
		try {
			return condition.getActions().get(this.numAction).getParam(i);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "";

	}



	public int numparam(){

		return getActions().getParams().size();
	}

	public void start(ListenerClient plc){

		System.out.println("not implemented function ");
	}

	//generic parameter call in Attributes Directory
	//TODO test it , and implement it, use the deprecated method 
	public String ValParam(String paramToParse, ListenerClient plc ){
		//String paramToParse= actions.getParam(numparam);
		//condition contain context, plc for evaluate  native argument, paramToParse is the input


		String test= paramToParse; //actions.getParam(numparam);
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
					System.out.println("attribute:"+attributetype+"  param:"+attributeParam);

					Constructor<Attributable> classToRun;
					
						classToRun = (Constructor<Attributable>) Class.forName("Attributes."+attributetype).getConstructor();
						Attributable pr = (Attributable) classToRun.newInstance();
						System.out.println("before:"+test);
						test=test.replaceFirst("@"+attributetype+"="+attributeParam+"=", pr.Get(attributeParam,condition,plc));
							
						System.out.println("after:"+test);
						i=0;  //optimizable TODO

						attributetype="";
						attributeParam="";						
						state=1;	
				}else{

					attributeParam+=test.charAt(i);
				}
			}

			} catch (Exception e) {

				e.printStackTrace();
				break;
				//return "problem on class or XML format";
			}
			
		}


		return test;
	}
	/*
	@Deprecated
	public String ValParam(Matcher mregex,int numparam,ListenerClient plc,Actions actions) throws Exception{

		String pmes= actions.getParam(numparam);

		pmes=	pmes.replaceAll("@@", "/@%²");
		pmes=	pmes.replaceAll("@ID",plc.getIdname() );
		pmes=	pmes.replaceAll("@IP", plc.getSock().getInetAddress().toString()); // a tester
		pmes=	pmes.replaceAll("@S", ""+plc.getEtat());
		pmes=	pmes.replaceAll("@D", new Date().toString());
		pmes=   pmes.replaceAll("\\\\n", ""+'\n');//   \\n -> \n 
		Pattern p = Pattern.compile("@R");
		Matcher m1 = p.matcher(pmes);
		int r;
		while(m1.find()) {
			r=(int)(Math.random()*10000);
			pmes=pmes.replaceFirst("@R", ""+r);

		}
		int i;// variable d'usage
		p = Pattern.compile("@V=");
		m1 = p.matcher(pmes);
		String ntest=pmes;
		String isolement;
		String var;
		while(m1.find()) {
			i=m1.end();
			isolement="";
			while( i!=pmes.length() &&pmes.charAt(i)!='=')i++;

			isolement=pmes.substring(m1.end(),i);

			var=plc.getVariables().get(isolement);	
			if (var==null)var=""; 
			ntest=ntest.replaceAll("@V="+isolement+"=",var);


		}

		p = Pattern.compile("@G=");
		m1 = p.matcher(pmes);
		while(m1.find()) {
			i=m1.end();
			isolement="";
			while( i!=pmes.length() &&pmes.charAt(i)!='=')i++;
			isolement=pmes.substring(m1.end(),i);
//System.out.println("replace @G="+isolement+ "by "+ mregex.group(isolement.charAt(0)-48));
			ntest=ntest.replaceAll("@G="+isolement+"=", mregex.group(isolement.charAt(0)-48));
		}

		ntest=	ntest.replaceAll( "/@%²","@");

		return ntest;


	}
	 */

	public void setDestination(String to){
		ma.id = to;
		if (ma.id.compareTo("me")==0)ma.dest = destination.ME;
		else if (ma.id.compareTo("all")==0)ma.dest = destination.ALL;
		else  ma.dest = destination.ID;

	}



	protected Actions getActions() {
		return this.condition.getActions().get(numAction);
	}

}