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
import by.matveev.christmascandyfall.core.PlatformActions
import android.net.Uri
import by.matveev.christmascandyfall.android.utils.Achievements
import by.matveev.christmascandyfall.screens.GameState

public class GameServices(val activity: Activity) : GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, PlatformActions {

    val achievements: Achievements
    val client: GoogleApiClient
    {
        achievements = Achievements(activity, this)

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

    override fun openUrl(url: String) {
        val appPackageName = activity.getPackageName();
        try {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (e: Exception) {
            activity.startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    override fun submitScore(score: Long) {
        Games.Leaderboards.submitScore(client, activity.getString(R.string.leaderboard_id), score);
    }

    override fun unlockAchievement(identifier: String) {
        println("unlockAchievement = [${identifier}]")
        Games.Achievements.unlock(client, identifier);
    }

    public override fun showAchievements() {
        if (isSignedIn()) {
            activity.startActivityForResult(
                    Games.Achievements.getAchievementsIntent(client), requestCodeUnused)
        } else {
            client.connect()
        }
    }

    public override fun showLeaderBoard() {
        if (isSignedIn()) {
            activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                    client, activity.getString(R.string.leaderboard_id)), requestCodeUnused);
        } else {
            client.connect()
        }
    }

    public fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

    override fun checkForAchievements(state: GameState) {
        achievements.checkFor(state)
    }
}