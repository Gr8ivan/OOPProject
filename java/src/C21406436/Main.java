package C21406436;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Planet());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
}