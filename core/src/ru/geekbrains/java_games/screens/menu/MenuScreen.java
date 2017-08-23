package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekuniversity.engine.Base2DScreen;


public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;
    Texture img;
    Texture img2;
    private Texture textureCircle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        img2 = new Texture("jon lee hooker.jpg");
        textureCircle = new Texture("circle.png");

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img2, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(img, 0, 0);
        batch.draw(textureCircle, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        textureCircle.dispose();
        super.dispose();
    }
}
