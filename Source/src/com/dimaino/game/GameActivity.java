package com.dimaino.game;


import com.dimaino.game.scene.AbstractScene;
import com.dimaino.game.scene.GameScene;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.content.SharedPreferences;
import android.view.KeyEvent;

public class GameActivity extends BaseGameActivity
{
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	
	private final String KEY_SOUND = "Sound";
	private final String KEY_HISCORE = "HiScore";
	
	Music mu = ResourceManager.getInstance().music;
	
	SharedPreferences settings;
	
	public void setSound(boolean sound)
	{
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	settingsEditor.putBoolean(KEY_SOUND, sound);
    	settingsEditor.commit();
	}
	
	public boolean isSound()
	{
		return settings.getBoolean(KEY_SOUND, true);
	}
	
	public void playSound(Sound soundToPlay)
	{
		if (isSound())
		{
			soundToPlay.play();
		}
	}
	
	public void playMusic(Music musicToPlay)
	{
		//if (isSound())
		//{
			musicToPlay.play();
		//}
	}
	
	public void stopMusic(Music m)
	{
		//if (isSound())
		//{
			m.stop();
		//}
	}
	
	public void setHiScore(int score)
	{
    	SharedPreferences.Editor settingsEditor = settings.edit();
    	settingsEditor.putInt(KEY_HISCORE, score);
    	settingsEditor.commit();
	}
	
	public int getHiScore()
	{
		return settings.getInt(KEY_HISCORE, 0);
	}	
	
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		settings = getSharedPreferences("andengine_game_prefs", MODE_PRIVATE);
		Camera camera = new MyCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, resolutionPolicy, camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		
		Debug.i("Engine configured");
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws IOException
	{
		ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
		ResourceManager.getInstance().loadSplashGraphics();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
	{
		// we just have to pass something to the callback
		pOnCreateSceneCallback.onCreateSceneFinished(null);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
		SceneManager.getInstance().showSplashAndMenuScene();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			//stopMusic(mu);
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
