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

public class ScrTail implements Screen, InputProcessor {

    Button btnMenu, btnQuit;
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txBar, txMHead, txMTail;
    int nHam1X1 = 150, nHam1Y1 = 150, nHam1X2 = 150, nHam1Y2 = 130, nHam1X3 = 150, nHam1Y3 = 110;
    int nHam1HeadX = 137, nHam1HeadY = 130, nHam1TailX = 150, nHam1TailY = 80;
    int nHam2X1 = 150, nHam2Y1 = 150, nHam2X2 = 150, nHam2Y2 = 130, nHam2X3 = 150, nHam2Y3 = 110;
    int nHam2HeadX = 137, nHam2HeadY = 130, nHam2TailX = 150, nHam2TailY = 80;
    Sprite sprMhead1,sprMtail1, sprMhead2,sprMtail2;

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
        sprMtail1 = new Sprite (txMTail);
        sprMtail1.setSize (20,40);
        sprMtail1.setFlip(false,true);
        sprMtail1.setPosition(nHam1TailX, nHam1TailY);
        sprMtail2 = new Sprite (txMTail);
        sprMtail2.setSize (20,40);
        sprMtail2.setFlip(false,true);
        sprMtail2.setPosition(nHam2TailX, nHam2TailY);
        txMHead = new Texture("hamsterhead.png");
        sprMhead1 = new Sprite(txMHead);
        sprMhead1.setSize(45, 60);
        sprMhead1.setFlip(false, true);
        sprMhead1.setPosition(nHam1TailX, nHam1TailY);
        sprMhead2 = new Sprite(txMHead);
        sprMhead2.setSize(45, 60);
        sprMhead2.setFlip(false, true);
        sprMhead2.setPosition(nHam2TailX, nHam2TailY);
        txBar = new Texture ("brownpiskel.png");
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);//Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nHam1TailX = nHam1X3;
            nHam1TailY = nHam1Y3;
            nHam1X3 = nHam1X2;
            nHam1Y3 = nHam1Y2;
            nHam1X2 = nHam1X1;
            nHam1Y2 = nHam1Y1;
            nHam1X1 += -20;
            nHam1HeadX += -20;

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nHam1TailX = nHam1X3;
            nHam1TailY = nHam1Y3 - 10;
            nHam1X3 = nHam1X2;
            nHam1Y3 = nHam1Y2;
            nHam1X2 = nHam1X1;
            nHam1Y2 = nHam1Y1;
            nHam1X1 += 20;
            nHam1HeadX += 20;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nHam1TailX = nHam1X3;
            nHam1TailY = nHam1Y3;
            nHam1X3 = nHam1X2;
            nHam1Y3 = nHam1Y2;
            nHam1X2 = nHam1X1;
            nHam1Y2 = nHam1Y1;
            nHam1Y1 += -20;
            nHam1HeadY += -20;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nHam1TailX = nHam1X3;
            nHam1TailY = nHam1Y3 - 10;
            nHam1X3 = nHam1X2;
            nHam1Y3 = nHam1Y2;
            nHam1X2 = nHam1X1;
            nHam1Y2 = nHam1Y1;
            nHam1Y1 += 20;
            nHam1HeadY += 20;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nHam2TailX = nHam2X3;
            nHam2TailY = nHam2Y3;
            nHam2X3 = nHam2X2;
            nHam2Y3 = nHam2Y2;
            nHam2X2 = nHam2X1;
            nHam2Y2 = nHam2Y1;
            nHam2X1 += -20;
            nHam2HeadX += -20;

        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nHam2TailX = nHam2X3;
            nHam2TailY = nHam2Y3 - 10;
            nHam2X3 = nHam2X2;
            nHam2Y3 = nHam2Y2;
            nHam2X2 = nHam2X1;
            nHam2Y2 = nHam2Y1;
            nHam2X1 += 20;
            nHam2HeadX += 20;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nHam2TailX = nHam2X3;
            nHam2TailY = nHam2Y3;
            nHam2X3 = nHam2X2;
            nHam2Y3 = nHam2Y2;
            nHam2X2 = nHam2X1;
            nHam2Y2 = nHam2Y1;
            nHam2Y1 += -20;
            nHam2HeadY += -20;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nHam2TailX = nHam2X3;
            nHam2TailY = nHam2Y3 - 10;
            nHam2X3 = nHam2X2;
            nHam2Y3 = nHam2Y2;
            nHam2X2 = nHam2X1;
            nHam2Y2 = nHam2Y1;
            nHam2Y1 += 20;
            nHam2HeadY += 20;
        }

        sprMhead1.setX(nHam1HeadX);
        sprMhead1.setY(nHam1HeadY);
        sprMtail1.setX(nHam1TailX);
        sprMtail1.setY(nHam1TailY);

        sprMhead2.setX(nHam2HeadX);
        sprMhead2.setY(nHam2HeadY);
        sprMtail2.setX(nHam2TailX);
        sprMtail2.setY(nHam2TailY);

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        sprMtail1.draw(batch);
        batch.draw(txBar, nHam1X1, nHam1Y1,20,20);
        batch.draw(txBar, nHam1X2, nHam1Y2,20,20);
        batch.draw(txBar, nHam1X3, nHam1Y3,20,20);
        sprMhead1.draw(batch);
        sprMtail2.draw(batch);
        batch.draw(txBar, nHam2X1, nHam2Y1,20,20);
        batch.draw(txBar, nHam2X2, nHam2Y2,20,20);
        batch.draw(txBar, nHam2X3, nHam2Y3,20,20);
        sprMhead2.draw(batch);
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
