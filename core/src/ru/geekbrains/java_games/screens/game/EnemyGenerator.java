package ru.geekbrains.java_games.screens.game;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekbrains.java_games.pools.EnemyPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class EnemyGenerator {

    private static final int ENEMY_TYPES_AMOUNT = 3;



    private static final float MIN_SPEED_ENEMY2 = -0.1f;
    private static final float MAX_SPEED_ENEMY2 = -0.25f;
    private static final float MIN_RELOAD_TIME_ENEMY2 = 0.4f;
    private static final float MAX_RELOAD_TIME_ENEMY2 = 0.7f;
    private static final float SIZE_ENEMY2 = 0.15f;
    private static final int BULLET_DAMAGE_ENEMY2 = 1;
    private static final int HEALTH_ENEMY2 = 1;

    private static final float MIN_SPEED_ENEMY3 = -0.1f;
    private static final float MAX_SPEED_ENEMY3 = -0.25f;
    private static final float MIN_RELOAD_TIME_ENEMY3 = 0.4f;
    private static final float MAX_RELOAD_TIME_ENEMY3 = 0.7f;
    private static final float SIZE_ENEMY3 = 0.15f;
    private static final int BULLET_DAMAGE_ENEMY3 = 1;
    private static final int HEALTH_ENEMY3 = 3;

    private static final ArrayList<EnemyType> enemyTypes = new ArrayList<EnemyType>();
    static {
        EnemyType type1 = new EnemyType("enemy0", 0.10f, 0.15f, 1f, 1.75f, 0.12f, 1, 1, 1);
        EnemyType type2 = new EnemyType("enemy1", 0.13f, 0.20f, 0.8f, 1.2f, 0.10f, 1, 1, 2);
        EnemyType type3 = new EnemyType("enemy2", 0.08f, 0.12f, 0.7f, 0.9f, 0.15f, 1, 2, 3);
        enemyTypes.add(type1);
        enemyTypes.add(type2);
        enemyTypes.add(type3);
    }

    private final EnemyPool enemyPool;
    private final BulletPool bulletPool;
    private final TextureAtlas atlas;

    private Rect worldBounds;

    public EnemyGenerator(TextureAtlas atlas, EnemyPool enemyPool, BulletPool bulletPool) {
        this.enemyPool = enemyPool;
        this.bulletPool = bulletPool;
        this.atlas = atlas;
    }

    private final float generateEnemyInterval = 3f;
    private float generateEnemyTimer;

    public void update(float deltaTime){
        generateEnemyTimer += deltaTime;
        if (generateEnemyTimer >= generateEnemyInterval){
            generateEnemyTimer = 0;
            Enemy enemy = enemyPool.obtain();
            int type = (int)(Rnd.nextFloat(0f, enemyTypes.size()));
            enemyTypes.get(type).setRandomEnemy(enemy, atlas, bulletPool, worldBounds);
        }
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }

}
