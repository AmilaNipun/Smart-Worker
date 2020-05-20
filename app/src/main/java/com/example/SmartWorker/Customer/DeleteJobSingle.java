package com.example.SmartWorker.Customer;

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

public class DeleteJobSingle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseReference ref;
    StorageReference sRef;
    FirebaseAuth mAuth;
    Button deleteJobBTN;
    ProgressBar progressBar;
    TextView jobBudget,jobCategory,jobDescription,JobDueDate,JobLocation,JobServiceType,jobTitle,JobType,contactname,contactnumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_job_single);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        deleteJobBTN = findViewById(R.id.JobDeleteButton);
        jobBudget = findViewById(R.id.jobBudgetView_delete);
        jobCategory = findViewById(R.id.textViewJobCategory_delete);
        jobDescription = findViewById(R.id.textViewJobsDescription_delete);
        JobDueDate = findViewById(R.id.textViewJobDueDate_delete);
        JobLocation = findViewById(R.id.textViewJobLocation_delete);
        JobServiceType = findViewById(R.id.textViewJobServiceType_delete);
        jobTitle = findViewById(R.id.jobTitleView_delete);
        JobType = findViewById(R.id.textViewJobType_delete);
        imageView = findViewById(R.id.deleteJobViewImage);
        progressBar = findViewById(R.id.prograssBarJobDelete);
        contactname = findViewById(R.id.textViewContactName_delete);
        contactnumber = findViewById(R.id.textViewContactNumber_delete);
        mAuth = FirebaseAuth.getInstance();


        //toolbar

        setSupportActionBar(toolbar);


        //navigation drawer menu

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        final String jobkey = getIntent().getStringExtra("JobKey");
        ref =FirebaseDatabase.getInstance().getReference().child("Add Jobs").child(jobkey);
        sRef = FirebaseStorage.getInstance().getReference().child("AddJobImages").child(jobkey+ "jpg");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String job_title = dataSnapshot.child("JobTile").getValue().toString();
                    String job_budget = dataSnapshot.child("JobBudget").getValue().toString();
                    String job_category = dataSnapshot.child("JobCategory").getValue().toString();
                    String description = dataSnapshot.child("JobDescription").getValue().toString();
                    String job_due_date = dataSnapshot.child("JobDueDate").getValue().toString();
                    String job_location = dataSnapshot.child("JobLocation").getValue().toString();
                    String job_service_type = dataSnapshot.child("JobServiceType").getValue().toString();
                    String job_type = dataSnapshot.child("JobType").getValue().toString();
                    String imageURL = dataSnapshot.child("ImageURL").getValue().toString();
                    String conname = dataSnapshot.child("ContactName").getValue().toString();
                    String connum = dataSnapshot.child("ContactNumber").getValue().toString();


                    Picasso.get().load(imageURL).into(imageView);
                    jobBudget.setText(job_budget);
                    jobCategory.setText(job_category);
                    jobDescription.setText(description);
                    JobDueDate.setText(job_due_date);
                    JobLocation.setText(job_location);
                    JobServiceType.setText(job_service_type);
                    jobTitle.setText(job_title);
                    JobType.setText(job_type);
                    contactname.setText(conname);
                    contactnumber.setText(connum);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deleteJobBTN.setOnClickListener(new View.OnClickListener() {
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
                                        startActivity(new Intent(DeleteJobSingle.this,Dashboard.class));
                                        Toast.makeText(DeleteJobSingle.this,"Job Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(DeleteJobSingle.this,"Error",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(DeleteJobSingle.this,"Error",Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(DeleteJobSingle.this,Dashboard.class));
                break;

            case R.id.nav_profile:
                String uid = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(DeleteJobSingle.this, UserProfile.class);
                intent.putExtra("UserID",uid);
                startActivity(intent);
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(this,"Successfully Logged Out!",Toast.LENGTH_SHORT).show();
                finish();
                break;

            case R.id.nav_share:
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
