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

import by.matveev.christmascandyfall.core.AbstractScreen
import by.matveev.christmascandyfall.utils.*
import by.matveev.christmascandyfall.core.Assets
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.utils.Align
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import by.matveev.christmascandyfall.Cfg
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Interpolation
import by.matveev.christmascandyfall.entities.Ripple
import by.matveev.christmascandyfall.core.Screens
import com.badlogic.gdx.Gdx
import by.matveev.christmascandyfall.core.Platform
import by.matveev.christmascandyfall.core.Prefs

public class MenuScreen(val intro: Boolean = false) : AbstractScreen() {

    override fun show() {
        super.show()

        label(root()) {
            text = "crazy christmas\ncandy fall".toUpperCase()
            font = Assets.get("fonts/font.fnt")
            fontColor = Color.WHITE
            align = Align.center

            hide().addAction(Actions.sequence(Actions.delay(if (intro) 3.5F else 0F), Actions.alpha(1F, 0.5F)))

            pack()
            setPosition((Cfg.width - getPrefWidth()) * 0.5F, Cfg.height * 0.85F)
        }

        val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

        image(root(), atlas.findRegion("penguin")) {
            originCenter()
            if (intro) {
                setScale(0F)
                addAction(Actions.scaleTo(1F, 1F, 0.7F, Interpolation.swingOut))
            }
            setPosition((Cfg.width - getPrefWidth()) * 0.5F, Cfg.height * 0.5F)
        }

        image(root(), atlas.findRegion("penguin")) {
            originCenter()
            if (intro) {
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(1.1F),
                        Actions.scaleTo(0.8F, 0.8F, 0.7F, Interpolation.swingOut)))
            }
            setPosition(Cfg.width * 0.15F, Cfg.height * 0.5F)
        }

        image(root(), atlas.findRegion("penguin")) {
            originCenter()
            if (intro) {
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(1.8F),
                        Actions.scaleTo(0.7F, 0.7F, 0.7F, Interpolation.swingOut)))
            }
            setPosition(Cfg.width * 0.63F, Cfg.height * 0.5F)
        }

        image(root(), atlas.findRegion("speech")) {
            originCenter()
            if (intro) {
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(2.5F),
                        Actions.scaleTo(0.7F, 0.7F, 0.7F, Interpolation.swingOut)))
            }
            setPosition((Cfg.width - getPrefWidth()) * 0.57F, Cfg.height * 0.62F)
        }

        image(root(), atlas.findRegion("playButton")) {
            setScale(0F)
            addAction(Actions.sequence(
                    Actions.delay(if (intro) 3.5F else 0F),
                    Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut)))
            setPosition((Cfg.width - getPrefWidth()) * 0.5F, Cfg.height * 0.25F)
            hoverEffect()
            clicked {
                stage.addActor(Ripple(getX() + getWidth() * 0.5F, getY() + getHeight() * 0.5F) {
                    Screens.set(ControlTypeScreen())
                })
            }
        }

        horizontalGroup(root()) {
            align = Align.center
            space = 30f

            image(this, atlas.findRegion("infoButton")) {
                rippleClicked {
                    Screens.set(HelpScreen())
                }
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(if (intro) 4F else 0.5F),
                        Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut)))
                hoverEffect()
            }

            image(this, atlas.findRegion("achievementsButton")) {
                clicked {
                    Platform.actions?.showAchievements()
                }
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(if (intro) 4F else 0.5F),
                        Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut)))
                hoverEffect()
            }

            image(this, atlas.findRegion("leaderboardsButton")) {
                clicked {
                    Platform.actions?.showLeaderBoard()
                }
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(if (intro) 4F else 0.5F),
                        Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut)))
                hoverEffect()
            }


            val on = "soundOnButton"
            val off = "soundOffButton"

            image(this) {
                setRegion(atlas.findRegion(if (Prefs.bool(Prefs.SOUNDS_KEY)) on else off))
                clicked {
                    setRegion(atlas.findRegion(if (Prefs.toggle(Prefs.SOUNDS_KEY)) on else off))
                }
                setScale(0F)
                addAction(Actions.sequence(
                        Actions.delay(if (intro) 4F else 0.5F),
                        Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut)))
                hoverEffect()
            }

            pack()
            centerInBounds(Cfg.width, 0F)
            setY(Cfg.height * 0.09F)
        }
    }

    override fun onHardKeyPressed(keyCode: Int) = Gdx.app.exit()
}