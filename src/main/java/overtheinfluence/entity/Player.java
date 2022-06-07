package entity;

import main.*;
import objects.*;

import java.awt.*;
import java.awt.image.*;

/**
 * Over the Influence is a game by Digital Athletics Inc. intended to educate individuals about the dangers of
 * drug addiction and alcoholism, as well as reinforce concepts related to overcoming and avoiding addiction.
 *
 * <p>This is a subclass of Entity that controls the movement and animation of the player.</p>
 *
 * <p>Work Allocation:<ul>
 * <li>Coordinate tracking - Alexander Peng</li>
 * <li>Image implementation - Kevin Zhan</li>
 * <li>Movement animatino - Kevin Zhan</li>
 * <li>Directional movement from keyboard input - Kevin Zhan & Alexander Peng</li>
 * <li>Character display - Kevin Zhan</li>
 * <li>Collision detection - Kevin Zhan</li>
 * </ul></p>
 *
 * <h2>ICS4U0 -with Krasteva, V.</h2>
 *
 * @author Kevin Zhan, Alexander Peng
 * @version 1.0
 */

public class Player extends Entity {
    /**
     * the input monitor for the player's keys
     */
    public KeyInput keyIn;

    /**
     * the player's x and y coordinates on the screen
     */
    public final int screenX, screenY;

    /**
     * additional images only used for the player
     */
    public BufferedImage up3, down3, left3, right3;

    /**
     * default speed
     */
    public final int defaultSpeed;

    /**
     * whether the player is currently debuffed
     */
    public boolean barrierDebuff;

    /**
     * timers for the player's speed debuff and barrier debuff
     */
    public int speedDebuffTimer, barrierDebuffTimer;

    /**
     * whether the player is currently
     */
    public boolean invincible;

    /**
     * timer for invincibility
     */
    public int invincibleTimer;

    /**
     * the Player constructor
     *
     * @param lvl   the level the player is in
     * @param keyIn the input monitor for the player's keys
     */
    public Player(Level lvl, KeyInput keyIn, int speed) {
        super(lvl);
        this.keyIn = keyIn;
        defaultSpeed = speed;

        screenX = lvl.screenWidth / 2 - lvl.tileSize / 2;
        screenY = lvl.screenHeight / 2 - lvl.tileSize / 2;

        if (lvl.levelNum == 1) {
            worldX = lvl.tileSize * 3;
            worldY = (int) (lvl.tileSize * 5.25);
        } else if (lvl.levelNum == 2) {
            worldX = (int) (lvl.tileSize * 1.5);
            worldY = lvl.worldHeight / 2 - lvl.tileSize / 2;
        } else if (lvl.levelNum == 3) {

        }
        this.speed = defaultSpeed;
        direction = "down";
        getPlayerImage();

        area = new Rectangle();
        area.x = 21;
        area.y = 30;
        areaDefaultX = area.x;
        areaDefaultY = area.y;
        area.width = 5;
        area.height = 7;
    }

    /**
     * gets and stores the images for each direction of the player
     */
    public void getPlayerImage() {
        up1 = setup("entities/player/player_up_1");
        up2 = setup("entities/player/player_up_2");
        up3 = setup("entities/player/player_up_3");
        down1 = setup("entities/player/player_down_1");
        down2 = setup("entities/player/player_down_2");
        down3 = setup("entities/player/player_down_3");
        left1 = setup("entities/player/player_left_1");
        left2 = setup("entities/player/player_left_2");
        left3 = setup("entities/player/player_left_3");
        right1 = setup("entities/player/player_right_1");
        right2 = setup("entities/player/player_right_2");
        right3 = setup("entities/player/player_right_3");
    }

