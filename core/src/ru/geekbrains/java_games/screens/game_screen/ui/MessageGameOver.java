package ru.geekbrains.java_games.screens.game_screen.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekuniversity.engine.sprites.Sprite;

public class MessageGameOver extends Sprite{

    private static final float HEIGHT = 0.07f;
    private static final float BOTTOM_MARGIN = 0.009f;

    public MessageGameOver(TextureAtlas atlas){
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM_MARGIN);
    }
}
