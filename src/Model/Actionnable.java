package Model;




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
	public String ValParam(String paramToParse, ListenerClient plc ){

		return condition.ValParam(paramToParse, plc);
	}


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