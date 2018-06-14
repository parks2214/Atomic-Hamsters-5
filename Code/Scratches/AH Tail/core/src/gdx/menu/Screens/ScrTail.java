package gdx.menu.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input;

import gdx.menu.GamMenu;
import gdx.menu.images.Button;
import java.util.Arrays;

public class ScrTail implements Screen, InputProcessor {

    Button btnMenu, btnQuit;
    GamMenu gamMenu;
    OrthographicCamera oc;
    SpriteBatch batch;
    Texture txBar;
    //int nX1,nY1,nX2,nY2,nY3,nX3,nX4=0,nY4,nDiff1=20,nDiff2=20;
    int nX1 = 150, nY1 = 150, nX2 = 130, nY2 = 150, nX3 = 110, nY3 = 150, nX4 = 90, nY4 = 150;
    int[] arnX = {nX1, nX2, nX3};
    int[] arnY = {nY1, nY2, nY3};
    int nHeadX = 150, nHeadY = 150;

    public ScrTail(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
    }

    @Override
    public void show() {
        oc = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        oc.update();
        batch = new SpriteBatch();
        txBar = new Texture ("The bar.png");
        nX1 = 130;
        nY1 = 150;
        nX2 = 110;
        nY2 = 150;
        nX3 = 90;
        nY3 = 150;
        /*nX1=580;
        nY1=Gdx.graphics.getHeight()/2;
        //nY2=nY1;
        //nY3=nY2;
        nY4=nY1;
        nX2=nX1-nDiff1;
        nX3=nX2;*/
        btnMenu = new Button(100, 50, Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, "Menu.jpg");
        btnQuit = new Button(100, 50, Gdx.graphics.getWidth() - 100, 0, "Quit.jpg");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);//Black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Arrays.sort(arnX);
        Arrays.sort(arnY);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nHeadX += -1;
            arnX[2] = arnX[0] - 40;
            arnY[2] = arnY[0];
            /*nX2 += -1;
            nX3 += -1;
            nX4 += -1;*/
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nHeadX += 1;
            arnX[0] = arnX[2] + 40;
            arnY[0] = arnY[2];
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            nHeadY += -1;
            arnY[2] = arnY[0] - 40;
            arnX[2] = arnX[0];
            /*nY2 += -1;
            nY3 += -1;
            nY4 += -1;*/
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            nHeadY += 1;
            arnY[0] = arnY[2] + 40;
            arnX[0] = arnX[2];
            /*nY2 += 1;
            nY3 += 1;
            nY4 += 1;*/
        }
        /*nX2=nX1-nDiff1;
        nX3=nX2-20;
        if (nX1>=600) {
            for (int i=0;i<=5;i++){
                nY1--;
            }
            nDiff1-=5;
           if(nDiff1<=0) {
               nDiff1=0;
               nY2=nY1-nDiff2;
               nY3=nY2-nDiff2;
           }
            nX1=595;
        } else if (nY1<=20) {
            for (int i=0;i<=60;i++){
                nX1--;
            }
        }
        //for (int i=0;i<=5;i++){
        //    nX1++;
        //}*/
        batch.begin();

        batch.draw(txBar, nHeadX, nHeadY,20,20);
        batch.draw(txBar, arnX[0], arnY[0],20,20);
        batch.draw(txBar, arnX[1], arnY[1],20,20);
        batch.draw(txBar, arnX[2], arnY[2],20,20);
        //batch.draw(txBar,nX2,nY2,20,20);
        //batch.draw(txBar,nX3,nY3,20,20);
        batch.setProjectionMatrix(oc.combined);
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
        /*if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            nX1-=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            nX1+=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            nY1-=5;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            nY1+=5;
        }*/
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

    /*public int firstSquare() {
        int nDif2, nDif3, nDif4;
        if (nDir == 2 && nDir == 0) {
            nDif2 = nX1 - nX2;
            nDif3 = nX1 - nX3;
            nDif4 = nX1 - nX4;
            if (nDif4 < nDif3 && nDif4 < nDif2) {
                return nX4;
            } else if (nDif3 < nDif4 && nDif3 < nDif2) {
                return nX3;
            } else {
                return nX2;
            }
        } else {
            nDif2 = nY1 - nY2;
            nDif3 = nY1 - nY3;
            nDif4 = nY1 - nY4;
            if (nDif4 < nDif3 && nDif4 < nDif2) {
                return nY4;
            } else if (nDif3 < nDif4 && nDif3 < nDif2) {
                return nY3;
            } else {
                return nY2;
            }
        }
    }

    public int lastSquare() {
        int nDif2, nDif3, nDif4;
        if (nDir == 2 && nDir == 0) {
            nDif2 = nX1 - nX2;
            nDif3 = nX1 - nX3;
            nDif4 = nX1 - nX4;
            if (nDif4 > nDif3 && nDif4 > nDif2) {
                return nX4;
            } else if (nDif3 > nDif4 && nDif3 > nDif2) {
                return nX3;
            } else {
                return nX2;
            }
        } else {
            nDif2 = nY1 - nY2;
            nDif3 = nY1 - nY3;
            nDif4 = nY1 - nY4;
            if (nDif4 > nDif3 && nDif4 > nDif2) {
                return nY4;
            } else if (nDif3 > nDif4 && nDif3 > nDif2) {
                return nY3;
            } else {
                return nY2;
            }
        }
    }*/
}
