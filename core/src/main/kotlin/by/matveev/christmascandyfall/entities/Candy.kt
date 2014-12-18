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
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.utils.Pool
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.utils.setRegion
import by.matveev.christmascandyfall.utils.random
import by.matveev.christmascandyfall.utils.originCenter
import by.matveev.christmascandyfall.core.Assets

val candyPool: Pool<Candy> = Pool({ Candy() }) { candy -> candy.reset() }

public fun createCandy(): Candy = candyPool.obtain().init()

public fun createBonus(): Candy {
    val type = if (MathUtils.randomBoolean()) CandyType.Freeze else CandyType.Multiply
    val candy = candyPool.obtain().init(type)
    candy.addAction(Actions.repeat(-1, Actions.sequence(
            Actions.scaleTo(1.2f, 1.2f, 0.8f),
            Actions.scaleTo(0.8f, 0.8f, 0.8f))))
    return candy
}

public fun createAntiBonus(): Candy {
    val type = if (MathUtils.randomBoolean()) CandyType.MinusScore else CandyType.MinusTime
    return candyPool.obtain().init(type)
}

public enum class CandyType {
    PlusScore
    PlusDoubleScore
    PlusTime
    Multiply
    Freeze
    MinusScore
    MinusTime
    Null
}

public class Candy(
        var type: CandyType = CandyType.Null,
        val bounds: Rectangle = Rectangle(),
        var velocity: Float = 0F) : Image() {

    override fun act(delta: Float) {
        super.act(delta)

        velocity += Cfg.gravity.y * delta
        setY(getY() - velocity * delta)

        bounds.setWidth(getWidth() * 0.8f)
        bounds.setHeight(getHeight() * 0.8f)
        bounds.setX(getX() + (getWidth() - bounds.getWidth()) * 0.5f)
        bounds.setY(getY() + (getHeight() - bounds.getHeight()) * 0.5f)
    }

    public fun init(targetType: CandyType = CandyType.Null): Candy {
        this.type = if (CandyType.Null.equals(targetType)) CandyType.values()[MathUtils.random(0, 2)] else targetType

        setRegion(regionByType(type))

        setRotation(MathUtils.random(360F))

        setY(Cfg.height + getHeight())
        setX(MathUtils.random(Cfg.width * 0.07F, Cfg.width * 0.93F))

        velocity = Cfg.candyVelocity.random()

        addAction(Actions.repeat(-1, Actions.rotateBy(10F, 0.1F)))

        originCenter()
        pack()

        return this
    }

    fun regionByType(candyType: CandyType): TextureRegion {
        val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

        when (candyType) {
            CandyType.PlusScore -> return atlas.findRegion("candy" + MathUtils.random(1, 6))
            CandyType.PlusTime -> return atlas.findRegion("candy" + 7)
            CandyType.PlusDoubleScore -> return atlas.findRegion("candy" + 8)
            CandyType.Freeze -> return atlas.findRegion("bonus_freeze")
            CandyType.Multiply -> return atlas.findRegion("bonus_multiply")
            CandyType.MinusScore -> return atlas.findRegion("antibonus_score")
            CandyType.MinusTime -> return atlas.findRegion("antibonus_time")
            else -> throw IllegalStateException("Unknow value ${candyType}")
        }
    }

    public fun free() {
        remove()
        candyPool.free(this)
    }

    public fun reset() {
        type = CandyType.Null
        bounds.set(0f, 0f, 0f, 0f)
        setDrawable(null)
        clearActions()
        clearListeners()
    }
}


