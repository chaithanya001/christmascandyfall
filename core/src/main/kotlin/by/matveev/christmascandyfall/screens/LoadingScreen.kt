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
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.g2d.ParticleEffect


public class LoadingScreen(val complete: () -> Unit) : AbstractScreen() {

    override fun show() {
        Assets.load("gfx/game.atlas", javaClass<TextureAtlas>())
        Assets.load("fonts/font.fnt", javaClass<BitmapFont>())
        Assets.load("sounds/music.ogg", javaClass<Music>())
        Assets.load("effects/stars.p", javaClass<ParticleEffect>())
        Assets.load("effects/snowflakes.p", javaClass<ParticleEffect>())
    }

    override fun render(delta: Float) {
        if (Assets.update()) complete()
    }
}