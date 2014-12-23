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
package by.matveev.christmascandyfall.entities

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.math.Interpolation
import by.matveev.christmascandyfall.utils.*
import by.matveev.christmascandyfall.core.Assets
import by.matveev.christmascandyfall.Cfg
import com.badlogic.gdx.scenes.scene2d.utils.Align

val popupPool: Pool<PopUp> = Pool({ PopUp(style) }) { popup -> popup.reset() }

val style = labelStyle {
    font = Assets.get("fonts/font.fnt")
    fontColor = Color.WHITE
}

public fun showMessage(parent: Group, text: String) {
    val message = popupPool.obtain()
    message.setText(text)
    message.pack()
    message.setX((Cfg.width - message.getPrefWidth()) * 0.5f)
    message.setY((Cfg.height - message.getPrefHeight()) * 0.5f)
    message.setAlignment(Align.center)
    message.getColor().a = 0f;

    message.addAction(Actions.sequence(
            Actions.alpha(1F, 1f, Interpolation.bounceOut),
            Actions.alpha(0F, 1f, Interpolation.bounceIn),
            Actions.run { message.free() }))

    parent.addActor(message)
}

public fun showPopup(parent: Group, text: String, x: Float, y: Float) {
    val popup = popupPool.obtain()
    popup.setText(text);
    popup.setPosition(x, y);

    popup.addAction(Actions.sequence(
            Actions.parallel(Actions.moveBy(0F, 50F, 0.5F), Actions.alpha(0F, 0.5F)),
            Actions.run { popup.free() }))

    parent.addActor(popup)
}

public class PopUp(style: Label.LabelStyle) : Label(null, style) {

    public fun free() {
        remove()
        popupPool.free(this)
    }

    public fun reset() {
        setText(null)
        clearActions()
        clearListeners()
        getColor().a = 1F;
    }

}