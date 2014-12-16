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
package by.matveev.christmascandyfall.android

import com.google.android.gms.common.api.GoogleApiClient
import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.games.Games
import com.google.android.gms.plus.Plus
import android.content.Intent
import android.app.Activity
import android.content.IntentSender
import com.google.android.gms.common.GooglePlayServicesUtil

public class GameServices(val activity: Activity) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    val client: GoogleApiClient
    {
        client = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build()
    }

    val requestCodeUnused = 5001
    val requestCodeSignIn = 9001

    val requestResultOk = -1

    var isResolvingConnectionFailure = false

    public fun start() {
        client.connect()
    }

    public fun stop() {
        if (client.isConnected())
            client.disconnect()
    }

    fun isSignedIn(): Boolean = client.isConnected()

    public fun showAchievements() {
        if (isSignedIn()) {
            activity.startActivityForResult(
                    Games.Achievements.getAchievementsIntent(client), requestCodeUnused)
        } else {
            client.connect()
        }
    }

    public fun showLeaderboards() {
        if (isSignedIn()) {
            activity.startActivityForResult(
                    Games.Leaderboards.getAllLeaderboardsIntent(client), requestCodeUnused);
        } else {
            client.connect()
        }
    }

    public fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == requestCodeSignIn) {
            isResolvingConnectionFailure = false
            if (resultCode == requestResultOk) {
                client.connect()
            } else {
                // should show some notification...
            }
        }
    }

    override fun onConnected(bundle: Bundle?) {
        // do nothing
    }

    override fun onConnectionSuspended(p0: Int) {
        client.connect()
    }

    override fun onConnectionFailed(result: ConnectionResult?) {
        if (!isResolvingConnectionFailure) {
            if (result!!.hasResolution()) {
                try {
                    result.startResolutionForResult(activity, requestCodeSignIn);
                    isResolvingConnectionFailure = true
                } catch (e: IntentSender.SendIntentException) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    client.connect()
                }
            } else {
                GooglePlayServicesUtil.getErrorDialog(
                        result.getErrorCode(), activity, requestCodeSignIn)?.show()
            }
        }
    }
}