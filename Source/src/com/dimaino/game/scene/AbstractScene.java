package com.dimaino.game.scene;

import com.dimaino.game.GameActivity;
import com.dimaino.game.ResourceManager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

/**
 * <h1>AbstractScene</h1>
 * <br>
 * The AbstractScene is a template for creating scenes.
 * <br>
 * @author Daniel Imaino
 * @version 1.0
 */

public abstract class AbstractScene extends Scene
{
    protected ResourceManager res = ResourceManager.getInstance();
    protected Engine engine = res.engine;
    protected GameActivity activity = res.activity;
    protected VertexBufferObjectManager vbom = res.vbom;
    protected Camera camera = res.camera;

    public abstract void populate();

    public void destroy()
    {

    }
    public void onBackKeyPressed()
    {
        Debug.d("Back Key Pressed");
    }

    public abstract void onPause();
    public abstract void onResume();
}