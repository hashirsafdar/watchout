
/*
    creates a DelayedRemovalArray of gates in random x locations
    checks last item in array for points and gate respawn
*/

package sfn.watchout;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.DelayedRemovalArray;

public class GateGenerator
{
    DelayedRemovalArray<Gate> newGates;
    int score = 0;

    public GateGenerator()
    {
        newGates = new DelayedRemovalArray<Gate>();
        newGates.add(new Gate(MathUtils.random(Constants.WORLD_SIZE_WIDTH - Constants.GATE_MARGINS)));
    }

    // spawns in new gates, checks for points, removes gates
    // TODO: will conflict with splitscreen
    void update(float delta)
    {
        if(newGates.peek().y > Constants.RESPAWN_POINT)
        {
            newGates.add(new Gate(MathUtils.random(Constants.WORLD_SIZE_WIDTH - Constants.GATE_MARGINS)));
        }

        for(Gate gate : newGates)
        {
            gate.update(delta);
            if(gate.y > Constants.PLAYER_STARTING_POSITION.y && !gate.pointed)
            {
                score++;
                gate.pointed = true;
            }
            if(gate.y > Constants.WORLD_SIZE_HEIGHT + 20)
            {
                newGates.removeValue(gate, false);
            }
        }
    }

    void render(ShapeRenderer renderer)
    {
        for(Gate gate : newGates)
        {
            gate.render(renderer);
        }
    }
}
