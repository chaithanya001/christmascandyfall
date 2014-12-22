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
package by.matveev.christmascandyfall

import by.matveev.christmascandyfall.core.AbstractGame
import com.badlogic.gdx.Gdx
import by.matveev.christmascandyfall.core.Screens
import by.matveev.christmascandyfall.screens.LoadingScreen
import by.matveev.christmascandyfall.screens.MenuScreen
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.screens.RateScreen
import by.matveev.christmascandyfall.core.Prefs

public class ChristmasCandyFall : AbstractGame() {

    override fun create() {
        Gdx.input.setCatchBackKey(true)

        Screens.set(LoadingScreen {
            Screens.set(MenuScreen(true))

            if (shouldShowRateScreen())
                Screens.push(RateScreen())
        })
    }

    fun shouldShowRateScreen(): Boolean {
        if (Prefs.bool(Prefs.DONT_SHOW_RATE_KEY)) return false

        val launchCount = Prefs.long(Prefs.LAUNCHES_COUNT_KEY) + 1L
        Prefs.set(Prefs.LAUNCHES_COUNT_KEY, launchCount)

        var firstLaunchTime = Prefs.long(Prefs.FIRST_LAUNCH_TIME_KEY)
        if (firstLaunchTime == 0L) {
            firstLaunchTime = System.currentTimeMillis()
            Prefs.set(Prefs.FIRST_LAUNCH_TIME_KEY, firstLaunchTime)
        }

        if (launchCount >= Cfg.launchesUntilRatePrompt) {
            return System.currentTimeMillis() >= (firstLaunchTime + (Cfg.daysUntilRatePrompt * 24 * 60 * 60 * 1000))
        }

        return false
    }

    override fun dispose() {
        Screens.dispose()
        Assets.dispose()
    }
}