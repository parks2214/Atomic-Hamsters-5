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
import gdx.menu.GamMenu;
import gdx.menu.images.*;

public class ScrGame4 implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    Texture txSheet1, txMap, txSheet2, txBar, txWall, txObstacle, txCornerHamster1, txCornerHamster2;
    Sprite sprHamster1, sprHamster2, sprMap, sprCornerHamster1, sprCornerHamster2;
    int nFrame, nPos, nPos2;
    Animation araniHamster1[], araniHamster2[];
    int fSx1, fSy1, fSx2, fSy2, fW1, fH1, fW2, fH2, nDir1 = 0, nDir2 = 2, nSizeX1 = 50, nSizeY1 = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    Wall[] arObstacle = new Wall[2];
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};
    float fSpeed1 = 0, fSpeed2 = 0;
    static int n4Points = 0, n4Points2 = 0;
    static int nWin4;
    int nChoice1, nChoice2;
    int nTimer=0,nBadTimer=0;
    PelletMaker pMaker1, pMaker2;
    float fSizeBar1 = 1, fSizeBar2 = 1, fSpeedBar1 = 1, fSpeedBar2 = 1;
    BitmapFont font, font2;
    AniSprite aniSprite1, aniSprite2;
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
        nChoice1 = ScrAnimalChoice.nChoice;
        nChoice2 = ScrAnimalChoice2.nChoice2;
        if (nChoice1 == 1) {
            txSheet1 = new Texture("sprmouse.png");
            txCornerHamster1 = new Texture("btnMouse.png");
        } else if (nChoice1 == 2) {
            txSheet1 = new Texture("sprmouse2.png");
            txCornerHamster1 = new Texture("btnMouse2.png");
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture("sprmouse.png");
            txCornerHamster2 = new Texture("btnMouse.png");
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
            txCornerHamster2 = new Texture("btnMouse2.png");
        }
        aniSprite1 = new AniSprite(100, 100, txSheet1);
        aniSprite2 = new AniSprite(490, 330, txSheet2);
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
        sprCornerHamster1 = new Sprite(txCornerHamster1);
        sprCornerHamster2 = new Sprite(txCornerHamster2);
        sprCornerHamster1.setFlip(false, true);
        sprCornerHamster2.setFlip(false, true);
        sprCornerHamster1.setSize(75, 100);
        sprCornerHamster2.setSize(75, 100);
        //Animation Stuff
        nFrame = 0;
        nPos = 0; //dennis was here
        nPos2 = 0;
        araniHamster1 = new Animation[4];
        araniHamster2 = new Animation[4];
        fW1 = txSheet1.getWidth() / 4;
        fH1 = txSheet1.getHeight() / 4;
        fW2 = txSheet2.getWidth() /4;
        fH2 = txSheet2.getHeight() /4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            Sprite[] arSprMouse2 = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx1 = j * fW1;
                fSy1 = i * fH1;
                fSy2 = i * fH2;
                fSx2 = j * fW2;
                sprHamster1 = new Sprite(txSheet1, fSx1, fSy1, fW1, fH1);
                sprHamster1.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprHamster1);
                sprHamster2 = new Sprite(txSheet2, fSx2, fSy2, fW2, fH2);
                sprHamster2.setFlip(false, true);
                arSprMouse2[j] = new Sprite(sprHamster2);
            }
            araniHamster1[i] = new Animation(0.8f, arSprMouse);
            araniHamster2[i] = new Animation(0.8f, arSprMouse2);

        }
        sprHamster1.setPosition(50, 50);
        sprHamster2.setPosition(Gdx.graphics.getWidth()-100,Gdx.graphics.getHeight()-100);
        Gdx.input.setInputProcessor(this);

        pMaker1 = new PelletMaker(50, 50, "Apple.png");
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
        //Input for aniSprite 1
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nDir1 = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nDir1 = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nDir1 = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nDir1 = 3;
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
        aniSprite2.move(nDir2, nSizeX2, nSizeY2);
        aniSprite1.move(nDir1, nSizeX1, nSizeY1);
        aniSprite1.animation(nFrame);
        aniSprite2.animation(nFrame);
        for (int i = 0; i < arWall.length; i++) {
            if (aniSprite2.isHitS(arWall[i], nSizeY2)) {
                aniSprite2.outOfBounds();
            }
            if (aniSprite1.isHitS(arWall[i], nSizeY1)) {
                aniSprite1.outOfBounds();
            }
        }
        for (int i = 0; i < arObstacle.length; i++) {
            if (aniSprite2.isHitS(arObstacle[i], nSizeY2)) {
                aniSprite2.outOfBounds();
            }
            if (aniSprite1.isHitS(arObstacle[i], nSizeY1)) {
                aniSprite1.outOfBounds();
            }
        }
        //pellet stuff
        for (int i = pMaker1.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker1.alPellets.get(i);
            if (aniSprite1.isHitS(p, nSizeY1)) {
                fSpeed1 += 0.5f;
                nTimer=240;
                // System.out.println(fSpeed);
                n4Points += 1;
                fSpeedBar1 += 0.1;
                fSizeBar1 += 0.1;
                // System.out.println("Points for first: " + n2Points);
                if (nSizeX1 < 100 && nSizeY1 < 100) {
                    nSizeX1 += 3;
                    nSizeY1 += 3;
                    // System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker1.removePellet(p);
            }
            if (aniSprite2.isHitS(p, nSizeY2)) {
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
                pMaker1.removePellet(p);
            }

            if (nTimer>=300) {
                System.out.println("The clock struck 5 seconds");
                pMaker1.removePellet(p);
                nTimer=0;

            }
        }
        //poison stuff
        for (int i = pMaker2.alPellets.size() - 1; i >= 0; i--) {
            Pellet p2 = pMaker2.alPellets.get(i);
            if (aniSprite1.isHitS(p2, nSizeY1)) {
                nBadTimer=60;
                nSizeX1 -= 3;
                nSizeY1-= 3;
                //System.out.println(fSpeed);
                fSizeBar1 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
            if (aniSprite2.isHitS(p2, nSizeY2)) {
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
        if (Intersector.overlaps(aniSprite1.getThisRect(nSizeY1), aniSprite2.getThisRect(nSizeY2))) {
            if ((nDir1 == 0 && nDir2 == 2) || (nDir1 == 2 && nDir2 == 2)) {
                if (nSizeX1 > nSizeX2) {
                    nWin4 = 1;
                } else if (nSizeX2 > nSizeX1) {
                    nWin4 = 2;
                } else {
                    nWin4 = 0;
                }
            } else {
                if (fSpeed1 > fSpeed2) {
                    nWin4 = 1;
                } else if (fSpeed2 > fSpeed1) {
                    nWin4 = 2;
                } else {
                    nWin4 = 0;
                }
            }
            System.out.println(nWin4);
            fSpeed1 = 0;
            fSpeed2 = 0;
            nSizeX1 = 50;
            nSizeY1= 50;
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
            nDir1 = 0;
            nDir2 = 2;
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
        sprCornerHamster1.setPosition(-10,Gdx.graphics.getHeight()-85);
        sprCornerHamster2.setPosition(Gdx.graphics.getWidth()-60, Gdx.graphics.getHeight()-85);
        sprCornerHamster1.draw(batch);
        sprCornerHamster2.draw(batch);
        //Pellet Stuff
        pMaker1.draw(batch);
        pMaker2.draw(batch);
        //Animation Stuff
        aniSprite1.spTemp.draw(batch);
        aniSprite2.spTemp.draw(batch);
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
        txSheet1.dispose();
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
                fSpeed1= 0;
                fSpeed2 = 0;
                nSizeX1= 50;
                nSizeY1 = 50;
                nSizeX2 = 50;
                nSizeY2 = 50;
                fSizeBar1 = 1;
                fSpeedBar1 = 1;
                fSizeBar2 = 1;
                fSpeedBar2 = 1;
                nDir1 = 0;
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