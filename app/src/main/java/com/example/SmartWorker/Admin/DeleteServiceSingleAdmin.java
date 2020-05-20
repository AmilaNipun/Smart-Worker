package com.example.SmartWorker.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmartWorker.Customer.Dashboard;
import com.example.SmartWorker.Customer.DeleteJobSingle;
import com.example.SmartWorker.LoginRegister.Login;
import com.example.SmartWorker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DeleteServiceSingleAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference ref;
    StorageReference sRef;
    FirebaseAuth mAuth;
    Button deleteServiceBTN;
    ProgressBar progressBar;
    TextView serviceTitle,serviceLocation,serviceDescription,serviceContact;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service_single_admin);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        deleteServiceBTN = findViewById(R.id.serviceDeleteButton);
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.deleteServiceImage);
        progressBar = findViewById(R.id.prograssBarServiceDelete);
        serviceTitle = findViewById(R.id.serviceTitle_delete);
        serviceLocation = findViewById(R.id.servicelocation_delete);
        serviceDescription = findViewById(R.id.serviceDescription_delete);
        serviceContact = findViewById(R.id.serviceContact_delete);


        //toolbar

        setSupportActionBar(toolbar);


        //navigation drawer menu

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final String serviceKey = getIntent().getStringExtra("ServiceKey");
        ref = FirebaseDatabase.getInstance().getReference().child("Add Services").child(serviceKey);
        sRef = FirebaseStorage.getInstance().getReference().child("AddPromotionImages").child(serviceKey+ "jpg");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String titleS = dataSnapshot.child("ServiceTopic").getValue().toString();
                    String location = dataSnapshot.child("ServiceLocation").getValue().toString();
                    String description = dataSnapshot.child("ServiceDescription").getValue().toString();
                    String contact = dataSnapshot.child("ServiceContact").getValue().toString();
                    String imageurl = dataSnapshot.child("ImageURL").getValue().toString();

                    Picasso.get().load(imageurl).into(imageView);
                    serviceTitle.setText(titleS);
                    serviceContact.setText(contact);
                    serviceDescription.setText(description);
                    serviceLocation.setText(location);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deleteServiceBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            sRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(DeleteServiceSingleAdmin.this, AdminDashboard.class));
                                        Toast.makeText(DeleteServiceSingleAdmin.this,"Service Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(DeleteServiceSingleAdmin.this,"Error",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(DeleteServiceSingleAdmin.this,"Error",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(DeleteServiceSingleAdmin.this,AdminDashboard.class));
                break;

            case R.id.nav_profile:
                String uid = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(DeleteServiceSingleAdmin.this, AdminUserProfile.class);
                intent.putExtra("UserID",uid);
                startActivity(intent);
                break;
            case R.id.nav_adduser:
                startActivity(new Intent(DeleteServiceSingleAdmin.this,RegisterAdmin.class));
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(this,"Successfully Logged Out!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
