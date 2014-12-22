/*
 * Copyright (C) 2014 Alexey Matveev
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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
package by.matveev.christmascandyfall.android.utils

import android.content.Context
import by.matveev.christmascandyfall.android.GameServices
import android.util.SparseArray
import by.matveev.christmascandyfall.android.R
import by.matveev.christmascandyfall.screens.GameState
import by.matveev.christmascandyfall.core.Prefs
import android.content.res.Resources

public class Achievements(context: Context, val services: GameServices) {

    val gameRelated: Map<Int, String>
    val scoreRelated: Map<Int, String>
    val resources: Resources
    {
        resources = context.getResources()
        gameRelated = resources.intKeyMap(R.array.game_achievements_ids)
        scoreRelated = resources.intKeyMap(R.array.score_achievements_ids)
    }

    public fun checkFor(state: GameState) {
        if (services.isSignedIn()) {

            services.submitScore(state.score.toLong())

            val gamesPlayed = Prefs.int(Prefs.GAMES_COUNT_KEY)
            for ((gamesCount, identifier) in gameRelated) {
                if (gamesPlayed >= gamesCount) services.unlockAchievement(identifier)
            }

            for ((score, identifier) in scoreRelated) {
                if (state.score >= score) services.unlockAchievement(identifier)
            }

            if (state.score < resources.getInteger(R.integer.minimum_score)) {
                services.unlockAchievement(resources.getString(R.string.ach_are_you_even_trying_id))
            }
        }
    }
}