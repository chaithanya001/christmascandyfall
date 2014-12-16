package by.matveev.christmascandyfall.android

import com.badlogic.gdx.backends.android.AndroidApplication
import by.matveev.christmascandyfall.ChristmasCandyFall
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import android.os.Bundle

public class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration()
        config.useWakelock = true
        config.useImmersiveMode = true
        initialize(ChristmasCandyFall(), config)
    }
}