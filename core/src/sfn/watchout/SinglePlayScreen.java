
/*
    game screen for single player, also deals with HUD
*/

package sfn.watchout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SinglePlayScreen extends ScreenAdapter
{
    static final String TAG = SinglePlayScreen.class.getName();

    WatchOutGame game;

    Viewport viewport;
    ShapeRenderer renderer;

    Player player;
    GateGenerator gateGenerator;

    Viewport HUDviewport;
    SpriteBatch batch;
    BitmapFont font;
    int topScore;
    Preferences prefs;

    public SinglePlayScreen(WatchOutGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        viewport = new FitViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        player = new Player(viewport);
        gateGenerator = new GateGenerator();

        // scale set to 5 cuz that works for android but TODO
        HUDviewport = new ScreenViewport();

        prefs = Gdx.app.getPreferences("watchoutprefs");
        topScore = prefs.getInteger("topscore", 0);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(5);
    }

    @Override
    public void render(float delta)
    {
        update(delta);

        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);
        gateGenerator.render(renderer);
        player.render(renderer);
        renderer.end();

        // renders score at top left
        HUDviewport.apply();
        batch.setProjectionMatrix(HUDviewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "Score: " + gateGenerator.score + "\nTop Score: " + topScore, HUDviewport.getWorldWidth() / 20, HUDviewport.getWorldHeight()/20 * 19);
        batch.end();
    }

    // updates and restarts game (if hit)
    void update(float delta)
    {
        player.update(delta);
        gateGenerator.update(delta);

        if(player.isHit(gateGenerator))
        {
            topScore = Math.max(topScore, gateGenerator.score);
            recordTopScore();
            game.showMenuScreen();
        }
    }

    // writes the topscore to the topscore file when game ends
    void recordTopScore()
    {
        prefs.putInteger("topscore", topScore);
        prefs.flush();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
        HUDviewport.update(width, height, true);
    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }
}
