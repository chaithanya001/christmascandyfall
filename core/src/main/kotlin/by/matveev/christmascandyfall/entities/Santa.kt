/*
 * Copyright (C) 2014 Alexey Matveev
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.utils.setRegion
import by.matveev.christmascandyfall.Cfg
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3

public class Santa(val bounds: Rectangle = Rectangle()) : Image() {

    {
        val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")
        setRegion(atlas.findRegion("santa"))
    }

    var velocity = 0f;

    override fun act(delta: Float) {
        super.act(delta)

        velocity *= Cfg.friction
        setX(getX() + velocity)

        bounds.setWidth(getWidth() * 0.7F);
        bounds.setHeight(getHeight() * 0.4F);
        bounds.setX(getX() + (getWidth() - bounds.getWidth()) * 0.5F);
        bounds.setY(getY());

    }
}