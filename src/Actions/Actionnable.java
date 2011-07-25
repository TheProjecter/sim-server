package Actions;


import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Engine.Actions;
import Engine.ListenerClient;

public abstract class  Actionnable extends Thread{
//thread usage is not mandatory
	Actions actions;

	ManageAction ma;
	
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


/*
 * 
 * order of these replacement is important
 * 
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
System.out.println("replace @G="+isolement+ "by "+ mregex.group(isolement.charAt(0)-48));
			ntest=ntest.replaceAll("@G="+isolement+"_", mregex.group(isolement.charAt(0)-48));
		}

		ntest=	ntest.replaceAll( "/@%²","@");

		return ntest;

	}


}