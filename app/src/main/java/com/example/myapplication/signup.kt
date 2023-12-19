package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        val loginTextBtn = findViewById<TextView>(R.id.loginTextBtn);
        val email = findViewById<EditText>(R.id.email);
        val password = findViewById<EditText>(R.id.password);
        val conPassword = findViewById<EditText>(R.id.conPassword);
        val signUpBtn = findViewById<Button>(R.id.signUpBtn);

        signUpBtn.setOnClickListener{
            val email1 = email.text.toString();
            val password1 = password.text.toString();
            val conPassword1 = conPassword.text.toString();

            if(email1.isNotEmpty() && password1.isNotEmpty() && conPassword1.isNotEmpty())
            {
                if(password1 == conPassword1)
                {
                    regis(email1,password1,conPassword1);
                }
                else
                {
                    Toast.makeText(this,"Password does not matched",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Fields can not be empty",Toast.LENGTH_SHORT).show()
            }
        }


        loginTextBtn.setOnClickListener{
            val login = Intent(this,login::class.java);

            startActivity(login)
        }



    }

    private fun regis(email1: String, password1: String, conPassword1: String) {

        // Initialize Firebase Authentication
        val auth = FirebaseAuth.getInstance()

// Get user input (replace these with your actual input variables)
//        val email = email1f
//        val password = password1

// Authenticate with Firebase using email and password
        auth.createUserWithEmailAndPassword(email1, password1)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Authentication successful, get the user information
                    val user: FirebaseUser? = auth.currentUser

                    // Store additional user data in the Realtime Database
                    //storeUserData(user?.uid, fName1, lName1)

                    val dash = Intent(this,dashboard::class.java)
                    startActivity(dash);

                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                    // You can do further actions here after successful authentication
                } else {
                    // Authentication failed

                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        // Invalid email or password\
                        Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_SHORT).show()
                    } catch (e: FirebaseAuthUserCollisionException) {
                        // Email already exists
                        Toast.makeText(this,"Email already exists",Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Other exceptions
                        Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }

//    private fun storeUserData(uid: String?, fName: String, lName: String) {
//        // Check if the user's UID is not null before storing data
//        uid?.let {
//            // Create a reference to the "users" node in the Realtime Database
//            val usersReference = FirebaseDatabase.getInstance().getReference("users")
//
//            // Create a data class or map to represent user data
//            val userData = mapOf(
//                "fName" to fName,
//                "lName" to lName
//            )
//
//            // Store user data in the Realtime Database under the user's UID
//            usersReference.child(it).setValue(userData)
//        }
//    }
}