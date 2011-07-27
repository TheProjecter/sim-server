package Actions;

import java.io.IOException;

import Engine.ListenerClient;

public class ManageAction {


public  enum destination{ID, ME, ALL}

destination dest = destination.ME; //send to me by default

String id;
public void SendString(String mes, ListenerClient plc){
	try {
	if (dest==destination.ID){
		ListenerClient l=plc.ls.GetClient(id);
		l.os.write(mes.getBytes());
	// send to id
	}else if(dest==destination.ME){
	
			plc.os.write( mes.getBytes());
		
		
	}else{
		//all but me
		ListenerClient l;
		for(int i=0;i<plc.ls.clients.size();i++){
			l = plc.ls.clients.get(i);
			if (l.getIdname().compareTo(plc.getIdname())!=0)
				l.os.write(mes.getBytes());
			
		}
		
	}
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
	
}
