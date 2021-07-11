package com.example.longtest

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import java.util.*

class LongIn : AppCompatActivity() {

    // for fragment long_in

    private var etEmail:EditText?=null
    private var etPassword:EditText?=null
    private var btnRegister:Button?=null
    private var btnLongIn:Button?=null

    // for fragment register
    private var name: EditText?=null
    private var email: EditText?=null
    private var password: EditText?=null
    private var password2: EditText?=null
    private var btnSingIn: Button?=null
    // for manager fragments
    var manager: FragmentManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_in)

        // for long_in
        etEmail=findViewById(R.id.edEmail_long)
        etPassword=findViewById(R.id.etPassword_long)
        btnLongIn=findViewById(R.id.btnlongIn)
        btnRegister=findViewById(R.id.btnRegister)

        // for fragment register
        name = findViewById(R.id.etName_register)
        email = findViewById(R.id.edEmail_register)
        password = findViewById(R.id.edPassword_register)
        password2 = findViewById(R.id.edPassword2_register)
        btnSingIn = findViewById(R.id.btnSingIn)




        manager=this.supportFragmentManager

        manager!!.beginTransaction()
            .hide(Objects.requireNonNull(manager!!.findFragmentById(R.id.fragment_register))!!)
            .commit()


        btnLongIn?.setOnClickListener {
            val sEmail:String= etEmail?.text.toString().trim()
            val sPassword:String = etPassword?.text.toString().trim()

            if (sPassword.isEmpty().not().and(checkEmail(sEmail))){
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("email",sEmail)
                    putExtra("password",sEmail)
                }
                startActivity(intent)
                this.finish()
            }else{
                if (checkEmail(sEmail).not()){
                    etEmail?.error="Ehe mail not correctly"
                }
                if (sPassword.isEmpty()){
                    etPassword?.error="this item cannot be empty"
                }
                Toast.makeText(applicationContext, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister?.setOnClickListener {
            manager!!.beginTransaction()
                    .show(Objects.requireNonNull(manager!!.findFragmentById(R.id.fragment_register))!!)
                    .addToBackStack(null)
                    .commit()
        }

        btnSingIn?.setOnClickListener{
            val sName:String = name?.text.toString().trim()
            val sEmail:String =email?.text.toString().trim()
            val password:String =password?.text.toString().trim()
            val password2:String =password2?.text.toString().trim()
            if (checkInformation(sName,sEmail,password,password2)){
                manager!!.beginTransaction()
                        .hide(Objects.requireNonNull(manager!!.findFragmentById(R.id.fragment_register))!!)
                        .addToBackStack(null)
                        .commit()
                Toast.makeText(applicationContext, "Register successfully! ", Toast.LENGTH_SHORT).show()
               etEmail?.setText(sEmail)
                etPassword?.text = null
                etEmail?.error = null
                etPassword?.error = null

            }else{
                Toast.makeText(applicationContext, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkInformation(name: String,email: String,password: String,password2: String): Boolean {
        val even =BooleanArray(4)
        // name
        if (name.isEmpty()){
            this.name?.let {setError(it)}
            even[0]=false
        }else{
            even[0]=true
        }
        // email
        if (email.isEmpty()){
            this.email?.let {setError(it)}
            even[1]=false
        }else
            even[1]=true
        if ( (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            this.email?.error = "the mail is not correctly"
            even[1]=false
        }else
            even[1]=true
        // password_1
        if (password.isEmpty()){
            this.password?.let {setError(it)}
            even[2]=false
        }else
            even[2]=true
        // password_2
        if (password2.isEmpty()){
            this.password2?.let {setError(it)}
            even[3]=false
        }else
            even[3]=true
        if (password != password2){
            this.password2?.error = "this item cannot be empty"
            even[3]=false
        }else
            even[3]=true
        even.forEach {e->
            if (e.not())
                return e
        }
        return true
    }
    private fun checkEmail(mail:String):Boolean{
        return (TextUtils.isEmpty(mail) || Patterns.EMAIL_ADDRESS.matcher(mail).matches())
    }


    private fun setError(t:EditText){
        t.error = "this item cannot be empty"
    }
}