package com.example.irfan.firebasetrial2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by user-ASUS on 28/12/2017.
 */

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password,name;
    private Button signin,signout,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        mAuth = FirebaseAuth.getInstance();

        signin = (Button)findViewById(R.id.signin);
        signout = (Button)findViewById(R.id.signout);
        signup = (Button)findViewById(R.id.signup);
        email = (EditText)findViewById(R.id.etEmail);
        name = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);

        //cek login belum
        if(mAuth.getCurrentUser() != null)
        {

            //user tidak login
            startActivity(new Intent(getApplicationContext(),SignIn.class));
        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();
                callsignin(getemail,getepassword);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getemail = email.getText().toString().trim();
                String getepassword = password.getText().toString().trim();
                callsignup(getemail,getepassword);

            }
        });
    }
    private void callsignup (String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Sign up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            userProfile();
                            Toast.makeText(SignInActivity.this,"Created Account", Toast.LENGTH_SHORT).show();
                            Log.d("TESTING", "Created Account");
                        }

                        // ...
                    }
                });
    }

    //Set dispaly user
    private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().
                    setDisplayName(name.getText().toString().trim())
                    //.setPhotoUrl(Uri.parse("https://example.com/xx.jpg"))
                    .build();

            user.updateProfile(profileUpdate)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("TESTING", "user profile update");
                            }
                        }
                    });
        }


    }

    private void callsignin(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent i = new Intent(SignInActivity.this,SignIn.class);
                            finish();
                            startActivity(i);
                        }

                        // ...
                    }
                });
    }
}

