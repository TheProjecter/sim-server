package Event;

import java.util.*;

public interface ServerListener extends EventListener{
	void stateChanged(String id,String newState);
	void connectedSocket(String id);
	void disconnectedSocket(String id);
	void ActionPerformed(String id,String actionName);
}
