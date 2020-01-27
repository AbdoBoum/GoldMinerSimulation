package Gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;

import static Environment.MiningField.MAP_HEIGHT;
import static Environment.MiningField.MAP_WIDTH;
import static Gui.EntityType.OBSTACLE;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GoldMiningApp extends GameApplication {

    public static final int BLOCK_SIZE = 60;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(MAP_HEIGHT * BLOCK_SIZE);
        gameSettings.setHeight(MAP_WIDTH * BLOCK_SIZE);
        gameSettings.setTitle("GOLD MINERS");
        gameSettings.setVersion("0.1");
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setPreserveResizeRatio(true);
    }


    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.SLATEGRAY);

        var miner = FXGL.getAssetLoader().loadTexture("miner.png");
        miner.setTranslateX(0);
        miner.setTranslateY(0);
        FXGL.getGameScene().addUINode(miner);

        var obstacle = FXGL.getAssetLoader().loadTexture("obstacle.png");
        obstacle.setTranslateX(80);
        obstacle.setTranslateY(350);
        FXGL.getGameScene().addUINode(obstacle);

        var gold = FXGL.getAssetLoader().loadTexture("gold.png");
        gold.setTranslateX(90);
        gold.setTranslateY(440);
        FXGL.getGameScene().addUINode(gold);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
