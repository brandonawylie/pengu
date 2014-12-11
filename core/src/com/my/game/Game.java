package com.my.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

public class Game extends ApplicationAdapter {
	// primitive timer used for animation testing
	final int TICK_DURATION_MS = 100;
	long lastTick;
	SpriteBatch batch;
	Texture img;
	/*****************************************
	 * This belongs in the player class
	 * just moved here until entity framework
	 * decided on
	 */
	TextureRegion[] walking;
	TextureRegion[] jumping;
	TextureRegion[] idle;
	/****************************************/
	//tmp
	int i = 0;
	int j = 0;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		/*****************************************************************
		 * This belongs in the player class
		 * just moved here until entity framework
		 * decided on
		 */
		// Loading the walking animation
		Texture playerTexture = new Texture("player.png");
		TextureRegion[][] tmp = TextureRegion.split(playerTexture, 64, 64);
		walking = new TextureRegion[8];
		int col = 0;
		for (int c = 4; c < 12; c++) {
			walking[c - 4] = tmp[c/8][c % 8];
		}
		
		// Loading the jumping animation
		jumping = new TextureRegion[8];
		col = 0;
		int count = 0;
		for (int c = 40; c < 48; c++) {
			jumping[c - 40] = tmp[c/8][c % 8];
			count++;
		}
		
		// Loading the idle animation
		idle = new TextureRegion[1];
		idle[0] = tmp[8][0];
		/*********************************************************************/
		
	}
	
	public void update() {
		
		long curTick = System.currentTimeMillis();
		if (curTick - lastTick > TICK_DURATION_MS) {
			i++;
			j++;
			if (i > walking.length - 1)
				i = 0;
			if (j > jumping.length - 1)
				j = 0;
			lastTick = curTick;
		}	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(walking[i], 0, 0);
		batch.draw(jumping[j], 65, 0);
		batch.draw(idle[0], 130, 0);
		batch.end();
	}
}
