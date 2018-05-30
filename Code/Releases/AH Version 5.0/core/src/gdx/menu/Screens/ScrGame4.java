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
import gdx.menu.images.*;

public class ScrGame4 implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    Texture txSheet, txMap, txSheet2, txBar, txWall, txObstacle, txCornerMouse, txCornerMouse2;
    Sprite sprMouse, sprMouse2, sprMap, sprCornerMouse, sprCornerMouse2;
    int nFrame, nPos, nPos2;
    Animation araniMouse[], araniMouse2[];
    int fSx, fSy, fSx2, fSy2, fW, fH, fW2, fH2, nDir = 0, nDir2 = 2, nSizeX = 50, nSizeY = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    Wall[] arObstacle = new Wall[2];
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};
    float fSpeed = 0, fSpeed2 = 0;
    static int n4Points = 0, n4Points2 = 0;
    static int nWin4;
    int nChoice, nChoice2;
    int nTimer=0,nBadTimer=0;
    PelletMaker pMaker, pMaker2;
    float fSizeBar1 = 1, fSizeBar2 = 1, fSpeedBar1 = 1, fSpeedBar2 = 1;
    BitmapFont font, font2;
    AniSprite aniSprite, aniSprite2;
    Hamster hamster, hamster2;
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
            txCornerMouse = new Texture("btnMouse.png");
            aniSprite = new AniSprite(txSheet);
        } else if (nChoice == 2) {
            txSheet = new Texture("sprmouse2.png");
            txCornerMouse = new Texture("btnMouse2.png");
            aniSprite = new AniSprite(txSheet);
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture("sprmouse.png");
            txCornerMouse2 = new Texture("btnMouse.png");
            aniSprite2 = new AniSprite(txSheet2);
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
            txCornerMouse2 = new Texture("btnMouse2.png");
            aniSprite2 = new AniSprite(txSheet2);
        }
        aniSprite.animate();
        aniSprite2.animate();
        hamster = new Hamster(100, 100, txSheet);
        hamster2 = new Hamster(490, 330, txSheet2);
        //Background
        txMap = new Texture("neptune.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(0.7f, 0.7f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        //Obstacle
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
        ScrGame.nInd = 4;
        //Corner Image stuff
        sprCornerMouse = new Sprite(txCornerMouse);
        sprCornerMouse2 = new Sprite(txCornerMouse2);
        sprCornerMouse.setFlip(false, true);
        sprCornerMouse2.setFlip(false, true);
        sprCornerMouse.setSize(75, 100);
        sprCornerMouse2.setSize(75, 100);
        //Animation Stuff
        nFrame = 0;
        nPos = 0; //dennis was here
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
        sprMouse.setPosition(50, 50);
        sprMouse2.setPosition(Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight()-100);
        Gdx.input.setInputProcessor(this);

        pMaker = new PelletMaker(50, 50, "Apple.png");
        pMaker2 = new PelletMaker(30, 30, "Apple Worm.png");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Timer Stuff
        nTimer++;
        nBadTimer++;
        //Animation Stuff

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
        for (int i = 0; i < arObstacle.length; i++) {
            if (hamster2.isHitS(arObstacle[i], nSizeY2)) {
                hamster2.outOfBounds();
            }
            if (hamster.isHitS(arObstacle[i], nSizeY)) {
                hamster.outOfBounds();
            }
        }
        //pellet stuff
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (hamster.isHitS(p, nSizeY)) {
                fSpeed += 0.5f;
                nTimer=240;
                // System.out.println(fSpeed);
                n4Points += 1;
                fSpeedBar1 += 0.1;
                fSizeBar1 += 0.1;
                // System.out.println("Points for first: " + n2Points);
                if (nSizeX < 100 && nSizeY < 100) {
                    nSizeX += 3;
                    nSizeY += 3;
                    // System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }
            if (hamster2.isHitS(p, nSizeY2)) {
                fSpeed2 += 0.5f;
                nTimer=240;
                // System.out.println(fSpeed2);
                n4Points2 += 1;
                fSpeedBar2 += 0.1;
                fSizeBar2 += 0.1;
                //System.out.println("Points for first: " + n2Points2);
                if (nSizeX2 < 100 && nSizeY2 < 100) {
                    nSizeX2 += 3;
                    nSizeY2 += 3;
                    // System.out.println(nSizeX2 + "   " + nSizeY2);
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
        //poison stuff
        for (int i = pMaker2.alPellets.size() - 1; i >= 0; i--) {
            Pellet p2 = pMaker2.alPellets.get(i);
            if (hamster.isHitS(p2, nSizeY)) {
                nBadTimer=60;
                nSizeX -= 3;
                nSizeY -= 3;
                //System.out.println(fSpeed);
                fSizeBar1 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
            if (hamster2.isHitS(p2, nSizeY2)) {
                fSpeed2 -= 0.5f;
                nBadTimer=60;
                nSizeX2 -= 3;
                nSizeY2 -= 3;
                //System.out.println(fSpeed2);
                fSizeBar2 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
            if (nBadTimer>=120) {
                System.out.println("Poison stuff");
                pMaker2.removePellet(p2); //Doesn't remove poison, adds a strawberry instead
                nBadTimer=0;
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(hamster.getThisRect(nSizeY), hamster2.getThisRect(nSizeY2))) {
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
        //Size and Speed Bar
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 25, 50 * fSizeBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar2, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 25, 50 * fSizeBar2, 20);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 20);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 20);
        //Corner Mouse stuff
        sprCornerMouse.setPosition(-10,Gdx.graphics.getHeight()-85);
        sprCornerMouse2.setPosition(Gdx.graphics.getWidth()-60, Gdx.graphics.getHeight()-85);
        sprCornerMouse.draw(batch);
        sprCornerMouse2.draw(batch);
        //Pellet Stuff
        pMaker.draw(batch);
        pMaker2.draw(batch);
        //Animation Stuff
        hamster.spTemp.draw(batch);
        hamster2.spTemp.draw(batch);
        //Buttons
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
}