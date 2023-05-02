package ie.tudublin;

import processing.core.PApplet;

public class Planet extends Visual {
    float planetSize = 100;
    float smoothedBass = 0;
    float smoothingFactor = 0.3f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.3f; // Adjust this value to control the maximum increase in size due to pulsing

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
