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
import com.badlogic.gdx.math.Rectangle;

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
    Hamster hamster, hamster2;
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
            aniSprite = new AniSprite(txSheet);
        } else if (nChoice == 2) {
            txSheet = new Texture("sprmouse2.png");
            aniSprite = new AniSprite(txSheet);
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture ("sprmouse.png");
            aniSprite2 = new AniSprite(txSheet2);
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
            aniSprite2 = new AniSprite(txSheet2);
        }
        aniSprite.animate();
        aniSprite2.animate();
        hamster = new Hamster(100, 100, txSheet);
        hamster2 = new Hamster(490, 330, txSheet2);
        txTextbox1 = new Texture("Textbox.png");
        txTextbox2 = new Texture("Textbox2.png");
        arsprTextbox[0] = new Sprite(txTextbox1);
        arsprTextbox[1] = new Sprite(txTextbox2);
        for (int i = 0; i < arsprTextbox.length; i++) {
            arsprTextbox[i].setFlip(false, true);
            arsprTextbox[i].setSize(300, 125);
            arsprTextbox[i].setPosition(Gdx.graphics.getWidth() / 2 - arsprTextbox[i].getWidth() / 2, 0);
        }
        txMap = new Texture("jupiter.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(1, 1.5f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        txWall = new Texture ("Wall2.jpg");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0, txWall);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50, txWall);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50, txWall);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50, txWall);    //Right Wall
        nFrame = 0;
        nPoints = 0;
        nPoints2 = 0;
        nInd = 1;
        Gdx.input.setInputProcessor(this);
        pMaker = new PelletMaker(25, 25,"Hamster Pellet.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (nFrame > 7) {
            nFrame = 0;
        }
        nFrame++;
        //Input for hamster 1
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nDir = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nDir = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nDir = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nDir = 3;
        }
        //Input for hamster 2
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nDir2 = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nDir2 = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nDir2 = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nDir2 = 3;
        }
        hamster2.move(nDir2, fSpeed2, nSizeX2, nSizeY2);
        hamster.move(nDir, fSpeed, nSizeX, nSizeY);
        hamster.animation(nFrame);
        hamster2.animation(nFrame);
        for (int i = 0; i < arWall.length; i++) {
            if (hamster2.isHitS(arWall[i], nSizeY2)) {
                hamster2.outOfBounds();
            }
            if (hamster.isHitS(arWall[i], nSizeY)) {
                hamster.outOfBounds();
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(hamster.getThisRect(nSizeY), hamster2.getThisRect(nSizeY2))) {
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
            hamster.reset();
            hamster2.reset();
            System.out.println("Hit");
            gamMenu.updateState(6);
            nPoints = 0;
            nPoints2 = 0;
            nDir = 0;
            nDir2 = 2;
        }
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (hamster2.isHitS(p, nSizeY2)) {
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
            } if (hamster.isHitS(p, nSizeY)) {
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
        pMaker.draw(batch);
        hamster.spTemp.draw(batch);
        hamster2.spTemp.draw(batch);
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
                hamster.reset();
                hamster2.reset();
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
