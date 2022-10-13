package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Reduces the code needed for overrides.
 */
public interface Keys extends KeyListener {
  /**
   * Key typed keylistener
   */
  default public void keyTyped(KeyEvent e){}
   /**
   * Key pressed keylistener
   */
  void keyPressed(KeyEvent e);
   /**
   * Key released keylistener
   */
  default public void keyReleased(KeyEvent e){}
}

