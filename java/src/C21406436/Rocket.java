package C21406436;

import ie.tudublin.Visual;

public class Rocket extends Visual {

    float floatAngle = 0;
    float planetColorAngle = 0;
    float planetSizeAngle = 0;

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        colorMode(HSB);
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
        
        int planetColor = color(127 * sin(radians(planetColorAngle)) + 128, 127 * sin(radians(planetColorAngle + 120)) + 128, 127 * sin(radians(planetColorAngle + 240)) + 128);
        float planetSizeOffset = 10 * sin(radians(planetSizeAngle));
        drawPlanet(200, 100, 80 + planetSizeOffset, planetColor);
        
        translate(width / 2, height / 2);
        float floatX = sin(radians(floatAngle)) * 10;
        float floatY = cos(radians(floatAngle)) * 10;
        translate(floatX, floatY);
        
        drawSpaceship();
        floatAngle += 0.5;
        planetColorAngle += 0.5;
        planetSizeAngle += 1;
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
        fill(planetColor);
        ellipse(x, y, size, size);
    }

    public void drawSpaceship() {
        fill(255);
        
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
    }
}





