package com.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.canyonbunny.utils.CameraHelper;

public class WorldController {
    public Sprite[] sprites;
    public int selectedSprite;
    public CameraHelper cameraHelper;

    public WorldController() {
        init();
    }

    private void init(){
        cameraHelper = new CameraHelper();
        initObjects();
    }

    public void update(float deltaTime){
        handleDebugInput(deltaTime);
        updateObjects(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void initObjects() {
        sprites = new Sprite[5];

        int width = 32;
        int height = 32;

        Pixmap pixmap = createProceduralPixmap(width, height);
        Texture texture = new Texture(pixmap);

        for (int i = 0; i < sprites.length; i++) {
            Sprite spr = new Sprite(texture);
            spr.setSize(1, 1);
            spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);

            float randomX = MathUtils.random(-2.0f, 2.0f);
            float randomY = MathUtils.random(-2.0f, 2.0f);
            spr.setPosition(randomX, randomY);

            sprites[i] = spr;
        }

        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height){
        Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        p.setColor(1, 0, 0, 0.5f);
        p.fill();

        // Draw a yellow-colored X shape on square
        p.setColor(1, 1, 0, 1);
        p.drawLine(0, 0, width, height);
        p.drawLine(width, 0, 0, height);

        // Draw a cyan-colored border around square
        p.setColor(0, 1, 1, 1);
        p.drawRectangle(0, 0, width, height);

        return p;
    }

    private void updateObjects(float deltaTime){
        // Get current rotation from selected sprite
        float rotation = sprites[selectedSprite].getRotation();

        // Rotate sprite by 90 degrees per second
        rotation += 90 * deltaTime;

        // Wrap around at 360 degrees
        rotation %= 360;

        // Set new rotation value to selected sprite
        sprites[selectedSprite].setRotation(rotation);
    }

    private void handleDebugInput(float deltaTime){
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) return;

        // Selected Sprite Controls
        float sprMoveSpeed = 5 * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(
                -sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.D))
            moveSelectedSprite(sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0,
                sprMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0,
                -sprMoveSpeed);

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)){
            selectedSprite = (selectedSprite + 1) % sprites.length;

            if(cameraHelper.hasTarget()){
                cameraHelper.setTarget(sprites[selectedSprite]);
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.UP)){
            cameraHelper.setZoom(2);
        }

        if (Gdx.input.isKeyJustPressed(Keys.DOWN)){
            cameraHelper.setZoom(1);
        }

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)){
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null : sprites[selectedSprite]);
        }

        if (Gdx.input.isKeyJustPressed(Keys.R)){
            init();
        }
    }

    private void moveSelectedSprite (float x, float y) {
        sprites[selectedSprite].translate(x, y);
    }
}
