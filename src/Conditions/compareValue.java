package Conditions;


import java.util.LinkedList;

import Engine.Condition;
import Engine.ListenerClient;
import Model.Conditionnable;

public class compareValue extends Conditionnable{

	public compareValue(LinkedList<String> param) {
		super(param);
		
	}
	
	public boolean Test(Condition c,ListenerClient plc){
		try{
			int i,j;
			String op;
		 i = Integer.parseInt(c.ValParam(getParam(0), plc));
		 op= getParam(1);
		j= Integer.parseInt(c.ValParam(getParam(2), plc));;
		if(op.compareTo("==")==0)return i==j;
		if(op.compareTo("=")==0)return i==j;//permissive operation
		if(op.compareTo(">")==0)return i>j;
		if(op.compareTo("<")==0)return i<j;
		if(op.compareTo("<=")==0)return i<=j;
		if(op.compareTo(">=")==0)return i>=j;
		if(op.compareTo("!=")==0)return i!=j;
		

		}catch (Exception e) {
		//	System.out.println("int cast error for compareValue");
		}
		
		//string evaluation:
		
		String i = c.ValParam(getParam(0), plc);
		String op =  getParam(1);
		String j = c.ValParam(getParam(2), plc);
		
		if(op.compareTo("==")==0)return (i.compareTo(j)==0);
		if(op.compareTo("=")==0)return (i.compareTo(j)==0);
		if(op.compareTo("!=")==0)return (i.compareTo(j)!=0);
		
		System.out.println("operator compareValue not recognized"+op+" because input type is String");
		return true;
		
		
	}
}
