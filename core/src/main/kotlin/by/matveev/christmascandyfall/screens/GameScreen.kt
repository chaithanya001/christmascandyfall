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
package by.matveev.christmascandyfall.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation
import by.matveev.christmascandyfall.screens.GameScreen.ControlType
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.core.AbstractScreen
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.entities.Santa
import by.matveev.christmascandyfall.core.Timer
import by.matveev.christmascandyfall.utils.*
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.entities.*
import by.matveev.christmascandyfall.entities.Effect
import com.badlogic.gdx.scenes.scene2d.Actor
import java.util.ArrayList

public class GameScreen(var controlType: ControlType) : AbstractScreen() {

    public enum class ControlType {
        Touch
        Tilt
    }

    val atlas: TextureAtlas;
    val music: Music
    val state: GameState
    {
        atlas = Assets.get<TextureAtlas>("gfx/game.atlas")
        music = Assets.get<Music>("sounds/music.ogg")
        state = GameState(this)
        isGutterVisible = true
    }

    val style = labelStyle {
        font = Assets.get("fonts/font.fnt")
        fontColor = Color.WHITE
    }

    val scoreLabel = label(0.toString(), style)
    val timeLabel = label(Cfg.initialTime.asString(), style)

    val candies = Group()
    val santa = Santa()

    override fun show() {
        super.show()

        if (state.start()) return

        val goalMessage = label("collect candies,\navoid bombs!", style)
        goalMessage.setPosition((Cfg.width - goalMessage.getPrefWidth()) * 0.5F, Cfg.height * 0.7F);
        root().addActor(goalMessage)

        val startMessage = label("tap to start", style)
        startMessage.setPosition((Cfg.width - startMessage.getPrefWidth()) * 0.5F, Cfg.height * 0.5F);
        root().addActor(startMessage)

        santa.setPosition((Cfg.width - santa.getPrefWidth()) * 0.5F, 0F)
        santa.pack()
        root().addActor(santa)

        root().clicked {
            root().clearListeners()

            goalMessage.remove()
            startMessage.remove()

            state.isPlaying = true

            candies.setSize(Cfg.width, Cfg.height)
            root().addActor(candies)

            root().addActor(scoreLabel)
            root().addActor(timeLabel)

            image(root(), atlas.findRegion("pauseButton")) {
                rippleClicked { Screens.push(PauseScreen()) }
                hoverEffect()
                centerInBounds(Cfg.width, Cfg.height)
                setY(Cfg.height * 0.9F)
            }
        }
    }

    val touch = Vector3()

    fun input(delta: Float) {
        touch.x = Gdx.input.getX().toFloat()
        touch.y = Gdx.input.getY().toFloat()

        stage.getCamera().unproject(touch)


        when (controlType) {
            ControlType.Touch -> santa.setX(touch.x - santa.getPrefWidth() * 0.5F)
            ControlType.Tilt -> santa.setX(santa.getX() - (Gdx.input.getAccelerometerX() * Cfg.santaVelocity * delta))
        }

        if (santa.getX() < 0F) {
            santa.setX(0F)
        }

        if (santa.getX() > Cfg.width - santa.getWidth()) {
            santa.setX(Cfg.width - santa.getWidth())
        }
    }

    override fun render(delta: Float) {
        super.render(delta)

        scoreLabel.setPosition(Cfg.width * 0.1f, Cfg.height * 0.9f)
        timeLabel.setPosition(Cfg.width - timeLabel.getPrefWidth() - Cfg.width * 0.1f, Cfg.height * 0.9f)

        if (!state.isPlaying) return

        state.update(delta)

        input(delta)

        checkCollisions()
    }

    fun checkCollisions() {
        val children = candies.getChildren()
        val actors = children.begin();

        actors.forEach { candy ->
            if (candy is Candy) {
                if (santa.bounds.overlaps(candy.bounds)) {
                    state.apply(candy)
                    println("applied for candyType = [${candy.type}] state=[gameTime=${state.gameTime}, bonusTime=${state.bonusTime}, score=${state.score}, countdown=${state.countdown.paused}]")

                    candy.free()
                }

                if (candy.getY() + candy.getPrefHeight() < 0) {
                    if (candy.getParent() != null) {
                        candy.free()
                    }
                }
            }
        }
        children.end();
    }

    override fun hide() {
        super.hide()
        state.stop()
    }

    override fun dispose() {
        super.dispose()
        state.dispose()
    }
}