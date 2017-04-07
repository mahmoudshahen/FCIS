package com.example.mahmoudshahen.fcis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String subject;
    List<Instructor> instructors;
    RecyclerView recyclerView;
    RecyclerViewTA recyclerViewAdapter;
    ChildEventListener listener;
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subject = getString(R.string.subject1);
        setTitle(getString(R.string.subject1));
        recyclerView = (RecyclerView)findViewById(R.id.rv_emails);

        instructors = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewTA(instructors, this);
        instructors = loadData(instructors);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(defaultItemAnimator);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd dialogAdd = new DialogAdd(MainActivity.this);
                dialogAdd.show();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    if (dy > 0)
                        fab.hide();
                    else if (dy < 0)
                        fab.show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Toast.makeText(this,"Oops..", Toast.LENGTH_LONG).show();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
    databaseReference.removeEventListener(listener);
        if (id == R.id.sub1) {
            // Handle the camera action
            fab.show();
            subject = getString(R.string.subject1);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject1));
        } else if (id == R.id.sub2) {
            fab.show();
            subject = getString(R.string.subject2);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject2));
        } else if (id == R.id.sub3) {
            fab.show();
            subject = getString(R.string.subject3);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject3));
        } else if (id == R.id.sub4) {
            fab.show();
            subject = getString(R.string.subject4);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject4));
        } else if (id == R.id.sub5) {
            fab.show();
            subject = getString(R.string.subject5);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject5));
        } else if (id == R.id.sub6) {
            fab.show();
            subject = getString(R.string.subject6);
            instructors = loadData(instructors);
            setTitle(getString(R.string.subject6));
        } else if(id == R.id.nav_feedback) {
            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.email)});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "feedback");
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public List<Instructor> loadData(final List<Instructor> ins) {

        instructors.clear();
        recyclerViewAdapter = new RecyclerViewTA(instructors, this);
        recyclerView.setAdapter(recyclerViewAdapter);
       // Log.v("bb", String.valueOf(instructors.size()));
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/FCIS/"+subject);

        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Instructor instructor = new Instructor();
                instructor.setName(dataSnapshot.child("name").getValue(String.class));
                instructor.setEmail(dataSnapshot.child("email").getValue(String.class));
                Log.v("dasd", "2");
                ins.add(instructor);
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(listener);
        return ins;
    }
}
