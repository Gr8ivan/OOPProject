package C21406436;

import ie.tudublin.Visual;
import processing.core.PVector;

import java.util.ArrayList;

public class Rocket extends Visual {

    float floatAngle = 0;
    float planetColorAngle = 0;
    float planetSizeAngle = 0;
    float time = 0;
    ArrayList<PVector> trailPositions = new ArrayList<>();
    int trailLength = 50;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        colorMode(HSB); // Set the color mode to HSB
        noStroke();
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("cantlie-slowed.mp3");
    }

    public void keyPressed() {
        if (key == ' ') {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    public void draw() {
        background(0);

        drawStars(100);

        int planetColor = color(127 * sin(radians(planetColorAngle)) + 128,
                127 * sin(radians(planetColorAngle + 120)) + 128, 127 * sin(radians(planetColorAngle + 240)) + 128);
        float planetSizeOffset = 10 * sin(radians(planetSizeAngle));
        drawPlanet(300, 100, 80 + planetSizeOffset, planetColor);

        // Update floatX and floatY with Perlin noise values
        float floatX = map(noise(time), 0, 1, -width / 2, width / 2);
        float floatY = map(noise(time + 10000), 0, 1, -height / 2, height / 2);

        translate(width / 2, height / 2);
        translate(floatX, floatY);
        drawSpaceship();

        floatAngle += 0.5;
        planetColorAngle += 0.5;
        planetSizeAngle += 1;
        time += 0.001; // Increment time value for Perlin noise
    }

    public void drawStars(int numStars) {
        fill(255);
        for (int i = 0; i < numStars; i++) {
            float x = random(width);
            float y = random(height);
            ellipse(x, y, 1, 1);
        }
    }

    public void drawPlanet(float x, float y, float size, int planetColor) {
        pushMatrix();
        translate(x, y);
        noStroke();

        // Draw the planet with shading
        for (int i = 0; i < 360; i += 10) {
            float angle = radians(i);
            float xOff = cos(angle) * size / 2;
            float yOff = sin(angle) * size / 2;
            int shade = color(hue(planetColor), saturation(planetColor), brightness(planetColor) * (1 - (i / 360.0f)));
            fill(shade);
            ellipse(xOff, yOff, size, size);
        }

        // Draw a textured overlay
        for (int i = 0; i < 50; i++) {
            float xPos = random(-size / 2, size / 2);
            float yPos = random(-size / 2, size / 2);
            float craterSize = random(3, 8);
            float brightnessOffset = random(-20, 20);
            int textureColor = color(hue(planetColor), saturation(planetColor),
                    brightness(planetColor) + brightnessOffset);
            fill(textureColor);
            ellipse(xPos, yPos, craterSize, craterSize);
        }

        popMatrix();
    }

    public void drawSpaceship() {
        fill(0, 0, 255); // Set the spaceship color using HSB values
        
        // Body of the spaceship
        beginShape();
        vertex(0, -20);
        vertex(-10, 10);
        vertex(10, 10);
        endShape(CLOSE);

        // Left wing
        beginShape();
        vertex(-10, 10);
        vertex(-30, 20);
        vertex(-20, 10);
        endShape(CLOSE);

        // Right wing
        beginShape();
        vertex(10, 10);
        vertex(30, 20);
        vertex(20, 10);
        endShape(CLOSE);

       // Engine trail
       float engineTrailSize = 10 + 5 * sin(radians(millis() / 5));
       fill(255, 225, 180);
       for (int i = 0; i < 10; i++) {
           float engineTrailOffset = 10 + 5 * i;
           ellipse(0, 10 + engineTrailOffset, engineTrailSize, engineTrailSize);
       }
    }

}
