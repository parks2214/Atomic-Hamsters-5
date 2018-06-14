package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import java.util.Arrays;

public class ScrTail implements Screen, InputProcessor {

    Button btnMenu, btnQuit;
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txBar, txMHead, txMTail;
    int nX1 = 150, nY1 = 150, nX2 = 150, nY2 = 130, nX3 = 150, nY3 = 110;
    int[] arnX = {nX1, nX2, nX3};
    int[] arnY = {nY1, nY2, nY3};
    int nHeadX = 137, nHeadY = 130, nTailX = 143, nTailY = 80;
    Sprite sprMhead,sprMtail;

    public ScrTail(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        txMTail = new Texture("Hamstertail.png");
        sprMtail = new Sprite (txMTail);
        sprMtail.setSize (20,40);
        sprMtail.setFlip(false,true);
        sprMtail.setPosition(nTailX, nTailY);
        txMHead = new Texture("hamsterhead.png");
        sprMhead = new Sprite(txMHead);
        sprMhead.setSize(45, 60);
        sprMhead.setFlip(false, true);
        sprMhead.setPosition(nTailX, nTailY);
        txBar = new Texture ("brownpiskel.png");
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);//Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Arrays.sort(arnX);
        Arrays.sort(arnY);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nHeadX += -20;
            nTailX += -20;
            arnX[2] = arnX[0] - 20;
            arnY[2] = arnY[0];
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nHeadX += 20;
            nTailX += 20;
            arnX[0] = arnX[2] + 20;
            arnY[0] = arnY[2];
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nHeadY += -20;
            nTailY += -20;
            arnY[2] = arnY[0] - 20;
            arnX[2] = arnX[0];
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nHeadY += 20;
            nTailY += 20;
            arnY[0] = arnY[2] + 20;
            arnX[0] = arnX[2];
        }

        sprMhead.setX(nHeadX);
        sprMhead.setY(nHeadY);
        sprMtail.setX(nTailX);
        sprMtail.setY(nTailY);

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        sprMtail.draw(batch);
        batch.draw(txBar, arnX[0], arnY[0],20,20);
        batch.draw(txBar, arnX[1], arnY[1],20,20);
        batch.draw(txBar, arnX[2], arnY[2],20,20);
        sprMhead.draw(batch);
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
    }

    @Override
    public boolean keyDown(int keycode) {
        /*if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nX1-=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nX1+=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nY1-=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nY1+=5;
        }*/
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
            //System.out.println(screenX +" " + screenY);
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
            } else if (isHitB(screenX, screenY, btnQuit)) {
                System.out.println("Quit");
                System.exit(0);
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

    public boolean isHitB(int nX, int nY, Sprite sprBtn) {
        if (nX > sprBtn.getX() && nX < sprBtn.getX() + sprBtn.getWidth() && nY > sprBtn.getY() && nY < sprBtn.getY() + sprBtn.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    /*public int firstSquare() {
        int nDif2, nDif3, nDif4;
        if (nDir == 2 && nDir == 0) {
            nDif2 = nX1 - nX2;
            nDif3 = nX1 - nX3;
            nDif4 = nX1 - nX4;
            if (nDif4 < nDif3 && nDif4 < nDif2) {
                return nX4;
            } else if (nDif3 < nDif4 && nDif3 < nDif2) {
                return nX3;
            } else {
                return nX2;
            }
        } else {
            nDif2 = nY1 - nY2;
            nDif3 = nY1 - nY3;
            nDif4 = nY1 - nY4;
            if (nDif4 < nDif3 && nDif4 < nDif2) {
                return nY4;
            } else if (nDif3 < nDif4 && nDif3 < nDif2) {
                return nY3;
            } else {
                return nY2;
            }
        }
    }

    public int lastSquare() {
        int nDif2, nDif3, nDif4;
        if (nDir == 2 && nDir == 0) {
            nDif2 = nX1 - nX2;
            nDif3 = nX1 - nX3;
            nDif4 = nX1 - nX4;
            if (nDif4 > nDif3 && nDif4 > nDif2) {
                return nX4;
            } else if (nDif3 > nDif4 && nDif3 > nDif2) {
                return nX3;
            } else {
                return nX2;
            }
        } else {
            nDif2 = nY1 - nY2;
            nDif3 = nY1 - nY3;
            nDif4 = nY1 - nY4;
            if (nDif4 > nDif3 && nDif4 > nDif2) {
                return nY4;
            } else if (nDif3 > nDif4 && nDif3 > nDif2) {
                return nY3;
            } else {
                return nY2;
            }
        }
    }*/
}
