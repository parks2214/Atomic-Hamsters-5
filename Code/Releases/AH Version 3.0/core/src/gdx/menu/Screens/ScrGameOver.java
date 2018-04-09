package gdx.menu.Screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class ScrGameOver implements Screen, InputProcessor {

    Button btnQuit, btnMenu;
    GamMenu gamMenu;
    Texture txBackground;
    OrthographicCamera oc;
    SpriteBatch batch;
    Sprite sprBackground;
    BitmapFont font;
    int nPoints, nPoints2;

    public ScrGameOver(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        font.setColor (Color.BLACK);
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, 0, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, "Quit.jpg");
        btnMenu.setFlip(false, false);
        btnQuit.setFlip(false, false);
        txBackground = new Texture ("explosion.png");
        sprBackground = new Sprite (txBackground);
        sprBackground.setScale(0.9f,0.7f);
        sprBackground.setPosition(Gdx.graphics.getWidth() / 2 - sprBackground.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprBackground.getHeight() / 2);
        sprBackground.setFlip(false, false);
        nPoints = ScrGame.nPoints;
        nPoints2 = ScrGame.nPoints2;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,0); //black background

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sprBackground.draw(batch);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        font.draw(batch, "Second Player's Points: " + nPoints2, 20, 50);
        font.draw(batch, "First Player's Points: " + nPoints, 20, 80);
        if (nPoints > nPoints2) {
            font.draw(batch, "WINNER: Player 1", 20, 450);
        } else if (nPoints2 > nPoints) {
            font.draw(batch, "WINNER: Player 2", 20, 450);
        } else {
            font.draw(batch, "PLAYERS TIED!", 20, 450);
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
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
        font.dispose();
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
            if (isHit(screenX, screenY, btnQuit)) {
                System.out.println("Quit");
                System.exit(0);

            } else if (isHit(screenX, screenY, btnMenu)) {
                System.out.println("Menu");
                gamMenu.updateState(0);
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
