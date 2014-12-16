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
import by.matveev.christmascandyfall.core.Prefs
import by.matveev.christmascandyfall.Cfg

public class PauseScreen : AbstractScreen() {

    override fun show() {
        super.show()

        /*content*/
        verticalGroup(root()) {

            space = 40F
            align = Align.center

            label(this) {
                text = "game paused".toUpperCase()
                font = Assets.get("fonts/font.fnt")
                fontColor = Color.WHITE
                align = Align.center
            }

            /*buttons*/
            horizontalGroup(this) {

                space = 10F
                align = Align.center

                val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

                image(this, atlas.findRegion("continueButton")) {
                    rippleClicked { Screens.pop() }
                    hoverEffect()
                }

                image(this, atlas.findRegion("replayButton")) {
                    rippleClicked { Screens.set(ControlTypeScreen()) }
                    hoverEffect()
                }

                image(this, atlas.findRegion("menuButton")) {
                    rippleClicked { Screens.set(MenuScreen()) }
                    hoverEffect();
                }


                val on = "soundOnButton"
                val off = "soundOffButton"

                image(this) {
                    setRegion(atlas.findRegion(if (Prefs.bool(Prefs.SOUNDS_KEY)) on else off))
                    clicked {
                        setRegion(atlas.findRegion(if (Prefs.toggle(Prefs.SOUNDS_KEY)) on else off))
                    }
                    hoverEffect()
                }
            }



            centerInBounds(Cfg.width, Cfg.height);
        }
    }
}