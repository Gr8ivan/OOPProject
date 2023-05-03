package C21406436;

import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Rocket extends Visual {

    float floatAngle = 0;
    float planetColorAngle = 0;
    float planetSizeAngle = 0;
    float time = 0;
    ArrayList<PVector> trailPositions = new ArrayList<>();
    int trailLength = 50;

    float circleRadius = 200;
    float circleAngle = 0;

    float planetSpeed = 0.2f;


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

    public void settings() {
        size(800, 800);
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
    
        drawStars(10);
        

        for (Star star : stars) {
            star.update();
            star.display();
        }
    
        float planetSize = 80 + 10 * sin(radians(planetSizeAngle));
        float planetColor = color(127 * sin(radians(planetColorAngle)) + 128,
                127 * sin(radians(planetColorAngle + 120)) + 128, 127 * sin(radians(planetColorAngle + 240)) + 128);
         // Set the planet position to the top left corner
         float planetX = 100; // Change this value to adjust the X position
         float planetY = 150; // Change this value to adjust the Y position
         drawRocketPlanet(planetX, planetY, planetSize, (int) planetColor);
     
        // Calculate the position of the spaceship moving in a circle
        float spaceshipX = circleRadius * cos(radians(circleAngle));
        float spaceshipY = circleRadius * sin(radians(circleAngle));
    
        translate(width / 2, height / 2);
        translate(spaceshipX, spaceshipY);
        drawRocket();
    
        circleAngle += 0.4f;
        planetColorAngle += 0.1f;
        planetSizeAngle += 0.5f;

       
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

    public void drawRocketPlanet(float x, float y, float size, int planetColor) {
        pushMatrix();
        translate(x, y);
        noStroke();
        colorMode(HSB);
        
        // planet with shading and gradient
        for (int i = 0; i < 360; i += 5) {
          float angle = radians(i);
          float xOff = cos(angle) * size / 2;
          float yOff = sin(angle) * size / 2;
          float distance = dist(xOff, yOff, 0, 0);
          int shade = color(hue(planetColor), map(distance, 0, size/2, 0, 100), brightness(planetColor) * (1 - (i / 360.0f)));
          fill(shade);
          ellipse(xOff, yOff, size, size);
        }
      
      
        // glowing aura around the planet
        for (int i = 0; i < 10; i++) {
          int glowColor = color(hue(planetColor), 100, 100, 50 - i*5);
          fill(glowColor);
          ellipse(0, 0, size + i*10, size + i*10);
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
