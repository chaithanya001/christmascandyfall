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
package by.matveev.christmascandyfall.core

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx

public abstract class AbstractGame : ApplicationListener {

    override fun pause(): Unit = Screens.current().pause()
    override fun resume(): Unit = Screens.current().resume()
    override fun render(): Unit = Screens.current().render(Gdx.graphics.getDeltaTime())
    override fun resize(width: Int, height: Int): Unit = Screens.current().resize(width, height)
}