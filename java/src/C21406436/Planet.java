package C21406436;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PApplet;

public class Planet extends Visual {
    float planetSize = 100;
    float smoothedBass = 0;
    float smoothingFactor = 0.3f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.3f; // Adjust this value to control the maximum increase in size due to pulsing
    float starDistance = 300;
    int numStars = 10;
    float[] starSizes;
    float[] starSpeeds;
    float[] starAngles;
    float[] starDistances;
    float[][] starPositions;
    float angle = 0;

    public void settings() {
        size(1024, 500, P3D);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("cantlie-slowed.mp3");

        starSizes = new float[numStars];
        starSpeeds = new float[numStars];
        starAngles = new float[numStars];
        starDistances = new float[numStars];
        starPositions = new float[numStars][3];

        for (int i = 0; i < numStars; i++) {
            starSizes[i] = random(5, 20);
            starSpeeds[i] = random(0.0005f, 0.0009f);
            starAngles[i] = random(0, TWO_PI);
            starDistances[i] = random(starDistance * 1f, starDistance * 2.5f);
            starPositions[i][0] = starDistances[i] * cos(starAngles[i]);
            starPositions[i][1] = starDistances[i] * sin(starAngles[i]);
            starPositions[i][2] = random(-5, 5);
        }
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }
    public void drawPlanet() {
        float bass = getSmoothedBands()[0] * maxPulseSize;
        smoothedBass = smoothingFactor * bass + (1 - smoothingFactor) * smoothedBass;
        float pulsingSize = planetSize + smoothedBass;
    
        // Draw the pulsing planet
        pushMatrix();
        translate(width / 2, height / 2);
        fill(255, 180, 0);
        lights();
        sphereDetail(120);
        sphere(pulsingSize);
        popMatrix();
    
        // Draw the stars
        pushMatrix();
        translate(width / 2, height / 2);
    
        for (int i = 0; i < numStars; i++) {
            float angle = starAngles[i] + millis() * starSpeeds[i];
            float distance = starDistances[i];
            float targetX = distance * cos(angle);
            float targetY = distance * sin(angle);
            float targetZ = random(-5, 5);
            
            starPositions[i][0] = lerp(starPositions[i][0], targetX, 0.05f);
            starPositions[i][1] = lerp(starPositions[i][1], targetY, 0.05f);
            starPositions[i][2] = lerp(starPositions[i][2], targetZ, 0.05f);
            
            pushMatrix();
            translate(starPositions[i][0], starPositions[i][1], starPositions[i][2]);
    
            // Change the star color based on its size
            if (starSizes[i] > 10) {
                fill(255, 255, 255, 150);
            } else {
                fill(255, 255, 255, 50);
            }
    
            noStroke();
            sphere(starSizes[i]);
            popMatrix();
    
            // Draw star trails
            float alpha = map(starSizes[i], 5, 15, 50, 200);
            stroke(255, alpha);
            noFill();
            //ellipse(x, y, trailLength, trailLength);
        }
    
        popMatrix();
    }
    
    public void draw() {
        background(0);
        try {
            // Call this if you want to use FFT data
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        // Call this if you want to use frequency bands
        calculateFrequencyBands();
    
        // Call this if you want to get the average amplitude
        calculateAverageAmplitude();
        drawPlanet();
    }
}
