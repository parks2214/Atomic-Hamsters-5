package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import gdx.menu.images.AniSprite;
import gdx.menu.images.LitJams;

public class ScrMenu implements Screen, InputProcessor {

    Button btnQuit, btnRules, btnGame;
    GamMenu gamMenu;
    Texture txSheet, txBackground;
    OrthographicCamera oc;
    SpriteBatch batch;
    Sprite sprBackground;
    int nFrame,nPos;
    int nDir = 0, nCount=0;
    AniSprite aniMouse;
    LitJams litjam = new LitJams();

    public ScrMenu(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        litjam.IngameSound(1);
        //Buttons
        batch = new SpriteBatch();
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        btnRules = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 0, "Rules.png");
        btnGame = new Button(100, 50, 0, 0, "Game.png");
        //Background
        txBackground = new Texture ("earth2.jpg");
        sprBackground = new Sprite (txBackground);
        sprBackground.setFlip(false, true);
        sprBackground.setPosition(Gdx.graphics.getWidth() / 2 - sprBackground.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprBackground.getHeight() / 2);
        sprBackground.setScale(0.7f,0.8f);
        //AniSprite stuff
        txSheet = new Texture("sprmouse.png");
        aniMouse = new AniSprite(150, 325, txSheet);
        nFrame = 0;
        nPos = 0;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        //Camera Stuff
        Gdx.gl.glClearColor(0,0,0,0); //black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Animation stuff
        if (nFrame > 7) {
            nFrame = 0;
        }
        if (nCount>=300) {
            nDir++;
            nCount=0;
        }
        if (nCount==200 && nDir==1 || nCount == 200 && nDir==3) {
            nDir++;
            nCount=0;
        }
        if (nDir==4) {
            nDir=0;
        }
        nCount++;
        nFrame++;
        aniMouse.move(nDir);
        aniMouse.animation(nFrame);
        //Drawing stuff
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sprBackground.draw(batch);
        btnQuit.draw(batch);
        btnRules.draw(batch);
        btnGame.draw(batch);
        aniMouse.spTemp.draw(batch);
        batch.end();
    }


    @Override
    public void pause() {
    }
    @Override
    public void resize (int width, int height) {
        Vector2 size = Scaling.fit.apply(640, 480, width, height);
        int viewportX = (int)(width - size.x) / 2;
        int viewportY = (int)(height - size.y) / 2;
        int viewportWidth = (int)size.x;
        int viewportHeight = (int)size.y;
        Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            litjam.IngameSound(0);
             if(isHit(screenX, screenY, btnQuit)){
                System.out.println("Quit");
                System.exit(0);
                nDir=0;
            } else if(isHit(screenX, screenY, btnRules)){
                System.out.println("Hit Rules");
                gamMenu.updateState(10);
                nDir=0;
            } else if(isHit(screenX, screenY, btnGame)){
                System.out.println("Hit Game");
                gamMenu.updateState(7);
                nDir=0;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isHit(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}