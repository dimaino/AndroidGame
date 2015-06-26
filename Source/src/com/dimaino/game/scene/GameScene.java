package com.dimaino.game.scene;

import com.dimaino.game.GameActivity;
import com.dimaino.game.ResourceManager;
import com.dimaino.game.SceneManager;
import com.dimaino.game.entity.CollidableEntity;
import com.dimaino.game.entity.Enemy;
import com.dimaino.game.entity.Platform;
import com.dimaino.game.entity.Player;
import com.dimaino.game.factory.EnemyFactory;
import com.dimaino.game.factory.PlatformFactory;
import com.dimaino.game.factory.PlayerFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.EntityBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.debug.Debug;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;

public class GameScene extends AbstractScene implements IOnSceneTouchListener
{
	
	private Player player;
	
	private Text scoreText;
	
	private PhysicsWorld physicsWorld;
	private PhysicsWorld physicsWorld1;
	
	Random rand = new Random();
	
	private Text endGameText;
	
	private int score;
	
	private LinkedList<Platform> platforms = new LinkedList<Platform>();	
	private LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	
	// TEST CODE
	public Rectangle aSprite;
	ButtonSprite testButton;
	
	
	float num1 = 0;
	float num2 = 0;
	
	Sound s = ResourceManager.getInstance().buzzBuzz;
	
	// Flag FOR EVENT
	private boolean rightIsTouchedFlag = false;
	private boolean leftIsTouchedFlag = false;
	private boolean jumpIsTouchedFlag = false;
	private boolean crouchIsTouchedFlag = false;
	
	public GameScene()
	{
		super();
		camera.reset();
		camera.setHUD(null);
		physicsWorld = new PhysicsWorld(new Vector2(0, -SensorManager.GRAVITY_EARTH * 4), true);
		physicsWorld1 = new PhysicsWorld(new Vector2(0, 0), true);
		PlayerFactory.getInstance().create(physicsWorld, vbom);
		PlatformFactory.getInstance().create(physicsWorld, vbom);
		EnemyFactory.getInstance().create(physicsWorld, vbom);
		s.setLooping(true);
	}

	private void addEnemy(float tx, float ty)
	{
		Enemy enemy = EnemyFactory.getInstance().createEnemy(tx, ty);
		attachChild(enemy);
		enemies.add(enemy);
	}
	
	@Override
	public void populate()
	{ 
		createBackground();
		createPlayer();
		camera.setChaseEntity(player);
		createHUD();
		
		//ResourceManager.getInstance().activity.playMusic(ResourceManager.getInstance().music);
		
		addPlatform(300, 100, false);
		addPlatform(450, 100, false);
		addPlatform(600, 100, false);
		addPlatform(750, 100, false);
		addPlatform(900, 100, false);
		addPlatform(1050, 100, false);
		addPlatform(1200, 300, false);
		addEnemy(1440, 300);
		addEnemy(1140, 100);
		addEnemy(1600, 200);
		addEnemy(1000, 400);
		addEnemy(1130, 500);
		addEnemy(1540, 100);
		addEnemy(1130, 200);
		addEnemy(1340, 300);
		addEnemy(1440, 400);
		addEnemy(1540, 500);
		addEnemy(1040, 100);
		addEnemy(1140, 200);
		addEnemy(1240, 300);
		addEnemy(1340, 400);
		addEnemy(1440, 500);
		addEnemy(1540, 100);
		
		/*
		for(int i = 0; i < 20; i++)
		{
			int t = 0;
			Random r = new Random();
			t = r.nextInt(400) + 100;
			addEnemy(940 + t, t);
			System.out.println("t:" + t + "  Y:" + (940 + t) + "   X:" + t);
		}
*/
		/*if((p % 5) == 0)
		{
			s.setLooping(true);
		}
		s.setLooping(true);*/
		setOnSceneTouchListener(this);
		registerUpdateHandler(physicsWorld);	
		
		physicsWorld.setContactListener(new MyContactListener(player));

		
		
	}
	
