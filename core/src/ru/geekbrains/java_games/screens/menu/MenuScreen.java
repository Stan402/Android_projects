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
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.sprites.Sprite;


public class MenuScreen extends Base2DScreen {

    private static final float STAR_WIDTH = 0.01f;
    private static final int NUMBER_OF_STARS = 200;

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars = new Star[NUMBER_OF_STARS];


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
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            stars[i].resize(worldBounds);
        }

    }


    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        //star.touchDown(touch, pointer);
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
        batch.end();
    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        atlas.dispose();
        super.dispose();
    }
}
