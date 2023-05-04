package C21406436;
import ddf.minim.AudioBuffer;
import example.AudioBandsVisual;
import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;
import processing.core.PGraphics;

public class Planet extends Visual {
    boolean started = false;
    float planetSize = 100;
    float smoothedBass = 0;
    float smoothingFactor = 0.2f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.4f; // Adjust this value to control the maximum increase in size due to pulsing
    float starDistance =350;
    int numStars = 200;
    float[] starSizes;
    float[] starSpeeds;
    float[] starAngles;
    float[] starDistances;
    float[][] starPositions;
    float angle = 0;
    int numShockwaves = 10;
    float shockwaveThickness = 3;
    PShape starsSphere;
    PImage starsTexture;
    boolean setupCalled = false;
    AudioBuffer ab = getAudioBuffer();

    Rocket rocket;
    PulseStar pulseStar;
    

    public void settings() {
        size(1024, 500, P3D);

    }

    

    public void setup() {    
        colorMode(HSB, 255); 
        startMinim();
        
        loadAudio("cantlie-slowed.mp3"); 
        rocket = new Rocket(this);      
        pulseStar = new PulseStar(this);
        
        initializeStars();
        starsTexture = createStarsTexture(2048, 2048, 1500);
        starsSphere = createStarsSphere(1600, 30, starsTexture);
    }
    int mode = 0;

    long musicStartTime = 0;

    public void keyPressed() {
        if (key == ' ' && !started) {
            started = true;
            musicStartTime = millis();
        } else if (key >= '0' && key <= '9') {
            mode = key - '0';
        }
    }

    

