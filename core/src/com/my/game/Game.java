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
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter {
	public static final float PPM = 100;
	public static final String TITLE = "finished";
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 480;
	public static final int SCALE = 1;
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
	
	//tmp
	int i = 0;
	int j = 0;
	/****************************************/
	
	/*
	 * Box2D stuff
	 */
	OrthographicCamera b2dCam;
	World world;
	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	
	// Player stuff
	Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		/*
		 * Box2d Stuff
		 */
		world = new World(new Vector2(0 ,-1), true);
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawBodies(true);
		
		//player = Player.MakePlayer(world, 100, 100);
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(60 / PPM, 120 / PPM);
		bdef.fixedRotation = true;
		bdef.linearVelocity.set(1f, 0f);
		
		// create body from bodydef
		Body body = world.createBody(bdef);
		
		// create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(13 / PPM, 13 / PPM);
		
		// create fixturedef for player collision box
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.friction = 0;
		
		// create player collision box fixture
		body.createFixture(fdef);
		shape.dispose();
		
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
	}
	
	public void update() {
		world.step(1/60f, 6, 2);
		//player.update();
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();
		//b2dCam.setPosition(player.getPosition().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
		b2dCam.update();
		debugRenderer.render(world, b2dCam.combined);

	}
}
