package com.home.service.app;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText Name,Password,Phone;
    private Button Register;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name=(EditText)findViewById(R.id.nameentry);
        Password=(EditText)findViewById(R.id.passwordentry);
        Phone=(EditText)findViewById(R.id.phoneentry);
        Register=(Button)findViewById(R.id.registerbutton);
        mref= FirebaseDatabase.getInstance().getReference();


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Name.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your name first..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Phone.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your Phone Number first..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your password first..", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!(dataSnapshot.child("Users").child(Phone.getText().toString()).exists()))
                            {
                                Intent i=new Intent(RegisterActivity.this,OtpActivity.class);
                                i.putExtra("Name",Name.getText().toString());
                                i.putExtra("Password",Password.getText().toString());
                                i.putExtra("Phone",Phone.getText().toString());
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Account with this number already exist..", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }
        });
    }
}
