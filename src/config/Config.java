package config;

import javafx.scene.image.Image;

public class Config {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int PLAYER_SIZE = 60;
    public static final Image PLAYER_IMG = new Image("file:assets/player.png");
    public static final Image EXPLOSION_IMG = new Image("file:assets/explosion.png");
    public static final int EXPLOSION_W = 128;
    public static final int EXPLOSION_ROWS = 3;
    public static final int EXPLOSION_COLS = 3;
    public static final int EXPLOSION_H = 128;
    public static final int EXPLOSION_STEPS = 15;
    public static final int INITIAL_SCORE = 0;
    public static final int BASE_SHOT_SIZE = 6;
    public static final int BIG_SHOT_SIZE = 9;
    public static final int SPEED_SHOT_SIZE = 4;
    public static final int POWER_UP_DURATION = 3 * 60;
    public static final int ITEM_DROP_SPEED = 6;
    public static final Image[] BOMBS_IMG = {new Image("file:assets/1.png"), new Image("file:assets/2.png"), new Image("file:assets/3.png"), new Image("file:assets/4.png")};
    public static final Image[] BoostShot_IMG = {new Image("file:assets/drop_item.png"), new Image("file:assets/drop_item.png"), new Image("file:assets/drop_item.png"), new Image("file:assets/drop_item.png")};
    public static final int MAX_BOMBS = 6;
    public static final int MAX_SHOTS = MAX_BOMBS * 2;

    public static final int BASESHOT_SPEED = 10;
    public static final int BASESHOT_SIZE = 6;
    public static final int BASESHOT_DAMAGE = 2;
    public static final int PADDING_BOMB = 30;
    public static final int PADDING_ROCKET = 5;
    public static final int BOSS_SHOT_SPEED = 4;
    public static final int BIGSHOT_DAMAGE = 5;
    public static final int BIGSHOT_SPEED = 3;
    public static final int BIGSHOT_SIZE = 9;
    public static final int SPEEDSHOT_SPEED = 20;
    public static final int SPEEDSHOT_SIZE = 4;
    public static final int SPEEDSHOT_DAMAGE = 1;
    public static final int SHOTS_PER_SHOOT = 4;
    public static final int SPACE_BETWEEN_SHOT = 20;
    public static final int REDUCE_SPEED = 1;


    public static final int BASE_BOMB_SPEED_FACTOR = 15;
    public static final int BASE_BOMB_BASE_SPEED = 2;
    public static final int BIG_BOMB_SPEED_FACTOR = 35;
    public static final int BIG_BOMB_BASE_SPEED = 2;
    public static final int FAST_BOMB_SPEED_FACTOR = 5;
    public static final int FAST_BOMB_BASE_SPEED = 2;
    public static final int BOSS_BOMB_SPEED_FACTOR = 50;
    public static final int BOSS_BOMB_BASE_SPEED = 2;
}
