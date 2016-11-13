package sfn.watchout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static final float WORLD_SIZE_HEIGHT = 160;
    public static final float WORLD_SIZE_WIDTH = 90;
    public static final Color BACKGROUND_COLOR = new Color(0.792f, 0.776f, 0.325f, 1);

    public static final Vector2 MENU_PLAYBUTTON = new Vector2(WORLD_SIZE_WIDTH/2, WORLD_SIZE_HEIGHT/2);
    public static final float MENU_PLAYBUTTON_RADIUS = 10;

    public static final Vector2 PLAYER_STARTING_POSITION = new Vector2(WORLD_SIZE_WIDTH/2, Constants.WORLD_SIZE_HEIGHT - 40);
    public static final float PLAYER_MOVEMENT_SPEED = 40;
    public static final float PLAYER_SIZE = 10;
    public static final Color PLAYER_COLOR = new Color(0.373f, 0.361f, 0.080f, 1);

    public static final float GATE_HEIGHT = 10;
    public static final float GAP_WIDTH = 15;
    public static final float GATE_SPEED = 30;
    public static final float RESPAWN_POINT = WORLD_SIZE_HEIGHT/4;
    public static final float GATE_STARTING_Y = -10;
    public static final float GATE_LEFT_MARGIN = WORLD_SIZE_WIDTH/6;
    public static final float GATE_MARGINS = WORLD_SIZE_HEIGHT/3;
}
