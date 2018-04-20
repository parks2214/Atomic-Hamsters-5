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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;

public class ScrGameOver implements Screen, InputProcessor {

    Button btnGame, btnMenu;
    GamMenu gamMenu;
    Texture txBackground;
    OrthographicCamera oc;
    SpriteBatch batch = new SpriteBatch();
    Sprite sprBackground;
    BitmapFont font;
    int nPoints, nPoints2;
    int nWin, nWin2;
    int nInd;

    public ScrGameOver(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        font = new BitmapFont(true);//this flips the font (https://stackoverflow.com/questions/8508749/draw-a-bitmapfont-rotated-in-libgdx)
        font.setColor(Color.BLACK);
        font.getData().setScale(1.2f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnGame = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Game.png");
        txBackground = new Texture("explosion.png");
        sprBackground = new Sprite(txBackground);
        sprBackground.setScale(0.9f, 0.7f);
        sprBackground.setPosition(Gdx.graphics.getWidth() / 2 - sprBackground.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprBackground.getHeight() / 2);
        sprBackground.setFlip(false, true);
        nPoints = ScrGame.nPoints;
        nPoints2 = ScrGame.nPoints2;
        nWin = ScrGame.nWin;
        nWin2 = ScrGame2.nWin2;
        nInd = ScrGame.nInd;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0); //black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        sprBackground.draw(batch);
        btnMenu.draw(batch);
        btnGame.draw(batch);
        font.draw(batch, "Second Player's Points: " + nPoints2, 20, 60);
        font.draw(batch, "First Player's Points: " + nPoints, 20, 80);
        if (nWin2 == 1 || nWin == 1) {
            font.draw(batch, "WINNER: Player 1", 490, 70);
        } else if (nWin2 == 2 || nWin == 2) {
            font.draw(batch, "WINNER: Player 2", 490, 70);
        } else {
            font.draw(batch, "PLAYERS TIED!", 490, 70);
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
            if (isHit(screenX, screenY, btnGame)) {
                System.out.println("Game");
                gamMenu.updateState(9);

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
