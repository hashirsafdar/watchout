
/*
    spawns in player, has collision checker, deals with movement
*/

package sfn.watchout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends InputAdapter
{
    public static final String TAG = Player.class.getName();

    Viewport viewport;

    Vector2 position;
    Vector2 touchPosition;

    Rectangle collisionRect;

    public Player(Viewport viewport)
    {
        this.viewport = viewport;
        position = Constants.PLAYER_STARTING_POSITION;
        Gdx.input.setInputProcessor(this);
    }

    void update(float delta)
    {
        if(Gdx.input.isKeyPressed(Keys.LEFT))
        {
            moveLeft(delta);
        }
        else if(Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            moveRight(delta);
        }
    }

    void moveLeft(float delta)
    {
        position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
    }

    void moveRight(float delta)
    {
        position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
    }

    // makes player follow your finger
    // TODO: do something if user chooses to not keep their finger down
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touchPosition = viewport.unproject(new Vector2(screenX, screenY));
        position.x = touchPosition.x - Constants.PLAYER_SIZE/2;
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touchPosition = viewport.unproject(new Vector2(screenX, screenY));
        position.x = touchPosition.x - Constants.PLAYER_SIZE/2;
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        touchPosition = viewport.unproject(new Vector2(screenX, screenY));
        position.x = touchPosition.x - Constants.PLAYER_SIZE/2;
        return false;
    }

    // checks for collision by creating a collision rectangle of the player and directly with gates
    public boolean isHit(GateGenerator generator)
    {
        // creates a rectangle for the player for collision to use .overlap

        collisionRect = new Rectangle(
                position.x,
                position.y,
                Constants.PLAYER_SIZE,
                Constants.PLAYER_SIZE
        );

        for(Gate gate : generator.newGates)
        {
            if(collisionRect.overlaps(gate.left) || collisionRect.overlaps(gate.right))
            {
                return true;
            }
        }

        return false;
    }

    void render(ShapeRenderer renderer)
    {
        renderer.rect(
                position.x,
                position.y,
                Constants.PLAYER_SIZE,
                Constants.PLAYER_SIZE,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR,
                Constants.PLAYER_COLOR
        );
    }
}
