package objects;

import entity.Entity;
import main.java.GamePanel;

public class OBJ_Wood extends Entity {

    public OBJ_Wood(GamePanel gp){
        super(gp);
        name = "Wood";
        down1 = setup("objects/Wood");
    }
}
