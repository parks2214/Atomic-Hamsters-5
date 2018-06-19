package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import gdx.menu.GamMenu;
import gdx.menu.images.*;

public class ScrGame5 implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    Texture txMap, txWall, txMHead, txMTail, txHamBar;
    Sprite sprMap;
    int nHam1X1 = 100, nHam1Y1 = 100, nHam1X2 = 100, nHam1Y2 = 80, nHam1X3 = 100, nHam1Y3 = 60;
    int nHam1HeadX = 87, nHam1HeadY = 80, nHam1TailX = 100, nHam1TailY = 30;
    int nHam2X1 = 490, nHam2Y1 = 330, nHam2X2 = 490, nHam2Y2 = 310, nHam2X3 = 490, nHam2Y3 = 290;
    int nHam2HeadX = 477, nHam2HeadY = 310, nHam2TailX = 490, nHam2TailY = 260;
    int nScaleY1 = 20, nScaleY2 = 20;
    Sprite sprMhead1, sprMtail1, sprMhead2, sprMtail2, sprMhead1test, sprMhead2test;
    Wall[] arWall = new Wall[4];
    static int n5Points = 0, n5Points2 = 0;
    static int nWin5;
    PelletMaker pMaker1;
    LitJams litjam = new LitJams();
    int nDir1, nDir2;
    int fSpeed = 0;
    //640, 480

    public ScrGame5(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        litjam.IngameSound(1);
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        //Hamsters
        txMTail = new Texture("Hamstertail.png");
        sprMtail1 = new Sprite(txMTail);
        sprMtail1.setSize(20, 40);
        sprMtail1.setFlip(false, true);
        sprMtail1.setPosition(nHam1TailX, nHam1TailY);
        sprMtail2 = new Sprite(txMTail);
        sprMtail2.setSize(20, 40);
        sprMtail2.setFlip(false, true);
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
        txHamBar = new Texture("brownpiskel.png");
        sprMhead1test = sprMhead1;
        sprMhead2test = sprMhead2;
        sprMhead1test.setX(sprMhead1test.getX());
        sprMhead2test.setX(sprMhead2test.getX());
        sprMhead1test.setY(sprMhead1test.getY());
        sprMhead2test.setY(sprMhead2test.getY());
        //Background
        txMap = new Texture("Venus.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(0.7f, 0.7f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        //Walls
        txWall = new Texture("Wall2.jpg");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0, txWall);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50, txWall);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50, txWall);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50, txWall);    //Right Wall
        //Other stuff
        n5Points = 0;
        n5Points2 = 0;
        ScrGame.nInd = 5;

        Gdx.input.setInputProcessor(this);

        pMaker1 = new PelletMaker("Cherry.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            sprMhead1test.setX(sprMhead1.getX() + -20);
            if (!isHitW(sprMhead1test)) {
                nHam1TailX = nHam1X3;
                nHam1TailY = nHam1Y3;
                nHam1X3 = nHam1X2;
                nHam1Y3 = nHam1Y2;
                nHam1X2 = nHam1X1;
                nHam1Y2 = nHam1Y1;
                nHam1X1 += -20;
                nHam1HeadX += -20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprMhead1test.setX(sprMhead1.getX() + 20);
            if (!isHitW(sprMhead1test)) {
                nHam1TailX = nHam1X3 + fSpeed;
                nHam1TailY = nHam1Y3 - 10;
                nHam1X3 = nHam1X2;
                nHam1Y3 = nHam1Y2;
                nHam1X2 = nHam1X1;
                nHam1Y2 = nHam1Y1;
                nHam1X1 += 20;
                nHam1HeadX += 20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            sprMhead1test.setY(sprMhead1.getY() + -20);
            if (!isHitW(sprMhead1test)) {
                nHam1TailX = nHam1X3;
                nHam1TailY = nHam1Y3;
                nHam1X3 = nHam1X2;
                nHam1Y3 = nHam1Y2;
                nHam1X2 = nHam1X1;
                nHam1Y2 = nHam1Y1;
                nHam1Y1 += -20;
                nHam1HeadY += -20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            sprMhead1test.setY(sprMhead1.getY() + 20);
            if (!isHitW(sprMhead1test)) {
                nHam1TailX = nHam1X3;
                nHam1TailY = nHam1Y3 - 10;
                nHam1X3 = nHam1X2;
                nHam1Y3 = nHam1Y2;
                nHam1X2 = nHam1X1;
                nHam1Y2 = nHam1Y1;
                nHam1Y1 += 20;
                nHam1HeadY += 20;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprMhead2test.setX(sprMhead2.getX() + -20);
            if (!isHitW(sprMhead2test)) {
                nHam2TailX = nHam2X3;
                nHam2TailY = nHam2Y3;
                nHam2X3 = nHam2X2;
                nHam2Y3 = nHam2Y2;
                nHam2X2 = nHam2X1;
                nHam2Y2 = nHam2Y1;
                nHam2X1 += -20;
                nHam2HeadX += -20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprMhead2test.setX(sprMhead2.getX() + 20);
            if (!isHitW(sprMhead2test)) {
                nHam2TailX = nHam2X3;
                nHam2TailY = nHam2Y3 - 10;
                nHam2X3 = nHam2X2;
                nHam2Y3 = nHam2Y2;
                nHam2X2 = nHam2X1;
                nHam2Y2 = nHam2Y1;
                nHam2X1 += 20;
                nHam2HeadX += 20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprMhead2test.setY(sprMhead2.getY() + -20);
            if (!isHitW(sprMhead2test)) {
                nHam2TailX = nHam2X3;
                nHam2TailY = nHam2Y3;
                nHam2X3 = nHam2X2;
                nHam2Y3 = nHam2Y2;
                nHam2X2 = nHam2X1;
                nHam2Y2 = nHam2Y1;
                nHam2Y1 += -20;
                nHam2HeadY += -20;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprMhead2test.setY(sprMhead2.getY() + 20);
            if (!isHitW(sprMhead2test)) {
                nHam2TailX = nHam2X3;
                nHam2TailY = nHam2Y3 - 10;
                nHam2X3 = nHam2X2;
                nHam2Y3 = nHam2Y2;
                nHam2X2 = nHam2X1;
                nHam2Y2 = nHam2Y1;
                nHam2Y1 += 20;
                nHam2HeadY += 20;
            }
        }
        sprMhead1.setX(nHam1HeadX);
        sprMhead1.setY(nHam1HeadY);
        sprMtail1.setX(nHam1TailX);
        sprMtail1.setY(nHam1TailY);
        sprMhead2.setX(nHam2HeadX);
        sprMhead2.setY(nHam2HeadY);
        sprMtail2.setX(nHam2TailX);
        sprMtail2.setY(nHam2TailY);
        //pellet stuff
        for (int i = pMaker1.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker1.alPellets.get(i);
            if (isHitS(p, sprMhead1)) {
                n5Points += 1;
                System.out.println("Points for first: " + n5Points);
                // mouse catche pellet
                pMaker1.removePellet(p);
            }
            if (isHitS(p, sprMhead2)) {
                n5Points2 += 1;
                System.out.println("Points for first: " + n5Points2);
                // mouse catche pellet
                pMaker1.removePellet(p);
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(sprMhead1.getBoundingRectangle(), sprMhead2.getBoundingRectangle())) {
            litjam.IngameSound(0);
            if (n5Points > n5Points2) {
                nWin5 = 1;
            } else if (n5Points2 > n5Points) {
                nWin5 = 2;
            } else {
                nWin5 = 0;
            }
            System.out.println(nWin5);
            reset();
            System.out.println("Hit");
            gamMenu.updateState(6);
            n5Points = 0;
            n5Points2 = 0;
        }

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
        sprMap.draw(batch);
        //Hamsters
        sprMtail1.draw(batch);
        batch.draw(txHamBar, nHam1X1, nHam1Y1, 20, 20);
        batch.draw(txHamBar, nHam1X2, nHam1Y2, 20, 20);
        batch.draw(txHamBar, nHam1X3, nHam1Y3, 20, nScaleY1);
        sprMhead1.draw(batch);
        sprMtail2.draw(batch);
        batch.draw(txHamBar, nHam2X1, nHam2Y1, 20, 20);
        batch.draw(txHamBar, nHam2X2, nHam2Y2, 20, 20);
        batch.draw(txHamBar, nHam2X3, nHam2Y3, 20, nScaleY2);
        sprMhead2.draw(batch);
        pMaker1.draw(batch, 51 + (float) (Math.random() * ((559 - 51) + 1)), 51 + (float) (Math.random() * ((399 - 51) + 1)));
        btnMenu.draw(batch);
        btnQuit.draw(batch);
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
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                litjam.IngameSound(0);
                System.out.println("Hit Menu");
                n5Points = 0;
                n5Points2 = 0;
                nWin5 = 0;
                reset();
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

    public boolean isHitW(Sprite spr) {
        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(arWall[i], spr)) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        nHam1X1 = 100;
        nHam1Y1 = 100;
        nHam1X2 = 100;
        nHam1Y2 = 80;
        nHam1X3 = 100;
        nHam1Y3 = 60;
        nHam1HeadX = 87;
        nHam1HeadY = 80;
        nHam1TailX = 100;
        nHam1TailY = 30;
        nHam2X1 = 490;
        nHam2Y1 = 330;
        nHam2X2 = 490;
        nHam2Y2 = 310;
        nHam2X3 = 490;
        nHam2Y3 = 290;
        nHam2HeadX = 477;
        nHam2HeadY = 310;
        nHam2TailX = 490;
        nHam2TailY = 260;
    }
}