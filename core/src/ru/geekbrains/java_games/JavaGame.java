package ru.geekbrains.java_games;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekuniversity.engine.Base2DScreen;

public class JavaGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture img2;
	private Texture textureCircle;
	private Base2DScreen base2DScreen;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		img2 = new Texture("jon lee hooker.jpg");
		textureCircle = new Texture("circle.png");

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.draw(img2, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		batch.draw(img, 0, 0);
		batch.draw(textureCircle, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
