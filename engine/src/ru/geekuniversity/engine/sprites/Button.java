package ru.geekuniversity.engine.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.ButtonListener;
import ru.geekuniversity.engine.math.Rect;


public class Button extends Sprite {

    public static final int BOTTOM_LEFT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int TOP_RIGHT = 3;
    public static final int TOP_LEFT = 4;

    private static final float SIZE_WHEN_PUSHED = 0.9f;

    private final String name;
    private final boolean isWidth;
    private final float size;
    private final float shiftHorizontal;
    private final float shiftVertical;
    private final int place;
    private final ButtonListener buttonListener;

    private boolean isPushed;
    private int pointer;

    public Button(String name, TextureRegion region, ButtonListener buttonListener, int place, float shiftHorizontal, float shiftVertical, float size, boolean isWidth) {
        super(region);
        this.name = name;
        this.buttonListener = buttonListener;
        this.isWidth = isWidth;
        this.size = size;
        this.shiftHorizontal = shiftHorizontal;
        this.shiftVertical = shiftVertical;
        this.place = place;
    }

    public Button(String name, TextureRegion region, ButtonListener buttonListener,int place, float size) {
        super(region);
        this.name = name;
        this.buttonListener = buttonListener;
        this.isWidth = true;
        this.size = size;
        this.place = place;
        this.shiftVertical = 0f;
        this.shiftHorizontal = 0f;
    }

    @Override
    public void resize(Rect worldBounds) {
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

    @Override
    public void draw(SpriteBatch batch) {
        if (isPushed){
            batch.draw(
                    regions[frame],
                    getLeft(), getBottom(),
                    halfWidth, halfHeight,
                    getWidth(), getHeight(),
                    scale * SIZE_WHEN_PUSHED, scale * SIZE_WHEN_PUSHED, angle
            );
        }
        else
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)){
            isPushed = true;
            this.pointer = pointer;
            buttonListener.onPushedButton(this);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!isPushed) return false;
        if (this.pointer != pointer) return false;
        if (isMe(touch))
            buttonListener.onActivatedButton(this);
        else
            buttonListener.onReleasedButton(this);
        isPushed = false;
        return false;
    }

    public String getName() {
        return name;
    }
}
