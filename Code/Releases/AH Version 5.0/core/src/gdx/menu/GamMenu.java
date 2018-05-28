package gdx.menu;
import com.badlogic.gdx.Game;
import gdx.menu.Screens.*;


public class GamMenu extends Game {
    ScrMenu scrMenu;
    ScrTail scrTail;
    ScrFood scrFood;
    ScrAnimation scrAnimation;
    ScrAniHit scrAniHit;
    ScrGame scrGame;
    ScrGameOver scrGameOver;
    ScrAnimalChoice scrAnimalChoice;
    ScrAnimalChoice2 scrAnimalChoice2;
    ScrGame2 scrGame2;
    ScrRules scrRules;
    ScrGame3 scrGame3;
    ScrGame4 scrGame4;
    int nScreen; // 0 for menu, 1 for play, 2 for Sign, 3 for Animation, 4 for AniHit, 5 for Game, 6 for GameOver, 7 for
    // AnimalChoice, 8 for AnimalChoice2, 9 for Game2, 10 for Rules, 11 for Game3, 12 for Game4

    public void updateState(int _nScreen) {
        nScreen = _nScreen;
        if ( nScreen == 0) {
            setScreen(scrMenu);
        } else if (nScreen == 1) {
            setScreen(scrTail);
        } else if (nScreen ==2) {
            setScreen(scrFood);
        } else if (nScreen == 3){
            setScreen(scrAnimation);
        } else if (nScreen == 4){
            setScreen(scrAniHit);
        } else if (nScreen == 5){
            setScreen(scrGame);
        } else if (nScreen == 6){
            setScreen(scrGameOver);
        } else if (nScreen == 7){
            setScreen(scrAnimalChoice);
        } else if (nScreen == 8){
            setScreen(scrAnimalChoice2);
        } else if (nScreen == 9){
            setScreen(scrGame2);
        } else if (nScreen == 10){
            setScreen(scrRules);
        } else if (nScreen == 11){
            setScreen(scrGame3);
        } else if (nScreen == 12){
            setScreen(scrGame4);
        }
    }

    @Override
    public void create() {
        nScreen = 0;
        // notice that "this" is passed to each screen. Each screen now has access to methods within the "game" master program
        scrMenu = new ScrMenu(this);
        scrTail = new ScrTail(this);
        scrFood = new ScrFood(this);
        scrAnimation = new ScrAnimation(this);
        scrAniHit = new ScrAniHit(this);
        scrGame = new ScrGame(this);
        scrGameOver = new ScrGameOver(this);
        scrAnimalChoice = new ScrAnimalChoice(this);
        scrAnimalChoice2 = new ScrAnimalChoice2(this);
        scrGame2 = new ScrGame2(this);
        scrRules = new ScrRules(this);
        scrGame3 = new ScrGame3(this);
        scrGame4 = new ScrGame4(this);
        updateState(0);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}