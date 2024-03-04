package com.example.login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    EditText edtFirstName,edtLastName,edtAddress,edtEmail,edtPassWord;
    Button btnRegister;
    private FirebaseAuth mAuth;
// ...
// Initialize Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtFirstName=findViewById(R.id.edtFirstName);
        edtLastName=findViewById(R.id.edtLastName);
        edtAddress=findViewById(R.id.edtAddress);
        edtEmail=findViewById(R.id.edtEmail);
        btnRegister=findViewById(R.id.btnRegister);
        edtPassWord=findViewById(R.id.edtPassWord);
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckDataEntered();
            }
        });
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    void CheckDataEntered(){
        boolean successed=true;
        if(isEmpty(edtFirstName)){
            Toast.makeText(this, "You must enter first name to register!", Toast.LENGTH_SHORT).show();
            successed=false;
        }
        if(isEmpty(edtLastName)){
            edtLastName.setError("Last name is required!");
            successed=false;

        }
        if(isEmpty(edtPassWord)){
            edtLastName.setError("PassWord is required!");
            successed=false;

        }
        if(isEmail(edtEmail)==false){
            edtEmail.setError("Enter valid Email!");
            successed=false;
        }
        if(successed==true){

            mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassWord.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(RegisterActivity.this, "Authentication successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent it =new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(it);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



        }
    }
}