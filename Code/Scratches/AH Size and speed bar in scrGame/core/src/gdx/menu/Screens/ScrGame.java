package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import gdx.menu.images.Pellet;
import gdx.menu.images.PelletMaker;
import gdx.menu.images.Wall;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class ScrGame implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    TextureRegion trTemp, trTemp2;
    Texture txSheet,txSheet2, txMap, txTextbox1, txTextbox2;
    Sprite sprMouse, sprMouse2, sprMap;
    Sprite arsprTextbox[] = new Sprite[2];
    int nFrame, nPos, nPos2, nX = 100, nY = 100, nX2 = 100, nY2 = 100, nTrig = 0;
    Animation araniMouse[], araniMouse2[];
    int fSx, fSy, fSx2, fSy2, fW, fH,fW2,fH2, nDir = 0, nDir2 = 0, nSizeX = 50, nSizeY = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};
    int DX2[] = {1, 0, -1, 0};
    int DY2[] = {0, -1, 0, 1};
    float fSpeed = 0, fSpeed2 = 0;
    static int nPoints = 0, nPoints2 = 0;
    int nChoice, nChoice2;
    static int nWin;
    static public int nInd = 0;
    Sprite spTemp, spTemp2;
    PelletMaker pMaker;

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
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50);    //Right Wall
        nFrame = 0;
        nPos = 0;
        nPos2 = 0;
        nPoints = 0;
        nPoints2 = 0;
        nInd = 1;
        //Animation Stuff for Case 1
        araniMouse = new Animation[4];
        araniMouse2 = new Animation[4];
        fW = txSheet.getWidth() / 4;
        fH = txSheet.getHeight() / 4;
        fW2 = txSheet2.getWidth() /4;
        fH2 = txSheet2.getHeight() /4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            Sprite[] arSprMouse2 = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                fSy2 = i * fH2;
                fSx2 = j * fW2;
                sprMouse = new Sprite(txSheet, fSx, fSy, fW, fH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
                sprMouse2 = new Sprite(txSheet2, fSx2, fSy2, fW2, fH2);
                sprMouse2.setFlip(false, true);
                arSprMouse2[j] = new Sprite(sprMouse2);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);
            araniMouse2[i] = new Animation(0.8f, arSprMouse2);
        }
        sprMouse.setPosition(200, 200);
        sprMouse2.setPosition(300, 200);
        Gdx.input.setInputProcessor(this);

        pMaker = new PelletMaker(25, 25,"Hamster Pellet.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprMouse.getX();
        float fSy = sprMouse.getY();
        float fSx2 = sprMouse2.getX();
        float fSy2 = sprMouse2.getY();
        //Animation Stuff

        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniMouse[nPos].getKeyFrame(nFrame, false);
        trTemp2 = araniMouse2[nPos2].getKeyFrame(nFrame, false);
        //Pellet for hamster 1
        if (spTemp == null) {
            spTemp = new Sprite(trTemp);
            spTemp.setFlip(false,true);
        } else {
            spTemp.setTexture(trTemp.getTexture());
        }
        //Pellet for hamster 2
        if (spTemp2 == null) {
            spTemp2 = new Sprite(trTemp2);
            spTemp2.setFlip(false,true);
        } else {
            spTemp2.setTexture(trTemp2.getTexture());
        }

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

        //Direction instruction for mouse 1
        if (DY[nDir] == 0) {
            sprMouse.setY(sprMouse.getY() + DY[nDir]);
            nY = nY += DY[nDir];
        } else if (DY[nDir] < 0) {
            sprMouse.setY(sprMouse.getY() + DY[nDir] - fSpeed);
            nY = nY += DY[nDir] - fSpeed;
        } else {
            sprMouse.setY(sprMouse.getY() + DY[nDir] + fSpeed);
            nY = nY += DY[nDir] + fSpeed;

        }
        if (DX[nDir] == 0) {
            sprMouse.setX(sprMouse.getX() + DX[nDir]);
            nX = nX += DX[nDir];
        } else if (DX[nDir] < 0) {
            sprMouse.setX(sprMouse.getX() + DX[nDir] - fSpeed);
            nX = nX += DX[nDir] - fSpeed;
        } else {
            sprMouse.setX(sprMouse.getX() + DX[nDir] + fSpeed);
            nX = nX += DX[nDir] + fSpeed;

        }
        //Direction instruction for mouse 2
        if (DY2[nDir2] == 0) {
            sprMouse2.setY(sprMouse2.getY() + DY2[nDir2]);
            nY2 = nY2 += DY2[nDir2];
        } else if (DY2[nDir2] < 0) {
            sprMouse2.setY(sprMouse2.getY() + DY2[nDir2] - fSpeed2);
            nY2 = nY2 += DY2[nDir2] - fSpeed2;
        } else {
            sprMouse2.setY(sprMouse2.getY() + DY2[nDir2] + fSpeed2);
            nY2 = nY2 += DY2[nDir2] + fSpeed2;

        }
        if (DX2[nDir2] == 0) {
            sprMouse2.setX(sprMouse2.getX() + DX2[nDir2]);
            nX2 = nX2 += DX2[nDir2];
        } else if (DX2[nDir2] < 0) {
            sprMouse2.setX(sprMouse2.getX() + DX2[nDir2] - fSpeed2);
            nX2 = nX2 += DX2[nDir2] - fSpeed2;
        } else {
            sprMouse2.setX(sprMouse2.getX() + DX2[nDir2] + fSpeed2);
            nX2 = nX2 += DX2[nDir2] + fSpeed2;

        }

        nFrame++;
        if (nDir == 0) {
            nPos = 2;
        } else if (nDir == 1) {
            nPos = 3;
        } else if (nDir == 2) {
            nPos = 1;
        } else if (nDir == 3) {
            nPos = 0;
        }
        if (nDir2 == 0) {
            nPos2 = 2;
        } else if (nDir2 == 1) {
            nPos2 = 3;
        } else if (nDir2 == 2) {
            nPos2 = 1;
        } else if (nDir2 == 3) {
            nPos2 = 0;
        }

        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(sprMouse, arWall[i])) {
                sprMouse.setPosition(fSx, fSy);
            }
            if (isHitS(sprMouse2, arWall[i])) {
                sprMouse2.setPosition(fSx2, fSy2);
            }
        }
        //Hit detection between mice
        Rectangle rMouse1 = new Rectangle(sprMouse.getX(), sprMouse.getY(), sprMouse.getWidth(), sprMouse.getHeight());
        Rectangle rMouse2 = new Rectangle(sprMouse2.getX(), sprMouse2.getY(), sprMouse2.getWidth(), sprMouse2.getHeight());
        if (Intersector.overlaps(rMouse1, rMouse2)) {
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
            nSizeY = 50;
            nSizeX2 = 50;
            nSizeY2 = 50;
            System.out.println("Hit");
            gamMenu.updateState(6);
            nPoints = 0;
            nPoints2 = 0;
        }
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (isHitS(p, spTemp)) {
                System.out.println("HERE");
                System.out.println("He hecking ate it");
                fSpeed += 0.5f;
                System.out.println(fSpeed);
                nTrig = 1;
                nPoints += 1;
                System.out.println("Points for first: " + nPoints);
                if (nSizeX < 100 && nSizeY < 100) {
                    nSizeX += 3;
                    nSizeY += 3;
                    System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }if (isHitS(p, spTemp2)) {
                System.out.println("HERE");
                System.out.println("He hecking ate it");
                fSpeed2 += 0.5f;
                System.out.println(fSpeed2);
                nPoints2 += 1;
                System.out.println("Points for first: " + nPoints2);
                if (nSizeX2 < 100 && nSizeY2 < 100) {
                    nSizeX2 += 3;
                    nSizeY2 += 3;
                    System.out.println(nSizeX2 + "   " + nSizeY2);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }
        }

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
        sprMap.draw(batch);
        pMaker.draw(batch);
        spTemp.setPosition(fSx, fSy);
        spTemp2.setPosition(fSx2, fSy2);
        batch.draw(trTemp, fSx, fSy, nSizeX, nSizeY);
        batch.draw(trTemp2, fSx2, fSy2, nSizeX2, nSizeY2);
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
                nPoints = 0;
                nPoints2 = 0;
                nWin = 0;
                fSpeed = 0;
                fSpeed2 = 0;
                nSizeX = 50;
                nSizeY = 50;
                nSizeX2 = 50;
                nSizeY2 = 50;
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
