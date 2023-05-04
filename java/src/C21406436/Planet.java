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
    boolean started = false; // Flag to indicate if the visualization has started
    float planetSize = 100; // Base size of the planet
    float smoothedBass = 0; // Smoothed bass value for planet pulsing effect
    float smoothingFactor = 0.2f; // Adjust this value to control the smoothness (0 < smoothingFactor <= 1)
    float maxPulseSize = 0.4f; // Adjust this value to control the maximum increase in size due to pulsing
    float starDistance =350; // Distance of the stars from the center
    int numStars = 200; // Number of stars to render
    float[] starSizes; // Array to store star sizes
    float[] starSpeeds; // Array to store star rotation speeds
    float[] starAngles; // Array to store star angles
    float[] starDistances; // Array to store star distances from the center
    float[][] starPositions; // Array to store star positions
    float angle = 0; // Angle to rotate the camera in the planet scene
    int numShockwaves = 10; // Number of shockwaves to render
    float shockwaveThickness = 3; // Thickness of the shockwaves
    PShape starsSphere; // Shape object to hold the starry sphere
    PImage starsTexture; // Image object to hold the texture for the starry sphere
    
    Rocket rocket; // Rocket object for the rocket scene
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

        // Initialize star arrays and generate star positions
        initializeStars();
        starsTexture = createStarsTexture(2048, 2048, 1500); // Create the texture for the starry sphere
        starsSphere = createStarsSphere(1600, 30, starsTexture); // Create the starry sphere shape
    }
    int mode = 0; // Mode variable to switch between scenes

    long musicStartTime = 0; // Variable to store the music start time

    public void keyPressed() {
        if (key == ' ' && !started) {
            started = true; // Set started flag to true
            musicStartTime = millis(); // Store music start time
        } else if (key >= '0' && key <= '9') {
            mode = key - '0';
        }
    }


    private void initializeStars() {
        // Initialize star arrays
        starSizes = new float[numStars];
        starSpeeds = new float[numStars];
        starAngles = new float[numStars];
        starDistances = new float[numStars];
        starPositions = new float[numStars][3];

        // Populate star arrays with random values
        for (int i = 0; i < numStars; i++) {
        starSizes[i] = random(5, 20); // Set random size for the star
        starSpeeds[i] = random(0.0005f, 0.0009f); // Set random rotation speed for the star
        starAngles[i] = random(0, TWO_PI); // Set random initial angle for the star
        starDistances[i] = random(starDistance * 1f, starDistance * 2.5f); // Set random distance from the center
        
        // Calculate initial positions for the stars
        starPositions[i][0] = starDistances[i] * cos(starAngles[i]);
        starPositions[i][1] = starDistances[i] * sin(starAngles[i]);
        starPositions[i][2] = random(-5, 5); // Set random depth for the star
        }
    }
    

    public void drawPlanet() {
        // Calculate pulsing size based on smoothed bass
        float bass = getSmoothedBands()[0] * maxPulseSize;
        
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
    
        // Iterate through the stars and update their positions
        for (int i = 0; i < numStars; i++) {
            float angle = starAngles[i] + millis() * starSpeeds[i];
            float distance = starDistances[i];
            float targetX = distance * cos(angle);
            float targetY = 0; // Set the Y-coordinate to 0 for horizontal
            float targetZ = distance * sin(angle);
            
            // Update star positions using lerp
            starPositions[i][0] = lerp(starPositions[i][0], targetX, 0.05f);
            starPositions[i][1] = lerp(starPositions[i][1], targetY, 0.05f);
            starPositions[i][2] = lerp(starPositions[i][2], targetZ, 0.05f);
            
            // Draw the star
            pushMatrix();
            translate(starPositions[i][0], starPositions[i][1], starPositions[i][2]);
            fill(127, 150);
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
        // Rotate asteroid based on the rotation speed
        pushMatrix();
        rotateX(millis() * rotationSpeed);
        rotateY(millis() * rotationSpeed * 0.7f);
        scale(scaleFactor);
        
        // Draw the asteroid shape
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
        // Create a new PGraphics object as a texture
        PGraphics texture = createGraphics(w, h);
        texture.beginDraw();
        texture.background(0, 0);
         // Generate random stars
        for (int i = 0; i < numStars; i++) {
            float x = random(w);
            float y = random(h);
            float size = random(1, 3);
            int c = color(255, size * 127);
            texture.stroke(c);
            texture.point(x, y);
        }
        texture.endDraw();
        // Return the final texture
        return texture;
    }

    public PShape createStarsSphere(float radius, int detail, PImage texture) {
        // Create a sphere
        PShape sphere = createShape(SPHERE, radius);
        // Set the texture
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

        // Calculate the color values based on time
        float r = PApplet.map(PApplet.sin(millis() * 0.0005f), -1, 1, 0, 255);
        float g = PApplet.map(PApplet.sin(millis() * 0.0006f), -1, 1, 0, 255);
        float b = PApplet.map(PApplet.sin(millis() * 0.0007f), -1, 1, 0, 255);

        pushMatrix();
        translate(width / 2, height / 2);
        noFill();
        // Draw the shockwaves
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
         
        if (!started) {
            // Display the "Press Space to Start" message before starting
            background(0);
            textAlign(CENTER, CENTER);
            textSize(24);
            fill(255);
            text("Press Space to Start", width / 2, height / 2);
        } else {
            // Play the audio after a 2-second delay
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
            
            // Determine the current scene
            switch (mode) {
                case 0:
                    // Set the camera position for the planet scene
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
                    pulseStar.draw();
                    break;
            }
        }    
    }
}