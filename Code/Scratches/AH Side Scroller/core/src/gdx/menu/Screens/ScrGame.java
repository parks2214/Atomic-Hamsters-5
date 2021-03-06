package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import gdx.menu.GamMenu;
import gdx.menu.images.*;

public class ScrGame implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    Texture txSheet,txSheet2, txMap, txTextbox1, txTextbox2, txWall;
    Sprite sprMap;
    Sprite arsprTextbox[] = new Sprite[2];
    int nFrame;
    int nDir = 0, nDir2 = 2, nSizeX = 50, nSizeY = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    float fSpeed = 0, fSpeed2 = 0;
    static int nPoints = 0, nPoints2 = 0;
    int nChoice, nChoice2;
    static int nWin;
    static public int nInd = 0;
    PelletMaker pMaker;
    int nTimer=0;
    AniSprite aniSprite, aniSprite2;
    public ScrGame(GamMenu _gamMenu) {
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        // Choice between which sprite they take
        nChoice = ScrAnimalChoice.nChoice;
        nChoice2 = ScrAnimalChoice2.nChoice2;
        if (nChoice == 1) {
            txSheet = new Texture("sprmouse.png");
        } else if (nChoice == 2) {
            txSheet = new Texture("sprmouse2.png");
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture ("sprmouse.png");
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
        }
        aniSprite = new AniSprite(100, 100, txSheet);
        aniSprite2 = new AniSprite(490, 330, txSheet2);
       //Background
        txMap = new Texture("jupiter.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(0.9f, 1.2f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        //Wall system
        txWall = new Texture ("Wall2.jpg");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0, txWall);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50, txWall);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50, txWall);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50, txWall);    //Right Wall
        //Other stuff
        nFrame = 0;
        nPoints = 0;
        nPoints2 = 0;
        nInd = 1;
        Gdx.input.setInputProcessor(this);
        pMaker = new PelletMaker("Hamster Pellet.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (nFrame > 7) {
            nFrame = 0;
        }
        nFrame++;
        //Input for aniSprite 1
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nDir = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nDir = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nDir = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nDir = 3;
        }
        //Input for aniSprite 2
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nDir2 = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nDir2 = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nDir2 = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nDir2 = 3;
        }
        aniSprite2.move(nDir2, fSpeed2, nSizeX2, nSizeY2);
        aniSprite.move(nDir, fSpeed, nSizeX, nSizeY);
        aniSprite.animation(nFrame);
        aniSprite2.animation(nFrame);
        for (int i = 0; i < arWall.length; i++) {
            if (aniSprite2.isHitS(arWall[i], nSizeY2)) {
                aniSprite2.outOfBounds();
            }
            if (aniSprite.isHitS(arWall[i], nSizeY)) {
                aniSprite.outOfBounds();
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(aniSprite.getThisRect(nSizeY), aniSprite2.getThisRect(nSizeY2))) {
            if (nPoints > nPoints2) {
                nWin = 1;
            } else if (nPoints2 > nPoints) {
                nWin = 2;
            } else {
                nWin = 0;
            }
            fSpeed = 0;
            fSpeed2 = 0;
            nSizeX = 50;
            nSizeX2 = 50;
            nSizeY = 50;
            nSizeY2 = 50;
            aniSprite.reset();
            aniSprite2.reset();
            System.out.println("Hit");
            gamMenu.updateState(6);
            nPoints = 0;
            nPoints2 = 0;
            nDir = 0;
            nDir2 = 2;
        }
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (aniSprite2.isHitS(p, nSizeY2)) {
                fSpeed2 += 0.5f;
                nTimer=240;
                System.out.println(fSpeed);
                nPoints += 1;
                System.out.println("Points for first: " + nPoints);
                if (nSizeX2 < 100 && nSizeY2 < 100) {
                    nSizeX2 += 3;
                    nSizeY2 += 3;
                    System.out.println(nSizeX2 + "   " + nSizeY2);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            } if (aniSprite.isHitS(p, nSizeY)) {
                nTimer=240;
                fSpeed += 0.5f;
                System.out.println(fSpeed);
                nPoints2 += 1;
                System.out.println("Points for first: " + nPoints2);
                if (nSizeX < 100 && nSizeY < 100) {
                    nSizeX += 3;
                    nSizeY += 3;
                    System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }
            if (nTimer>=300) {
                System.out.println("The clock struck 5 seconds");
                pMaker.removePellet(p);
                nTimer=0;
            }
        }

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
        sprMap.draw(batch);
<<<<<<< HEAD
        pMaker.draw(batch, 51 + (float)(Math.random() * ((559 - 51) + 1)), 51 + (float)(Math.random() * ((399 - 51) + 1)));
        aniMouse1.spTemp.draw(batch);
        aniMouse2.spTemp.draw(batch);
=======
        pMaker.draw(batch);
        aniSprite.spTemp.draw(batch);
        aniSprite2.spTemp.draw(batch);
>>>>>>> parent of 5fe5497... Merge remote-tracking branch 'origin/master'
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
        txSheet.dispose();
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
                aniSprite.reset();
                aniSprite2.reset();
                nPoints = 0;
                nPoints2 = 0;
                nWin = 0;
                fSpeed = 0;
                fSpeed2 = 0;
                nSizeX = 50;
                nSizeY = 50;
                nSizeX2 = 50;
                nSizeY2 = 50;
                nDir = 0;
                nDir2 = 2;
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
