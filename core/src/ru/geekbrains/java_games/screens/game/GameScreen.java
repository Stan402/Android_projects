package ru.geekbrains.java_games.screens.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.Background;
import ru.geekbrains.java_games.Explosion;
import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekbrains.java_games.pools.EnemyPool;
import ru.geekbrains.java_games.pools.ExplosionPool;
import ru.geekbrains.java_games.screens.stars.Star;
import ru.geekbrains.java_games.screens.stars.TrackingStar;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class GameScreen extends Base2DScreen{

    private static final float STAR_HEIGHT = 0.01f;
    private static final int STAR_COUNT = 50;

    private final BulletPool bulletPool = new BulletPool();
    private final EnemyPool enemyPool = new EnemyPool();
    private ExplosionPool explosionPool;

    private Background background;
    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private TrackingStar[] stars = new TrackingStar[STAR_COUNT];
    private EnemyGenerator enemyGenerator;

    private Sound sndExplosion;
    private Rect worldBounds;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, sndExplosion);

        background = new Background(new TextureRegion(textureBackground));
        mainShip = new MainShip(atlas, bulletPool);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, bulletPool);


        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_COUNT; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new TrackingStar(starRegion, vx, vy, starHeight, mainShip.getV());
        }
    }

    @Override
    protected void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i].resize(worldBounds);
        }
        enemyGenerator.resize(worldBounds);

    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.1f, touch);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private final float explosionsInterval = 3.0f;
    private float explosionsTimer;

    private void update(float deltaTime) {
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i].update(deltaTime);
        }
        bulletPool.updateActiveSprites(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);
        enemyPool.updateActiveSprites(deltaTime);
        mainShip.update(deltaTime);
        enemyGenerator.update(deltaTime);

        explosionsTimer += deltaTime;
        if (explosionsTimer >= explosionsInterval){
            explosionsTimer = 0f;
            boom();
        }
    }

    private void boom(){
        float x = ((float)Math.random() - 0.5f) * worldBounds.getWidth();
        float y = ((float) Math.random() - 0.5f) * worldBounds.getHeight();
        Vector2 vector2 = new Vector2();
        vector2.set(x, y);
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.1f, vector2);
    }

    private void checkCollisions(){

    }

    private void deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i].draw(batch);
        }
        bulletPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        explosionPool.dispose();
        textureBackground.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        sndExplosion.dispose();
        super.dispose();
    }
}
