package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import gdx.menu.images.Pellet;
import gdx.menu.images.PelletMaker;
import gdx.menu.images.Wall;

public class ScrGame4 implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    TextureRegion trTemp, trTemp2;
    Texture txSheet, txMap, txTextbox1, txTextbox2, txSheet2, txBar, txWall, txObstacle;
    Sprite sprMouse, sprMouse2, sprMap, spTemp, spTemp2;
    Sprite arsprTextbox[] = new Sprite[2];
    int nFrame, nPos, nPos2, nX = 100, nY = 100, nX2 = 100, nY2 = 100;
    Animation araniMouse[], araniMouse2[];
    int fSx, fSy, fSx2, fSy2, fW, fH, fW2, fH2, nDir = 0, nDir2 = 0, nSizeX = 50, nSizeY = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    Wall[] arObstacle = new Wall[2];
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};
    float fSpeed = 0, fSpeed2 = 0;
    static int n4Points = 0, n4Points2 = 0;
    static int nWin4;
    Rectangle rectMouse, rectMouseNew, rectMouse2, rectMouseNew2;
    int nChoice, nChoice2;
    PelletMaker pMaker, pMaker2;
    float fSizeBar1 = 1, fSizeBar2 = 1, fSpeedBar1 = 1, fSpeedBar2 = 1;
    BitmapFont font, font2;
    //640, 480

    public ScrGame4(GamMenu _gamMenu) {
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
        txBar = new Texture ("The bar.png");
        font = new BitmapFont(true);//this flips the font (https://stackoverflow.com/questions/8508749/draw-a-bitmapfont-rotated-in-libgdx)
        font.setColor(Color.WHITE);
        font.getData().setScale(3f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font2 = new BitmapFont(true);//this flips the font (https://stackoverflow.com/questions/8508749/draw-a-bitmapfont-rotated-in-libgdx)
        font2.setColor(Color.BLACK);
        font2.getData().setScale(1.1f);
        font2.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
        txMap = new Texture("neptune.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(0.7f, 0.7f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        txWall = new Texture ("Wall2.jpg");
        txObstacle = new Texture ("Rocksan.png");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0, txWall);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50, txWall);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50, txWall);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50, txWall);    //Right Wall
        arObstacle[0] = new Wall(65, 50, (51 + (int)(Math.random() * ((524 - 51) + 1))), (51 + (int)(Math.random() * ((379 - 51) + 1))), txObstacle);   //Horizontal obstacle
        arObstacle[1] = new Wall(50, 65, (51 + (int)(Math.random() * ((539 - 51) + 1))), (51 + (int)(Math.random() * ((364 - 51) + 1))), txObstacle);    //Vertical obstacle
        n4Points = 0;
        n4Points2 = 0;
        ScrGame.nInd = 3;
        //Animation Stuff
        nFrame = 0;
        nPos = 0;
        nPos2 = 0;
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

        pMaker = new PelletMaker(50, 50, "BluBery.png");
        pMaker2 = new PelletMaker(30, 30, "Poison.png");
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
        trTemp = (TextureRegion) araniMouse[nPos].getKeyFrame(nFrame, false);
        trTemp2 =  (TextureRegion) araniMouse2[nPos2].getKeyFrame(nFrame, false);
        ///Pellet for hamster 1
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
        } else if (DY[nDir] < 0){
            sprMouse.setY(sprMouse.getY() + DY[nDir] - fSpeed);
            nY = nY += DY[nDir] - fSpeed;
        }
        else {
            sprMouse.setY(sprMouse.getY() + DY[nDir] + fSpeed);
            nY = nY += DY[nDir] + fSpeed;

        }
        if (DX[nDir] == 0) {
            sprMouse.setX(sprMouse.getX() + DX[nDir]);
            nX = nX += DX[nDir];
        } else if (DX[nDir] < 0){
            sprMouse.setX(sprMouse.getX() + DX[nDir] - fSpeed);
            nX = nX += DX[nDir] - fSpeed;
        }
        else {
            sprMouse.setX(sprMouse.getX() + DX[nDir] + fSpeed);
            nX = nX += DX[nDir] + fSpeed;

        }
        //Direction instruction for mouse 2
        if (DY[nDir2] == 0) {
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2]);
            nY2 = nY2 += DY[nDir2];
        } else if (DY[nDir2] < 0){
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2] - fSpeed2);
            nY2 = nY2 += DY[nDir2] - fSpeed2;
        }
        else {
            sprMouse2.setY(sprMouse2.getY() + DY[nDir2] + fSpeed2);
            nY2 = nY2 += DY[nDir2] + fSpeed2;

        }
        if (DX[nDir2] == 0) {
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2]);
            nX2 = nX2 += DX[nDir2];
        } else if (DX[nDir2] < 0){
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2] - fSpeed2);
            nX2 = nX2 += DX[nDir2] - fSpeed2;
        }
        else {
            sprMouse2.setX(sprMouse2.getX() + DX[nDir2] + fSpeed2);
            nX2 = nX2 += DX[nDir2] + fSpeed2;

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
            }if (isHitS(sprMouse2, arWall[i])) {
                sprMouse2.setPosition(fSx2, fSy2);
            }
        }
        for (int i = 0; i < arObstacle.length; i++) {
            if (isHitS(sprMouse, arObstacle[i])) {
                sprMouse.setPosition(fSx, fSy);
            }if (isHitS(sprMouse2, arObstacle[i])) {
                sprMouse2.setPosition(fSx2, fSy2);
            }
        }
        //pellet stuff
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (isHitS(p, spTemp)) {
                fSpeed += 0.5f;
                System.out.println(fSpeed);
                n4Points += 1;
                fSpeedBar1 += 0.1;
                fSizeBar1 += 0.1;
                System.out.println("Points for first: " + n4Points);
                if (nSizeX < 100 && nSizeY < 100) {
                    nSizeX += 3;
                    nSizeY += 3;
                    System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }if (isHitS(p, spTemp2)) {
                fSpeed2 += 0.5f;
                System.out.println(fSpeed2);
                n4Points2 += 1;
                fSpeedBar2 += 0.1;
                fSizeBar2 += 0.1;
                System.out.println("Points for first: " + n4Points2);
                if (nSizeX2 < 100 && nSizeY2 < 100) {
                    nSizeX2 += 3;
                    nSizeY2 += 3;
                    System.out.println(nSizeX2 + "   " + nSizeY2);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }
        }
        //poison stuff
        for (int i = pMaker2.alPellets.size() - 1; i >= 0; i--) {
            Pellet p2 = pMaker2.alPellets.get(i);
            if (isHitS(p2, spTemp)) {
                fSpeed -= 0.5f;
                System.out.println(fSpeed);
                fSpeedBar1 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }if (isHitS(p2, spTemp2)) {
                fSpeed2 -= 0.5f;
                System.out.println(fSpeed2);
                fSpeedBar2 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
            for (int x = 0; x < arWall.length; x++) {
                if (isHitS(p2,arWall[x])) {
                    pMaker2.removePellet(p2);
                }
            }
        }
        //Hit detection between mice
        Rectangle rMouse1 = new Rectangle (sprMouse.getX(), sprMouse.getY(), sprMouse.getWidth(), sprMouse.getHeight());
        Rectangle rMouse2 = new Rectangle (sprMouse2.getX(), sprMouse2.getY(), sprMouse2.getWidth(), sprMouse2.getHeight());
        if (Intersector.overlaps(rMouse1, rMouse2)) {
            if ((nDir == 0 && nDir2 == 2) || (nDir == 2 && nDir2 == 2)) {
                if (nSizeX > nSizeX2) {
                    nWin4 = 1;
                } else if (nSizeX2 > nSizeX) {
                    nWin4 = 2;
                } else {
                    nWin4 = 0;
                }
            } else {
                if (fSpeed > fSpeed2) {
                    nWin4 = 1;
                } else if (fSpeed2 > fSpeed) {
                    nWin4 = 2;
                } else {
                    nWin4 = 0;
                }
            }
            System.out.println(nWin4);
            fSpeed = 0;
            fSpeed2 = 0;
            nSizeX = 50;
            nSizeY = 50;
            nSizeX2 = 50;
            nSizeY2 = 50;
            fSizeBar1 = 1;
            fSpeedBar1 = 1;
            fSizeBar2 = 1;
            fSpeedBar2 = 1;
            System.out.println("Hit");
            gamMenu.updateState(6);
            n4Points = 0;
            n4Points2 = 0;
        }

        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
        sprMap.draw(batch);
        for (int i = 0; i < arObstacle.length; i++) {
            arObstacle[i].draw(batch);
        }
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 25, 50 * fSizeBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar2, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 25, 50 * fSizeBar2, 20);
        font.draw(batch, "1", Gdx.graphics.getWidth() - 625, Gdx.graphics.getHeight() - 45);
        font.draw(batch, "2", Gdx.graphics.getWidth() - 35, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 20);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 20);
        pMaker.draw(batch);
        pMaker2.draw(batch);
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
                n4Points = 0;
                n4Points2 = 0;
                nWin4 = 0;
                fSpeed = 0;
                fSpeed2 = 0;
                nSizeX = 50;
                nSizeY = 50;
                nSizeX2 = 50;
                nSizeY2 = 50;
                fSizeBar1 = 1;
                fSpeedBar1 = 1;
                fSizeBar2 = 1;
                fSpeedBar2 = 1;
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
        rectMouse = spr1.getBoundingRectangle();
        rectMouseNew = new Rectangle(rectMouse.getX(), (rectMouse.getY() + rectMouse.getHeight() - 45), rectMouse.getWidth(), rectMouse.getHeight() - 10);
        rectMouse2 = spr2.getBoundingRectangle();
        rectMouseNew2 = new Rectangle(rectMouse2.getX(), rectMouse2.getY() - 8, rectMouse2.getWidth(), rectMouse2.getHeight() - 8);
        return rectMouseNew.overlaps(rectMouseNew2);
    }
}
