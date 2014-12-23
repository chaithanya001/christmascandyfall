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
package by.matveev.christmascandyfall.entities

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.Gdx
import by.matveev.christmascandyfall.utils.*

public class Ripple(x: Float, y: Float, val velocity: Float = 3 * 1000F, val complete: () -> Unit) : Actor(), Disposable {

    {
        setPosition(x, y)
    }

    val renderer = ShapeRenderer()
    var radius: Float = 0F

    override fun act(delta: Float) {
        super<Actor>.act(delta)
        radius += velocity * delta

        if (radius > Gdx.graphics.getHeight()) {
            complete()
            remove()
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super<Actor>.draw(batch, parentAlpha)

        batch?.end()
        with(renderer) {
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            begin(ShapeRenderer.ShapeType.Filled)
            setColor(Color.WHITE)
            circle(getX(), getY(), radius)
            end()
        }

        getStage().updateViewport()

        batch?.begin()
    }

    override fun dispose() {
        renderer.dispose()
    }
}