    private void initializeStars() {
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
    

    public void drawPlanet() {
        float bass = getSmoothedBands()[0] * maxPulseSize;
        
        if (getSmoothedBands() != null)
        {
            System.out.print(getSmoothedBands()[0]);
            System.out.print("not null");
        }
        smoothedBass = smoothingFactor * bass + (1 - smoothingFactor) * smoothedBass;
        float pulsingSize = planetSize + smoothedBass;
    
        // Change planet color based on time
        float r = map(sin(millis() * 0.0005f), -1, 1, 50, 255);
        float g = map(sin(millis() * 0.0006f), -1, 1, 50, 255);
        float b = map(sin(millis() * 0.0007f), -1, 1, 50, 255);
    
        ambientLight(100, 100, 100);
    
        // Draw the pulsing planet
        pushMatrix();
        translate(width / 2, height / 2);
        fill(r, g, b);
        lights();
        sphereDetail(120);
        sphere(pulsingSize);
        popMatrix();
    }
    


    public void drawStars() {
        pushMatrix();
        translate(width / 2, height / 2);
    
        for (int i = 0; i < numStars; i++) {
            float hueOffset = random(0, 255);
            float hue = (40 + hueOffset) % 255;
            float angle = starAngles[i] + millis() * starSpeeds[i];
            float distance = starDistances[i];
            float targetX = distance * cos(angle);
            float targetY = 0; // Set the Y-coordinate to 0 for horizontal arrangement
            float targetZ = distance * sin(angle);
    
            starPositions[i][0] = lerp(starPositions[i][0], targetX, 0.05f);
            starPositions[i][1] = lerp(starPositions[i][1], targetY, 0.05f);
            starPositions[i][2] = lerp(starPositions[i][2], targetZ, 0.05f);
    
            pushMatrix();
            translate(starPositions[i][0], starPositions[i][1], starPositions[i][2]);
    
            // Change the asteroid color based on its size
            if (starSizes[i] > 10) {
                fill(127, 150);
            } else {
                fill(127, 50);
            }
    
            noStroke();
            // Draw the asteroid shape
            float scaleFactor = starSizes[i] / 3.5f;
            float rotationSpeed = starSpeeds[i] * 1000;
            drawAsteroid(scaleFactor, rotationSpeed);
            popMatrix();
        }
    
        popMatrix();
    }

    class Star {
        PVector position;
        float speed = 10;
        int trailLength;

        Star(PVector position, float speed, int trailLength) {
            this.position = position;
            this.speed = speed;
            this.trailLength = trailLength;
        }
    }
    
    public void drawAsteroid(float scaleFactor, float rotationSpeed) {
        pushMatrix();
        rotateX(millis() * rotationSpeed);
        rotateY(millis() * rotationSpeed * 0.7f);
        scale(scaleFactor);
    
        beginShape(QUADS);
        vertex(-1, 1, 0);
        vertex(1, 1, 1);
        vertex(1, -1, 1);
        vertex(-1, -1, 0);
        vertex(-2, 0, 0);
        vertex(0, 0, -1);
        endShape();
    
        popMatrix();
    }
    
    
    

    public PImage createStarsTexture(int w, int h, int numStars) {
        PGraphics texture = createGraphics(w, h);
        texture.beginDraw();
        texture.background(0, 0);
        for (int i = 0; i < numStars; i++) {
            float x = random(w);
            float y = random(h);
            float size = random(1, 3);
            int c = color(255, size * 127);
            texture.stroke(c);
            texture.point(x, y);
        }
        texture.endDraw();
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
    
        float shockwaveScale = 2f;
        float shockwaveSpacing = 2;

        float r = PApplet.map(PApplet.sin(millis() * 0.0005f), -1, 1, 0, 255);
        float g = PApplet.map(PApplet.sin(millis() * 0.0006f), -1, 1, 0, 255);
        float b = PApplet.map(PApplet.sin(millis() * 0.0007f), -1, 1, 0, 255);

        pushMatrix();
        translate(width / 2, height / 2);
        noFill();
        for (int i = 0; i < numShockwaves; i++) {
            float shockwaveRadius = (planetSize + smoothedBass) * 1.1f + i * (shockwaveThickness + shockwaveSpacing);
            float shockwaveAlpha = map(i, 0, numShockwaves, 200, 0);
            float shockwaveHue = (r + i * 10) % 255;
            stroke(shockwaveHue, g, b, shockwaveAlpha);
            stroke(r, g, b, shockwaveAlpha);
            rotateX(PI / 2);
            ellipse(0, 0, shockwaveRadius * 2 * shockwaveScale, shockwaveRadius * 2 * shockwaveScale);
        }
        popMatrix();
    }

    
    public void draw() {

        if (!setupCalled) {
            setup();
            setupCalled = true;
        }
            

        if (!started) {
        background(0);
        textAlign(CENTER, CENTER);
        textSize(24);
        fill(255);
        text("Press Space to Start", width / 2, height / 2);
        } else {
            if (musicStartTime != 0 && millis() - musicStartTime >= 2000 && !getAudioPlayer().isPlaying()) {
                getAudioPlayer().cue(0);
                getAudioPlayer().play();
                musicStartTime = 0;
            }    
            background(0);
            try {
                calculateFFT();
            } catch (VisualException e) {
                e.printStackTrace();
            }
            calculateFrequencyBands();
            calculateAverageAmplitude();
        
            switch (mode) {
                case 0:
                    float cameraX = PApplet.cos(angle) * 500;
                    float cameraY = PApplet.sin(angle) * 500;
                    float cameraZ = 500;
                    camera(cameraX, cameraY, cameraZ, width / 2, height / 2, 0, 0, 1, 0);
        
                    drawStarryBackground();
                    drawPlanet();
                    drawStars();
                    drawShockwaves();
        
                    angle += 0.01;
                    break;
                case 1:
                    // Reset the camera to its default position for the rocket scene
                    camera(width / 2.0f, height / 2.0f, (height / 2.0f) / tan(PI * 30.0f / 180.0f), width / 2.0f, height / 2.0f, 0, 0, 1, 0);
                    rocket.draw(this);
                    break;
                case 2:
                    pulseStar.draw(this);
            }
        }    
    }
    
}