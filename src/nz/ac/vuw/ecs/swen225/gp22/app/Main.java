package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.SwingUtilities;

/*
 * This method will create a new ChipVsChap object in which the game will run from.
 * Uses Swing.
 */

public class Main {
  public static void main(String[]a){
    SwingUtilities.invokeLater(ChipVsChap::new);
  }
}