package imgs;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public enum Img{
    floor_tiles,
    walls,
    exit,
    yellow_lock,
    yellow_key,
    red_key,
    red_lock,
    green_key,
    green_lock,
    blue_key,
    blue_lock,
    info,
    fullmap,
    exitlock,
    coin,
    Marco, 
    homeScreen;
    
public final BufferedImage image;
Img(){image=loadImage(this.name());}
static private BufferedImage loadImage(String name){
  URL imagePath = Img.class.getResource(name+".png");
  try{return ImageIO.read(imagePath);}
  catch(IOException e) { throw new Error(e); }
}
}

