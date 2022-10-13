package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Controller class that holds the keybindings relating to the movement of Chap.
 * Contains methods used to alter the keybindings in ChipVsChap.
 */
public class Controller {
    public static Character[] characters = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static int[] arrows = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
    public static Character[] characterControls = new Character[]{	'\u2191', '\u2193','\u2190','\u2192'};

    /**
     * Used to translate KeyEvent variables to it's respective character variable.
     * Only used when KeyEvent is an arrow key.
     * 
     * @param e KeyEvent that comes from KeyListener.
     * @return e.getKeyChar() This is the converted KeyEvent into its arrow character.
     */
    public static Character getArrow(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                return '\u2191';
            case KeyEvent.VK_DOWN:
                return '\u2193';
            case KeyEvent.VK_LEFT:
                return '\u2190';
            case KeyEvent.VK_RIGHT:
                return '\u2192';
        }
       return e.getKeyChar();
    }  
    /**
     * This method will return the ExtendedKeyCode for the character argument.
     * Only used to translate arrow key symbols to its KeyEvent variable.
     * 
     * @param c Arrow key letter.
     * @return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c) This is the ExtendedKeyCode for character c.
     */
    public static int getCode(Character c){
        switch(c){
            case '\u2191':
                return KeyEvent.VK_UP;
            case '\u2193':
                return KeyEvent.VK_DOWN;
            case '\u2190':
                return KeyEvent.VK_LEFT;
            case '\u2192':
                return KeyEvent.VK_RIGHT;
        }
       return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
    }  
    /**
     * This method returns an actionlistener relative to the direction that the keybind relates to.
     * 
     * @param code Key code that correlates to the array index of the direction.
     * @param component Button that correlates to the direction being changed.
     * @return actionListener This is specific to direction.
     */
    public static ActionListener reMap(int code, JButton component){
        return (e) -> {
                component.setText("Waiting for input");
                component.addKeyListener(new Keys(){
                    public void keyPressed(KeyEvent e) {
                            if(!Arrays.stream(characterControls).anyMatch(c->getArrow(e).equals(c)) && Arrays.stream(arrows).anyMatch(k->k==e.getExtendedKeyCode())){
                                switch(e.getKeyCode()){
                                    case KeyEvent.VK_UP:
                                    characterControls[code] = '\u2191';
                                        break;
                                    case KeyEvent.VK_DOWN:
                                    characterControls[code] = '\u2193';
                                        break;
                                    case KeyEvent.VK_LEFT:
                                    characterControls[code] = '\u2190';
                                        break;
                                    case KeyEvent.VK_RIGHT:
                                    characterControls[code] = '\u2192';
                                        break;
                                }
                                component.setText(""+ characterControls[code]);
                                component.removeKeyListener(this);
                            }
                            else if(!Arrays.stream(characterControls).anyMatch(c->c==Character.toUpperCase(e.getKeyChar())) && Arrays.stream(characters).anyMatch(c->c==Character.toUpperCase(e.getKeyChar()))){
                                characterControls[code] = Character.toUpperCase(e.getKeyChar());
                                component.setText(""+ characterControls[code]);
                                component.removeKeyListener(this);   
                            }  
                            else{
                            JOptionPane.showMessageDialog(null, "Key is Invalid");
                            component.setText(""+characterControls[code]);
                            component.removeKeyListener(this);
                            }
                    }
                });
            };
        };



}
