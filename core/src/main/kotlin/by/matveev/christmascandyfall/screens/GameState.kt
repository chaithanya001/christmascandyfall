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

import by.matveev.christmascandyfall.entities.Candy
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.core.Timer
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.utils.*
import com.badlogic.gdx.math.MathUtils
import by.matveev.christmascandyfall.entities.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group

public class GameState(val screen: GameScreen) {

    var isPlaying = false

    var score = 0

    var gameTime = Cfg.initialTime
    var bonusTime = 0
    var powerUpTime = 0

    val countdown: Timer
    val bonusGenerator: Timer
    val candyGenerator: Timer
    val powerUpGenerator: Timer

    val controlType = screen.controlType

    var multiplier = 1

    val root: Group
    {
        root = screen.stage.root()

        countdown = Timer.every(1f) {
            gameTime -= 1000
            if (gameTime <= 0) {
                isPlaying = false
                Screens.set(GameOverScreen(this))
            }
        }

        bonusGenerator = Timer.every(Cfg.bonusTime) {
            if (MathUtils.randomBoolean(MathUtils.random())) {
                screen.candies.addActor(createBonus())
            }
            if (MathUtils.randomBoolean(MathUtils.random())) {
                screen.candies.addActor(createAntiBonus())
            }
        }

        powerUpGenerator = Timer.every(Cfg.powerUpTime) {
            screen.candies.addActor(createPowerUp())
        }

        candyGenerator = Timer.every(0.5F) { screen.candies.addActor(createCandy()) }
    }

    fun apply(candy: Candy) {
        val x = candy.getX()
        val y = candy.getY()

        when (candy.type) {
            CandyType.PlusScore -> {
                val add = Cfg.scoreBonus * multiplier
                score += add
                showPopup(root, "+${add}", x, y)
            }

            CandyType.PlusTime -> {
                val add = Cfg.timeBonus * multiplier
                gameTime += add
                showPopup(root, "+${add.asString()}", x, y)
            }

            CandyType.PlusDoubleScore -> {
                val add = Cfg.doubleScoreBonus * multiplier
                score += add
                showPopup(root, "+${add}", x, y)
            }

            CandyType.Freeze -> {
                showMessage(root, "Freeze Time")
                snowflakes(root, Cfg.width * 0.5f, Cfg.height * 0.5f)

                if (!countdown.paused) {
                    countdown.paused(true)
                    screen.indicator.start(Cfg.frozenTimeDelay)
                    Timer.times(Cfg.frozenTimeDelay, 1) {
                        countdown.paused(false)
                    }
                }
            }

            CandyType.Multiply -> {
                multiplier = 2
                showMessage(root, "Double Score & Time");
                stars(root, Cfg.width * 0.5f, Cfg.height * 0.5f)
                screen.indicator.start(2 * Cfg.frozenTimeDelay)
                Timer.times(2 * Cfg.frozenTimeDelay, 1) {
                    multiplier = 1
                }
            }

            CandyType.MinusScore -> {
                score -= Cfg.scoreAntiBonus;
                score = Math.max(0, score)
                showPopup(root, "-${Cfg.scoreAntiBonus}", x, y);
                Gdx.input.vibrate(Cfg.vibrateDuration);
                shake()
            }

            CandyType.MinusTime -> {
                gameTime -= Cfg.timeAntiBonus;
                gameTime = Math.max(0, gameTime)
                showPopup(root, "-${Cfg.timeAntiBonus.asString()}", x, y);
                Gdx.input.vibrate(Cfg.vibrateDuration);
                shake()
            }

            CandyType.CandyRain -> {
                showMessage(root, "Candy Rain");
                stars(root, Cfg.width * 0.5f, Cfg.height * 0.5f)
                candyGenerator.paused(true)
                powerUpGenerator.paused(true)
                bonusGenerator.paused(true)
                screen.candies.clear()

                screen.indicator.start(50f * 0.1f)
                val timer = Timer.times(0.1f, 50) {
                    screen.candies.addActor(createGiftCandy())
                }

                timer.complete = {
                    candyGenerator.paused(false)
                    powerUpGenerator.paused(false)
                    bonusGenerator.paused(false)
                }
            }

        }
    }

    fun shake() {
        val offset: Float = 10f;

        root.addAction(Actions.sequence(Actions.repeat(4,
                Actions.sequence(
                        Actions.moveBy(offset, 0f, 0.08f, Interpolation.swingOut),
                        Actions.moveBy(-offset, 0f, 0.08f, Interpolation.swingOut))),
                Actions.run {
                    root.setPosition(0F, 0F)
                    screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                }))
    }

    fun update(delta: Float) {
        Timer.update(delta)

        with(screen) {
            timeLabel.setText(gameTime.asString())
            scoreLabel.setText(score.toString())
        }

        if (gameTime <= 0) {
            Screens.set(GameOverScreen(this))
        }
    }

    fun start(): Boolean {
        if (isPlaying) {
            Timer.paused(false)
        }
        return isPlaying
    }

    fun isGameOver(): Boolean = gameTime <= 0

    fun stop() {
       Timer.paused(true)
    }

    fun dispose() {
        Timer.cancel()
    }
}