package main;

import java.io.*;
import java.util.*;

/**
 * <p>This class contains the information of one question that the player will answer at certain stages.</p>
 *
 * <p>Work Allocation:<ul>
 * <li>Question class - Kevin Zhan</li>
 * </ul></p>
 *
 * <h2>ICS4U0 -with Krasteva, V.</h2>
 *
 * @author Kevin Zhan, Alexander Peng
 * @version 1.0
 */

public class Question {

    /**
     * the lines of text that contain the question
     */
    public final ArrayList<String> lines = new ArrayList<>();

    /**
     * the different options that the player can choose from
     */
    public final ArrayList<String> options = new ArrayList<>();

    /**
     * the number of the correct answer
     */
    public int answer;

    /**
     * the current option the player has selected
     */
    public int selected = -1;

    /**
     * whether this is the player's second attempt at answering the question
     */
    public boolean secondAttempt;

    /**
     * whether the player has answered completed the question
     */
    public boolean complete = false;

    /**
     * constructor for Question objects
     *
     * @param ui  the user interface used to display the question
     * @param num the number of the question
     */
    public Question(UI ui, int num) {
        BufferedReader br = null;
        if (ui.lvl.levelNum == 2) {
            br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/resources/questions/lvl2/question" + num + ".txt"))));
        } else if (ui.lvl.levelNum == 3) {
            br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/resources/questions/lvl3/question" + num + ".txt"))));
        }
        if (ui.lvl.levelNum == 2 || ui.lvl.levelNum == 3) {
            try {
                String line = br.readLine();
                while (line != null) {
                    lines.add(line);
                    line = br.readLine();
                }
            } catch (IOException ignored) {
            }
            answer = Integer.parseInt(lines.remove(lines.size() - 1));
            for (int i = 0; i < 6; i++) {
                options.add(lines.remove(lines.size() - 1));
            }
            Collections.reverse(options);
        }
    }
}
