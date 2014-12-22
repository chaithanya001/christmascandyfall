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

import android.content.res.Resources
import android.util.SparseArray

public inline fun <T> SparseArray<out T>.forEach(operation: (key: Int, value: T) -> Unit): Unit {
    var index = 0
    while (index < size()) {
        val key = keyAt(index)
        operation(key, valueAt(key))
        index++
    }
}

public fun Resources.sparseArray(resourceId: Int): SparseArray<String> {
    val array = getStringArray(resourceId)
    val result = SparseArray<String>(array.size())
    array.forEach { entry ->
        val pair = entry.split("\\|")
        result.put(pair[0].toInt(), pair[1])
    }
    return result;
}