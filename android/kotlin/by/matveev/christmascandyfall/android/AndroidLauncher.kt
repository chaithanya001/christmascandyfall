package by.matveev.christmascandyfall.android

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import by.matveev.christmascandyfall.ChristmasCandyFall

public class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration()
        config.useWakelock = true
        config.useImmersiveMode = true
        initialize(ChristmasCandyFall(), config)
    }
}