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
import com.badlogic.gdx.scenes.scene2d.utils.Align
import by.matveev.christmascandyfall.core.Assets
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.core.Screens
import com.badlogic.gdx.Gdx
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.core.Platform
import by.matveev.christmascandyfall.core.Prefs


public class GameOverScreen(var state: GameState) : AbstractScreen() {

    override fun show() {
        super.show()

        verticalGroup(root()) {
            align = Align.center
            space = 40F

            label(this) {
                text = "You collected\n${state.score}\ncandies".toUpperCase()
                font = Assets.get("fonts/font.fnt")
                fontColor = Color.WHITE
                align = Align.center
            }

            val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

            image(this, atlas.findRegion("gameover_logo"))

            horizontalGroup(this) {
                align = Align.center
                space = 20F

                image(this, atlas.findRegion("replayButton")) {
                    rippleClicked { Screens.set(GameScreen(state.controlType)) }
                    hoverEffect()
                }

                image(this, atlas.findRegion("menuButton")) {
                    rippleClicked { Screens.set(MenuScreen()) }
                    hoverEffect();
                }

                image(this, atlas.findRegion("exitButton")) {
                    rippleClicked { Gdx.app.exit() }
                    hoverEffect();
                }
            }

            pack()
            centerInBounds(Cfg.width, Cfg.height)
        }

        Prefs.set(Prefs.GAMES_COUNT_KEY, Prefs.int(Prefs.GAMES_COUNT_KEY, 0) + 1)
        Platform.actions?.checkForAchievements(state)
    }

}