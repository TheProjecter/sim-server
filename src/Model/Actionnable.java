package Model;



import java.util.Date;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Engine.Actions;
import Engine.ListenerClient;
import Model.BroadcastingAction.destination;

public abstract class  Actionnable extends Thread{
//thread usage is not mandatory
	protected Actions actions;

	protected BroadcastingAction ma= new BroadcastingAction();
	
	public Actionnable(Actions pactions) {
		actions= pactions;
	}

	public int numparam(){

		return actions.getParams().size();
	}

	public void start(ListenerClient plc,Matcher m){

		System.out.println("not implemented function ");
	}


	

	public String ValParam(Matcher mregex,int numparam,ListenerClient plc,Actions actions) throws Exception{


/* THIS CODE IS BETTER
		String test= actions.getParam(numparam);
		
		
		
		String attributetype="";
		String attributeParam="";
		int state=1;

		for(int i=0;i<test.length();i++){
		
			switch(state){
			case 1:
				if(test.charAt(i)=='@') state=2;
				break;
			case 2:
				if(test.charAt(i)=='@'){ 
					state=1;
					break;
				}
				if(test.charAt(i)=='='){ 
					state=3;
					break;
				}
				attributetype+=test.charAt(i);

				break;
			case 3:

				if(test.charAt(i)=='='){ 
					System.out.println("attribute:"+attributetype+"  param:"+attributeParam);
					
					Constructor classToRun =   Class.forName("Attributes."+attributetype).getConstructor();
					Attributable pr = (Attributable) classToRun.newInstance();
					
					test.replaceFirst("@"+attributetype+"="+attributeParam+"=", pr.Get(attributeParam,mregex,plc));
					attributetype="";
					attributeParam="";
					state=1;
					break;
				}

				attributeParam+=test.charAt(i);
				break;
			}
		}
		return test;
		
		*/
		
		
		
		
		
		
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
			while( i!=pmes.length() &&pmes.charAt(i)!='_')i++;

			isolement=pmes.substring(m1.end(),i);

			var=plc.getVariables().get(isolement);	
			if (var==null)var=""; 
			ntest=ntest.replaceAll("@V="+isolement+"_",var);


		}

		p = Pattern.compile("@G=");
		m1 = p.matcher(pmes);
		while(m1.find()) {
			i=m1.end();
			isolement="";
			while( i!=pmes.length() &&pmes.charAt(i)!='_')i++;
			isolement=pmes.substring(m1.end(),i);
//System.out.println("replace @G="+isolement+ "by "+ mregex.group(isolement.charAt(0)-48));
			ntest=ntest.replaceAll("@G="+isolement+"_", mregex.group(isolement.charAt(0)-48));
		}

		ntest=	ntest.replaceAll( "/@%²","@");

		return ntest;
		

	}
	
	
	public void setDestination(String to){
		ma.id = to;
		if (ma.id.compareTo("me")==0)ma.dest = destination.ME;
		else if (ma.id.compareTo("all")==0)ma.dest = destination.ALL;
		else  ma.dest = destination.ID;
		
	}

}