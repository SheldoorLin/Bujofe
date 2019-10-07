package com.sheldon.bujofe.login


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sheldon.bujofe.MainActivity
import com.sheldon.bujofe.R
import com.sheldon.bujofe.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private lateinit var binding: ActivityLoginBinding

    private val RC_SIGN_IN: Int = 1

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var googleSignInOptions: GoogleSignInOptions

    private lateinit var firebaseAuth: FirebaseAuth

    companion object{
        const val GOOGLE_LOGIN_FAIL = "Google sign in failed:("
    }

    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            startActivity(MainActivity.getLaunchIntent(this))

            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setupUI()

        configureGoogleSignIn()

    }

    private fun setupUI() {

        binding.btnLoginDesign.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {

        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun configureGoogleSignIn() {

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {

                    firebaseAuthWithGoogle(it)

                    UserManager.userName = it.displayName /*SharedPreferences*/
                    UserManager.userEmail = it.email
                    UserManager.userPhotoUrl = it.photoUrl.toString()
                }

            } catch (e: ApiException) {

                Toast.makeText(this, GOOGLE_LOGIN_FAIL, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {


                viewModel.serverUserIdChecker(firebaseAuth.uid.toString())

                UserManager.userId = firebaseAuth.uid.toString()/*SharedPreferences*/

                startActivity(MainActivity.getLaunchIntent(this))

            } else {

                Toast.makeText(this, GOOGLE_LOGIN_FAIL, Toast.LENGTH_LONG).show()
            }
        }
    }
}

