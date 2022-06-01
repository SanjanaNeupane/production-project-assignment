package com.home.service.app;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {

    EditText Subject,Message;
    Button Send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);


        Subject=(EditText)findViewById(R.id.subject);
        Message=(EditText)findViewById(R.id.emailcontent);
        Send=(Button)findViewById(R.id.emailsend);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject=Subject.getText().toString().trim();
                String message=Message.getText().toString().trim();
                
                SendEmail(subject,message);
            }
        });
    }

    private void SendEmail(String subject, String message) {

        Intent mEmailIntent=new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mailto:"));
        mEmailIntent.setType("text/plain");
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"deeprajm77@gmail.com"});
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT,message);

        try {

            startActivity(Intent.createChooser(mEmailIntent,"Choose an Email Client.."));
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
