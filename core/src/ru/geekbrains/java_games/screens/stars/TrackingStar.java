package ru.geekbrains.java_games.screens.stars;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.screens.game.MainShip;


public class TrackingStar extends Star {

    private final Vector2 v0 = new Vector2();
    private final MainShip mainShip;
    private final float vCorrection;

    public TrackingStar(TextureAtlas atlas, float vx, float vy, float height, MainShip mainShip, float vCorrection) {
        super(atlas.findRegion("star"), vx, vy, height);
        v0.set(v);
        this.mainShip = mainShip;
        this.vCorrection = vCorrection;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        v.set(v0);
        v.mulAdd(mainShip.getV(), vCorrection);
    }
}
