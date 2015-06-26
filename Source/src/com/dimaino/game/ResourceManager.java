package com.dimaino.game;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.font.Font;

import android.graphics.Color;

/**
 * Singleton Class
 * 
 * Class is called though a static method called getInstance()
 * 
 * @author Daniel Imaino
 * @version 1.0
 */


public class ResourceManager
{
    private static final ResourceManager INSTANCE = new ResourceManager();

    // Common Objects
    public GameActivity activity;
    public Engine engine;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    // Game Textures
    // TiledTextureRegions - Same as normal textures but they define subregions within themselves. Used for sprites that change shape or are animated
    // TextureRegions - A surface applied to an object.
    public ITiledTextureRegion playerTextureRegion;
    public ITiledTextureRegion enemyTextureRegion;
    public ITextureRegion platformTextureRegion;
    public ITextureRegion cloudTextureRegion1;
    public ITextureRegion cloudTextureRegion2;

    // Sounds - ogg file of a sound
    public Sound soundFall;
    public Sound soundJump;
    
    public Sound finishHim;
    public Sound buzzBuzz;

    //Music - mp3 file of some music
    public Music music;

    //Fonts - font made by andengine
    public Font font;

    // TextureAtlas - Collection of textures on a single image
    private BuildableBitmapTextureAtlas gameTextureAtlas;
    
    // Splash Screen Resources
    public ITextureRegion splashTextureRegion;
	private BitmapTextureAtlas splashTextureAtlas;	

	
    private ResourceManager()
    {

    }

    public static ResourceManager getInstance()
    {
        return INSTANCE;
    }

    
    public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObjectManager vbom)
    {
        this.activity = activity;
        this.engine = engine;
        this.camera = camera;
        this.vbom = vbom;
    }

    /**
     * <h1>loadGameGraphics()</h1>
     * <br>
     * This method loads the graphics for the main game screen from the gfx/ folder. Also useds the BlackPawnTextureAtlasBuilder to build the textures automatically. 
     * The BlackPawnTextureAtlasBuilder accepts three parameters (Atlas border spacing, Source spacing, Source padding)
     * <br>
     * Example:
     * <br>
     * {@code ResourceManager.getInstance().loadGameGraphics();}
     * @exception RuntimeException when files not found
     * @author Daniel Imaino
     * @version 1.0
     */
    public void loadGameGraphics()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        gameTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 1024, 512, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA); // BitmapTextureFormat.RGBA_8888,
        playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "player1.png", 3, 1);
        enemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas, activity.getAssets(), "test2.png", 1, 2);
        platformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "platform.png");
        cloudTextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "cloud1.png");
        cloudTextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas, activity.getAssets(), "cloud2.png");

        try
        {
            this.gameTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 0, 2));
            this.gameTextureAtlas.load();
        }
        catch(final TextureAtlasBuilderException e)
        {
            throw new RuntimeException("Error while loading game textures", e);
        }
    }

    /**
     * <h1>loadGameAudio()</h1>
     * <br>
     * This method loads the music and sound for the splash screen from the sfx/ folder
     * <br>
     * Example:
     * <br>
     * {@code ResourceManager.getInstance().loadGameAudio();}
     * @exception RuntimeException when files not found
     * @author Daniel Imaino
     * @version 1.0
     */
    public void loadGameAudio() 
    {
        try 
        {
            SoundFactory.setAssetBasePath("sfx/");
            soundJump = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "jump.wav");
            soundFall = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "fall.ogg");
            finishHim = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "finishHim.ogg");
            buzzBuzz = SoundFactory.createSoundFromAsset(activity.getSoundManager(), activity, "buzzBuzz.ogg");
            MusicFactory.setAssetBasePath("sfx/");
            music = MusicFactory.createMusicFromAsset(activity.getMusicManager(), activity, "music.mp3");
        }
        catch(Exception e) 
        {
            throw new RuntimeException("Error while loading audio", e);
        }
    }

    /**
     * <h1>loadFont()</h1>
     * <br>
     * This method loads the font from the font/font.ttf file and creates the size, color, antialising, and takes in the characters that you want to use.
     * <br>
     * Example:
     * <br>
     * {@code ResourceManager.getInstance().loadFont();}
     * @author Daniel Imaino
     * @version 1.0
     */
    public void loadFont()
    {
    	// FontFactory creates fonts and stroked fonts from a system font from font files.
        font = FontFactory.createFromAsset(activity.getFontManager(), activity.getTextureManager(), 256, 512, activity.getAssets(), "font/font.ttf", 46, true, Color.BLACK);
        font.prepareLetters("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?abcdefghijklmnopqrstuvwxyz".toCharArray());
        font.load();
    }
    
    /**
     * <h1>loadSplashGraphics()</h1>
     * <br>
     * This method loads the graphics for the splash screen from the gfx/ folder
     * <br>
     * Example:
     * <br>
     * {@code ResourceManager.getInstance().loadSplashGraphics();}
     * @author Daniel Imaino
     * @version 1.0
     */
    public void loadSplashGraphics() 
    {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");		
		splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, BitmapTextureFormat.RGBA_8888, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas, activity.getAssets(), "LoadScreenBadge.png", 0, 0);	
		splashTextureAtlas.load();
	}
	
    /**
     * <h1>unloadSplashGraphics()</h1>
     * <br>
     * This method unloads the graphics for the splash screen
     * <br>
     * Example:
     * <br>
     * {@code ResourceManager.getInstance().unloadSplashGraphics();}
     * @author Daniel Imaino
     * @version 1.0
     */
	public void unloadSplashGraphics()
	{
		splashTextureAtlas.unload();
	}	
}