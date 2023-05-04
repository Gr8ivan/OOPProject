package C21406436;

import processing.core.*;

public class Part1 extends VisualSetup {
    
    // declare variables
    private VisualSetup part;
    private float smoothedAmplitude;
    private float[] lerpedBuffer;
    private float width, height;

    // constructor for visual 1
    public Part1(float width, float height, float lerpedbuffer[], VisualSetup part)
    {
        this.width = width;
        this.height = height;
        this.lerpedBuffer = lerpedbuffer;
        this.part = part;
    }

    // render called from draw
    public void render()
    {   
        part.background(0);

        float halfH = height / 2;
        float average = 0;
        float sum = 0;


        // Calculate sum and average of the samples
        // Also lerp each element of buffer;
        for(int i = 0 ; i < part.ab.size() ; i ++)
        {
            sum += abs(part.ab.get(i));
            lerpedBuffer[i] = lerp(lerpedBuffer[i], part.ab.get(i), 0.1f);
        }
        average = sum / (float) part.ab.size();

        smoothedAmplitude = lerp(smoothedAmplitude, average, 0.1f);
        part.fill(0, 0);
        part.stroke(31, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 200); 
        part.stroke(63, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 400); 
        part.stroke(95, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 600); 
        part.stroke(127, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 800); 
        part.stroke(159, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 1000); 
        part.stroke(191, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 1200);
        part.stroke(223, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 1400); 
        part.stroke(255, 255, 255);
        part.circle(width/2, height/2, smoothedAmplitude * 1600);  

        for(int i = 0 ; i < part.ab.size() ; i ++)
        {
                float c = map(i, 0, part.ab.size(), 0, 255);
                part.stroke(c, 255, 255);
                float f = lerpedBuffer[i] * halfH * 2.0f;
                
                part.line(0, i, f, i);              
                part.line(width, i, width - f, i);              
                part.line(i, 0, i, f);          
                part.line(i, height, i, height - f); 
                
        }
    }
            /*for (int i = 0; i < part.ab.size(); i += part.ab.size() * 0.25)
            {
                float c = map(i, 0, part.ab.size(), 0, 255);
                part.stroke(c, 255, 255);
                part.fill(0);
                float f = lerpedbuffer[i] * height * 0.1f;

                part.circle(random(width), random(height), f)
            }*/
}