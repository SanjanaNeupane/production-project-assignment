package com.home.service.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class RequestedServiceActivity extends AppCompatActivity {

    ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list=new ArrayList<>();

    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_service);

        listView=(ListView)findViewById(R.id.listview);
        mref= FirebaseDatabase.getInstance().getReference();

        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        mref.child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set=new HashSet<String>();
                Iterator i=dataSnapshot.getChildren().iterator();
                while (i.hasNext())
                {
                    set.add((String) ((DataSnapshot)i.next()).getValue());
                }
                list.clear();
                list.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected=listView.getItemAtPosition(position).toString();
                String latitude = selected.split(":")[3].trim();
                String longitude = selected.split(":")[4].trim();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+latitude+","+longitude));
                startActivity(intent);
                //Toast.makeText(RequestedServiceActivity.this, latitude+" "+longitude, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
