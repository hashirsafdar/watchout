package sfn.watchout;

import com.badlogic.gdx.Game;

public class WatchOutGame extends Game
{
    SinglePlayScreen singlePlayScreen = new SinglePlayScreen(this);

    @Override
    public void create()
    {
        setScreen(new MenuScreen(this));
    }

    void showSinglePlayerScreen()
    {
        setScreen(singlePlayScreen);
    }

    void showMenuScreen()
    {
        setScreen(new MenuScreen(this));
    }
}
