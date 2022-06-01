package com.home.service.app;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.home.service.app.Model.Users;
import com.home.service.app.Prevalent.Prevalent;

public class LoginActivity extends AppCompatActivity {

    private EditText Phone,Password;
    private Button Login;
    private DatabaseReference mref;
    private ProgressDialog LoadingBar;
    String myphone,mypassword;
    TextView Newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Phone=(EditText)findViewById(R.id.loginphone);
        Password=(EditText)findViewById(R.id.loginpassword);
        Login=(Button)findViewById(R.id.loginbutton);
        Newuser=(TextView)findViewById(R.id.Newuser);

        LoadingBar=new ProgressDialog(this);
        mref= FirebaseDatabase.getInstance().getReference();
        mypassword=Password.getText().toString();
        myphone="+977"+Phone.getText().toString();


        Newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(Phone.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your phone number..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your password...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginUser();
                }
            }
        });
    }

    private void LoginUser() {



        LoadingBar.setTitle("Login Account");
        LoadingBar.setMessage("Please wait while we are checking our credentials..");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

        AllowAccessToUser(myphone,mypassword);
    }

    private void AllowAccessToUser(final String myphone, final String mypassword) {

        if(Phone.getText().toString().equals("9999999999")&&Password.getText().toString().equals("admin"))
        {
            LoadingBar.dismiss();
            Toast.makeText(LoginActivity.this, "Logged in Successfully..", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(LoginActivity.this,RequestedServiceActivity.class);
            // i.putExtra("Name",userdata.getName());
            startActivity(i);
        }
        else
        {
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.child("Users").child("+977"+Phone.getText().toString()).exists())
                    {
                        final Users userdata=dataSnapshot.child("Users").child("+977"+Phone.getText().toString()).getValue(Users.class);
                        if(userdata.getPhone().equals("+977"+Phone.getText().toString()))
                        {

                            if(userdata.getPassword().equals(Password.getText().toString()))
                            {

                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Logged in Successfully..", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.currentOnlineUser=userdata;
                                startActivity(i);


                            }
                            else
                            {
                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "please enter correct password..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "please create your account first with this number ..", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
