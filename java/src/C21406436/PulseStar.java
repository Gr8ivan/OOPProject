package C21406436;

import processing.core.*;

public class PulseStar
{   
    //declare variables
    Planet pulse;
    float cy = 0;
    float[] lerpedBufferX;
    int numStars = 8;
    float smoothedAmplitude;
    float amplitude;

    // constructor
    public PulseStar(Planet pulse)
    {
        this.pulse = pulse;
        cy = this.pulse.height / 2;
        lerpedBufferX = new float[pulse.getAudioBuffer().size()];
    }

    // draw method
    public void draw()
    {
        for(int i = 0 ; i < pulse.getAudioBuffer().size() ; i ++)
        {
            lerpedBufferX[i] = PApplet.lerp(lerpedBufferX[i], pulse.getAudioBuffer().get(i), 0.02f);
        }

        // calculate average and make smoothed amplitude variable
        float total = 0;
		for(int i = 0 ; i < pulse.getAudioBuffer().size() ; i ++)
        {
			total += PApplet.abs(pulse.getAudioBuffer().get(i));
		}
		amplitude = total / pulse.getAudioBuffer().size();
		smoothedAmplitude = PApplet.lerp(smoothedAmplitude, amplitude, 0.1f);

        // set colormode()
        pulse.colorMode(PApplet.HSB);

        // wave forms top and bottom of screen
        for(int i = 0 ; i < pulse.getAudioBuffer().size() ; i ++)
        {   
            pulse.stroke(PApplet.map(i, 0, pulse.getAudioBuffer().size(), 0, 255), 255, 255);
            float lbX = lerpedBufferX[i] * cy * 2;
            pulse.line(i, 0, i, lbX);
            pulse.line(i, pulse.height, i, pulse.height - lbX);
        }

        // pulsating wave forms
        for(int i = 0, col = 32; i < 8; i ++)
        {
            pulse.fill(0, 0);
            pulse.stroke(31 + col * i + 1, 255, 255);
            pulse.circle(pulse.width*0.85f, pulse.height/2, smoothedAmplitude * 75 * (i + 1));
            pulse.circle(pulse.width/2, pulse.height/2, smoothedAmplitude * 150 * (i + i));
            pulse.circle(pulse.width*0.15f, pulse.height/2, smoothedAmplitude * 75 * (i + 1));
        }        
    }
}