package C21406436;

import ie.tudublin.Visual;
import processing.core.PApplet;

public class PulseStar extends Visual {
    PApplet parent;
    float angle = 0;
    float starSize = 50;

    public PulseStar(PApplet parent) 
    {
        this.parent = parent;
    }

    public void draw(PApplet parent) 
    {

        float[] bands = getSmoothedBands();

        // Draw waveforms at the 4 edges of the screen
        parent.stroke(255);
        parent.noFill();

        System.out.println(bands.length);
        
        for (int i = 0; i < bands.length; i++) {
            // Top
            parent.line(i * (parent.width / (float) bands.length), 0, i * (parent.width / (float) bands.length), bands[i]);
            // Bottom
            parent.line(i * (parent.width / (float) bands.length), parent.height, i * (parent.width / (float) bands.length), parent.height - bands[i]);
            // Left
            parent.line(0, i * (parent.height / (float) bands.length), bands[i], i * (parent.height / (float) bands.length));
            // Right
            parent.line(parent.width, i * (parent.height / (float) bands.length), parent.width - bands[i], i * (parent.height / (float) bands.length));
        }

        // Draw a pulsing, rotating star in the middle
        parent.pushMatrix();
        parent.translate(parent.width / 2, parent.height / 2);
        parent.rotate(angle);
        parent.fill(255);
        parent.beginShape();
        float pulsingSize = starSize + getAmplitude() * 200;

        for (int i = 0; i < 10; i++) {
            float radius = (i % 2 == 0) ? pulsingSize : pulsingSize / 2;
            float x = radius * PApplet.cos(PApplet.radians(i * 36));
            float y = radius * PApplet.sin(PApplet.radians(i * 36));
            parent.vertex(x, y);
        }

        parent.endShape(PApplet.CLOSE);
        parent.popMatrix();

        angle += 0.01;
    }
}

