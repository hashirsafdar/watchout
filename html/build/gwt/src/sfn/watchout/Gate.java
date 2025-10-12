
/*
    Responsible for creating the gate, rendering it, and holding the gap's location
    uses two separate rectangles for ez collision using overlap
 */

package sfn.watchout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Gate
{
    float y;

    public Rectangle left;
    public Rectangle right;

    // check if gate has been scored yet, true if point given and above player
    boolean pointed;

    // creates left and right rectangle creating a gap at x with size at constant
    public Gate(float x)
    {
        x += Constants.GATE_LEFT_MARGIN;
        y = Constants.GATE_STARTING_Y;
        left = new Rectangle(0, y, x - Constants.GAP_WIDTH/2, Constants.GATE_HEIGHT);
        right = new Rectangle(x + Constants.GAP_WIDTH/2, y, Constants.WORLD_SIZE_WIDTH - x, Constants.GATE_HEIGHT);
        pointed = false;
    }

    // moves gate up, keeping x constant
    // TODO: will conflict with splitscreen
    public void update(float delta)
    {
        y += delta * Constants.GATE_SPEED;
        left.y = y;
        right.y = y;
    }

    void render(ShapeRenderer renderer)
    {
        // renders both sids of gate

        renderer.rect(
                left.x,
                left.y,
                left.getWidth(),
                left.getHeight(),
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR
        );

        // write color 4 times cuz idk how this works lmao

        renderer.rect(
                right.x,
                right.y,
                right.getWidth(),
                right.getHeight(),
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR
        );
    }
}
