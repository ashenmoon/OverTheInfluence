package entity;

import main.*;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

/**
 * <p>This is the superclass representing all entities.</p>
 *
 * <p>Work Allocation:<ul>
 * <li>Entity class - Alexander Peng</li>
 * </ul></p>
 *
 * <h2>ICS4U0 -with Krasteva, V.</h2>
 *
 * @author Kevin Zhan, Alexander Peng
 * @version 1.0
 */

public class Entity {
    /**
     * the level the entity is in
     */
    public final Level lvl;

    /**
     * the x and y coordinates of the entity in the world
     */
    public int worldX, worldY;
    /**
     * the player's movement speed
     */
    public int speed;

    /**
     * images for the entity in each direction
     */
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    /**
     * the entity's current direction
     */
    public String direction;

    /**
     * sprite counter for the entity's animation
     */
    public int spriteCnt = 0;
    /**
     * the entity's current sprite number
     */
    public int spriteNum = 1;

    /**
     * the solid area of the entity
     */
    public Rectangle area;

    /**
     * the default values for the solid area of the entity
     */
    public int areaDefaultX, areaDefaultY;

    /**
     * whether the object can be collided with and blocks the player and other objects
     */
    public boolean collision;

    /**
     * the entity's name
     */
    public String name = "";

    /**
     * the entity's current life
     */
    public int life;

    /**
     * the entity's maximum life
     */
    public int maxLife;

    /**
     * the entity's dialogue if applicable
     */
    public final ArrayList<String> dialogue = new ArrayList<>();

    /**
     * constructor for the Entity class
     *
     * @param lvl the level the entity is in
     */
    public Entity(Level lvl) {
        this.lvl = lvl;
        direction = "down";
    }

    /**
     * helps set up the entity image
     *
     * @param path the path to the image
     */
    public BufferedImage setup(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/" + path + ".png")));
            img = scaleImage(img, lvl.tileSize, lvl.tileSize);
        } catch (IOException ignored) {
        }
        return img;
    }

    /**
     * draws the entity
     *
     * @param g2D the graphics object
     */
    public void draw(Graphics2D g2D) {
        int screenX = worldX - lvl.player.worldX + lvl.player.screenX;
        int screenY = worldY - lvl.player.worldY + lvl.player.screenY;
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        //stop moving camera at edge
        if (lvl.player.screenX > lvl.player.worldX) {
            screenX = worldX;
        }
        if (lvl.player.screenY > lvl.player.worldY) {
            screenY = worldY;
        }
        int rightOffset = lvl.screenWidth - lvl.player.screenX;
        int bottomOffset = lvl.screenHeight - lvl.player.screenY;
        if (rightOffset > lvl.worldWidth - lvl.player.worldX) {
            screenX = lvl.screenWidth - lvl.worldWidth + worldX;
        }
        if (bottomOffset > lvl.worldHeight - lvl.player.worldY) {
            screenY = lvl.screenHeight - lvl.worldHeight + worldY;
        }

        //only draws when around player
        if (worldX + lvl.tileSize > lvl.player.worldX - lvl.player.screenX - 16 * lvl.tileSize &&
                worldX - lvl.tileSize < lvl.player.worldX + lvl.player.screenX + 16 * lvl.tileSize &&
                worldY + lvl.tileSize > lvl.player.worldY - lvl.player.screenY - 16 * lvl.tileSize &&
                worldY - lvl.tileSize < lvl.player.worldY + lvl.player.screenY + 16 * lvl.tileSize) {
            g2D.drawImage(image, screenX, screenY, null);
        } else if (lvl.player.screenX > lvl.player.worldX ||
                lvl.player.screenY > lvl.player.worldY ||
                rightOffset > lvl.worldWidth - lvl.player.worldX ||
                bottomOffset > lvl.worldHeight - lvl.player.worldY) {
            g2D.drawImage(image, screenX, screenY, null);
        }
    }

    /**
     * sets the position of the entity in the world
     *
     * @param x the x coordinate to set
     * @param y the y coordinate to set
     */
    public void setPosition(int x, int y) {
        worldX = x;
        worldY = y;
    }

    /**
     * the entity speaks by displaying its dialogue
     */
    public void speak() {
        lvl.speaker = this;
        lvl.gameState = lvl.DIALOGUE_STATE;
    }

    /**
     * resizes the image to the specified size
     * @param image the image to be scaled
     * @param width the width of the scaled image
     * @param height the height of the scaled image
     * @return the scaled image
     */
    public BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(image, 0, 0, width, height, null);
        g2D.dispose();
        return scaledImage;
    }
}
