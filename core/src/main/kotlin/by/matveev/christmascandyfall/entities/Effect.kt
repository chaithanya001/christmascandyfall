package by.matveev.christmascandyfall.entities

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import by.matveev.christmascandyfall.utils.Pool
import by.matveev.christmascandyfall.core.Assets
import com.badlogic.gdx.scenes.scene2d.Group

private val startsPool: Pool<Effect> = Pool({ Effect(Assets.get("effects/stars.p")) }) {}
private val snowflakesPool: Pool<Effect> = Pool({ Effect(Assets.get("effects/snowflakes.p")) }) {}

public fun snowflakes(parent: Group, x: Float, y: Float) {
    val effect = snowflakesPool.obtain()
    effect.pool = snowflakesPool;
    effect.setPosition(x, y)
    parent.addActor(effect)
}

public fun stars(parent: Group, x: Float, y: Float) {
    val effect = startsPool.obtain()
    effect.pool = startsPool;
    effect.setPosition(x, y)
    parent.addActor(effect)
}

public class Effect(var effect: ParticleEffect, var pool: Pool<Effect>? = null) : Actor() {

    {
        effect.start()
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        effect.draw(batch)
    }

    override fun act(delta: Float) {
        super.act(delta)
        effect.setPosition(getX(), getY())
        effect.update(delta)
        if (effect.isComplete()) {
            effect.reset()
            effect.setPosition(0f, 0f)
            setPosition(0f, 0f)
            remove()
            pool?.free(this)
        }
    }
}