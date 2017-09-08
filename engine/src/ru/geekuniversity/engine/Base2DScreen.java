package ru.geekuniversity.engine;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;

public class Base2DScreen implements Screen, InputProcessor{

    private static final float WORLD_HEIGHT = 1f;

    protected final Game game;

    private final Rect screenBounds = new Rect(); // границы области
    private final Rect worldBounds = new Rect();
    private final Rect glBounds = new Rect(0f, 0f, 1f, 1f);

    protected final Matrix4 matWorldToGL = new Matrix4();

    protected final Matrix3 matScreenToWorld = new Matrix3();

    protected SpriteBatch batch;


    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        if (batch != null) throw new RuntimeException("batch != null, повторная установка screen без dispose");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        //System.out.println("resize: width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width/ (float)height;
        worldBounds.setHeight(WORLD_HEIGHT);
        worldBounds.setWidth(WORLD_HEIGHT * aspect);
        MatrixUtils.calcTransitionMatrix(matWorldToGL, worldBounds, glBounds);
        batch.setProjectionMatrix(matWorldToGL);
        resize(worldBounds);
        MatrixUtils.calcTransitionMatrix(matScreenToWorld, screenBounds, worldBounds);

    }

    protected void resize(Rect worldBounds){


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
        batch.dispose();
        batch = null;
    }

    //Для перехвата тачей оверрайдим эти методы!!!
    protected void touchDown(Vector2 touch, int pointer){

    }
    protected void touchMove(Vector2 touch, int pointer){

    }
    protected void touchUp(Vector2 touch, int pointer){

    }

   //Эти методы не оверрайдим никогда!
     private final Vector2 touch = new Vector2(); //вектор для принятия/перевода/передачи тачей

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchMove(touch, pointer);
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

}
