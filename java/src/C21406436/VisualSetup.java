package C21406436;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.*;
import processing.core.PVector;

public class VisualSetup extends Visual
{    
    //declare variables
    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;
    AudioInput ai;

    public PVector center;

    float lerpedbuffer[];

    Part1 part1;
    Part2 part2;

    private int mode = 1;

    public void settings()
    {
        size(1024, 500);
        
        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN); 
    }

    public void keyPressed()
    {
        if (key >= '1' && key <= '4')
        {
            mode = key - '0';
        }
    }

    public void setup()
    {
        colorMode(RGB);

        minim = new Minim(this);
        startMinim();
        ap = minim.loadFile("cantlie-slowed.mp3");
        ap.play();
        ab = ap.mix;

        //lerpedbuffer = new float[width];

        center = new PVector(width/2, height/2);

        part1 = new Part1(width, height, center, this);
        part2 = new Part2(width, height, center, this);
                
        // Call loadAudio to load an audio file to process 
        loadAudio("cantlie-slowed.mp3");   

        
        // Call this instead to read audio from the microphone
        //startListening(); 
        
        
    }

    

    public void draw()
    {
        
        switch (mode) 
        {

            case 1:
            {
                part1.render();
                break;
            }
            case 2:
            {
                part2.render();
                break;
            }
        }     
    }
}
