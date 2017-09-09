package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class EnemyType {

    private final String regionName;

    private final float minSpeed;
    private final float maxSpeed;
    private final float minReloadTime;
    private final float maxReloadTime;
    private final float size;
    private final int bulletDamage;
    private final int gunsNumber;
    private final int health;

    public EnemyType(String regionName,
                     float minSpeed,
                     float maxSpeed,
                     float minReloadTime,
                     float maxReloadTime,
                     float size,
                     int bulletDamage,
                     int gunsNumber,
                     int health) {
        this.regionName = regionName;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.minReloadTime = minReloadTime;
        this.maxReloadTime = maxReloadTime;
        this.size = size;
        this.bulletDamage = bulletDamage;
        this.gunsNumber = gunsNumber;
        this.health = health;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getGunsNumber() {
        return gunsNumber;
    }

    public float getSize() {
        return size;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setRandomEnemy(Enemy enemy, TextureAtlas atlas, BulletPool bulletPool, Rect worldBounds){
        Vector2 v = new Vector2(0, - Rnd.nextFloat(minSpeed, maxSpeed));
        float reloadTime = Rnd.nextFloat(minReloadTime, maxReloadTime);
        enemy.set(atlas, bulletPool, worldBounds, this, v, reloadTime);
    }
}
