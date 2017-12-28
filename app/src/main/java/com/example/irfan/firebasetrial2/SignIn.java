package com.example.irfan.firebasetrial2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by user-ASUS on 25/12/2017.
 */
public class SignIn extends AppCompatActivity {
    Button signout,upload_bttn,showData,showBuyer;
    private FirebaseAuth mAuth;
    TextView username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        mAuth = FirebaseAuth.getInstance(); //important call
        signout = (Button)findViewById(R.id.signout);
        username = (TextView)findViewById(R.id.tvName);
        upload_bttn = (Button)findViewById(R.id.upload);
        showData = (Button)findViewById(R.id.show_data);
        showBuyer = (Button) findViewById(R.id.buyer_list);
        //cek login lagi
        if(mAuth.getCurrentUser() == null)
        {
            //if user not login
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        //fetch the display name of current user
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            username.setText("Welcome, "+ user.getDisplayName());
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        upload_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UploadInfo.class));
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ImageListActivity.class));
            }
        });

        showBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BuyerList.class));
            }
        });

    }
}
