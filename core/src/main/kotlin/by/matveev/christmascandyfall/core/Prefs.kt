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

import com.badlogic.gdx.Preferences
import com.badlogic.gdx.Gdx

public class Prefs private() {

    class object {
        public val PREFS_KEY: String = "by.matveev.christmascandyfall.prefs";
        public val SOUNDS_KEY: String = "by.matveev.christmascandyfall.prefs.sounds";
        public val GAMES_COUNT_KEY: String = "by.matveev.christmascandyfall.prefs.gamesCount";

        private val prefs = Gdx.app.getPreferences(PREFS_KEY)

        public fun bool(key: String, fallback: Boolean = false): Boolean = prefs.getBoolean(key, fallback)
        public fun int(key: String, fallback: Int = 0): Int = prefs.getInteger(key, fallback)
        public fun long(key: String, fallback: Long = 0L): Long = prefs.getLong(key, fallback)
        public fun string(key: String, fallback: String? = null): String = prefs.getString(key, fallback)

        public fun toggle(key: String): Boolean {
            val value = !bool(key)
            set(key, value)
            return value
        }

        public fun set(key: String, value: Any) {
            with(prefs) {
                when (value) {
                    is Int -> putInteger(key, value)
                    is Boolean -> putBoolean(key, value)
                    is String -> putString(key, value)
                }
                prefs.flush()
            }
        }

    }
}