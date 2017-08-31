package ru.geekuniversity.engine.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekuniversity.engine.math.Rect;


public class Button extends Sprite {

    public static final int BOTTOM_LEFT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int TOP_RIGHT = 3;
    public static final int TOP_LEFT = 4;


    private final boolean isWidth;


    private Rect worldBounds;
    private float size;
    private final float shiftHorizontal;
    private final float shiftVertical;
    private final int place;


    public Button(TextureRegion region, int place, float shiftHorizontal, float shiftVertical, float size, boolean isWidth) {
        super(region);
        this.isWidth = isWidth;
        this.size = size;
        this.shiftHorizontal = shiftHorizontal;
        this.shiftVertical = shiftVertical;
        this.place = place;
    }

    public Button(TextureRegion region, int place, float size) {
        super(region);
        this.isWidth = true;
        this.size = size;
        this.place = place;
        this.shiftVertical = 0f;
        this.shiftHorizontal = 0f;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        switch (place){
            case BOTTOM_LEFT:
                setLeft(worldBounds.getLeft() + shiftHorizontal * worldBounds.getWidth());
                setBottom(worldBounds.getBottom() + shiftVertical * worldBounds.getHeight());
                break;
            case BOTTOM_RIGHT:
                setRight(worldBounds.getRight() - shiftHorizontal * worldBounds.getWidth());
                setBottom(worldBounds.getBottom() + shiftVertical * worldBounds.getHeight());
                break;
            case TOP_LEFT:
                setLeft(worldBounds.getLeft() + shiftHorizontal * worldBounds.getWidth());
                setTop(worldBounds.getTop() - shiftVertical * worldBounds.getHeight());
                break;
            case TOP_RIGHT:
                setRight(worldBounds.getRight() - shiftHorizontal * worldBounds.getWidth());
                setTop(worldBounds.getTop() - shiftVertical * worldBounds.getHeight());
                break;
            default:
                throw new RuntimeException("Wrong placement for the Button");
        }
        if (isWidth)
            setWidthProportion(worldBounds.getWidth() * size);
        else
            setHeightProportion(worldBounds.getHeight() * size);
    }
}
