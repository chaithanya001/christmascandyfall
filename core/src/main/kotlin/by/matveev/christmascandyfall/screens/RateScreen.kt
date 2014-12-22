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
import by.matveev.christmascandyfall.core.Assets
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.Color
import by.matveev.christmascandyfall.utils.*
import com.badlogic.gdx.scenes.scene2d.utils.Align
import by.matveev.christmascandyfall.Cfg
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import by.matveev.christmascandyfall.core.Prefs
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.core.Platform

public class RateScreen(): AbstractScreen() {

    override fun show() {
        super.show()

        label(root()) {
            text = "Liked this game?\n" +
                   "Please, rate it!\n" +
                   "Thanks!".toUpperCase()
            font = Assets.get("fonts/font.fnt")
            fontColor = Color.WHITE
            align = Align.center
            pack()
            setX((Cfg.width - getPrefWidth()) * 0.5f)
            setY(Cfg.height * 0.5f)
        }

        val style = TextButton.TextButtonStyle()
        with(style) {
            pressedOffsetX = -5f;
            pressedOffsetY = -5f;
            font = Assets.get("fonts/font.fnt")
            fontColor = Color.WHITE
            downFontColor = Color(1f, 1f, 1f, 0.5f)
        }

        horizontalGroup(root()) {
            space = 25f
            align = Align.center

            textButton(this, style) {
                setText("rate".toUpperCase())
                getLabel().setFontScale(1.2f)
                rippleClicked {
                    Prefs.set(Prefs.DONT_SHOW_RATE_KEY, true);
                    Platform.actions?.openMarketPage()
                    Screens.pop()

                }
            }

            textButton(this, style) {
                setText("later".toUpperCase())
                rippleClicked {
                    Prefs.set(Prefs.FIRST_LAUNCH_TIME_KEY, 0L);
                    Prefs.set(Prefs.LAUNCHES_COUNT_KEY, 0L);
                    Screens.pop()
                }
            }

            textButton(this, style) {
                setText("never".toUpperCase())
                getLabel().setFontScale(0.8f)
                rippleClicked {
                    Prefs.set(Prefs.DONT_SHOW_RATE_KEY, true)
                    Screens.pop()
                }
            }

            pack()
            setX((Cfg.width - getPrefWidth()) * 0.5f)
            setY(Cfg.height * 0.3f)
        }
    }
}

