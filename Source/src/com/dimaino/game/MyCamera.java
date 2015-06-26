package com.dimaino.game;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.IEntity;

import com.dimaino.game.entity.Player;

public class MyCamera extends SmoothCamera
{
	private IEntity chaseEntity;

	private boolean gameOver = false;
	
	private boolean busy = false;
	
	private boolean fivePressed = false;
	private boolean twoPressed = false;
	
	int getCenterY = (int) getCenterY();
	int setCenterY = getCenterY;
	
	int getCenterX = (int) getCenterX();
	int setCenterX = getCenterX;
	
	
	
	
	final int MOVE_BOX_AREA_RIGHT = 200;
	final int MOVE_BOX_AREA_LEFT = 100;

	public MyCamera(float pX, float pY, float pWidth, float pHeight)
	{
		super(pX, pY, pWidth, pHeight, 3000f, 1000f, 1f);
	}

	@Override
	public void setChaseEntity(IEntity pChaseEntity)
	{
		super.setChaseEntity(pChaseEntity);
		this.chaseEntity = pChaseEntity;
	}

	@Override
	public void updateChaseEntity()
	{
		
		if(chaseEntity != null)
		{
			setCenter(chaseEntity.getX(), chaseEntity.getY());
		}
			//setCenter(chaseEntity.getX(), getCenterY());
			// Fully Works Fucking Finally LOL
			/*if(chaseEntity.getX() > getCenterX())//chaseEntity.getY() <= setCenterY && 
			{
				setCenter(chaseEntity.getX(), getCenterY());
				System.out.println("HEY 1");
			}
			if(chaseEntity.getX() < getCenterX() - MOVE_BOX_AREA_RIGHT)//chaseEntity.getY() <= setCenterY && 
			{
				offsetCenter(-5, 0);
				System.out.println("HEY 2");
			}
			if(((chaseEntity.getY() > getCenterY() && chaseEntity.getX() == getCenterX() || chaseEntity.getY() < getCenterY() && chaseEntity.getX() == getCenterX() || chaseEntity.getX() > getCenterX()) && !(chaseEntity.getY() < setCenterY)) && !busy)
			{
				setCenter(chaseEntity.getX(), chaseEntity.getY());
				System.out.println("WRONG 4");
				busy = true;
			}*/
			
			/*
			if(chaseEntity.getX() <= getCenterX() - MOVE_BOX_AREA_RIGHT && chaseEntity.getY() > getCenterY() && !(chaseEntity.getY() < setCenterY))
			{
				// Change for jump
				offsetCenter(-5, 5);
				System.out.println("WRONG 3 and 1");
				busy = true;
			}
			else if(((chaseEntity.getX() <= getCenterX() - MOVE_BOX_AREA_RIGHT && !(chaseEntity.getY() < setCenterY)) && !busy))
			{
				offsetCenter(-5, 0);
				System.out.println("WRONG 3");
				busy = true;
			}
			else if(((chaseEntity.getX() >= getCenterX() - MOVE_BOX_AREA_RIGHT && chaseEntity.getY() > getCenterY()) && !(chaseEntity.getY() < setCenterY)) && !busy)
			{
				// Change for jump
				offsetCenter(0, 5);
				System.out.println("WRONG 1");
				busy = true;
			}
			if(chaseEntity.getX() <= getCenterX() - MOVE_BOX_AREA_RIGHT && chaseEntity.getY() < getCenterY() && !(chaseEntity.getY() < setCenterY))
			{
				offsetCenter(-5, -5);
				System.out.println("WRONG 3 and 2");
				busy = true;
			}
			else if(((chaseEntity.getX() <= getCenterX() - MOVE_BOX_AREA_RIGHT && !(chaseEntity.getY() < setCenterY)) && !busy))
			{
				offsetCenter(-5, 0);
				System.out.println("WRONG 3");
				busy = true;
			}
			else if(((chaseEntity.getX() >= getCenterX() - MOVE_BOX_AREA_RIGHT && chaseEntity.getY() < getCenterY()) && !(chaseEntity.getY() < setCenterY)) && !busy)
			{
				offsetCenter(0, -5);
				System.out.println("WRONG 2");
				busy = true;
			}*/
		//	busy = false;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*
			// BASED OFF LEFT RIGHT SYSTEM NOT CENTER
			if(chaseEntity.getY() <= setCenterY && chaseEntity.getX() > getCenterX() - MOVE_BOX_AREA_LEFT)
			{
				offsetCenter(5, 0);
				System.out.println("HEY 1");
			}
			if(chaseEntity.getY() <= setCenterY && chaseEntity.getX() < getCenterX() - MOVE_BOX_AREA_RIGHT)
			{
				offsetCenter(-5, 0);
				System.out.println("HEY 2");
			}
			if(((chaseEntity.getY() > getCenterY() && chaseEntity.getX() == getCenterX() - MOVE_BOX_AREA_LEFT || chaseEntity.getY() < getCenterY() && chaseEntity.getX() == getCenterX() - MOVE_BOX_AREA_LEFT || chaseEntity.getX() > getCenterX() - MOVE_BOX_AREA_LEFT) && !(chaseEntity.getY() < setCenterY)) && !busy)
			{
				offsetCenter(5, 0);
				//setCenter(chaseEntity.getX(), chaseEntity.getY());
				System.out.println("WRONG 4");
				busy = true;
			}*/
	//	}
	}

	@Override
	public void reset()
	{
		super.reset();
		gameOver = false;
		set(0, 0, GameActivity.CAMERA_WIDTH, GameActivity.CAMERA_HEIGHT);
		setCenterDirect(GameActivity.CAMERA_WIDTH / 2, GameActivity.CAMERA_HEIGHT / 2);
	}
}