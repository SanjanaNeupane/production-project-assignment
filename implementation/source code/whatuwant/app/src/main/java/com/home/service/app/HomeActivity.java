package com.home.service.app;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.home.service.app.Prevalent.Prevalent;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button Electrician,Plumber,Driver,Carpenter,BabySitter,Painter,Tutor,Guard,AcElectrician,ComputerOperator,Dumper,InteriorDesigner,Mason,SoftwareInstaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Prevalent.currentOnlineUser.getPhone().equals("+911111111111"))
                {
                    Intent i=new Intent(HomeActivity.this,LoginActivity.class);
                    Toast.makeText(HomeActivity.this, "Login First to Send Email..", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent i=new Intent(HomeActivity.this,EmailActivity.class);
                    startActivity(i);
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);









        View headerView=navigationView.getHeaderView(0);
        TextView currentUser=headerView.findViewById(R.id.currentuser);

        currentUser.setText(Prevalent.currentOnlineUser.getName());



        Electrician=(Button)findViewById(R.id.btnelectrician);
        Plumber=(Button)findViewById(R.id.btnplumer);
        Driver=(Button)findViewById(R.id.btndriver);
        Carpenter=(Button)findViewById(R.id.btncarpenter);
        BabySitter=(Button)findViewById(R.id.btnbabysitter);
        Painter=(Button)findViewById(R.id.btnpainter);
        Tutor=(Button)findViewById(R.id.btntutor);
        Guard=(Button)findViewById(R.id.btnguard);
        AcElectrician=(Button)findViewById(R.id.btnAcElectrician);
        ComputerOperator=(Button)findViewById(R.id.btnComputeroperator);
        Dumper=(Button)findViewById(R.id.btndumper);
        InteriorDesigner=(Button)findViewById(R.id.btninteriordesigner);
        Mason=(Button)findViewById(R.id.btnmason);
        SoftwareInstaller=(Button)findViewById(R.id.btnsoftwareinstaller);

        Electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Electrician");
                startActivity(i);
            }
        });
        Plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Plumber");
                startActivity(i);

            }
        });
        Driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Driver");
                startActivity(i);
            }
        });
        Carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Carpenter");
                startActivity(i);
            }
        });
        BabySitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","BabySitter");
                startActivity(i);
            }
        });
        Painter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Painter");
                startActivity(i);
            }
        });

        Tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Tutor");
                startActivity(i);
            }
        });
        Guard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Guard");
                startActivity(i);
            }
        });
        AcElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","AcElectrician");
                startActivity(i);
            }
        });
        ComputerOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","ComputerOperator");
                startActivity(i);
            }
        });
        Dumper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Dumper");
                startActivity(i);
            }
        });
        InteriorDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","InteriorDesigner");
                startActivity(i);
            }
        });
        Mason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","Mason");
                startActivity(i);
            }
        });
        SoftwareInstaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MessageActivity.class);
                i.putExtra("RequestedWorker","SoftwareInstaller");
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(Prevalent.currentOnlineUser.getPhone().equals("+911111111111"))
            {
                super.onBackPressed();

            }
            else
            {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

       /* if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about)
        {
            Intent i=new Intent(HomeActivity.this,AboutActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {

            Intent i=new Intent(HomeActivity.this,welcomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        } else if (id == R.id.nav_standardcharge) {

            Intent i=new Intent(HomeActivity.this,StandardCharges.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
