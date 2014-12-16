package by.matveev.christmascandyfall.android

import com.badlogic.gdx.backends.android.AndroidApplication
import by.matveev.christmascandyfall.ChristmasCandyFall
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.ConnectionResult

public class AndroidLauncher : AndroidApplication() {

    var service: GameServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AndroidApplication>.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration()
        config.useWakelock = true
        config.useImmersiveMode = true
        initialize(ChristmasCandyFall(), config)

        service = GameServices(this)
    }

    override fun onStart() {
        super<AndroidApplication>.onStart()
        service?.start()
    }

    override fun onStop() {
        super<AndroidApplication>.onStop()
        service?.stop()
    }
}