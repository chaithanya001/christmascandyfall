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

import com.badlogic.gdx.scenes.scene2d.utils.Align
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.core.AbstractScreen
import by.matveev.christmascandyfall.utils.*
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.Cfg

public class ControlTypeScreen : AbstractScreen() {

    override fun show() {
        super.show()

        verticalGroup(root()) {
            align = Align.center
            space = 100F

            label(this) {
                text = "Please, select\ncontrol type:".toUpperCase()
                font = Assets.get("fonts/font.fnt")
                fontColor = Color.WHITE
                align = Align.center
            }

            horizontalGroup(this) {
                space = 50F
                align = Align.center

                val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

                verticalGroup(this) {
                    space = 20F
                    align = Align.center

                    label(this) {
                        text = "touch".toUpperCase()
                        font = Assets.get("fonts/font.fnt")
                        fontColor = Color.WHITE
                        align = Align.center
                    }

                    image(this, atlas.findRegion("controlTouchButton")) {
                        rippleClicked { Screens.set(GameScreen(GameScreen.ControlType.Touch)) }
                        hoverEffect()
                    }
                }

                verticalGroup(this) {
                    space = 20F
                    align = Align.center

                    label(this) {
                        text = "tilt".toUpperCase()
                        font = Assets.get("fonts/font.fnt")
                        fontColor = Color.WHITE
                        align = Align.center
                    }

                    image(this, atlas.findRegion("controlTiltButton")) {
                        rippleClicked { Screens.set(GameScreen(GameScreen.ControlType.Tilt)) }
                        hoverEffect()
                    }
                }
            }

            pack()
            centerInBounds(Cfg.width, Cfg.height)
        }
    }
}