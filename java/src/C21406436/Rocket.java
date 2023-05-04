package C21406436;

import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.Random;

import java.util.ArrayList;

public class Rocket extends Visual {

    ArrayList<PVector> trailPositions = new ArrayList<>();
    int trailLength = 50;

    float circleRadius = 200;
    float circleAngle = 0;

    float planetSpeed = 0.2f;

    int startTime;

    // Add a new Planet class
    class Planet {
        float x;
        float y;
        float size;
        int color;
        float speed;

        Planet(float x, float y, float size, int color, float speed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.speed = speed;
        }

        void update() {
            y += speed;
            if (y > height + size) {
                y = -size;
                x = random(width);
            }
        }

        void display() {
            drawRocketPlanet(x, y, size, color);
        }
    }

    class Star {
        PVector position;
        float speed = 10;
        int trailLength;

        Star(float x, float y, float speed, int trailLength) {
            this.position = new PVector(x, y);
            this.speed = speed;
            this.trailLength = trailLength;
        }

        void update() {
            position.y += speed;
            if (position.y > height) {
                position.y = 0;
                position.x = random(width);
            }
        }

        void display() {
            for (int i = 0; i < trailLength; i++) {
                fill(0, 0, 255, 255 * (1 - (i / (float) trailLength)));
                ellipse(position.x, position.y - i * speed, 1, 1);
            }
        }
    }

    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Planet> planets = new ArrayList<>();

    public void settings() {
        size(1024, 500, P3D);
    }

    public void setup() {
        colorMode(HSB); // Set the color mode to HSB
        noStroke();
        startMinim();

        // Call loadAudio to load an audio file to process
        loadAudio("cantlie-slowed.mp3");

        createRandomPlanets(6);
        startTime = millis();
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
    
        for (Star star : stars) {
            star.update();
            star.display();
        }
    
        // Check if the elapsed time is greater than 5 seconds (5000 milliseconds)
        if (millis() - startTime > 5000) {
            for (Planet planet : planets) {
                planet.update();
                planet.display();
            }
        }
    
        // Calculate the position of the spaceship moving in a circle
        float spaceshipX = circleRadius * cos(radians(circleAngle));
        float spaceshipY = circleRadius * sin(radians(circleAngle));
    
        translate(width / 2, height / 2);
        translate(spaceshipX, spaceshipY);
        drawRocket();
    
        circleAngle += 0.4f;
    }    
    

    public void drawStars(int numStars) {
        if (stars.size() < numStars) {
            for (int i = stars.size(); i < numStars; i++) {
                float x = random(width);
                float y = random(height);
                float speed = random(1, 3);
                int trailLength = (int) random(5, 20);
                stars.add(new Star(x, y, speed, trailLength));
            }
        }
    }

    public void createRandomPlanets(int numPlanets) {
        for (int i = 0; i < numPlanets; i++) {
            float x = random(width);
            float y = -random(height) * (i + 1); // Multiply the random height value by (i + 1)
            float size = random(50, 200);
            int color = color(random(255), random(255), random(255));
            float speed = random(1, 3);
            planets.add(new Planet(x, y, size, color, speed));
        }
    }
    
    

    public void drawRocketPlanet(float x, float y, float size, int planetColor) {
        pushMatrix();
        translate(x, y);
        noStroke();
    
        for (int i = 0; i < 360; i += 5) {
            float angle = radians(i);
            float angleNext = radians(i + 5);
    
            float x1 = cos(angle) * size / 2;
            float y1 = sin(angle) * size / 2;
            float x2 = cos(angleNext) * size / 2;
            float y2 = sin(angleNext) * size / 2;
    
            float distance1 = dist(x1, y1, 0, 0);
            float distance2 = dist(x2, y2, 0, 0);
    
            int shade1 = color(hue(planetColor), map(distance1, 0, size / 2, 0, 100), brightness(planetColor) * (1 - (i / 360.0f)));
            int shade2 = color(hue(planetColor), map(distance2, 0, size / 2, 0, 100), brightness(planetColor) * (1 - ((i + 5) / 360.0f)));
    
            beginShape();
            fill(shade1);
            vertex(x1, y1);
            fill(shade2);
            vertex(x2, y2);
            fill(planetColor);
            vertex(0, 0);
            endShape(CLOSE);
        }
    
        // Glowing aura around the planet
        for (int i = 0; i < 10; i++) {
            int glowColor = color(hue(planetColor), 100, 100, 50 - i * 5);
            fill(glowColor);
            ellipse(0, 0, size + i * 10, size + i * 10);
        }
    
        popMatrix();
    }
    
      

      public void drawRocket() {
        fill(0, 0, 255); // Set the rocket color using HSB values
        
        // Body of the rocket
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
        for (int i = 0; i < 10; i++) {
            float engineTrailSize = 10 + 5 * sin(radians(millis() / 5)) * (1 - (i / 10.0f));
            float engineTrailOffset = 10 + 5 * i;
            fill(255, 225, 180, 255 - i * 20); // Added transparency to the trail
            ellipse(0, 10 + engineTrailOffset, engineTrailSize, engineTrailSize);
        }
    }
    

}
