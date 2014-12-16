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
package by.matveev.christmascandyfall.actors

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.Group

public class PagedScroll(parent: Group) : ScrollPane(null) {

    var content: Table
    {
        content = Table();
        content.defaults().space(10F)
        setWidget(content)
        parent.addActor(this)
    }

    var currentX: Float = 0F;
    var interact: Boolean = false;

    override fun act(delta: Float) {
        super.act(delta);
        if (interact && !isPanning() && !isDragging() && !isFlinging()) {
            interact = false;
            scrollToPage();
        } else {
            if (isPanning() || isDragging() || isFlinging()) {
                interact = true;
            }
        }
    }

    override fun addActor(actor: Actor) {
        with(content) {
            add(actor).expandY().fillY()
            invalidate()
        }
    }

    override fun setWidth(width: Float) {
        super.setWidth(width);

        with(content) {
            getCells().forEach { it.width(width) }
            invalidate()
        }
    }

    fun scrollToPage() {
        val width = getWidth()
        val scrollX = getScrollX()
        val maxX = getMaxX()

        if (scrollX >= maxX || scrollX <= 0) return

        val pages = content.getChildren()
        var pageX: Float = 0F
        var pageWidth: Float = 0F
        if (pages.size > 0) {
            var ix = 0
            for (a in pages) {
                ix++
                pageX = a.getX()
                pageWidth = a.getWidth()
                if (scrollX < (pageX.toDouble() + pageWidth.toDouble() * 0.5)) {
                    break
                }
            }
            setScrollX(MathUtils.clamp(pageX - (width - pageWidth) / 2F, 0F, maxX))
        }
    }
}
