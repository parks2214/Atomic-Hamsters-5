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
    //int[] arnX = {nX1, nX2, nX3};
    //int[] arnY = {nY1, nY2, nY3};
    int nHeadX = 137, nHeadY = 130, nTailX = 150, nTailY = 80;
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

        Gdx.gl.glClearColor(1, 1, 1, 1);//Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Arrays.sort(arnX);
        //Arrays.sort(arnY);
        /*if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
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
        }*/

        sprMhead.setX(nHeadX);
        sprMhead.setY(nHeadY);
        sprMtail.setX(nTailX);
        sprMtail.setY(nTailY);

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        sprMtail.draw(batch);
        batch.draw(txBar, nX1, nY1,20,20);
        batch.draw(txBar, nX2, nY2,20,20);
        batch.draw(txBar, nX3, nY3,20,20);
        //batch.draw(txBar, arnX[0], arnY[0],20,20);
        //batch.draw(txBar, arnX[1], arnY[1],20,20);
        //batch.draw(txBar, arnX[2], arnY[2],20,20);
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nTailX = nX3;
            nTailY = nY3;
            nX3 = nX2;
            nY3 = nY2;
            nX2 = nX1;
            nY2 = nY1;
            nX1 += -20;
            nHeadX += -20;
            //sprMtail.setFlip(true, false);
            sprMtail.setRotation(90);
            //false, true
            /*arnX[2] = arnX[0] - 20;
            arnY[2] = arnY[0];*/

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nTailX = nX3;
            nTailY = nY3 - 10;
            nX3 = nX2;
            nY3 = nY2;
            nX2 = nX1;
            nY2 = nY1;
            nX1 += 20;
            nHeadX += 20;
            /*arnX[0] = arnX[2] + 20;
            arnY[0] = arnY[2];*/
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nTailX = nX3;
            nTailY = nY3;
            nX3 = nX2;
            nY3 = nY2;
            nX2 = nX1;
            nY2 = nY1;
            nY1 += -20;
            nHeadY += -20;
            /*arnY[2] = arnY[0] - 20;
            arnX[2] = arnX[0];*/
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nTailX = nX3;
            nTailY = nY3 - 10;
            nX3 = nX2;
            nY3 = nY2;
            nX2 = nX1;
            nY2 = nY1;
            nY1 += 20;
            nHeadY += 20;
            /*arnY[0] = arnY[2] + 20;
            arnX[0] = arnX[2];*/
        }
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
}
//for (int z = dots; z > 0; z--) {
//    x[z] = x[(z - 1)];
//    y[z] = y[(z - 1)];
//}
