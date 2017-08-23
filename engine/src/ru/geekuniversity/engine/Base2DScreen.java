package ru.geekuniversity.engine;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;

public class Base2DScreen implements Screen, InputProcessor{

    protected final Game game;
    protected Matrix4 transMat = new Matrix4().idt();
    protected Matrix3 transMat3 = new Matrix3().idt();
    protected static final Rect oglRect = new Rect(0, 0, 1, 1);

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
        Rect newSize = new Rect(0, 0, (float) height/width, 1);
        MatrixUtils.calcTransitionMatrix(transMat, oglRect, newSize);
        MatrixUtils.calcTransitionMatrix(transMat3, oglRect, newSize);

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown x = " + screenX + " y = " + screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
