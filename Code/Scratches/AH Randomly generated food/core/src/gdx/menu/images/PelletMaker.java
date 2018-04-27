/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.menu.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 *
 * @author parks2214
 */
public class PelletMaker {
    int nCounter1=2,nActual1=0;
    int nCounter2=1,nActual2=0;
    public ArrayList <Pellet> alPellets = new ArrayList<Pellet>();

    public ArrayList<Poison> alPoison = new ArrayList<Poison>();

    Texture txPellet,txPoison;
    public PelletMaker() {
        txPellet = new Texture(Gdx.files.internal("HamP.png"));  //For example "SignB.jpg"
        txPoison = new Texture(Gdx.files.internal("Poison.png"));
    }

    public void draw(SpriteBatch batch) {
        for (Pellet p : alPellets) {
            p.draw(batch);
        }
        for (Poison b : alPoison) {
            b.draw(batch);
        }
        if (nActual1<nCounter1) {
            makePellet();
            nActual1++;
        }
        if (nActual2<nCounter2) {
            makePoison();
            nActual2++;
        }
    }

    public void makePellet() {
        float fX = (float) Math.random() * Gdx.graphics.getWidth();
        float fY = (float) Math.random() * Gdx.graphics.getHeight();
        alPellets.add(new Pellet(fX, fY, txPellet));
    }

    public void removePellet(Pellet pellet) {
        alPellets.remove(pellet);
        nActual1--;
    }

    public void makePoison() {
        float fX = (float) Math.random() * Gdx.graphics.getWidth();
        float fY = (float) Math.random() * Gdx.graphics.getHeight();
        alPoison.add(new Poison(fX, fY, txPoison));
    }

    public void removePoison(Poison poison) {
        alPellets.remove(poison);
        nActual2--;
    }
}
