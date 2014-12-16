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

import com.badlogic.gdx.Screen
import com.badlogic.gdx.Gdx
import java.util.Stack

public class Screens private() {

    class object {

        val stack = Stack<AbstractScreen>()

        public fun set(screen: AbstractScreen) {
            while (stack.isNotEmpty()) {
                val old = stack.pop()
                old.hide()
                old.dispose()
            }
            push(screen)
        }

        public fun replace(screen: AbstractScreen) {
            if (stack.isNotEmpty()) {
                val oldScreen = stack.pop()
                oldScreen.hide()
                oldScreen.dispose()

                stack.add(0, screen)
                show(screen)
            }
        }

        public fun push(screen: AbstractScreen) {
            if (stack.isNotEmpty()) {
                val old = stack.peek()
                old.hide()
            }
            stack.push(screen)

            show(screen)
        }

        public fun pop() {
            if (stack.isNotEmpty()) {
                val oldScreen = stack.pop()
                oldScreen.hide()
                oldScreen.dispose()

                if (stack.isNotEmpty()) {
                    show(stack.peek())
                }
            }
        }

        public fun dispose() {
            while (stack.isNotEmpty()) {
                stack.pop().dispose()
            }
        }

        public fun current(): Screen =
                if (stack.isNotEmpty()) stack.peek()
                else throw IllegalStateException("screens stack is empty")

        private fun show(screen: AbstractScreen) {
            screen.show()
            screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())
        }
    }
}