    /**
     * updates the player's position using the key input
     */
    public void update() {
        if (speedDebuffTimer > 0 && lvl.gameState == lvl.PLAY_STATE) {
            speed = defaultSpeed / 2;
            speedDebuffTimer--;
            if (speedDebuffTimer == 0) {
                speed = defaultSpeed;
                lvl.ui.showMessage("Speed restored");
            }
        }
        if (barrierDebuffTimer > 0 && lvl.gameState == lvl.PLAY_STATE) {
            if (!barrierDebuff) {
                barrierDebuff = true;
                lvl.assetSetter.barrierDebuff();
            }
            barrierDebuffTimer--;
            if (barrierDebuffTimer == 0) {
                barrierDebuff = false;
                lvl.assetSetter.barrierClear();
                lvl.ui.showMessage("Barriers cleared");
            }
        }
        if (invincibleTimer > 0 && lvl.gameState == lvl.PLAY_STATE) {
            invincibleTimer--;
            if (invincibleTimer == 0) {
                invincible = false;
            }
        }
        //collision detection for interaction purposes
        interactObject(lvl.collisionDetect.objectCollide(this, true), false);
        interactObject(lvl.collisionDetect.entityCollide(this, lvl.blocks, true), true);
        if (keyIn.up || keyIn.down || keyIn.left || keyIn.right) {
            if (keyIn.up) {
                direction = "up";
                worldY -= speed;
            } else if (keyIn.down) {
                direction = "down";
                worldY += speed;
            } else if (keyIn.left) {
                direction = "left";
                worldX -= speed;
            } else if (keyIn.right) {
                direction = "right";
                worldX += speed;
            }

            collidingB = false;
            collidingL = false;
            collidingR = false;
            collidingT = false;
            lvl.collisionDetect.tileCollide(this);
            lvl.collisionDetect.entityCollide(this, lvl.blocks, true);
            lvl.collisionDetect.entityCollide(this, lvl.npcs, true);

            //check object collision and interact with objects
            interactObject(lvl.collisionDetect.objectCollide(this, true), false);

            if (collidingT) {
                worldY += speed;
            }
            if (collidingB) {
                worldY -= speed;
            }
            if (collidingL) {
                worldX += speed;
            }
            if (collidingR) {
                worldX -= speed;
            }


            spriteCnt++; //how long the sprite can be displayed for
            int limiter = 2; //how many frames the sprite can be displayed for
            if(speedDebuffTimer > 0) {
                limiter = 4;
            }
            if (spriteCnt > limiter) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 5;
                } else if (spriteNum == 5) {
                    spriteNum = 6;
                } else if (spriteNum == 6) {
                    spriteNum = 7;
                } else if (spriteNum == 7) {
                    spriteNum = 8;
                } else if (spriteNum == 8 || spriteNum == 9) {
                    spriteNum = 1;
                }
                spriteCnt = 0; //reset the sprite timer
            }
        } else {
            spriteNum = 9;
        }
    }

    /**
     * allows the player to interact with objects
     *
     * @param index the index of the object in its respective arraylist
     * @param block whether the object is a block entity or an object entity
     */
    public void interactObject(int index, boolean block) {
        if (index != -1) {
            String name = "";
            if (block) {
                name = lvl.blocks.get(index).name;
            } else {
                name = lvl.objects.get(index).name;
            }
            switch (name) {
                case "Door":
                    lvl.ui.showMessage("Press E to open door");
                    if (keyIn.interact) {
                        Door temp = (Door) lvl.objects.get(index);
                        lvl.objects.remove(index);
                        lvl.playSFX(2);
                        Thread tempThread = new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                                lvl.objects.add(index, temp);
                            } catch (InterruptedException e) {
                            }
                        });
                        tempThread.start();
                    }
                    break;
                case "TriggerBlock":
                    if (block) {
                        ((TriggerBlock) (lvl.blocks.get(index))).trigger();
                    } else {
                        ((TriggerBlock) (lvl.objects.get(index))).trigger();
                    }
                    break;
            }
        }
    }

    /**
     * draws the player
     *
     * @param g2D the graphics object which provides control over coordinate system and color
     */
    public void draw(Graphics2D g2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1 || spriteNum == 2 || spriteNum == 5 || spriteNum == 6) {
                    image = up1;
                } else if (spriteNum == 3 || spriteNum == 4 || spriteNum == 7 || spriteNum == 8) {
                    image = up2;
                } else if (spriteNum == 9) {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 1 || spriteNum == 2 || spriteNum == 5 || spriteNum == 6) {
                    image = down1;
                } else if (spriteNum == 3 || spriteNum == 4 || spriteNum == 7 || spriteNum == 8) {
                    image = down2;
                } else if (spriteNum == 9) {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNum == 1 || spriteNum == 2 || spriteNum == 3) {
                    image = left1;
                } else if (spriteNum == 5 || spriteNum == 6 || spriteNum == 7) {
                    image = left2;
                } else if (spriteNum == 4 || spriteNum == 8 || spriteNum == 9) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1 || spriteNum == 2 || spriteNum == 3) {
                    image = right1;
                } else if (spriteNum == 5 || spriteNum == 6 || spriteNum == 7) {
                    image = right2;
                } else if (spriteNum == 4 || spriteNum == 8 || spriteNum == 9) {
                    image = right3;
                }
                break;
        }
        int x = screenX;
        int y = screenY;
        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }
        int rightOffset = lvl.screenWidth - screenX;
        if (rightOffset > lvl.worldWidth - worldX || rightOffset < 0 && x < 0) {
            x = lvl.screenWidth - (lvl.worldWidth - worldX);
        }
        int bottomOffset = lvl.screenHeight - screenY;
        if (bottomOffset > lvl.worldHeight - worldY || bottomOffset < 0) {
            y = lvl.screenHeight - (lvl.worldHeight - worldY);
        }

        g2D.drawImage(image, x, y, null);
    }
}
