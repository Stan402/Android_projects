package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.Ship;
import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.utils.Regions;


public class Enemy extends Ship {

    private EnemyType enemyType;

    public Enemy() {
    }

    public void set(TextureAtlas atlas,
                    BulletPool bulletPool,
                    Rect worldBounds,
                    EnemyType enemyType,
                    Vector2 v,
                    float reloadInterval) {

        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.enemyType = enemyType;

        bulletRegion = atlas.findRegion("bulletEnemy");

        regions = Regions.split(atlas.findRegion(enemyType.getRegionName()), 1, 2, 2);
        bulletHeight = 0.01f;
        this.reloadInterval = reloadInterval;
        bulletV.set(0f, -0.5f);
        bulletDamage = enemyType.getBulletDamage();

        setHeightProportion(enemyType.getSize());
        setTop(this.worldBounds.getTop());
        float shift = (float) Math.random() * (this.worldBounds.getWidth() - getWidth());
        setLeft(this.worldBounds.getLeft() + shift);
        this.v.set(v);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(enemyType.getSize());
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v, deltaTime);
        reloadTimer += deltaTime;
        if (reloadTimer >= reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
        if (isOutside(worldBounds)) destroy();
    }
}
