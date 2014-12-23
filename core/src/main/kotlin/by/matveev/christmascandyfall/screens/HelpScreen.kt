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
import by.matveev.christmascandyfall.actors.PagedScroll
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import by.matveev.christmascandyfall.utils.*
import com.badlogic.gdx.graphics.Color
import by.matveev.christmascandyfall.Cfg
import by.matveev.christmascandyfall.core.Screens
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation

public class HelpScreen : AbstractScreen() {

    override fun show() {
        super.show()

        val atlas = Assets.get<TextureAtlas>("gfx/game.atlas")

        with(PagedScroll(root())) {
            setFlingTime(0.2f);

            val style = labelStyle {
                font = Assets.get("fonts/font.fnt")
                fontColor = Color.WHITE
            }


            table(this) {
                add(label("welcome to\ncrazy christmas\ncandy fall", style)).center().pad(40F).row()
                add(label("swipe left", style)).center().pad(40F).row()
            }

            table(this) {
                add(label("candies: ", style)).center().colspan(2).pad(50F).row()

                add(image(region = atlas.findRegion("candy1"))).center().padRight(20F).padBottom(10F)
                add(label("plus score", style)).left().row()

                add(image(region = atlas.findRegion("candy7"))).center().padRight(20F).padBottom(10F)
                add(label("plus time", style)).left().row()

                add(image(region = atlas.findRegion("candy8"))).center().padRight(20F).padBottom(10F)
                add(label("plus x2 score ", style)).left().row()

                pack()
            }

            table(this) {
                add(label("bonuses: ", style)).center().colspan(2).pad(50F).row()

                add(image(region = atlas.findRegion("bonus_freeze"))).center().padRight(20F).padBottom(10F)
                add(label("freeze time", style)).left().row()

                add(image(region = atlas.findRegion("bonus_multiply"))).center().padRight(20F).padBottom(10F)
                add(label("score & time x2", style)).left().row()

                add(image(region = atlas.findRegion("bonus_gifts"))).center().padRight(20F).padBottom(10F)
                add(label("candy rain", style)).left().row()

                pack()
            }

            table(this) {
                add(label("anti-bonuses: ", style)).center().colspan(2).pad(50F).row()

                add(image(region = atlas.findRegion("antibonus_score"))).center().padRight(20F).padBottom(10F)
                add(label("minus score", style)).left().row()

                add(image(region = atlas.findRegion("antibonus_time"))).center().padRight(20F).padBottom(10F)
                add(label("minus time", style)).left().row()

                pack()
            }

            setFillParent(true)
        }

        image(root(), atlas.findRegion("exitButton")) {
            setPosition((Cfg.width - getWidth()) * 0.5F, Cfg.height * 0.1F)
            rippleClicked { Screens.set(MenuScreen(false)) }
            hoverEffect();
            setScale(0F)
            addAction(Actions.scaleTo(1F, 1F, 0.5F, Interpolation.swingOut))
        }
    }

    override fun onHardKeyPressed(keyCode: Int) = Screens.set(MenuScreen())
}