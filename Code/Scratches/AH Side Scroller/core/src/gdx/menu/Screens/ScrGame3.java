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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

public class ScrGame3 implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;

    Texture txSheet, txMap, txTextbox1, txTextbox2, txSheet2, txBar, txWall, txObstacle, txCornerMouse, txCornerMouse2;
    Sprite sprMap, sprCornerMouse, sprCornerMouse2;
    Sprite arsprTextbox[] = new Sprite[2];
    int nFrame, nPos, nPos2;
    int nDir = 0, nDir2 = 2, nSizeX = 50, nSizeY = 50, nSizeX2 = 50, nSizeY2 = 50;
    Wall[] arWall = new Wall[4];
    Wall[] arObstacle = new Wall[2];
    float fSpeed = 0, fSpeed2 = 0;
    static int n3Points = 0, n3Points2 = 0;
    static int nWin3;
    int nChoice, nChoice2;
    PelletMaker pMaker, pMaker2;
    float fSizeBar1 = 1, fSizeBar2 = 1, fSpeedBar1 = 1, fSpeedBar2 = 1;
    BitmapFont font, font2;
    AniSprite aniSprite, aniSprite2;
    //640, 480

    public ScrGame3(GamMenu _gamMenu) {
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
        } else if (nChoice == 2) {
            txSheet = new Texture("sprmouse2.png");
            txCornerMouse = new Texture("btnMouse2.png");
        }
        if (nChoice2 == 1) {
            txSheet2 = new Texture ("sprmouse.png");
            txCornerMouse2 = new Texture("btnMouse.png");
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
            txCornerMouse2 = new Texture("btnMouse2.png");
        }
        aniSprite = new AniSprite(100, 100, txSheet);
        aniSprite2 = new AniSprite(490, 330, txSheet2);
        //Background
        txMap = new Texture("mars.jpg");
        sprMap = new Sprite(txMap);
        sprMap.setScale(0.7f, 0.7f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        //Walls
        txWall = new Texture ("Wall2.jpg");
        txObstacle = new Texture ("Rocksan.png");
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0, txWall);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50, txWall);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50, txWall);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50, txWall);    //Right Wall
        arObstacle[0] = new Wall(65, 50, (51 + (int)(Math.random() * ((524 - 51) + 1))), (51 + (int)(Math.random() * ((379 - 51) + 1))), txObstacle);   //Horizontal obstacle
        arObstacle[1] = new Wall(50, 65, (51 + (int)(Math.random() * ((539 - 51) + 1))), (51 + (int)(Math.random() * ((364 - 51) + 1))), txObstacle);    //Vertical obstacle
        //Other stuff
        n3Points = 0;
        n3Points2 = 0;
        ScrGame.nInd = 3;
        //Corner Image stuff
        sprCornerMouse = new Sprite(txCornerMouse);
        sprCornerMouse2 = new Sprite(txCornerMouse2);
        sprCornerMouse.setFlip(false, true);
        sprCornerMouse2.setFlip(false, true);
        sprCornerMouse.setSize(75, 100);
        sprCornerMouse2.setSize(75, 100);
        //Animation Stuff
        nFrame = 0;
        nPos = 0;
        nPos2 = 0;

        Gdx.input.setInputProcessor(this);

        pMaker = new PelletMaker(50, 50, "BluBery.png");
        pMaker2 = new PelletMaker(30, 30, "Poison.png");
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
        for (int i = 0; i < arObstacle.length; i++) {
            if (aniSprite2.isHitS(arObstacle[i], nSizeY2)) {
                aniSprite2.outOfBounds();
            }
            if (aniSprite.isHitS(arObstacle[i], nSizeY)) {
                aniSprite.outOfBounds();
            }
        }
        //pellet stuff
        for (int i = pMaker.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker.alPellets.get(i);
            if (aniSprite.isHitS(p, nSizeY)) {
                fSpeed += 0.5f;
                System.out.println(fSpeed);
                n3Points += 1;
                fSpeedBar1 += 0.1;
                fSizeBar1 += 0.1;
                System.out.println("Points for first: " + n3Points);
                if (nSizeX < 100 && nSizeY < 100) {
                    nSizeX += 3;
                    nSizeY += 3;
                    System.out.println(nSizeX + "   " + nSizeY);
                }
                // mouse catche pellet
                pMaker.removePellet(p);
            }if (aniSprite2.isHitS(p, nSizeY2)) {
                fSpeed2 += 0.5f;
                System.out.println(fSpeed2);
                n3Points2 += 1;
                fSpeedBar2 += 0.1;
                fSizeBar2 += 0.1;
                System.out.println("Points for first: " + n3Points2);
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
            if (aniSprite.isHitS(p2, nSizeY)) {
                fSpeed -= 0.5f;
                System.out.println(fSpeed);
                fSpeedBar1 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }if (aniSprite2.isHitS(p2, nSizeY2)) {
                fSpeed2 -= 0.5f;
                System.out.println(fSpeed2);
                fSpeedBar2 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(aniSprite.getThisRect(nSizeY), aniSprite2.getThisRect(nSizeY2))) {
            if ((nDir == 0 && nDir2 == 2) || (nDir == 2 && nDir2 == 2)) {
                if (nSizeX > nSizeX2) {
                    nWin3 = 1;
                } else if (nSizeX2 > nSizeX) {
                    nWin3 = 2;
                } else {
                    nWin3 = 0;
                }
            } else {
                if (fSpeed > fSpeed2) {
                    nWin3 = 1;
                } else if (fSpeed2 > fSpeed) {
                    nWin3 = 2;
                } else {
                    nWin3 = 0;
                }
            }
            System.out.println(nWin3);
            fSpeed = 0;
            fSpeed2 = 0;
            nSizeX = 50;
            nSizeY = 50;
            nSizeX2 = 50;
            nSizeY2 = 50;
            aniSprite.reset();
            aniSprite2.reset();
            System.out.println("Hit");
            gamMenu.updateState(6);
            n3Points = 0;
            n3Points2 = 0;
            nDir = 0;
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
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 25, 50 * fSizeBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar2, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 25, 50 * fSizeBar2, 20);
        font.draw(batch, "1", Gdx.graphics.getWidth() - 625, Gdx.graphics.getHeight() - 45);
        font.draw(batch, "2", Gdx.graphics.getWidth() - 35, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 20);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 20);
        //Corner Mouse stuff
        sprCornerMouse.setPosition(-10,Gdx.graphics.getHeight()-85);
        sprCornerMouse2.setPosition(Gdx.graphics.getWidth()-60, Gdx.graphics.getHeight()-85);
        sprCornerMouse.draw(batch);
        sprCornerMouse2.draw(batch);
        pMaker.draw(batch);
        pMaker2.draw(batch);
        aniSprite.spTemp.draw(batch);
        aniSprite2.spTemp.draw(batch);
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
                n3Points = 0;
                n3Points2 = 0;
                nWin3 = 0;
                fSpeed = 0;
                fSpeed2 = 0;
                nSizeX = 50;
                nSizeY = 50;
                nSizeX2 = 50;
                nSizeY2 = 50;
                nDir = 0;
                nDir2 = 2;
                aniSprite.reset();
                aniSprite2.reset();
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