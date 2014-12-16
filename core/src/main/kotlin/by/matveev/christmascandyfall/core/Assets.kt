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

import com.badlogic.gdx.assets.AssetManager

public class Assets {

    class object {
        private val manager = AssetManager()

        fun <T> load(path: String, type: Class<T>): Unit = manager.load(path, type)

        fun <T> get(path: String): T =
                if (manager.isLoaded(path)) manager.get<T>(path) else throw IllegalStateException()

        fun update(): Boolean = manager.update();

        fun dispose(): Unit = manager.dispose()
    }

}