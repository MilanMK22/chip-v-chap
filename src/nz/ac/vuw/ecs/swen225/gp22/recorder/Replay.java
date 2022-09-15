package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.util.Stack;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;




public class Replay {
  
	private Stack<String> moves;
	private int level;
	

	public Replay(Stack<String> moves, int level) {
		this.moves = moves;
		this.level = level;
	}
	
	
	
	public Stack<String> getMoves() {
		return moves;
	}

	public void setMoves(Stack<String> moves) {
		this.moves = moves;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Replay() {
		
	}
	// will create an xml file using the moves stack and the level number
	public void SaveReplay() {
		
		try {
			FileOutputStream fos = new FileOutputStream(new File("./replay-level" + level + ".xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(this);
			encoder.close();
			fos.close();
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	
	
}

