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
import by.matveev.christmascandyfall.screens.PlayScreen.ControlType
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.core.AbstractScreen
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.entities.Santa
import by.matveev.christmascandyfall.core.Timer
import by.matveev.christmascandyfall.utils.*
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.entities.*

public class PlayScreen(var controlType: ControlType) : AbstractScreen() {

    public enum class ControlType {
        Touch
        Tilt
    }

    val atlas: TextureAtlas;
    val music: Music
    {
        atlas = Assets.get<TextureAtlas>("gfx/game.atlas")
        music = Assets.get<Music>("sounds/music.ogg")
    }

    var isPlay = false

    var gameTime: Int = Cfg.initialTime
    var bonusTime: Int = 0
    var score: Int = 0;


    val style = labelStyle {
        font = Assets.get("fonts/font.fnt")
        fontColor = Color.WHITE
    }

    var scoreLabel = label(0.toString(), style)
    var timeLabel = label(Cfg.initialTime.asString(), style)

    val candiesLayer = Group()

    var santa = Santa()

    var countdown: Timer? = null


    override fun show() {
        super.show()

        if (isPlay) {

            Timer.paused(false)
            return
        }


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

            isPlay = true

            candiesLayer.setSize(Cfg.width, Cfg.height)
            root().addActor(candiesLayer)

            root().addActor(scoreLabel)
            root().addActor(timeLabel)

            image(root(), atlas.findRegion("pauseButton")) {
                rippleClicked { Screens.push(PauseScreen()) }
                hoverEffect()
                centerInBounds(Cfg.width, Cfg.height)
                setY(Cfg.height * 0.9F)
            }

            countdown = Timer.every(1F) {
                gameTime -= 1000
                if (gameTime > 0) {
                    timeLabel.setText(gameTime.asString())
                } else Screens.set(GameOverScreen(score))
            }

            Timer.every(1F) {
                bonusTime += 1000
                if (bonusTime >= Cfg.bonusTime) {
                    if (MathUtils.randomBoolean(MathUtils.random(0.9F, 1F))) {
                        candiesLayer.addActor(createBonus())
                    }
                    if (MathUtils.randomBoolean(MathUtils.random(0.95F, 1F))) {
                        candiesLayer.addActor(createAntiBonus())
                    }
                    bonusTime = 0
                }
            }

            Timer.every(0.5F) { candiesLayer.addActor(createCandy()) }
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

        if (!isPlay) return

        Timer.update(delta)

        input(delta)

        checkCollisions()

        if (gameTime <= 0) {
            isPlay = false;
            Screens.set(GameOverScreen(score));
        }
    }

    fun checkCollisions() {
        val children = candiesLayer.getChildren()
        val actors = children.begin();

        actors.forEach { candy ->
            if (candy is Candy) {
                if (santa.bounds.overlaps(candy.bounds)) {
                    applyEffect(candy)
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

    fun applyEffect(candy: Candy) {

        val x = candy.getX()
        val y = candy.getY()

        when (candy.type) {
            CandyType.PlusScore -> {
                score += 1
                scoreLabel.setText(score.toString())
                showPopup(root(), "+1", x, y)
            }
            CandyType.PlusTime -> {
                gameTime += 1000
                timeLabel.setText(gameTime.asString())
                showPopup(root(), "+00:01", x, y)
            }
            CandyType.PlusDoubleScore -> {
                score += 2
                scoreLabel.setText(score.toString())
                showPopup(root(), "+2", x, y)
            }
            CandyType.Freeze -> {
                showMessage(root(), "Freeze Time")
                countdown?.paused(true)
                Timer.times(10F, 1) { countdown?.paused(false) }
            }

            CandyType.Multiply -> {
                showMessage(root(), "Double Score");
                score *= 2;
                scoreLabel.setText(score.toString())
            }

            CandyType.MinusScore -> {
                score -= 20;
                scoreLabel.setText(score.toString())
                showPopup(root(), "-20", x, y);
                Gdx.input.vibrate(1 * 1000);
                shake();
            }

            CandyType.MinusTime -> {
                gameTime -= 10 * 1000;
                timeLabel.setText(gameTime.asString())
                showPopup(root(), "-00:10", x, y);
                Gdx.input.vibrate(1 * 1000);
                shake();
            }
        }
    }

    fun shake() {
        val offset: Float = 10f;

        stage.addAction(Actions.sequence(Actions.repeat(4,
                Actions.sequence(
                        Actions.moveBy(offset, -offset, 0.08F, Interpolation.swingOut),
                        Actions.moveBy(-offset, -offset, 0.08F, Interpolation.swingOut))),
                Actions.run {
                    stage.root().setPosition(0F, 0F)
                    resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }))

    }

    override fun hide() {
        super.hide()

        Timer.paused(true)
    }

    override fun dispose() {
        super.dispose()

        Timer.cancel()
    }
}