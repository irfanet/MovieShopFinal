package com.example.irfan.firebasetrial2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by user-ASUS on 28/12/2017.
 */

public class BuyActiviy extends AppCompatActivity {


    private Button mBuy;
    private DatabaseReference mDatabase;

    private EditText mName;
    private EditText mEmail;
    private EditText mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity);

        mBuy = (Button) findViewById(R.id.buyBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("buyer");

        mName = (EditText) findViewById(R.id.etName);
        mEmail = (EditText) findViewById(R.id.etEmail);
        mAddress = (EditText) findViewById(R.id.etAddress);

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String address = mAddress.getText().toString().trim();

                HashMap<String, String> dataMap = new HashMap<String, String>();
                dataMap.put("Name", name);
                dataMap.put("Email", email);
                dataMap.put("Address", address);

                mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BuyActiviy.this,"Buy Success !",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(BuyActiviy.this,"Error ! Please Try Again !",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
}
