package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface Keys extends KeyListener {
  default public void keyTyped(KeyEvent e){}
  void keyPressed(KeyEvent e);
  default public void keyReleased(KeyEvent e){}
}