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
package by.matveev.christmascandyfall.utils

import com.badlogic.gdx.math.MathUtils

public fun FloatRange.random(): Float {
    return MathUtils.random(start, end)
}

private val builder = StringBuilder()

public fun Int.asString(): CharSequence {
    builder.delete(0, builder.length())

    if (this < 0) {
        builder.append(0)
        return builder
    }

    val seconds = (this / 1000) % 60
    val minutes = ((this / (1000 * 60)) % 60)
    if (minutes < 10) {
        builder.append(0)
    }
    builder.append(minutes)
    builder.append(":")
    if (seconds < 10) {
        builder.append(0)
    }
    builder.append(seconds)
    return builder
}