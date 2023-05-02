package ie.tudublin;

import processing.core.PApplet;

public class Planet extends Visual {
    float planetSize = 100;
    float smoothedBass = 0;
    float smoothingFactor = 0.3f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.3f; // Adjust this value to control the maximum increase in size due to pulsing
    float starDistance = 300;
    int numStars = 50;
    float[] starSizes;
    float[] starSpeeds;
    float[] starAngles;
    float[] starDistances;
    

    public void settings() {
        size(1024, 500, P3D);

        // Use this to make fullscreen
        //fullScreen();

        // Use this to make fullscreen and use P3D for 3D graphics
        //fullScreen(P3D, SPAN);
    }

    public void setup() {
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("cantlie-slowed.mp3");

        // Call this instead to read audio from the microphone
        //startListening();
        starSizes = new float[numStars];
        starSpeeds = new float[numStars];
        starAngles = new float[numStars];
        starDistances = new float[numStars];

        for (int i = 0; i < numStars; i++) {
        starSizes[i] = random(5, 15);
        starSpeeds[i] = random(0.001f, 0.005f);
        starAngles[i] = random(0, TWO_PI);
        starDistances[i] = random(starDistance * 1f, starDistance * 3.8f);}
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void drawPlanet() {
        float bass = getSmoothedBands()[0] * maxPulseSize; // Adjust the multiplier to control the pulsing sensitivity
        smoothedBass = smoothingFactor * bass + (1 - smoothingFactor) * smoothedBass;
        float pulsingSize = planetSize + smoothedBass;

        // Draw the pulsing planet
        pushMatrix();
        translate(width / 2, height / 2);
        fill(0, 0, 255);
        lights();
        sphere(pulsingSize);
        popMatrix();

        pushMatrix();
        translate(width / 2, height / 2);
        
        noFill();
        stroke(255, 100);
        
        for (int i = 0; i < numStars; i++) {
          float angle = starAngles[i] + millis() * starSpeeds[i];
          float distance = starDistances[i];
        
          float x = distance * cos(angle);
          float y = distance * sin(angle);
          float z = random(-5, 5);
        
          pushMatrix();
          translate(x, y, z);
          fill(255);
          noStroke();
          sphere(starSizes[i]);
          popMatrix();
        
          ellipse(0, 0, distance * 2, distance * 2);
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
