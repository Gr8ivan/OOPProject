package C21406436;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;

public class Planet extends Visual {
    float planetSize = 100;
    float smoothedBass = 0;
    float smoothingFactor = 0.3f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.3f; // Adjust this value to control the maximum increase in size due to pulsing
    float starDistance = 300;
    int numStars = 15;
    float[] starSizes;
    float[] starSpeeds;
    float[] starAngles;
    float[] starDistances;
    float[][] starPositions;
    float angle = 0;
    int numShockwaves = 10;
    float shockwaveThickness = 2;
    PShape starsSphere;
    PImage starsTexture;

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

        // Draw the stars
        for (int i = 0; i < numStars; i++) {
            float x = random(width);
            float y = random(height);
            float size = random(5, 20);
            float alpha = map(size, 5, 15, 50, 200);
            starSizes[i] = size;
            starSpeeds[i] = random(0.0005f, 0.0009f);
            starAngles[i] = random(0, TWO_PI);
            starDistances[i] = random(starDistance * 1f, starDistance * 2.5f);
            starPositions[i][0] = starDistances[i] * cos(starAngles[i]);
            starPositions[i][1] = starDistances[i] * sin(starAngles[i]);
            starPositions[i][2] = random(-5, 5);
            stroke(255, alpha);
            noFill();
            ellipse(x, y, size, size);
        }

        // Create star texture
        starsTexture = createStarsTexture(2048, 2048, 1500);
        starsSphere = createStarsSphere(1600, 30, starsTexture);
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
    
        // Change planet color based on time
        float r = map(sin(millis() * 0.0005f), -1, 1, 0, 255);
        float g = map(sin(millis() * 0.0006f), -1, 1, 0, 255);
        float b = map(sin(millis() * 0.0007f), -1, 1, 0, 255);
    
        // Draw the pulsing planet
        pushMatrix();
        translate(width / 2, height / 2);
        fill(r, g, b);
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

    public PImage createStarsTexture(int w, int h, int numStars) {
        PImage texture = createImage(w, h, RGB);
        texture.loadPixels();
        for (int i = 0; i < numStars; i++) {
            float x = random(w);
            float y = random(h);
            int index = (int) (y * w + x);
            float size = random(1, 3);
            int c = color(255, size * 127);
            texture.pixels[index] = c;
        }
        texture.updatePixels();
        return texture;
    }

    public PShape createStarsSphere(float radius, int detail, PImage texture) {
        PShape sphere = createShape(SPHERE, radius);
        sphere.setTexture(texture);
        sphereDetail(detail);
        return sphere;
    }

    public void drawStarryBackground() {
        pushMatrix();
        translate(width / 2, height / 2);
        shape(starsSphere);
        popMatrix();
    }

    public void drawShockwaves() {
        float bass = getSmoothedBands()[0] * maxPulseSize;
        smoothedBass = smoothingFactor * bass + (1 - smoothingFactor) * smoothedBass;

        pushMatrix();
        translate(width / 2, height / 2);
        noFill();
        for (int i = 0; i < numShockwaves; i++) {
            float shockwaveRadius = planetSize + smoothedBass + i * shockwaveThickness;
            float shockwaveAlpha = map(i, 0, numShockwaves, 200, 0);
            stroke(255, shockwaveAlpha);
            ellipse(0, 0, shockwaveRadius * 4, shockwaveRadius * 4);
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
        
        // Set the camera's position
        float cameraX = cos(angle) * 500;
        float cameraY = sin(angle) * 500;
        float cameraZ = 500;
        camera(cameraX, cameraY, cameraZ, width / 2, height / 2, 0, 0, 1, 0);
    
        // Draw starry background
        drawStarryBackground();
    
        // Draw the planet and stars
        drawPlanet();

        // Draw the shockwaves
        drawShockwaves();
    
        // Increment the angle
        angle += 0.01;
    }
    
}