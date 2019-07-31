package com.example.findnerds.finderagain.SignIn

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.findnerds.finderagain.NewsData.news.FragTru
import com.example.findnerds.finderagain.R
import com.example.findnerds.finnder_2.ui.SignIn.Users
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_bar_header.*
import kotlinx.android.synthetic.main.bottom_drawer.*


class BottomDrawerFrag: BottomSheetDialogFragment(){

    val RC_Sign_IN = 1
    var mGoogleSignClient: GoogleApiClient? = null
    var mAuth: FirebaseAuth? = null
    var mAuthListner: FirebaseAuth.AuthStateListener? = null


    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.bottom_drawer,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        option()

        mAuth = FirebaseAuth.getInstance()
        mAuthListner = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                context!!.toast("User exist")
                updateUI(firebaseAuth.currentUser!!)
            }
        }
        signIn_btn.setOnClickListener {
            Toast.makeText(this.context, "Working", Toast.LENGTH_SHORT).show()
            signIn()
        }
        sign_Out.setOnClickListener{
            signOut()
        }
        taskBtn.setOnClickListener{
            context!!.toast("Workingngngn")
        }
            //make the button always in clicked state
            /*fragBtn.setOnTouchListener { v, event ->
                fragBtn.isPressed = true
                true
            }
*/
            Home_btn.setOnClickListener {
                val fragtry = FragTru.newInstance()
                val transaction = activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragtry)
                transaction.commit()
                Toast.makeText(context,"WotkingDD",Toast.LENGTH_SHORT).show()
            }
        }
    override fun onPause() {
        super.onPause()
        mGoogleSignClient!!.stopAutoManage(this.activity!!)
        // mGoogleSignClient!!.disconnect()
    }

    override fun onResume() {
        super.onResume()
        mGoogleSignClient!!.connect()
    }
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListner!!)
    }
    override fun onStop() {
        super.onStop()
        if (mAuthListner != null) mAuth!!.removeAuthStateListener(mAuthListner!!)
    }


    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        Auth.GoogleSignInApi.signOut(mGoogleSignClient)
        signIn_btn.visibility=View.VISIBLE
        userNameEmail.visibility=View.GONE

    }
    fun option() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignClient = GoogleApiClient.Builder(this.context!!).enableAutoManage(this.activity!!) {
            context!!.toast("Connection Failed")
        }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }
    fun Context.toast(message: CharSequence) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 600)
        toast.show()
    }

    fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignClient)
        startActivityForResult(signInIntent, RC_Sign_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_Sign_IN) {
            val task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                //Log.w(FragmentActivity.TAG, "Google sign in failed", e)
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this.activity!!) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this.activity, "LogIn Success", Toast.LENGTH_LONG).show()
                        // Sign in success, update UI with the signed-in user's information
                        // Log.d(FragmentActivity.TAG, "signInWithCredential:success")
                        val user = mAuth!!.currentUser

                        //startActivity(Intent(this,SignOut::class.java))
                        updateUI(user!!)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(FragmentActivity.TAG, "signInWithCredential:failure", task.exception)
                        // S.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                        // updateUI(null)
                    }

                    // ...
                }
    }

    private fun updateUI(user: FirebaseUser) {
        signIn_btn.visibility=View.GONE
        userNameEmail.visibility=View.VISIBLE
        val usr = Users(user.displayName!!, user.email!!,user.photoUrl.toString(),user.uid)
        profile_name.text = usr.username
        profile_email.text = usr.email
        Picasso.get().load(usr.photUrl).into(profile_img)
    }


}
