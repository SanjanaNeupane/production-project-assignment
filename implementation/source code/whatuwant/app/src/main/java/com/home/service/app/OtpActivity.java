package com.home.service.app;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    EditText OtpEntry;
    Button Submit;
    private ProgressDialog LoadingBar;
    String Phone2,Phone,Name,Password,VerificationId;
    private FirebaseAuth mAuth;
    private DatabaseReference mref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Phone2=getIntent().getStringExtra("Phone");
        Password=getIntent().getStringExtra("Password");
        Name=getIntent().getStringExtra("Name");
        Phone="+977"+Phone2;

        mAuth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();

        OtpEntry=(EditText)findViewById(R.id.otpentry);
        Submit=(Button)findViewById(R.id.otpbutton);
        LoadingBar=new ProgressDialog(this);
        sendVerificationCode(Phone);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code=OtpEntry.getText().toString().trim();
                if(code.isEmpty() || code.length() <6)
                {
                    OtpEntry.setError("Enter Code..");
                    OtpEntry.requestFocus();
                    return;
                }
                LoadingBar.setTitle("Please Wait..");
                LoadingBar.setMessage("Please Wait while we are checking our credentials...");
                LoadingBar.show();
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,code);
        //SignInWithCredential(credential);
        CreateAccount(Phone,Name,Password);
    }

    private void CreateAccount(String phone, String name, String password)
    {


        Map<String,Object> details=new HashMap<String,Object>();
        details.put("Phone",Phone);
        details.put("Name",Name);
        details.put("Password",Password);
        details.put("Address","");
        mref.child("Users").child(Phone).updateChildren(details)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            LoadingBar.dismiss();
                            Intent i=new Intent(OtpActivity.this,LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*private void SignInWithCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Intent i=new Intent(OtpActivity.this,LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(OtpActivity.this, credential.toString(), Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

    private void sendVerificationCode(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            VerificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code != null)
            {
                LoadingBar.setTitle("Please Wait..");
                LoadingBar.setMessage("Please Wait while we are checking our credentials...");
                LoadingBar.show();
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
