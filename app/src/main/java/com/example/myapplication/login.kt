package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser

class login() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signupTextBtn = findViewById<TextView>(R.id.signupTextBtn);
        val email = findViewById<TextView>(R.id.email);
        val password = findViewById<TextView>(R.id.password);
        val loginBtn = findViewById<TextView>(R.id.loginBtn);

        loginBtn.setOnClickListener {
            val email1 = email.text.toString()
            val password1 = password.text.toString()

            if(email1.isNullOrEmpty() || password1.isNullOrEmpty())
            {
                Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show()
            }
            else
            {
                logi(email1,password1);
            }
        }

        signupTextBtn.setOnClickListener {
            val signup = Intent(this,signup::class.java);

            startActivity(signup)
        }


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<login> {
        override fun createFromParcel(parcel: Parcel): login {
            return login(parcel)
        }

        override fun newArray(size: Int): Array<login?> {
            return arrayOfNulls(size)
        }
    }

    private fun logi(email1: String, password1: String) {

        val auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email1,password1)
            .addOnCompleteListener(this){ task: Task<AuthResult> ->
                if(task.isSuccessful)
                {
                    val user: FirebaseUser? = auth.currentUser

                    val dash = Intent(this,dashboard::class.java)
                    startActivity(dash);

                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    // Authentication failed

                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        // Invalid email or password\
                        Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Other exceptions
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
}