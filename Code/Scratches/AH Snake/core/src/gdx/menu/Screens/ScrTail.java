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
import gdx.menu.images.Wall;

public class ScrTail implements Screen, InputProcessor {

    Button btnMenu, btnQuit;
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txMHead, txMTail,txBar;
    Sprite sprMouse, sprAni, sprMhead,sprMtail;
    int  nX2, nY2, nX = 50, nY = 50,nY2=55,nX2=55,nY3=60,nX3=60, nDx, nDy;


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
        txBar = new Texture ("The bar.png");
        sprMtail = new Sprite (txMTail);
        sprMtail.setSize (20,40);
        sprMtail.setFlip(false,true);
        sprMtail.setPosition(nX,nY);
        txMHead = new Texture("hamsterhead.png");
        sprMhead = new Sprite(txMHead);
        sprMhead.setSize(60, 80);
        sprMhead.setFlip(false, true);
        sprMhead.setPosition(nX, nY);
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);//Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        nX = nX+nDx;
        nY = nY+nDy;
        if (nDy!=0) {
            nY2= nY+nDy-nY3;
        } else {
            nY2= nY;
        }
        if (nDx!=0) {
            nX2= nX+nDx-nX3;
        } else {
            nX2=nX;
        }
        sprMhead.setX(nX);
        sprMhead.setY(nY);
        sprMtail.setX(nX2);
        sprMtail.setY(nY2);
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        btnQuit.draw(batch);
        batch.draw(txBar,nX2,nY2);
        sprMhead.draw(batch);
        sprMtail.draw(batch);
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
        if (keycode == Input.Keys.A) {
            nDx=-1;
            nDy=0;
        } else if (keycode == Input.Keys.D) {
            nDx=1;
            nDy=0;
        } else if (keycode == Input.Keys.W) {
            nDy=-1;
            nDx=0;
        } else if (keycode == Input.Keys.S) {
            nDy=1;
            nDx=0;
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

    public boolean isHitS(Sprite spr1, Sprite spr2) {
        return spr1.getBoundingRectangle().overlaps(spr2.getBoundingRectangle());
    }
}
