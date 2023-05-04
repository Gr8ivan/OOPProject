package C21406436;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.*;

public class VisualSetup extends Visual
{    
    //declare variables
    Minim minim;
    AudioPlayer ap;
    AudioBuffer ab;
    AudioInput ai;

    float lerpedbuffer[];

    Part1 part1;
    Part2 part2;

    private int mode = 1;

    public void settings()
    {
        size(1920, 1080);
        
        // Use this to make fullscreen
        fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN); 
    }

    // keyPressed
    public void keyPressed()
    {
        // choose visual
        if (key >= '1' && key <= '4')
        {
            mode = key - '0';
        }// end if

        // spacebar to pause
        if (keyCode == ' ')
        {
            // if playing
            if (ap.isPlaying())
            {
                ap.pause(); // pause

            }// end if
            // if paused
            else
            {
                ap.rewind(); // rewind
                ap.play(); // play     

            }// end else
        }// end if
    }// end keyPressed

    // setup
    public void setup()
    {
        colorMode(HSB);
        background(0);

        minim = new Minim(this);
        startMinim();
        ap = minim.loadFile("cantlie-slowed.mp3", width);
        ap.play();
        ab = ap.mix;

        lerpedbuffer = new float[width];

        // declare visuals
        part1 = new Part1(width, height, lerpedbuffer, this);
       // part2 = new Part2(width, height, lerpedbuffer, this);
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
