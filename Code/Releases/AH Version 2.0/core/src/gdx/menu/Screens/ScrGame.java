package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import gdx.menu.images.Wall;

public class ScrGame implements Screen, InputProcessor {

    SpriteBatch batch;
    GamMenu gamMenu;
    OrthographicCamera oc;
    Button btnMenu, btnQuit;
    TextureRegion trTemp;
    Texture txSheet, txNamGame, txMap, txHamP, txTextbox1, txTextbox2, txHouse;// txTextbox3, txHouse;
    Sprite sprNamGame, sprMouse, sprMap, sprHamP, sprHouse;   //sprAni is a ghost, a sprite used for hit detection, maybe a bit redundant
    Sprite arsprTextbox[] = new Sprite[2];
    int nFrame, nPos, nX = 100, nY = 100, nTrig = 0;
    Animation araniMouse[];
    int fSx, fSy, fW, fH, nDir = 0;
    Wall[] arWall = new Wall[4];
    int DX[] = {1, 0, -1, 0};
    int DY[] = {0, -1, 0, 1};
    float fSpeed = 0;

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
        txSheet = new Texture("sprmouse.png");
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
        //txHouse = new Texture("House.png");
        sprMap = new Sprite(txMap);
        sprMap.setScale(1, 1.5f);
        sprMap.setPosition(Gdx.graphics.getWidth() / 2 - sprMap.getWidth() / 2, Gdx.graphics.getHeight() / 2 - sprMap.getHeight() / 2);
        sprMap.setFlip(false, true);
        arWall[0] = new Wall(Gdx.graphics.getWidth(), 50, 0, 0);   //Top Wall
        arWall[1] = new Wall(Gdx.graphics.getWidth(), 50, 0, Gdx.graphics.getHeight() - 50);    //Bottom Wall
        arWall[2] = new Wall(50, Gdx.graphics.getHeight() - 20, 0, 50);   //Left Wall
        arWall[3] = new Wall(50, Gdx.graphics.getHeight() - 20, Gdx.graphics.getWidth() - 50, 50);    //Right Wall
        txHamP = new Texture("HamP.png");
        sprHamP = new Sprite(txHamP);
        sprHamP.setPosition(400, 270);
        sprHamP.setSize(50, 50);
        sprHamP.setFlip(false, true);
        //Animation Stuff
        nFrame = 0;
        nPos = 0;
        araniMouse = new Animation[4];
        fW = txSheet.getWidth() / 4;
        fH = txSheet.getHeight() / 4;
        for (int i = 0; i < 4; i++) {
            Sprite[] arSprMouse = new Sprite[4];
            for (int j = 0; j < 4; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprMouse = new Sprite(txSheet, fSx, fSy, fW, fH);
                sprMouse.setFlip(false, true);
                arSprMouse[j] = new Sprite(sprMouse);
            }
            araniMouse[i] = new Animation(0.8f, arSprMouse);

        }
        sprMouse.setPosition(200, 200);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); //White background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float fSx = sprMouse.getX();
        float fSy = sprMouse.getY();
        //Animation Stuff

        if (nFrame > 7) {
            nFrame = 0;
        }
        trTemp = araniMouse[nPos].getKeyFrame(nFrame, false);
        if (isHitS(sprMouse, sprHamP) && nTrig == 0) {
            System.out.println("He hecking ate it");
            fSpeed += 0.5f;
            System.out.println(fSpeed);
            nTrig = 1;
        } else if (isHitS(sprMouse, sprHamP) && nTrig == 3) {
            nTrig = 3;
        } else if (!isHitS(sprMouse, sprHamP)) {
            nTrig = 0;
        }
        if (nTrig == 1 && Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            nTrig = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nDir = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nDir = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nDir = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nDir = 3;
        }

        //Direction instruction      
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
        
        for (int i = 0; i < arWall.length; i++) {
            if (isHitS(sprMouse, arWall[i])) {
                sprMouse.setPosition(fSx, fSy);
            }
        }
        batch.begin();
        batch.setProjectionMatrix(oc.combined);
        for (int i = 0; i < arWall.length; i++) {
            arWall[i].draw(batch);
        }
        sprMap.draw(batch);
        sprHamP.draw(batch);
        batch.draw(trTemp, fSx, fSy);
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
            //System.out.println(screenX +" " + screenY);
            if (isHitB(screenX, screenY, btnMenu)) {
                gamMenu.updateState(0);
                System.out.println("Hit Menu");
                fSpeed = 1;
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

