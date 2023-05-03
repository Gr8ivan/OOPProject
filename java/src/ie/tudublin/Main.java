package ie.tudublin;

import C21406436.Planet;
import C21406436.Rocket;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Rocket());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
}