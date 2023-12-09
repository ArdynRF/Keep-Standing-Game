package model;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Abstract class representing a game object.
 * The class contains properties for position, size, and collision detection box.
 */
public abstract class GameObject {

    // Position properties
    protected float x;
    protected float y;
    // Size properties
    protected int width;
    protected int height;
    // Collision detection box
    protected Rectangle collisionBox;
    
    public GameObject(float x, float y, int width, int height) {
        // Constructor
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        // Create a new collision box
        this.collisionBox = new Rectangle((int)x, (int)y, width, height);
    }
    
    protected void updateCollisionBox(){
        // Update the position of the collision box to match the object's position
        this.collisionBox.x = (int) x;
        this.collisionBox.y = (int) y;
    }

    public Rectangle getCollisionBox() {
        // Get the collision box (as a Rectangle)
        return this.collisionBox;
    }
    
    /**
     * Renders the game object.
     * Subclasses must implement this method to define the specific rendering logic.
     * 
     * @param g The Graphics object used for rendering.
     */
    public abstract void render(Graphics g);
    
}
