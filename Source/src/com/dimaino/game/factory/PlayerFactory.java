package com.dimaino.game.factory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.dimaino.game.ResourceManager;
import com.dimaino.game.entity.Player;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerFactory
{
    private static PlayerFactory INSTANCE = new PlayerFactory();
    private VertexBufferObjectManager vbom;

    private PhysicsWorld physicsWorld;
    public static final FixtureDef PLAYER_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, .5f, false);

    private PlayerFactory()
    {

    }

    public static PlayerFactory getInstance()
    {
        return INSTANCE;
    }

    public void create(VertexBufferObjectManager vbom)
    {
        this.vbom = vbom;
    }

    public Player createPlayer(float x, float y)
    {
        Player player = new Player(x, y, ResourceManager.getInstance().playerTextureRegion, vbom);
        player.setZIndex(2);

        Body playerBody = PhysicsFactory.createBoxBody(physicsWorld, player, BodyDef.BodyType.DynamicBody, PLAYER_FIXTURE);
        playerBody.setLinearDamping(1f);
        playerBody.setFixedRotation(true);
        playerBody.setUserData(player);

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(player, playerBody, true, true));

        player.setBody(playerBody);
        return player;
    }

    public void create(PhysicsWorld physicsWorld, VertexBufferObjectManager vbom)
    {
        this.physicsWorld = physicsWorld;
        this.vbom = vbom;
    }
}