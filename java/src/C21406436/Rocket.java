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
    PApplet parent;

    public Rocket(PApplet parent) {
    this.parent = parent;
}
    


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

        void display(PApplet parent) {
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
                parent.fill(0, 0, 255, 255 * (1 - (i / (float) trailLength)));
                parent.ellipse(position.x, position.y - i * speed, 1, 1);
            }
        }
    }

    ArrayList<Star> stars = new ArrayList<>();
    ArrayList<Planet> planets = new ArrayList<>();


    public void settings() {
        size(1024, 500, P3D);

    }

    

    public void setup() {
        colorMode(HSB, 255);
        
        parent.noStroke();
        

        // Call loadAudio to load an audio file to process
        

        createRandomPlanets(6);
        startTime = parent.millis();
    }

    
   

    public void draw(PApplet parent) {
        
    
        drawStars(100, parent);
    
        for (Star star : stars) {
            star.update();
            star.display();
        }
    
        // Check if the elapsed time is greater than 5 seconds (5000 milliseconds)
        if (parent.millis() - startTime > 5000) {
            for (Planet planet : planets) {
                planet.update();
                planet.display(parent);
            }
        }
    
        // Calculate the position of the spaceship moving in a circle
        float spaceshipX = circleRadius * PApplet.cos(PApplet.radians(circleAngle));
        float spaceshipY = circleRadius * PApplet.sin(PApplet.radians(circleAngle));
    
        parent.translate(parent.width / 2, parent.height / 2);
        parent.translate(spaceshipX, spaceshipY);
        drawRocket(parent);
    
        circleAngle += 0.4f;
    }    
    

    public void drawStars(int numStars, PApplet parent) {
        if (stars.size() < numStars) {
            for (int i = stars.size(); i < numStars; i++) {
                float x = parent.random(parent.width);
                float y = parent.random(parent.height);
                float speed = parent.random(1, 3);
                int trailLength = (int) parent.random(5, 20);
                stars.add(new Star(x, y, speed, trailLength));
            }
        }
    }

    public void createRandomPlanets(int numPlanets) {
        for (int i = 0; i < numPlanets; i++) {
            float x = parent.random(parent.width);
            float y = -parent.random(parent.height) * (i + 1); // Multiply the random height value by (i + 1)
            float size = parent.random(50, 200);
            int color = parent.color(parent.random(255), parent.random(255), parent.random(255));
            float speed = parent.random(1, 3);
            planets.add(new Planet(x, y, size, color, speed));
        }
    }
    
    

    public void drawRocketPlanet(float x, float y, float size, int planetColor) {
        parent.pushMatrix();
        parent.translate(x, y);
        parent.noStroke();
    
        for (int i = 0; i < 360; i += 5) {
            float angle = PApplet.radians(i);
            float angleNext = PApplet.radians(i + 5);
    
            float x1 = PApplet.cos(angle) * size / 2;
            float y1 = PApplet.sin(angle) * size / 2;
            float x2 = PApplet.cos(angleNext) * size / 2;
            float y2 = PApplet.sin(angleNext) * size / 2;
    
            float distance1 = PApplet.dist(x1, y1, 0, 0);
            float distance2 = PApplet.dist(x2, y2, 0, 0);
    
            int shade1 = parent.color(parent.hue(planetColor), PApplet.map(distance1, 0, size / 2, 0, 100), parent.brightness(planetColor) * (1 - (i / 360.0f)));
            int shade2 = parent.color(parent.hue(planetColor), PApplet.map(distance2, 0, size / 2, 0, 100), parent.brightness(planetColor) * (1 - ((i + 5) / 360.0f)));
    
            parent.beginShape();
            parent.fill(shade1);
            parent.vertex(x1, y1);
            parent.fill(shade2);
            parent.vertex(x2, y2);
            parent.fill(planetColor);
            parent.vertex(0, 0);
            parent.endShape(CLOSE);
        }
    
        // Glowing aura around the planet
        for (int i = 0; i < 10; i++) {
            int glowColor = parent.color(parent.hue(planetColor), 100, 100, 50 - i * 5);
            parent.fill(glowColor);
            parent.ellipse(0, 0, size + i * 10, size + i * 10);
        }
    
        parent.popMatrix();
    }
    
      

      public void drawRocket(PApplet parent) {
        parent.fill(0, 0, 255); // Set the rocket color using HSB values
        
        // Body of the rocket
        parent.beginShape();
        parent.vertex(0, -20);
        parent.vertex(-10, 10);
        parent.vertex(10, 10);
        parent.endShape(CLOSE);
    
        // Left wing
        parent.beginShape();
        parent.vertex(-10, 10);
        parent.vertex(-30, 20);
        parent.vertex(-20, 10);
        parent.endShape(CLOSE);
    
        // Right wing
        parent.beginShape();
        parent.vertex(10, 10);
        parent.vertex(30, 20);
        parent.vertex(20, 10);
        parent.endShape(CLOSE);
    
        // Engine trail
        for (int i = 0; i < 10; i++) {
            float engineTrailSize = 10 + 5 * PApplet.sin(PApplet.radians(millis() / 5)) * (1 - (i / 10.0f));
            float engineTrailOffset = 10 + 5 * i;
            parent.fill(255, 225, 180, 255 - i * 20); // Added transparency to the trail
            parent.ellipse(0, 10 + engineTrailOffset, engineTrailSize, engineTrailSize);
        }
    }
    

}
