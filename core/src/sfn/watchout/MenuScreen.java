
/*

*/

package sfn.watchout;

// TODO: create menu, save high score, control app when off, control finger movement
// when game ends, pause 2sec, go back to main screen
// pass game to screen for setscreen instruction
// add instruction on main screen - 'keep your finger on the screen!'

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class MenuScreen extends InputAdapter implements Screen
{
    static final String TAG = MenuScreen.class.getName();

    WatchOutGame game;
    int topScore;
    Preferences prefs;

    ShapeRenderer renderer;
    Viewport viewport;
    SpriteBatch batch;
    BitmapFont font;

    Vector2 touchPosition;

    public MenuScreen(WatchOutGame game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        viewport = new FitViewport(Constants.WORLD_SIZE_WIDTH, Constants.WORLD_SIZE_HEIGHT);
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(0.5f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        touchPosition = new Vector2();
        prefs = Gdx.app.getPreferences("watchoutprefs");
        topScore = prefs.getInteger("topscore", 0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta)
    {
        viewport.apply();

        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);
        renderer.rect(Constants.MENU_PLAYBUTTON.x - Constants.MENU_PLAYBUTTON_RADIUS,
                Constants.MENU_PLAYBUTTON.y - Constants.MENU_PLAYBUTTON_RADIUS,
                Constants.MENU_PLAYBUTTON_RADIUS*2, Constants.MENU_PLAYBUTTON_RADIUS*2,
                Constants.PLAYER_COLOR, Constants.PLAYER_COLOR, Constants.PLAYER_COLOR, Constants.PLAYER_COLOR);

        renderer.triangle(Constants.MENU_PLAYBUTTON.x - Constants.MENU_PLAYBUTTON_RADIUS/2, Constants.MENU_PLAYBUTTON.y - Constants.MENU_PLAYBUTTON_RADIUS/2,
                Constants.MENU_PLAYBUTTON.x + Constants.MENU_PLAYBUTTON_RADIUS/2, Constants.MENU_PLAYBUTTON.y,
                Constants.MENU_PLAYBUTTON.x - Constants.MENU_PLAYBUTTON_RADIUS/2, Constants.MENU_PLAYBUTTON.y + Constants.MENU_PLAYBUTTON_RADIUS/2,
                Constants.BACKGROUND_COLOR, Constants.BACKGROUND_COLOR, Constants.BACKGROUND_COLOR);
        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        font.draw(batch, "Top Score: " + topScore, viewport.getWorldWidth() / 2 - 20, 60);
        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        renderer.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touchPosition = viewport.unproject(new Vector2(screenX, screenY));

        if(touchPosition.dst(Constants.MENU_PLAYBUTTON) < Constants.MENU_PLAYBUTTON_RADIUS)
        {
            game.showSinglePlayerScreen();
        }
        return true;
    }
}
