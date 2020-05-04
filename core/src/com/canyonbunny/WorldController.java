package com.canyonbunny;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.canyonbunny.game.objects.BunnyHead;
import com.canyonbunny.game.objects.Feather;
import com.canyonbunny.game.objects.GoldCoin;
import com.canyonbunny.game.objects.Level;
import com.canyonbunny.game.objects.Rock;
import com.canyonbunny.utils.CameraHelper;
import com.canyonbunny.utils.Constants;

public class WorldController {
    public static final String TAG = WorldController.class.getName();

    public CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    public WorldController() {
        init();
    }

    private void init(){
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;

        initLevel();
    }

    private void initLevel () {
        score = 0;
        level = new Level(Constants.LEVEL_01);
        cameraHelper.setTarget(level.bunnyHead);
    }

    public void update(float deltaTime){
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        testCollisions();
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime){
        if (Gdx.app.getType() != ApplicationType.Desktop) return;

        if (!cameraHelper.hasTarget(level.bunnyHead)) {

            // Camera Controls (move)
            float camMoveSpeed = 5 * deltaTime;
            float camMoveSpeedAccelerationFactor = 5;

            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
                camMoveSpeed *= camMoveSpeedAccelerationFactor;

//            if (Gdx.input.isKeyPressed(Keys.LEFT))
//                moveCamera(-camMoveSpeed, 0);
//
//            if (Gdx.input.isKeyPressed(Keys.RIGHT))
//                moveCamera(camMoveSpeed, 0);
//
//            if (Gdx.input.isKeyPressed(Keys.UP))
//                moveCamera(0, camMoveSpeed);
//
//            if (Gdx.input.isKeyPressed(Keys.DOWN))
//                moveCamera(0, -camMoveSpeed);

            if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
                cameraHelper.setPosition(0, 0);
        }
    }

    private void onCollisionBunnyHeadWithRock(Rock rock){
        BunnyHead bunnyHead = level.bunnyHead;
        float heightDifference = Math.abs(bunnyHead.position.y - ( rock.position.y + rock.bounds.height));

        if (heightDifference > 0.25f) {
            boolean hitRightEdge = bunnyHead.position.x > (rock.position.x + rock.bounds.width / 2.0f);

            if (hitRightEdge) {
                bunnyHead.position.x = rock.position.x + rock.bounds.width;
            } else {
                bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
            }
            return;
        }

        switch (bunnyHead.jumpState) {
            case GROUNDED:
                break;

            case FALLING:
            case JUMP_FALLING:
                bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
                bunnyHead.jumpState = BunnyHead.JUMP_STATE.GROUNDED;
                break;

            case JUMP_RISING:
                bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
                break;
        }
    }

    private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin){
        goldcoin.collected = true;
        score += goldcoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }

    private void onCollisionBunnyWithFeather(Feather feather){
        feather.collected = true;
        score += feather.getScore();
        level.bunnyHead.setFeatherPowerup(true);
        Gdx.app.log(TAG, "Feather collected");
    }

    private void testCollisions () {
        r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,
                level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);

        // Test collision: Bunny Head <-> Rocks
        for (Rock rock : level.rocks) {
            r2.set(rock.position.x, rock.position.y, rock.bounds.width,
                    rock.bounds.height);

            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyHeadWithRock(rock);
            // IMPORTANT: must do all collisions for valid
            // edge testing on rocks.
        }

        // Test collision: Bunny Head <-> Gold Coins
        for (GoldCoin goldcoin : level.goldcoins) {
            if (goldcoin.collected) continue;
            r2.set(goldcoin.position.x, goldcoin.position.y,
                    goldcoin.bounds.width, goldcoin.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyWithGoldCoin(goldcoin);
            break;
        }

        // Test collision: Bunny Head <-> Feathers
        for (Feather feather : level.feathers) {
            if (feather.collected) continue;
            r2.set(feather.position.x, feather.position.y,
                    feather.bounds.width, feather.bounds.height);

            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyWithFeather(feather);
            break;
        }
    }
}
