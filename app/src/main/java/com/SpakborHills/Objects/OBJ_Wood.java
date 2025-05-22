package com.SpakborHills.Objects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Wood extends SuperObject{
    public OBJ_Wood(){
        name = "Wood";
        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/Wood.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
