package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Locale;

import ru.geekuniversity.engine.Base2DScreen;


public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;
    private Texture textureCircle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt();
        textureCircle = new Texture("circle.png");

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.getProjectionMatrix().idt().mul(transMat);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureCircle, -1f, -0.5f, 1f, 1f);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        textureCircle.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float y = (float) (Gdx.graphics.getHeight() - screenY)/Gdx.graphics.getHeight() * 2f - 1f;
        float x = (float) screenX/Gdx.graphics.getWidth() * 2f - 1f;
        Vector3 vec3 = new Vector3(x, y, 0);
        vec3 = vec3.mul(transMat3.inv());
        transMat3.inv();


        System.out.println(String.format(Locale.US, "touchDown: x = %.2f y = %.2f", vec3.x, vec3.y));
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }
}
