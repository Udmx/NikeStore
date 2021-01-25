package ir.udmx.nikestore.data.repo.source

import android.content.SharedPreferences
import io.reactivex.Single
import ir.udmx.nikestore.data.MessageResponse
import ir.udmx.nikestore.data.TokenContainer
import ir.udmx.nikestore.data.TokenResponse


const val ACCESS_TOKEN = "access_token"
const val REFRESH_TOKEN = "refresh_token"

class UserLocalDataSource(val sharedPreferences: SharedPreferences) : UserDataSource {
    override fun login(username: String, password: String): Single<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN, null),
            sharedPreferences.getString(REFRESH_TOKEN, null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, token)
            putString(REFRESH_TOKEN, refreshToken)
        }.apply()
    }
}