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
import by.matveev.christmascandyfall.core.Assets
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.utils.centerInBounds
import com.badlogic.gdx.graphics.g2d.Batch

public class Indicator : Actor() {

    {
        setSize(Cfg.width * 0.9f, 15f)
        centerInBounds(Cfg.width, Cfg.height)
        setY(Cfg.height * 0.85f)
        setVisible(false)
    }

    val region = Assets.get<TextureAtlas>("gfx/game.atlas").findRegion("rect_mask")

    var duration = 0f
    var time = 0f

    public fun start(duration: Float) {
        this.duration = duration
        this.time = duration
        setVisible(true)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch?.draw(region, getX(), getY(), getWidth() * (time / duration), getHeight())
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (time < 0) return
        time -= delta
        if (time < 0) {
            setVisible(false)
        }
    }
}