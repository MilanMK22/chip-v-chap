package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.awt.*;
import nz.ac.vuw.ecs.swen225.gp22.app.ChipVsChap;
import nz.ac.vuw.ecs.swen225.gp22.app.Controller;
import nz.ac.vuw.ecs.swen225.gp22.app.Keys;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Mapprint;
import nz.ac.vuw.ecs.swen225.gp22.renderer.printInventory;
import sounds.sounds.SOUND;

import java.awt.event.*;

import javax.swing.JOptionPane;

/**
 * This class has the functions for a Auto replay
 * and A play by play replay
 */
public class Recorder {

    public static long prev;

    /**
     * This function decides what action to do based on the
     * string that is given by GameAction.
     *
     * @Author Milan Kriletich
     * @param cur this is the current game we are making changes to
     * @param r   this is the current action we want to use
     */
    public static void doAction(ChipVsChap cur, GameAction r) {

        try {
            if (r.getName().equals("Up")) {
                Controller.action(null, "Up", () -> cur.model.chap().up());
            } else if (r.getName().equals("Down")) {
                Controller.action(null, "Down", () -> cur.model.chap().down());
            } else if (r.getName().equals("Left")) {
                Controller.action(null, "Left", () -> cur.model.chap().left());
            } else if (r.getName().equals("Right")) {
                Controller.action(null, "Right", () -> cur.model.chap().right());
            }
        } catch (Error b) {
        }
    }

    /**
     * This method runs when the replay is over and takes us back to
     * the main menu.
     * 
     * @Author Milan Kriletich
     * @param cur         this is the current game we are making changes to
     * @param replaySpeed if this check box is present in the replay we want to
     *                    remove it
     */
    public static void quitReplay(ChipVsChap cur, Checkbox replaySpeed) {
        ChipVsChap.timerLabel.setText(String.format("%d:%02d", 1, 0));
        if (replaySpeed != null) {
            cur.remove(replaySpeed);
        }
        cur.delay = 50;
        cur.timer.setDelay(50);
        cur.closePhase.run();
        SOUND.GAME.stop();
        cur.menu();
        cur.ReplayListner = null;
        cur.PlaybPlayListner = null;
        return;

    }

    /**
     * This is method does an auto replay and gives an option of a x2 replay speed.
     * 
     * @Author Milan Kriletich
     * @param cur this is the current game we are doing our automatic replay changes
     *            to
     */
    public static void Auto(ChipVsChap cur) {

        Replay rep = Replay.readXML();
        var replaySpeed = new Checkbox("2x Replay Speed");
        replaySpeed.setBounds(190, 5, 180, 20);
        replaySpeed.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    cur.delay = 25;
                    cur.timer.setDelay(25);
                } else {
                    cur.delay = 50;
                    cur.timer.setDelay(50);
                }
            }
        });
        cur.add(replaySpeed);
        if (rep.getLevel() == 1) {
            cur.levelOne();
        } else if (rep.getLevel() == 2) {
            cur.levelTwo();
        } else {
            cur.levelPersistency();
        }

        cur.removeKeyListener(cur.Replistner);
        ActionListener newone = e -> {
            if (rep.getMoves().size() == 1) {
                quitReplay(cur, replaySpeed);
                return;
            }
            GameAction r = rep.getMoves().peek();
            if (r.getTime() == cur.totalticks) {
                prev = r.getTime();
                r = rep.getMoves().remove();
                doAction(cur, r);

                r = rep.getMoves().peek();

                if (r != null) {
                    while (r.getTime() == prev) {
                        prev = r.getTime();
                        r = rep.getMoves().remove();
                        doAction(cur, r);
                    }
                }
            }
        };
        cur.timer.addActionListener(newone);
        cur.ReplayListner = newone;

    }

    /**
     * This method does a play by play replay. each press of the right arrow key
     * will increment the game 1/20th of a second. If you hold down the arrow key
     * the game should play at a normal speed.
     * 
     * 
     * @Author Milan Kriletich
     * @param cur this is the current game we are doing the play by play replay on
     * 
     */
    public static void PlaybPlay(ChipVsChap cur) {
        Replay rep = Replay.readXML();
        if (rep.getLevel() == 1) {
            cur.levelOne();
        } else if (rep.getLevel() == 2) {
            cur.levelTwo();
        } else {
            cur.levelPersistency();
        }
        cur.removeKeyListener(cur.Replistner);
        cur.timer.stop();
        Mapprint.printMap(cur.model, cur.background.getGraphics());

        JOptionPane.showMessageDialog(null, "Hold Right Arrow key \n to step through replay", "InfoBox: " + "Information", JOptionPane.INFORMATION_MESSAGE);

        KeyListener forward = new Keys() {

            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    if (cur.timePassed % 1000 == 0) {
                        if (cur.count == 0) {
                            cur.timer.stop();
                            ChipVsChap.timerLabel.setText("No time");
                        } else {
                            int minutes = cur.count / 60;
                            int seconds = cur.count % 60;
                            ChipVsChap.timerLabel.setText(String.format("%d:%02d", minutes, seconds));
                            cur.count--;
                        }
                    }
                    cur.timePassed += cur.delay;
                    cur.model.tick();
                    cur.totalticks++;
                    Mapprint.printMap(cur.model, cur.background.getGraphics());
                    printInventory.printIn(cur.model, cur.backgroundImage.getGraphics());
                    ChipVsChap.chips.setText("" + (cur.numOfChips - cur.model.chap().heldTreasure()));

                    if (rep.getMoves().size() == 1) {
                        quitReplay(cur, null);
                        cur.removeKeyListener(this);
                        return;
                    }
                    GameAction r = rep.getMoves().peek();

                    if (r.getTime() == cur.totalticks) {
                        prev = r.getTime();
                        r = rep.getMoves().remove();
                        doAction(cur, r);

                        r = rep.getMoves().peek();

                        if (r != null) {
                            while (r.getTime() == prev) {
                                prev = r.getTime();
                                r = rep.getMoves().remove();
                                doAction(cur, r);
                            }
                        }
                    }
                }
            }

        };
        cur.addKeyListener(forward);
        cur.PlaybPlayListner = forward;

    }

}
