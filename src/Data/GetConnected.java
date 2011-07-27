package Data;

import java.util.LinkedList;

import Engine.ListenerClient;

public class GetConnected {


	public LinkedList<String> Get(ListenerClient plc) {
		LinkedList<String> temp = new LinkedList<String>();
		for(int i=0;i<plc.ls.clients.size();i++){
			temp.add(plc.ls.clients.get(i).getIdname());
		}
		return temp;
	}	

}
