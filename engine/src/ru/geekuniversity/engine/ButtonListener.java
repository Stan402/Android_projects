package ru.geekuniversity.engine;

import ru.geekuniversity.engine.sprites.Button;

public interface ButtonListener {

    void onActivatedButton(Button button);
    void onPushedButton(Button button);
    void onReleasedButton(Button button);

}
