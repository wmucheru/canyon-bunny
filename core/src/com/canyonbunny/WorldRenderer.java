package com.canyonbunny;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.canyonbunny.utils.Constants;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController controller;

    public WorldRenderer(WorldController controller) {
        this.controller = controller;
        init();
    }

    private void init(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render(){
        renderTestObjects();
    }

    private void renderTestObjects() {
        controller.cameraHelper.applyTo(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Sprite sprite : controller.sprites){
            sprite.draw(batch);
        }
        batch.end();
    }

    public void resize(int width, int height){
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}