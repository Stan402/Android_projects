package ru.geekbrains.java_games.pools;

import ru.geekbrains.java_games.screens.game.Enemy;
import ru.geekuniversity.engine.pool.SpritesPool;


public class EnemyPool extends SpritesPool<Enemy> {
    @Override
    protected Enemy newObject() {
        return new Enemy();
    }

    @Override
    protected void debugLog() {
        System.out.println("EnemyPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
