package Actions;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//file -> client
public class PipeFromFile implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	public PipeFromFile(OutputStream outclient,String file) {
		try {
			InputStream in = new FileInputStream(file);
			int c = 0;
			while ((c= in.read()) !=-1){
			
					outclient.write(c);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();

		}
	}
}
