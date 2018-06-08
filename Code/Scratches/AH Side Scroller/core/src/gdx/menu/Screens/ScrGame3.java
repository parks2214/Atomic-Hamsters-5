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

    Texture txSheet1, txMap, txSheet2, txBar, txWall, txObstacle, txCornerHamster1, txCornerHamster2;
    Sprite sprMap, sprCornerHamster1, sprCornerHamster2;
    int nFrame, nPos1, nPos2;
    int nDir1 = 0, nDir2 = 2;
    Wall[] arWall = new Wall[4];
    Wall[] arObstacle = new Wall[2];
    float fSpeed1 = 0, fSpeed2 = 0;
    static int n3Points = 0, n3Points2 = 0;
    static int nWin3;
    int nChoice1, nChoice2;
    PelletMaker pMaker1, pMaker2;
    float fSizeBar1 = 1, fSizeBar2 = 1, fSpeedBar1 = 1, fSpeedBar2 = 1;
    BitmapFont font1, font2;
    AniSprite aniMouse1, aniMouse2;
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
        font1 = new BitmapFont(true);//this flips the font (https://stackoverflow.com/questions/8508749/draw-a-bitmapfont-rotated-in-libgdx)
        font1.setColor(Color.WHITE);
        font1.getData().setScale(3f);
        font1.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
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
            txSheet2 = new Texture ("sprmouse.png");
            txCornerHamster2 = new Texture("btnMouse.png");
        } else if (nChoice2 == 2) {
            txSheet2 = new Texture("sprmouse2.png");
            txCornerHamster2 = new Texture("btnMouse2.png");
        }
        aniMouse1 = new AniSprite(100, 100, txSheet1);
        aniMouse2 = new AniSprite(490, 330, txSheet2);
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
        sprCornerHamster1 = new Sprite(txCornerHamster1);
        sprCornerHamster2 = new Sprite(txCornerHamster2);
        sprCornerHamster1.setFlip(false, true);
        sprCornerHamster2.setFlip(false, true);
        sprCornerHamster1.setSize(75, 100);
        sprCornerHamster2.setSize(75, 100);
        //Animation Stuff
        nFrame = 0;
        nPos1 = 0;
        nPos2 = 0;

        Gdx.input.setInputProcessor(this);

        pMaker1 = new PelletMaker(50, 50, "BluBery.png");
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
        aniMouse2.move(nDir2);
        aniMouse1.move(nDir1);
        aniMouse1.animation(nFrame);
        aniMouse2.animation(nFrame);
        for (int i = 0; i < arWall.length; i++) {
            if (aniMouse2.isHitS(arWall[i])) {
                aniMouse2.outOfBounds();
            }
            if (aniMouse1.isHitS(arWall[i])) {
                aniMouse1.outOfBounds();
            }
        }
        for (int i = 0; i < arObstacle.length; i++) {
            if (aniMouse2.isHitS(arObstacle[i])) {
                aniMouse2.outOfBounds();
            }
            if (aniMouse1.isHitS(arObstacle[i])) {
                aniMouse1.outOfBounds();
            }
        }
        //pellet stuff
        for (int i = pMaker1.alPellets.size() - 1; i >= 0; i--) {
            Pellet p = pMaker1.alPellets.get(i);
            if (aniMouse1.isHitS(p)) {
                aniMouse1.increaseSpeed();
                System.out.println(fSpeed1);
                n3Points += 1;
                fSpeedBar1 += 0.1;
                fSizeBar1 += 0.1;
                System.out.println("Points for first: " + n3Points);
                aniMouse1.increaseSize();
                // mouse catche pellet
                pMaker1.removePellet(p);
            }if (aniMouse2.isHitS(p)) {
                aniMouse2.increaseSpeed();
                System.out.println(fSpeed2);
                n3Points2 += 1;
                fSpeedBar2 += 0.1;
                fSizeBar2 += 0.1;
                System.out.println("Points for first: " + n3Points2);
                aniMouse2.increaseSize();
                // mouse catche pellet
                pMaker1.removePellet(p);
            }
        }
        //poison stuff
        for (int i = pMaker2.alPellets.size() - 1; i >= 0; i--) {
            Pellet p2 = pMaker2.alPellets.get(i);
            if (aniMouse1.isHitS(p2)) {
                aniMouse1.decreaseSpeed();
                System.out.println(fSpeed1);
                fSpeedBar1 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }if (aniMouse2.isHitS(p2)) {
                aniMouse2.decreaseSpeed();
                System.out.println(fSpeed2);
                fSpeedBar2 -= 0.1;
                // mouse catche pellet
                pMaker2.removePellet(p2);
            }
        }
        //Hit detection between mice
        if (Intersector.overlaps(aniMouse1.getThisRect(), aniMouse2.getThisRect())) {
            if ((nDir1 == 0 && nDir2 == 2) || (nDir1 == 2 && nDir2 == 2)) {
                if (aniMouse1.getScaleX() > aniMouse2.getScaleX()) {
                    nWin3 = 1;
                } else if (aniMouse2.getScaleX() > aniMouse1.getScaleX()) {
                    nWin3 = 2;
                } else {
                    nWin3 = 0;
                }
            } else {
                if (aniMouse1.getSpeed() > aniMouse2.getSpeed()) {
                    nWin3 = 1;
                } else if (aniMouse2.getSpeed() > aniMouse1.getSpeed()) {
                    nWin3 = 2;
                } else {
                    nWin3 = 0;
                }
            }
            System.out.println(nWin3);
            aniMouse1.reset();
            aniMouse2.reset();
            System.out.println("Hit");
            gamMenu.updateState(6);
            n3Points = 0;
            n3Points2 = 0;
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
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 25, 50 * fSizeBar1, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 50, 50 * fSpeedBar2, 20);
        batch.draw(txBar, Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 25, 50 * fSizeBar2, 20);
        font1.draw(batch, "1", Gdx.graphics.getWidth() - 625, Gdx.graphics.getHeight() - 45);
        font1.draw(batch, "2", Gdx.graphics.getWidth() - 35, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 590, Gdx.graphics.getHeight() - 20);
        font2.draw(batch, "Size", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 45);
        font2.draw(batch, "Speed", Gdx.graphics.getWidth() - 220, Gdx.graphics.getHeight() - 20);
        //Corner Mouse stuff
        sprCornerHamster1.setPosition(-10,Gdx.graphics.getHeight()-85);
        sprCornerHamster2.setPosition(Gdx.graphics.getWidth()-60, Gdx.graphics.getHeight()-85);
        sprCornerHamster1.draw(batch);
        sprCornerHamster2.draw(batch);
        pMaker1.draw(batch);
        pMaker2.draw(batch);
        aniMouse1.spTemp.draw(batch);
        aniMouse2.spTemp.draw(batch);
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
                n3Points = 0;
                n3Points2 = 0;
                nWin3 = 0;
                nDir1 = 0;
                nDir2 = 2;
                aniMouse1.reset();
                aniMouse2.reset();
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