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

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.MathUtils

public class Cfg {

    class object {
        val width = 480F
        val height = 800F

        val gravity = Vector2(0F, -9.8F)
        val friction = 0.7f

        val acceleration = 2f
        val santaVelocityMin = -100f
        val santaVelocityMax = 100f

        val initialTime = 60 * 1000
        val bonusTime = 7 * 1000

        val warningTimeThreshold = 10 * 1000

        val frozenTimeDelay = 5f

        val scoreBonus = 1
        val doubleScoreBonus = 2 * scoreBonus
        val timeBonus = 1 * 1000

        val scoreMultiplier = 1.3f

        val scoreAntiBonus = 50
        val timeAntiBonus = 20 * 1000

        val vibrateDuration = 1000

        val candyVelocity = 250F..450F
        val bonusVelocity = 35F..60F
    }
}