	private void addPlatform(float tx, float ty, boolean moving)
	{
		Platform platform;
		if(moving)
		{
			platform = PlatformFactory.getInstance().createMovingPlatform(tx, ty, (rand.nextFloat() - 0.5f) * 10f);
		}
		else
		{
			platform = PlatformFactory.getInstance().createPlatform(tx, ty);
		}
		attachChild(platform);
		platforms.add(platform);
	}
	
		
    private void createHUD()
    {
		HUD hud = new HUD();
		scoreText = new Text(16, 480, res.font, "0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setAnchorCenter(0, 1);
		score = 0;
		scoreText.setText(String.valueOf(score));
		hud.attachChild(scoreText);
		
		
		
		
		
		final Rectangle rightButton = new Rectangle(120, 200, 60, 60, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
			{
				if(touchEvent.isActionDown())
				{
					rightIsTouchedFlag = true;
					
				}
				if(touchEvent.isActionUp())
				{
					rightIsTouchedFlag = false;
				}
				return true;
			}
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				if(rightIsTouchedFlag)
				{
					//player.setPosition(player.getX() + 5, player.getY());
					player.getBody().setLinearVelocity(new Vector2(5, player.getBody().getLinearVelocity().y));;
					//player.increaseFootContacts();
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		
		rightButton.setAnchorCenter(0, 1);
		hud.registerTouchArea(rightButton);
		hud.attachChild(rightButton);
		
		
		final Rectangle leftButton = new Rectangle(20, 200, 60, 60, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
			{
				if(touchEvent.isActionDown())
				{
					leftIsTouchedFlag = true;
				}
				if(touchEvent.isActionUp())
				{
					leftIsTouchedFlag = false;
				}
				return true;
			}
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				if(leftIsTouchedFlag)
				{
					//player.setPosition(player.getX() - 5, player.getY());
					player.getBody().setLinearVelocity(new Vector2(-5, player.getBody().getLinearVelocity().y));;
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		
		leftButton.setAnchorCenter(0, 1);
		hud.registerTouchArea(leftButton);
		hud.attachChild(leftButton);
		
		
		final Rectangle jumpButton = new Rectangle(70, 300, 60, 60, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
			{
				if(touchEvent.isActionDown() && !jumpIsTouchedFlag)
				{
					jumpIsTouchedFlag = true;
					//player.setPosition(player.getX(), player.getY() + 50);
					/*int i = (int) player.getX();
					if(i == player.getX())
					{*/
					player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 25));
					//}
				}
				if(jumpIsTouchedFlag && !touchEvent.isActionDown())
				{
					player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 25));
					jumpIsTouchedFlag = false;
				}
				/*if(touchEvent.isActionUp())
				{
					jumpIsTouchedFlag = false;
				}*/
				return true;
			}
			// TAKE OUT FOR JUMPING
		/*	
			@Override
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				if(jumpIsTouchedFlag)
				{
					player.getBody().setLinearVelocity(new Vector2(player.getBody().getLinearVelocity().x, 5));
					//player.setPosition(player.getX(), player.getY() + 5);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}*/
		};
		
		jumpButton.setAnchorCenter(0, 1);
		hud.registerTouchArea(jumpButton);
		hud.attachChild(jumpButton);
		
		final Rectangle crouchButton = new Rectangle(70, 100, 60, 60, vbom)
		{
			@Override
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
			{
				if(touchEvent.isActionDown())
				{
					crouchIsTouchedFlag = true;
				}
				if(touchEvent.isActionUp())
				{
					crouchIsTouchedFlag = false;
				}
				return true;
			}
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed)
			{
				if(crouchIsTouchedFlag)
				{
					player.setPosition(player.getX(), player.getY() - 5);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
		};
		
		crouchButton.setAnchorCenter(0, 1);
		hud.registerTouchArea(crouchButton);
		hud.attachChild(crouchButton);
		
		hud.setTouchAreaBindingOnActionDownEnabled(true);
		hud.setTouchAreaBindingOnActionMoveEnabled(true);
		//hud.setOnSceneTouchListener(this);
		
		endGameText = new Text(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2, res.font, "GAME OVER! TAP TO CONTINUE", new TextOptions(HorizontalAlign.CENTER), vbom);
		endGameText.setAutoWrap(AutoWrap.WORDS);
		endGameText.setAutoWrapWidth(300f);
		endGameText.setVisible(false);
		hud.attachChild(endGameText);
		
		camera.setHUD(hud);
	}	

	private void createPlayer()
	{
		player = PlayerFactory.getInstance().createPlayer(350, 200);
		attachChild(player);
	}

	private void createBackground()
	{
		Entity background = new Entity();
		Sprite cloud1 = new Sprite(200, 300, res.cloudTextureRegion1, vbom);
		Sprite cloud2 = new Sprite(300, 600, res.cloudTextureRegion2, vbom);
		background.attachChild(cloud1);
		background.attachChild(cloud2);
		setBackground(new EntityBackground(0.82f, 0.96f, 0.97f, background));
	}

	@Override
	public void onPause()
	{
		
	}

	@Override
	public void onResume()
	{
		
	}
	
	private static final float MIN = 50f;
	private static final float MAX = 250f;
	double i = 0;
	int p = 0;
	boolean even;
	@Override
	protected void onManagedUpdate(float pSecondsElapsed)
	{
		super.onManagedUpdate(pSecondsElapsed);
		boolean added = false;
		
		i += pSecondsElapsed;
		p = (int)i;
		System.out.println(p);
		even = (p & 1) == 0;
		

		while (camera.getYMax() > platforms.getLast().getY())
		{
			// x position of next platform
			float tx = rand.nextFloat() * GameActivity.CAMERA_WIDTH;
			// y position of next platform
			float ty = platforms.getLast().getY() + MIN + rand.nextFloat() * (MAX - MIN);
			// 10 % chance to add enemy on the platform
			if (rand.nextFloat() < 0.1)
			{
				addEnemy(tx, ty);
			}
			boolean moving = rand.nextBoolean();
			addPlatform(tx, ty, moving);
			added = true;
		}
		
		
		if (added)
		{
			sortChildren();
		}
		//TODO STUFF TO CHANGE
		// player below last platform
		if (player.getY() < 50)
		{
			player.die();
		}
		//cleanEntities(platforms, camera.getYMin());
		//cleanEntities(enemies, camera.getYMin());
		calculateScore();
		
		if (player.isDead())
		{
			//player.dispose();
			
			endGameText.setVisible(true);
			if (score > activity.getHiScore())
			{
				activity.setHiScore(score);
			}
		}
	}
	
	private void calculateScore()
	{
		if (camera.getXMin() > score)
		{
			score = Math.round(camera.getXMin());
			scoreText.setText(String.valueOf(score));
		}
	}

	private void cleanEntities(List<? extends CollidableEntity> list, float bound)
	{
		Iterator<? extends CollidableEntity> iter = list.iterator();
		while (iter.hasNext())
		{
			CollidableEntity ce = iter.next();
		    if (ce.getY() < bound)
		    {
		    	iter.remove();
		    	ce.detachSelf();
		    	physicsWorld.destroyBody(ce.getBody());
		    }
		}		
	}	
	
	private void restartGame()
	{
		setIgnoreUpdate(true);
		unregisterUpdateHandler(physicsWorld);
		enemies.clear();
		platforms.clear();
		physicsWorld.clearForces();
		physicsWorld.clearPhysicsConnectors();
		while (physicsWorld.getBodies().hasNext())
		{
			physicsWorld.destroyBody(physicsWorld.getBodies().next());
		}
		camera.reset();
		camera.setHUD(null);
		camera.setChaseEntity(null);
		detachChildren();
		
		populate();
		setIgnoreUpdate(false);		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		if(pSceneTouchEvent.isActionUp() && player.isDead())
		{
			restartGame();
			return true;
		}
		return false;
	}
	
	@Override
	public void destroy()
	{
		camera.reset();
		camera.setHUD(null);
	}

	@Override
	public void onBackKeyPressed()
	{
		SceneManager.getInstance().showMenuScene();
	}
}
