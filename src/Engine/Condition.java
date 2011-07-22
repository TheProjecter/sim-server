package Engine;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Actions.*;

public class Condition {
	Pattern pattern;
	int etat=0;
	int futuretat=0;

	LinkedList<Actions>  actions= new LinkedList<Actions>();
	/*
	 * CLOSE Connection (id or this(ok) or all)  plc.sock.close()            //close connection
	 * SEND Message (id or this(ok) or all*)                                  //send message                    1 pipe to client?
	 * SEND File (id or this* or all)										//pipe from file
	 * GET Connected														 //send connected
	 * SET Idname					ok								//SetIdname
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
			pattern= Pattern.compile(".*");	
		}else{
			pattern = Pattern.compile(strpattern);
		}
		etat=petat;
		futuretat=pfuturetat;
		actions=pactions;
	}

	public void checkpattern(ListenerClient plc){

		if(plc.getEtat()==etat){

			Matcher m = pattern.matcher(plc.buffer);

			if(m.matches() ){
				System.out.println("entered");
				for (int i=0;i<actions.size();i++){
					
					Actions actionTodo =actions.get(i);
					String action = actionTodo.type.toString();
					System.out.println("action= " +action);
					
					try {
						Constructor classToRun =   Class.forName("Actions."+action).getConstructor(Actions.class);
						Actionnable cl = (Actionnable) classToRun.newInstance(actionTodo);
						cl.start(plc, m);
		
					} catch (Exception e) {
						System.out.println(action+".class  inexistant : "+e);
					}
					
					/*
				 if(action=="SendFile"){
						
						String  file = ValParam(i, m, 0,plc);
						System.out.println("ENVOI!");
						new PipeFromFile(plc.os,file);
					

					}else if (action=="GetConnected"){
					}else if (action=="SetIdname"){
						String id_candidat=ValParam(i, m, 0,plc);
						if(plc.ls.is_IDlibre(id_candidat))plc.idname=id_candidat;
					}else if (action=="StoreFile"){
						plc.pipeWithFile(ValParam(i, m, 0,plc), "\n");
					}else if (action=="Exec"){

						String  cmd = ValParam(i, m, 0,plc);
						System.out.println("cmd:"+cmd);
						try {
							System.out.println("runtime");
							Runtime r = Runtime.getRuntime();
							Process p = r.exec(cmd);
							System.out.println("wait");
							//p.waitFor();//si l'application doit attendre a ce que ce process fini
							boolean returnOutput=false;


							InputStream in = p.getInputStream();
							int c = 0;
							while ((c= in.read()) !=-1)	if (returnOutput)plc.os.write(c);

						}catch(Exception e) {
							System.out.println("erreur d'execution " + cmd + e.toString());
							
						}
								System.out.println("fin de l'execution");


				
					}else if (action=="PlaySound"){
						
						new PlaySound(ValParam(i, m, 0,plc)).start();
						
					}else if (action=="PipeWith"){
						
						try {//si le cast ne passe pas
							plc.pipeWithServer(ValParam(i, m, 0,plc),Integer.parseInt(ValParam(i, m, 1,plc)));//rajouter ce que l'on doit envoyer en premier
						} catch (Exception e) {		}
						
				
					}else if (action=="SetVar"){
						plc.variables.put(ValParam(i, m, 0,plc), ValParam(i, m, 1,plc))	;
					}else if(action=="SetHashVar"){
						plc.variables.put(ValParam(i, m, 0,plc), ""+ValParam(i, m, 1,plc).hashCode())	;
					}else{
						System.out.println("fonction non implementé : "+action);
					}
					*/
				}


				plc.setEtat(futuretat);
				plc.buffer="";
				plc.ls.perform_protocol(plc);
			}
		}
	}




	
}
