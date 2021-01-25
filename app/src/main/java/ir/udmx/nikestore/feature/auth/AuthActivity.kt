package ir.udmx.nikestore.feature.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.udmx.nikestore.R
import ir.udmx.nikestore.common.NikeActivity

class AuthActivity : NikeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, LoginFragment())
        }.commit()
    }
}