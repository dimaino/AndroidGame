package com.dimaino.game.entity;

/**
 * Created by dimaino on 4/19/2015.
 */

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.physics.box2d.Body;

public class Player extends TiledSprite implements CollidableEntity
{
	
	boolean dead = false;
	private Body body;
	public static final String TYPE = "Player";
	
	int playerX = 100;
	int playerY = 100;
	
	private boolean canRun = false;

	private int footContacts = 0;
	
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		pX = playerX;
		pY = playerY;
	}
	
	public boolean isDead() 
	{
		return dead;
	}

	public void setDead(boolean dead) 
	{
		this.dead = dead;
	}	
	
	public void turnLeft() 
	{
		setFlippedHorizontal(true);
	}
	
	public void turnRight() 
	{
		setFlippedHorizontal(false);
	}
	
	public void fly() 
	{
		setCurrentTileIndex(0);
	}
	
	public void fall() 
	{
		setCurrentTileIndex(1);
	}
	
	public void die() 
	{
		setDead(true);
		setCurrentTileIndex(2);
	}
	/*
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
	{
		if(pSceneTouchEvent.isActionDown())
		{
			setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			return true;
		}
		else
		{
			setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
			return true;
		}
	}	
	*/
	@Override
	protected void onManagedUpdate(float pSecondsElapsed)
	{
		super.onManagedUpdate(pSecondsElapsed);
		// if somebody set we are dying, we can't switch anymore
	/*	if(getCurrentTileIndex() < 2)
		{
			if(body.getLinearVelocity().y < 0)
			{
				fall();
			}
			else
			{
				fly();
			}
		}*/
	}
	
	@Override
	public void setBody(Body body)
	{
		this.body = body;	
	}

	@Override
	public Body getBody()
	{
		return body;
	}

	@Override
	public String getType()
	{
		return TYPE;
	}	
	
	
	public void increaseFootContacts()
	{
		footContacts++;
	}
	
	public void decreaseFootContacts()
	{
		footContacts--;
	}
	public void setPostion(int x, int y)
	{
		playerX = x;
		playerY = y;
	}
}