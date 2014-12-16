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
        val width: Float = 480F
        val height: Float = 800F

        val gravity = Vector2(0F, -10F)

        val initialTime: Int = 60 * 1000
        val bonusTime: Int = 7 * 1000

        val warningTimeThreshold: Int = 10 * 1000

        val frozenTimeDelay: Int = 5 * 1000

        val santaVelocity: Float = 100F
        val candyVelocity: FloatRange = 250F..450F
        val bonusVelocity: FloatRange = 35F..60F


    }
}

val pixelToMeterRatio = 50F

public fun Float.toMeters(): Float = this / pixelToMeterRatio
public fun Float.toPixels(): Float = this * pixelToMeterRatio

public fun Float.toDegrees(): Float {
    return MathUtils.radiansToDegrees * this
}