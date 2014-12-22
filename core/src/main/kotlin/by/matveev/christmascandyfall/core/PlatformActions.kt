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

import by.matveev.christmascandyfall.screens.GameState

public object Platform {
    public var actions: PlatformActions? = null
}

public trait PlatformActions {
    public fun openMarketPage()

    public fun submitScore(score: Long)
    public fun unlockAchievement(identifier: String)
    public fun showAchievements()
    public fun showLeaderBoard()

    public fun checkForAchievements(state: GameState)
}