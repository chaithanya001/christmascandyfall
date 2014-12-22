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

    val gameRelated: SparseArray<String>
    val scoreRelated: SparseArray<String>
    val resources: Resources
    {
        resources = context.getResources()
        gameRelated = resources.sparseArray(R.array.game_achievements_ids)
        scoreRelated = resources.sparseArray(R.array.score_achievements_ids)
    }

    public fun checkFor(state: GameState) {
        println("checkFor()")
        println("services.isSignedIn() -> ${services.isSignedIn()}")
        if (services.isSignedIn()) {

            services.submitScore(state.score.toLong())

            val gameAchievementId = gameRelated.get(Prefs.int(Prefs.GAMES_COUNT_KEY))
            if (gameAchievementId.isNotEmpty()) {
                services.unlockAchievement(gameAchievementId)
            }

            println("score -> ${state.score}")
            if (state.score < resources.getInteger(R.integer.minimum_score)) {
                println("unlock achivement, are you even trying!")
                services.unlockAchievement(resources.getString(R.string.ach_are_you_even_trying_id))
            }

            val scoreAchievementId = scoreRelated.get(state.score)
            if (scoreAchievementId.isNotEmpty()) {
                services.unlockAchievement(scoreAchievementId)
            }
        }

    }
}