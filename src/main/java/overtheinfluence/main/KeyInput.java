package main;

import java.awt.event.*;

/**
 * <p>This class processes and manages all keyboard input for different controls.</p>
 *
 * <p>Work Allocation:<ul>
 * <li>KeyInput- Alexander Peng</li>
 * </ul></p>
 *
 * <h2>ICS4U0 -with Krasteva, V.</h2>
 *
 * @author Kevin Zhan, Alexander Peng
 * @version 1.0
 */

public class KeyInput implements KeyListener {

    /**
     * whether the player is moving in a specific direction
     */
    public boolean up, down, left, right;

    /**
     * whether the player is interacting with an object
     */
    public boolean interact;

    /**
     * whether the enter key has been pressed
     */
    public boolean enter;

    /**
     * whether to retry the level
     */
    public boolean retry;

    /**
     * whether the escape key has been pressed
     */
    public boolean escape;

    /**
     * whether the shift key has been pressed
     */
    public boolean shift;

    /**
     * the level the game is on
     */
    final Level lvl;

    /**
     * constructor for the KeyInput class
     */
    public KeyInput(Level lvl) {
        this.lvl = lvl;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (lvl.started) {
            if (key == KeyEvent.VK_W) {
                up = true;
            }
            if (key == KeyEvent.VK_A) {
                left = true;
            }
            if (key == KeyEvent.VK_S) {
                down = true;
            }
            if (key == KeyEvent.VK_D) {
                right = true;
            }
            if (key == KeyEvent.VK_E) {
                interact = true;
            }
        }
        if (key == KeyEvent.VK_P) {
            if (lvl.gameState == lvl.PLAY_STATE) {
                lvl.gameState = lvl.PAUSE_STATE;
                lvl.stopMusic();
            } else if (lvl.gameState == lvl.PAUSE_STATE) {
                lvl.gameState = lvl.PLAY_STATE;
                lvl.playMusic(-1);
            }
        }
        if (key == KeyEvent.VK_1) {
            if (lvl.ui.question != null && !lvl.ui.question.complete) {
                lvl.ui.question.selected = 1;
            }
            if (lvl.gameState == lvl.INVENTORY_STATE) {
                lvl.ui.inventorySelect = 1;
            }
        }
        if (key == KeyEvent.VK_2) {
            if (lvl.ui.question != null && !lvl.ui.question.complete) {
                lvl.ui.question.selected = 2;
            }
            if (lvl.gameState == lvl.INVENTORY_STATE) {
                lvl.ui.inventorySelect = 2;
            }
        }
        if (key == KeyEvent.VK_3) {
            if (lvl.ui.question != null && !lvl.ui.question.complete) {
                lvl.ui.question.selected = 3;
            }
        }
        if (key == KeyEvent.VK_4) {
            if (lvl.ui.question != null && !lvl.ui.question.complete) {
                lvl.ui.question.selected = 4;
            }
        }
        if (key == KeyEvent.VK_ENTER) {
            enter = true;
        }
        if (key == KeyEvent.VK_R) {
            retry = true;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            escape = true;
            if (lvl.gameState == lvl.INVENTORY_STATE) {
                lvl.gameState = lvl.PLAY_STATE;
                lvl.ui.inventorySelect = -1;
            }
        }
        if (key == KeyEvent.VK_SHIFT && lvl.gameState == lvl.PLAY_STATE) {
            shift = true;
        }
        if (key == KeyEvent.VK_I && lvl.gameState == lvl.PLAY_STATE && lvl.levelNum == 3) {
            lvl.gameState = lvl.INVENTORY_STATE;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            up = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_D) {
            right = false;
        }
        if (key == KeyEvent.VK_E) {
            interact = false;
        }
        if (key == KeyEvent.VK_ENTER) {
            enter = false;
            lvl.ui.dialogueReady = true;
        }
        if (key == KeyEvent.VK_R) {
            retry = false;
        }
        if (key == KeyEvent.VK_ESCAPE) {
            escape = false;
        }
        if (key == KeyEvent.VK_SHIFT) {
            shift = false;
        }
    }
}
