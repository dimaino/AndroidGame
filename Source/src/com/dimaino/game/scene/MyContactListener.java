package com.dimaino.game.scene;

import com.dimaino.game.ResourceManager;
import com.dimaino.game.entity.CollidableEntity;
import com.dimaino.game.entity.Enemy;
import com.dimaino.game.entity.Platform;
import com.dimaino.game.entity.Player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener
{
	Player player;
	
	public MyContactListener(Player player)
	{
		this.player = player;
	}

	@Override
	public void beginContact(Contact contact)
	{
		if (checkContact(contact, Player.TYPE, Enemy.TYPE))
		{
			player.die();
			ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().finishHim);
		}
		if(checkContact(contact, Player.TYPE, Platform.TYPE))
		{
			System.out.println("Contact");
			ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
			player.increaseFootContacts();
		}
	}

	@Override
	public void endContact(Contact contact)
	{
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold)
	{
		/*if (checkContact(contact, Player.TYPE, Platform.TYPE))
		{
			// player and platform
			if(!player.isDead() && player.getBody().getLinearVelocity().y <= 0)
			{
				player.getBody().setLinearVelocity(new Vector2(0, 0));
				ResourceManager.getInstance().activity.playSound(ResourceManager.getInstance().soundJump);
			}
			else
			{
				contact.setEnabled(false);
			}			
		}*/
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse)
	{
		
	}

	private boolean checkContact(Contact contact, String typeA, String typeB)
	{
		if (contact.getFixtureA().getBody().getUserData() instanceof CollidableEntity && contact.getFixtureB().getBody().getUserData() instanceof CollidableEntity)
		{
			CollidableEntity ceA = (CollidableEntity) contact.getFixtureA().getBody().getUserData();
			CollidableEntity ceB = (CollidableEntity) contact.getFixtureB().getBody().getUserData();
			
			if (typeA.equals(ceA.getType()) && typeB.equals(ceB.getType()) || typeA.equals(ceB.getType()) && typeB.equals(ceA.getType()))
			{
				return true;
			}
		}
		return false;
	}
}
