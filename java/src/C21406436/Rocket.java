/*
This program is a visual simulation that displays a rocket orbiting around the center of the screen in a 2D environment with stars and planets in the background. 
The rocket has a trail of engine fire, and the planets have a glowing aura. 
Features separate classes for the rocket, Rocketstars, and Rocketplanets, each with their own properties and behavior. 
The main drawing loop handles updating and displaying all the objects on the canvas.
 */


package C21406436;

// Import required libraries
import ie.tudublin.Visual;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;

// Rocket class which extends Visual class
public class Rocket extends Visual {

    // Variables for rocket trail
    ArrayList<PVector> trailPositions = new ArrayList<>();
    int trailLength = 50;

    // circular movement
    float circleRadius = 200;
    float circleAngle = 0;
    float planetSpeed = 0.2f;
    int startTime;

    // Add PApplet parent
    PApplet parent;
    boolean setupCalled = false;

    // ArrayLists to hold Star and Planet objects
    ArrayList<RocketStar> stars = new ArrayList<>();
    ArrayList<RocketPlanet> planets = new ArrayList<>();

    // rocket Constructor for PApplet parent
    public Rocket(PApplet parent) {
        this.parent = parent;
    }

    // Planet class to represent planet objects
    class RocketPlanet {
        float x;
        float y;
        float size;
        int color;
        float speed;
        int canvasHeight = 1180;
        int canvasWidth = 1920;

        // Constructor for planet
        RocketPlanet(float x, float y, float size, int color, float speed) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
            this.speed = speed;
        }

        // Update planet position
        void update() {
            y += speed;
            if (y > canvasHeight) {
                y = -size; // resets position
                x = random(canvasWidth);
            }
        }

        // Display planet calling drawRocketPlanet method
        void display(PApplet parent) {
            drawRocketPlanet(x, y, size, color);
        }
    }

    // Star class to represent star objects
    class RocketStar {
        PVector position; // 2D vector for position of stars
        float speed = 10; // default fall speed
        int trailLength;
        int canvasHeight = 1080;
        int canvasWidth = 1920;

        // Constructor
        RocketStar(float x, float y, float speed, int trailLength) {
            this.position = new PVector(x, y);
            this.speed = speed;
            this.trailLength = trailLength;
        }

        // Update star position
        void update() {
            position.y += speed; // increase the speed of falling star
            if (position.y > canvasHeight) {
                position.y = 0; // resets position
                position.x = random(canvasWidth); // gives random new x value
            }
        }

        // Display star
        void display() {
            for (int i = 0; i < trailLength; i++) {
                parent.fill(0, 0, 255, 255 * (1 - (i / (float) trailLength)));
                parent.ellipse(position.x, position.y - i * speed, 1, 1); // trail effect with ellipse offset of i * speed
            }
        }
    }

    // Set up canvas size and rendering mode
    public void settings() {
        size(1024, 500, P3D);
    }

    // Set up initial state, create stars and planets
    public void setup() {
        drawStars(120, parent);
        createRandomPlanets(8);
        startTime = parent.millis(); // records start time in milliseconds

    }

    // Main drawing loop
    public void draw(PApplet parent) {

        // To see if setup is called but only once
        if (!setupCalled) {
            setup();
            setupCalled = true;
        }

        // Update and display stars
        // Iterates through each star object in stars array
        for (RocketStar star : stars) {
            star.update();
            star.display();
        }

        // Update and display planets after 5 seconds
        if (parent.millis() - startTime > 5000) {

            // iterates through each planet object in planets array
            for (RocketPlanet planet : planets) {
                planet.update(); // updates planet position
                planet.display(parent);
            }
        }

        // Calculate spaceship position and draw rocket
        float spaceshipX = circleRadius * PApplet.cos(PApplet.radians(circleAngle)); // calculates x position of rocket based off circle radius and circleAngle
        float spaceshipY = circleRadius * PApplet.sin(PApplet.radians(circleAngle)); // calculates y position of rocket based off circle radius and circleAngle

        parent.translate(parent.width / 2, parent.height / 2);
        parent.translate(spaceshipX, spaceshipY);
        drawRocket(parent); // draws the rocket at translated origin

        circleAngle += 0.4f; // Increments the circleAngle to make the rocket orbit around the center of the canvas
    }

    // Create stars
    public void drawStars(int numStars, PApplet parent) {

        if (stars.size() < numStars) {
            for (int i = stars.size(); i < numStars; i++) {
                float x = parent.random(parent.width);
                float y = parent.random(parent.height);
                float speed = parent.random(1, 3);
                int trailLength = (int) parent.random(5, 20);
                stars.add(new RocketStar(x, y, speed, trailLength));
            }
        }
    }

    // Create random planets
    public void createRandomPlanets(int numPlanets) {
        for (int i = 0; i < numPlanets; i++) {
            float x = parent.random(parent.width);
            float y = -parent.random(parent.height) * (i + 1);
            float size = parent.random(50, 200);
            int color = parent.color(parent.random(255), parent.random(255), parent.random(255));
            float speed = parent.random(1, 3);
            planets.add(new RocketPlanet(x, y, size, color, speed));
        }
    }

    // Draw rocket planet
    public void drawRocketPlanet(float x, float y, float size, int planetColor) {
        parent.pushMatrix(); // Saves the current transformation matrix
        parent.translate(x, y); // so planet will be drawn at x,y position
        parent.noStroke();

        // Draw planet surface with shading
        for (int i = 0; i < 360; i += 5) {
            float angle = PApplet.radians(i); // Converts the current angle in degrees to radians.
            float angleNext = PApplet.radians(i + 5); // Converts the next angle in degrees to radians

            // calculates the coordinates of two vertices on the circumference of the planet
            float x1 = PApplet.cos(angle) * size / 2;
            float y1 = PApplet.sin(angle) * size / 2;
            float x2 = PApplet.cos(angleNext) * size / 2;
            float y2 = PApplet.sin(angleNext) * size / 2;

            // calculates the distance of the two vertices from planets center
            float distance1 = PApplet.dist(x1, y1, 0, 0);
            float distance2 = PApplet.dist(x2, y2, 0, 0);

            // shading calculated from planets center and the planets colour to give crater
            // look
            int shade1 = parent.color(parent.hue(planetColor), PApplet.map(distance1, 0, size / 2, 0, 100),
                    parent.brightness(planetColor) * (1 - (i / 360.0f)));
            int shade2 = parent.color(parent.hue(planetColor), PApplet.map(distance2, 0, size / 2, 0, 100),
                    parent.brightness(planetColor) * (1 - ((i + 5) / 360.0f)));

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
            int glowColor = parent.color(parent.hue(planetColor), 100, 100, 50 - i * 5); // calculates current ellipse and decreases alpha value by 5
                                                                                                                                                     
            parent.fill(glowColor);
            parent.ellipse(0, 0, size + i * 10, size + i * 10); // draws ellipses at the center increasings to create glow effect
        }

        parent.popMatrix(); // used to reset transformation at the begining of drawRocketPlanet
    }

    // Draw rocket
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
