package com.my.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter {
	private static final float PIXELS_TO_METERS = 100f;
	private static final float METERS_TO_PIXELS = 1f/100f;
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
	//tmp
	int i = 0;
	int j = 0;
	/****************************************/
	
	/*
	 * Box2D stuff
	 */
	OrthographicCamera camera;
	World world;
	Box2DDebugRenderer debugRenderer;
	Body body;
	Matrix4 debugMatrix;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(800, 600);
		/*
		 * Box2d Stuff
		 */
		world = new World(new Vector2(0 ,-1), true);
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawBodies(true);
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
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(-10, -10));
		
		body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(64f/METERS_TO_PIXELS, 64f/METERS_TO_PIXELS);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;
		
		Fixture fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
		/*********************************************************************/
		
	}
	
	public void update() {
		camera.update();
		world.step(1/60f, 6, 2);
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
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		batch.draw(walking[i], 0, 0);
		batch.draw(jumping[j], 65, 0);
		batch.draw(idle[0], 130, 0);
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
                PIXELS_TO_METERS, 0);
		
		batch.end();
		debugRenderer.render(world, debugMatrix);
	}
}
