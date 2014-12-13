package com.my.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter implements InputProcessor{
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
	OrthographicCamera worldCam;
	Player player;
	Body body;
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
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		
		makePlayer();
		makeGround();
		worldCam = new OrthographicCamera();
		worldCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Gdx.input.setInputProcessor(this);
	}
	
	public void update() {
		world.step(1/60f, 6, 2);
		player.update();
		
		
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		worldCam.position.set(player.body.getPosition().x, player.body.getPosition().y, 0f);
		worldCam.update();
		batch.setProjectionMatrix(worldCam.combined);
		batch.begin();
		
		
		player.render(batch);
		batch.end();
		//b2dCam.setPosition(player.getPosition().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
		debugRenderer.render(world, b2dCam.combined);

	}
	
	public void makePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(60 / PPM, 150 / PPM);
		bdef.fixedRotation = true;
		
		// create body from bodydef
		body = world.createBody(bdef);
		
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
		
		player = new Player(body);
	}
	
	public void makeGround() {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		bdef.position.set(50 / PPM, 8 / PPM);
		bdef.fixedRotation = true;
		
		// create body from bodydef
		body = world.createBody(bdef);
		
		// create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50 / PPM, 100 / PPM);
		
		// create fixturedef for player collision box
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.friction = 0;
		
		// create player collision box fixture
		body.createFixture(fdef);
		shape.dispose();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.RIGHT) {
			player.body.setLinearVelocity(.002f, player.body.getLinearVelocity().y);
			player.body.applyForceToCenter(10, 0, true);
		} else {
			player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
		}
		if (keycode == Input.Keys.LEFT) {
			player.body.setLinearVelocity(.002f, player.body.getLinearVelocity().y);
			player.body.applyForceToCenter(-10, 0, true);
		} else {
			player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
		}
		if (keycode == Input.Keys.UP) {
			player.body.setLinearVelocity(player.body.getLinearVelocity().x, .002f);
			player.body.applyForceToCenter(0, 10, true);
		} else {
			player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
		}
//		if (keycode == Input.Keys.DOWN) {
//			player.body.setLinearVelocity(.002f, 0);
//			player.body.applyForceToCenter(10, 0, true);
//		} else {
//			player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
//		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.RIGHT) {
			player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
		}
		if (keycode == Input.Keys.LEFT) {
			player.body.setLinearVelocity(0, player.body.getLinearVelocity().y);
		}
		if (keycode == Input.Keys.UP) {
			player.body.setLinearVelocity(player.body.getLinearVelocity().x, 0);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
