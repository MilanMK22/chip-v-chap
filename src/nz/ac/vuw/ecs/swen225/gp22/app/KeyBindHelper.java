package nz.ac.vuw.ecs.swen225.gp22.app;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;


public class KeyBindHelper {

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

    public static ActionListener reMap(int code, JButton component){
        return (e) -> {
                component.setText("Waiting for input");
                component.addKeyListener(new Keys(){
                    public void keyPressed(KeyEvent e) {
                            if(!Arrays.stream(ChipVsChap.characterControls).anyMatch(c->getArrow(e).equals(c)) && Arrays.stream(ChipVsChap.arrows).anyMatch(k->k==e.getExtendedKeyCode())){
                                switch(e.getKeyCode()){
                                    case KeyEvent.VK_UP:
                                    ChipVsChap.characterControls[code] = '\u2191';
                                        break;
                                    case KeyEvent.VK_DOWN:
                                    ChipVsChap.characterControls[code] = '\u2193';
                                        break;
                                    case KeyEvent.VK_LEFT:
                                    ChipVsChap.characterControls[code] = '\u2190';
                                        break;
                                    case KeyEvent.VK_RIGHT:
                                    ChipVsChap.characterControls[code] = '\u2192';
                                        break;
                                }
                                component.setText(""+ ChipVsChap.characterControls[code]);
                                component.removeKeyListener(this);
                            }
                            else if(!Arrays.stream(ChipVsChap.characterControls).anyMatch(c->c==Character.toUpperCase(e.getKeyChar())) && Arrays.stream(ChipVsChap.characters).anyMatch(c->c==Character.toUpperCase(e.getKeyChar()))){
                                ChipVsChap.characterControls[code] = Character.toUpperCase(e.getKeyChar());
                                component.setText(""+ ChipVsChap.characterControls[code]);
                                component.removeKeyListener(this);   
                            }  
                            else{
                            JOptionPane.showMessageDialog(null, "Key is Invalid");
                            component.setText(""+ChipVsChap.characterControls[code]);
                            component.removeKeyListener(this);
                            }
                        updateKeys();
                    }
                });
            };
        };

    private static void updateKeys() {for (int i = 0; i < ChipVsChap.controls.length; i++) {ChipVsChap.controls[i] = java.awt.event.KeyEvent.getExtendedKeyCodeForChar(ChipVsChap.characterControls[i]);}}



}
