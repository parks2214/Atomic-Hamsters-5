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

public class ScrMenu implements Screen, InputProcessor {

    Button btnPlay, btnAni, btnSign, btnQuit, btnRules, btnGame;
    GamMenu gamMenu;
    Texture txNamM, txBackground;
    OrthographicCamera oc;
    SpriteBatch batch;
    Sprite sprNamM,sprBackground;

    public ScrMenu(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        btnPlay = new Button(100, 50, 0, Gdx.graphics.getHeight() - 50, "Tail Button.png");
        btnAni = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, "Animation.jpg");
        btnSign = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Food.png");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        btnRules = new Button(100, 50, Gdx.graphics.getWidth()/2 - 50, 0, "Rules.png");
        btnGame = new Button(100, 50, 0, 0, "Game.png");
        txBackground = new Texture ("EarthBG.jpg");
        sprBackground = new Sprite (txBackground);
        sprBackground.setFlip(false, true);
        sprBackground.setScale(4.05f,2.75f);
        txNamM = new Texture("M.jpg");
        sprNamM = new Sprite(txNamM);
        sprNamM.setFlip(false, true);
        sprNamM.setSize(60, 80);
        sprNamM.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0); //black background
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sprBackground.draw(batch);
        sprNamM.draw(batch);
        btnPlay.draw(batch);
        btnAni.draw(batch);
        btnSign.draw(batch);
        btnQuit.draw(batch);
        btnRules.draw(batch);
        btnGame.draw(batch);
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
        txNamM.dispose();
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
            if (isHit(screenX, screenY, btnPlay)) {
                System.out.println("Hit Play");
                gamMenu.updateState(1);
            } else if (isHit(screenX, screenY, btnAni)) {
                System.out.println("Hit Animation");
                gamMenu.updateState(3);
            } else if(isHit(screenX, screenY, btnSign)){
                System.out.println("Hit Food");
                gamMenu.updateState(2);
            } else if(isHit(screenX, screenY, btnQuit)){
                System.out.println("Quit");
                System.exit(0);
            } else if(isHit(screenX, screenY, btnRules)){
                System.out.println("Hit Rules");
                gamMenu.updateState(10);
            } else if(isHit(screenX, screenY, btnGame)){
                System.out.println("Hit Game");
                gamMenu.updateState(7);
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