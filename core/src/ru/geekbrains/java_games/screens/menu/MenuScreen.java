package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Locale;

import ru.geekbrains.java_games.screens.stars.Star;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.ButtonListener;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.sprites.Button;
import ru.geekuniversity.engine.sprites.Sprite;


public class MenuScreen extends Base2DScreen implements ButtonListener{

    private static final float STAR_WIDTH = 0.01f;
    private static final int NUMBER_OF_STARS = 300;

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars = new Star[NUMBER_OF_STARS];
    private Button btPlay;
    private Button btExit;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        background = new Background(new TextureRegion(textureBackground));
        TextureRegion regionStar = atlas.findRegion("star");
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starWidth = STAR_WIDTH * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new Star(regionStar, vx, vy, starWidth);
        }
        TextureRegion regionBtPlay = atlas.findRegion("btPlay");
        TextureRegion regionBtExit = atlas.findRegion("btExit");
        btPlay = new Button("Play" ,regionBtPlay, this, Button.BOTTOM_LEFT, 0f, 0f, 0.3f, true);
        btExit = new Button("Exit", regionBtExit, this, Button.BOTTOM_RIGHT, 0.3f);
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            stars[i].resize(worldBounds);
        }
        btPlay.resize(worldBounds);
        btExit.resize(worldBounds);

    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        //star.touchDown(touch, pointer);
        btPlay.touchDown(touch, pointer);
        btExit.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        btPlay.touchUp(touch, pointer);
        btExit.touchUp(touch, pointer);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float deltaTime) {
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            stars[i].update(deltaTime);
        }

    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            stars[i].draw(batch);
        }
        btPlay.draw(batch);
        btExit.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void onActivatedButton(Button button) {
        System.out.println("Button " + button.getName() + " is activated");
    }

    @Override
    public void onPushedButton(Button button) {
        System.out.println("Button " + button.getName() + " is pushed");
    }

    @Override
    public void onReleasedButton(Button button) {
        System.out.println("Button " + button.getName() + " is released without activation");

    }


}
