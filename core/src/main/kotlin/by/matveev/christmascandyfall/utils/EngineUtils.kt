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

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.Align
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.Input
import by.matveev.christmascandyfall.entities.Ripple

public fun verticalGroup(parent: Group? = null, init: VerticalGroup.() -> Unit): VerticalGroup {
    val group = VerticalGroup();
    parent?.addActor(group)
    group.init();
    return group;
}

public var VerticalGroup.space: Float
    get() = getSpace()
    set(space) {
        space(space)
    }

public var VerticalGroup.align: Int
    get() = getAlign()
    set(align) {
        align(align)
    }


public fun horizontalGroup(parent: Group? = null, init: HorizontalGroup.() -> Unit): HorizontalGroup {
    val group = HorizontalGroup();
    parent?.addActor(group)
    group.init();
    return group;
}

public var HorizontalGroup.space: Float
    get() = getSpace()
    set(space) {
        space(space)
    }

public var HorizontalGroup.align: Int
    get() = getAlign()
    set(align) {
        align(align)
    }


public fun label(parent: Group? = null, style: Label.LabelStyle? = null, align: Int? = Align.center, init: Label.() -> Unit): Label {
    val defaultStyle = style ?: labelStyle {
        font = BitmapFont();
        fontColor = Color.BLACK
    }
    val label = Label(null, defaultStyle)
    parent?.addActor(label)
    label.init()
    label.setAlignment(align!!, align)
    return label;
}

public fun label(text: CharSequence, style: Label.LabelStyle? = null, align: Int? = Align.center, parent: Group? = null): Label {
    val label = Label(text, style)
    parent?.addActor(label)
    label.setAlignment(align!!, align)
    return label;
}

public fun labelStyle(init: LabelStyle.() -> Unit): Label.LabelStyle {
    val style = Label.LabelStyle()
    style.init()
    return style
}

public var Label.text: CharSequence
    get() = getText()
    set(text) = setText(text)

public var Label.background: Drawable
    get() = getStyle().background
    set(background) {
        val style = getStyle()
        style.background = background
        setStyle(style)
    }

public var Label.font: BitmapFont
    get() = getStyle().font
    set(font) {
        val style = getStyle()
        style.font = font
        setStyle(style)
    }

public var Label.fontColor: Color
    get() = getStyle().fontColor
    set(fontColor) {
        val style = getStyle()
        style.fontColor = fontColor
        setStyle(style)
    }

public var Label.fontScaleX: Float
    get() = getFontScaleX()
    set(fontScaleX) = setFontScaleX(fontScaleX)

public var Label.fontScaleY: Float
    get() = getFontScaleY()
    set(fontScaleY) = setFontScaleX(fontScaleY)

public var Label.align: Int
    get() = 0
    set(align) = setAlignment(align, align)


public fun table(parent: Group? = null, init: Table.() -> Unit): Table {
    val table = Table()
    parent?.addActor(table)
    table.init()
    return table;
}

public fun image(parent: Group? = null, region: TextureRegion? = null, init: Image.() -> Unit): Image {
    val image = if (region != null) Image(region) else Image()
    parent?.addActor(image)
    image.init()
    return image;
}

public fun image(parent: Group? = null, region: TextureRegion? = null): Image {
    val image = if (region != null) Image(region) else Image()
    parent?.addActor(image)
    return image;
}

public fun Image.setRegion(region: TextureRegion) {
    setDrawable(TextureRegionDrawable(region))
}

public fun Image.hoverEffect() {
    originCenter()
    exit {
        clearActions()
        addAction(Actions.scaleTo(1F, 1F, 0.2F))
    }

    enter {
        clearActions()
        addAction(Actions.scaleTo(1.1F, 1.1F, 0.2F))
    }
}

public fun Actor.hasActions(): Boolean = this.getActions().size > 0

public fun Actor.centerInBounds(width: Float = 0F, height: Float = 0F) {
    var childWidth: Float = 0F;
    var childHeight: Float = 0F;

    if (this is Actor) {
        childWidth = getWidth()
        childHeight = getHeight()
    }

    if (this is Widget) {
        this.pack()
        childWidth = this.getPrefWidth()
        childHeight = this.getPrefWidth()
    }
    setPosition((width - childWidth) * 0.5F, (height - childHeight) * 0.5F)
}


public fun Actor.clicked(handler: () -> Unit) {
    private class Listener : ClickListener() {
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            handler()
        }
    }
    addListener(Listener())
}

public fun Actor.rippleClicked(handler: () -> Unit) {
    private class Listener : ClickListener() {
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            val position = Vector2(getX(), getY())
            localToStageCoordinates(position)

            getStage().addActor(Ripple(position.x + getWidth() * 0.5F, position.y * 0.5F) {
                handler()
            })
        }
    }
    addListener(Listener())
}


public fun Actor.exit(handler: () -> Unit) {
    private class Listener : ClickListener() {
        override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
            handler()
        }
    }
    addListener(Listener())
}

public fun Actor.enter(handler: () -> Unit) {
    private class Listener : ClickListener() {
        override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
            handler()
        }
    }
    addListener(Listener())
}

public fun Actor.alpha(value: Float): Actor {
    this.getColor().a = value
    return this
}

public fun Actor.hide(): Actor = alpha(0F)
public fun Actor.show(): Actor = alpha(1F)

public fun Stage.onKeyDown(handler: (keycode: Int) -> Unit) {
    val buttons: IntArray = intArray(Input.Keys.BACK, Input.Keys.MENU, Input.Keys.ESCAPE);
    private class Listener : ClickListener() {

        override fun keyDown(event: InputEvent?, keycode: Int): Boolean {
            if (buttons.contains(keycode)) handler(keycode)
            return super.keyDown(event, keycode)
        }
    }
    addListener(Listener())
}

public fun Widget.originCenter() {
    setOrigin(getPrefWidth() * 0.5F, getPrefHeight() * 0.5F)
}

public fun Stage.root(): Group {
    return getRoot()
}