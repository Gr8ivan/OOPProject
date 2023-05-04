package C21406436;

import ddf.minim.AudioBuffer;
import ie.tudublin.Visual;
import processing.core.*;

public class Pulse extends Visual {
    
    // declare variables
    PApplet parent;
    Visual pulse;
    float width, height;
    AudioBuffer ab;
    float smoothedAmplitude;
    float[] lerpedBuffer;

    // constructor for pulse
    public Pulse(AudioBuffer ab, float smoothedAmplitude, PApplet parent)
    {
        this.ab = ab;
        this.smoothedAmplitude = smoothedAmplitude;
        this.parent = parent;
    }

    // render called from draw
    public void draw()
    {   
        parent.background(0);

        float halfH = height / 2;
        float average = 0;
        float sum = 0;
        lerpedBuffer = new float[ab.size()];

        /*
        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < ab.size() ; i ++)
        {
            sum += abs(ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], ab.get(i), 0.1f);
        }
        average = sum / (float) ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        fill(0, 0);
        stroke(31, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 200); 
        stroke(63, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 400); 
        stroke(95, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 600); 
        stroke(127, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 800); 
        stroke(159, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 1000); 
        stroke(191, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 1200);
        stroke(223, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 1400); 
        stroke(255, 255, 255);
        circle(width/2, height/2, smoothedAmplitude * 1600);  

        for(int i = 0 ; i < ab.size() ; i ++)
        {
                float c = map(i, 0, ab.size(), 0, 255);
                parent.stroke(c, 255, 255);
                float f = lerpedBuffer[i] * halfH * 2.0f;
                
                parent.line(0, i, f, i);              
                parent.line(width, i, width - f, i);              
                parent.line(i, 0, i, f);          
                parent.line(i, height, i, height - f); 
                
        }
    
        for (int i = 0; i < ab.size(); i += ab.size() * 0.25)
        {
            float c = map(i, 0, ab.size(), 0, 255);
            parent.stroke(c, 255, 255);
            parent.fill(0);
            float f = lerpedBuffer[i] * height * 0.1f;

            parent.circle(random(width), random(height), f);
        }
        */
    }
}
