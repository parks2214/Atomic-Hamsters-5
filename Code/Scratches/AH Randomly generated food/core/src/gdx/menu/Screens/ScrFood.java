package gdx.menu.Screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import gdx.menu.images.Pellet;
import gdx.menu.images.PelletMaker;

public class ScrFood implements Screen, InputProcessor {

    boolean arbDirection[] = new boolean[4];
    Button btnMenu, btnQuit;
    OrthographicCamera oc;
    Texture txNamS, txHamP, txBox1, txBox2, txSheet;
    GamMenu gamMenu;
    SpriteBatch batch;
    Sprite sprNamSign, sprHamP, sprBox1, sprBox2, sprMouse, sprAni;
    int nTrig = 0; //Trigger variable for sign
    int nFrame, nPos, nW, nH, nSx, nSy, nX = 100, nY = 100, nSizeX = 50, nSizeY = 50;
    float fSpeed = 1;
    Animation araniMouse[];
    TextureRegion trTemp;
    Sprite spTemp;
    PelletMaker pMaker;

    public ScrFood(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        //Camerafy
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        //button
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        txNamS = new Texture("S.png");
        //mouse stuff
        txSheet = new Texture("sprmouse.png");
        //tis a sign
        sprNamSign = new Sprite(txNamS);
        sprNamSign.setFlip(false, true);
        sprNamSign.setSize(60, 80);
        sprNamSign.setPosition(Gdx.graphics.getWidth() / 2 - 30, Gdx.graphics.getHeight() / 2 - 40);

        // Textbox stuff
        txBox1 = new Texture("Textbox.png");
        sprBox1 = new Sprite(txBox1);
        sprBox1.setSize(300, 125);
        sprBox1.setPosition(Gdx.graphics.getWidth() / 2 - sprBox1.getWidth() / 2, 0);
        sprBox1.setFlip(false, true);
        txBox2 = new Texture("Textbox2.png");
        sprBox2 = new Sprite(txBox2);
        sprBox2.setPosition(Gdx.graphics.getWidth() / 2 - sprBox1.getWidth() / 2, 0);
        sprBox2.setSize(300, 125);
        sprBox2.setFlip(false, true);

        //Direction sets
        arbDirection[0] = false;
        arbDirection[1] = false;
        arbDirection[2] = false;
        arbDirection[3] = false;

        //Animation stuff
        nFrame = 0;
        nPos = 0;
        araniMouse = new Animation[4];
        nW = txSheet.getWidth() / 4;
        nH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                nSx = j * nW;
                nSy = i * nH;
                sprMouse = new Sprite(txSheet, nSx, nSy, nW, nH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = sprMouse;
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);

        }
        sprAni = new Sprite(txNamS, 0, 0, nW, nH);
        sprAni.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);

        //Pellet maker stuff
        pMaker = new PelletMaker();
    }

    @Override
    public void render(float Delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1); //Blue background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprAni.getX();
        float fSy = sprAni.getY();
        //Animation Stuff
        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniMouse[nPos].getKeyFrame(nFrame, false);
        if (spTemp == null) {
            spTemp = new Sprite(trTemp);
            spTemp.setFlip(false,true);
        } else {
            spTemp.setTexture(trTemp.getTexture());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            arbDirection[0] = true;
            arbDirection[1] = false;
            arbDirection[2] = false;
            arbDirection[3] = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            arbDirection[0] = false;
            arbDirection[1] = true;
            arbDirection[2] = false;
            arbDirection[3] = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            arbDirection[0] = false;
            arbDirection[1] = false;
            arbDirection[2] = true;
            arbDirection[3] = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            arbDirection[0] = false;
            arbDirection[1] = false;
            arbDirection[2] = false;
            arbDirection[3] = true;
        }

        //Direction instructions
        if (arbDirection[0] == true) {
            sprAni.setX(sprAni.getX() - fSpeed);
            nX = nX -= fSpeed;
            nPos = 1;
            nFrame++;
        }
        if (arbDirection[1] == true) {
            sprAni.setX(sprAni.getX() + fSpeed);
            nX = nX += fSpeed;
            nPos = 2;
            nFrame++;
        }
        if (arbDirection[2] == true) {
            sprAni.setY(sprAni.getY() - fSpeed);
            nY = nY -= fSpeed;
            nPos = 3;
            nFrame++;
        }
        if (arbDirection[3] == true) {
            sprAni.setY(sprAni.getY() + fSpeed);
            nY = nY += fSpeed;
            nPos = 0;
            nFrame++;
        }

        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (isHitS(p, spTemp)) {
                System.out.println("HERE");
                // mouse catche pellet
                pMaker.removePellet(p);
            }
        }




        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        btnMenu.draw(batch);
        sprNamSign.draw(batch);
        btnQuit.draw(batch);
        pMaker.draw(batch);
        batch.draw(trTemp, fSx, fSy, nSizeX, nSizeY);
        spTemp.setPosition(fSx, fSy);
//        spTemp.draw(batch);
//        batch.draw(spTemp, fSx, fSy, nSizeX, nSizeY);
        if (nTrig == 1) {
            sprBox1.draw(batch);
        } else if (nTrig == 3) {
            sprBox2.draw(batch);
        }
        batch.end();
    }

    /*
     * UpdateState(0) for Menu
     * UpdateState(1) for Play
     */
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
        txNamS.dispose();
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
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
                fSpeed=1;
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

