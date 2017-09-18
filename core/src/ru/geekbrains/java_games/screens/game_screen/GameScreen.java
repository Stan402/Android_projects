package ru.geekbrains.java_games.screens.game_screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.geekbrains.java_games.common.Background;
import ru.geekbrains.java_games.common.bullets.Bullet;
import ru.geekbrains.java_games.common.enemies.EnemiesEmitter;
import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.enemies.Enemy;
import ru.geekbrains.java_games.common.enemies.EnemyPool;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekbrains.java_games.common.stars.TrackingStar;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Font;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class GameScreen extends Base2DScreen{

    private static final float STAR_HEIGHT = 0.01f;
    private static final int STAR_COUNT = 50;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private Background background;
    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private MainShip mainShip;
    private TrackingStar[] stars = new TrackingStar[STAR_COUNT];
    EnemiesEmitter enemiesEmitter;

    private Font font;
    private Music music;
    private Sound sndLaser;
    private Sound sndBullet;
    private Sound sndExplosion;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        sndBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        sndLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));



        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, sndExplosion);


        background = new Background(new TextureRegion(textureBackground));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, worldBounds, sndLaser);

        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, mainShip);

        enemiesEmitter = new EnemiesEmitter(enemyPool, worldBounds, atlas, sndBullet);

        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < STAR_COUNT; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new TrackingStar(starRegion, vx, vy, starHeight, mainShip.getV());
        }

        font = new Font("fonts/font1.fnt", "fonts/font1.png");
        font.setWorldSize(1f);

        music.setLooping(true);
        music.play();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i].resize(worldBounds);
        }

    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
//        Explosion explosion =explosionPool.obtain();
//        explosion.set(0.1f, touch);
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

//    private float randomBoomInterval = 3f;
//    private float randomBoomTimer;
//    private final Vector2 randomBoomPos = new Vector2();


    private void update(float deltaTime) {

//        randomBoomTimer += deltaTime;
//        if (randomBoomTimer >= randomBoomInterval){
//            randomBoomTimer = 0;
//            Explosion explosion = explosionPool.obtain();
//            randomBoomPos.x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
//            randomBoomPos.y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
//            explosion.set(0.1f, randomBoomPos);
//        }

        enemiesEmitter.generateEnemies(deltaTime);
        enemyPool.updateActiveSprites(deltaTime);
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i].update(deltaTime);
        }
        bulletPool.updateActiveSprites(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);
        mainShip.update(deltaTime);
    }

    private void checkCollisions(){
        ArrayList<Enemy> enemies = enemyPool.getActiveObjects();
        final int enemyCount = enemies.size();
        ArrayList<Bullet> bullets = bulletPool.getActiveObjects();
        final int bulletsCount = bullets.size();

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDestroyed()) continue;
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist){
                enemy.boom();
                enemy.destroy();
                return;
            }
        }
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDestroyed()) continue;
            for (int j = 0; j < bulletsCount; j++) {
                Bullet bullet = bullets.get(j);
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()){
                        enemy.boom();
                        frags++;
                        break;
                    }
                }
            }
        }

//        for (int i = 0; i < bulletsCount; i++) {
//            Bullet bullet = bullets.get(i);
//            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) continue;
//            if (mainShip.isBulletCollision(bullet)){
//                mainShip.damage(bullet.getDamage());
//                bullet.destroy();
//            }
//        }

    }

    private void deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
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
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        mainShip.draw(batch);

        font.draw(batch, "H", worldBounds.getLeft(), worldBounds.getTop());
        batch.end();
    }

    private int frags;

    @Override
    public void dispose() {

        music.dispose();
        sndBullet.dispose();
        sndLaser.dispose();
        sndExplosion.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        textureBackground.dispose();
        atlas.dispose();
        bulletPool.dispose();
        font.dispose();
        super.dispose();
    }
}
