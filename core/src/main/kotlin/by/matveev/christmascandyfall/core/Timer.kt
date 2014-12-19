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

import java.util.concurrent.CopyOnWriteArrayList

/**Used to verify when some times passed, can be used to execute some task from time to time.*/
public class Timer(delay: Float, callback: () -> Unit, count: Int = -1) {

    class object {
        private val timers = CopyOnWriteArrayList<Timer>();

        public fun update(delta: Float): Unit = timers.forEach { it.tick(delta) }
        public fun paused(paused: Boolean): Unit = timers.forEach { it.paused = paused }
        public fun cancel(): Unit = timers.forEach { it.cancel() }
        public fun every(delay: Float, callback: () -> Unit): Timer = Timer(delay, callback)
        public fun times(delay: Float, count: Int, callback: () -> Unit): Timer = Timer(delay, callback, count)
    }

    {
        timers.add(this);
    }

    // optional on completing callback
    var complete : (() -> Unit)? = null

    val delay = delay;
    val count = count;
    val callback = callback;

    var time = delay;
    var executed = 0;
    var canceled = false;

    var paused: Boolean = false

    public fun tick(delta: Float) {
        if (paused) return

        if (canceled || executed == count) {
            timers.remove(this);
            if (complete != null) complete!!()
            return
        }

        time -= delta;
        if (time < 0) {
            time = delay;
            callback()

            if (count > 0)
                executed++;
        }
    }

    public fun cancel(): Unit {
        canceled = true;
    }

    public fun paused(paused: Boolean) {
        this.paused = paused
    }
}