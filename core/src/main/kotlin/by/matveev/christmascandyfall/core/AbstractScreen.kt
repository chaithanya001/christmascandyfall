/*
 * Copyright (C) 2014 Alexey Matveev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.matveev.christmascandyfall.core

import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.Group
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.utils.onKeyDown
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

public abstract class AbstractScreen : Screen {

    val clearRed = 230F / 255F
    val clearGreen = 75F / 255F
    val clearBlue = 60F / 255F
    val clearAlpha = 1F

    val renderer = ShapeRenderer()
    var isGutterVisible = false;

    public val stage: Stage;
    {
        stage = Stage(FitViewport(Cfg.width, Cfg.height))
        stage.onKeyDown { onHardKeyPressed(it) }
    }

    override fun render(delta: Float) {
        with(Gdx.graphics.getGL20()) {
            if (isGutterVisible) {
                glClearColor(1f, 1f, 1f, 1f)
            } else {
                glClearColor(clearRed, clearGreen, clearBlue, clearAlpha)
            }
            glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        }

        if (isGutterVisible) {
            with(renderer) {
                setProjectionMatrix(stage.getCamera().combined)
                begin(ShapeRenderer.ShapeType.Filled);
                setColor(clearRed, clearGreen, clearBlue, clearAlpha)
                rect(0f, 0f, Cfg.width, Cfg.height)
                end()
            }
        }

        stage.act()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.getViewport().update(width, height)
    }

    override fun show() = Gdx.input.setInputProcessor(stage)

    override fun hide() = Gdx.input.setInputProcessor(null)

    open fun onHardKeyPressed(keyCode: Int) {
        // do nothing
    }

    override fun pause() {
        // do nothing
    }

    override fun resume() {
        // do nothing
    }

    override fun dispose() = stage.getRoot().clearChildren()

    public fun root(): Group = stage.getRoot()
}