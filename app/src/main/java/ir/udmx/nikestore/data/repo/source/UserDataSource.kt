package ir.udmx.nikestore.data.repo.source

import io.reactivex.Single
import ir.udmx.nikestore.data.MessageResponse
import ir.udmx.nikestore.data.TokenResponse

interface UserDataSource {

    fun login(username: String, password: String): Single<TokenResponse>
    fun signUp(username: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)

}