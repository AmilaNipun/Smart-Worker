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
import com.example.SmartWorker.Customer.UserProfile;
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

public class DeleteJobsSingleAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        setContentView(R.layout.activity_delete_jobs_single_admin);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        deleteJobBTN = findViewById(R.id.JobDeleteButton_admin);
        jobBudget = findViewById(R.id.jobBudgetView_delete_admin);
        jobCategory = findViewById(R.id.textViewJobCategory_delete_admin);
        jobDescription = findViewById(R.id.textViewJobsDescription_delete_admin);
        JobDueDate = findViewById(R.id.textViewJobDueDate_delete_admin);
        JobLocation = findViewById(R.id.textViewJobLocation_delete_admin);
        JobServiceType = findViewById(R.id.textViewJobServiceType_delete_admin);
        jobTitle = findViewById(R.id.jobTitleView_delete_admin);
        JobType = findViewById(R.id.textViewJobType_delete_admin);
        imageView = findViewById(R.id.deleteJobViewImage_admin);
        progressBar = findViewById(R.id.prograssBarJobDelete_admin);
        contactname = findViewById(R.id.textViewContactName_delete_admin);
        contactnumber = findViewById(R.id.textViewContactNumber_delete_admin);
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
        ref = FirebaseDatabase.getInstance().getReference().child("Add Jobs").child(jobkey);
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
                                        startActivity(new Intent(DeleteJobsSingleAdmin.this,AdminDashboard.class));
                                        Toast.makeText(DeleteJobsSingleAdmin.this,"Job Deleted Successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(DeleteJobsSingleAdmin.this,"Error",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(DeleteJobsSingleAdmin.this,"Error",Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(DeleteJobsSingleAdmin.this,AdminDashboard.class));
                break;

            case R.id.nav_profile:
                String uid = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(DeleteJobsSingleAdmin.this, AdminUserProfile.class);
                intent.putExtra("UserID",uid);
                startActivity(intent);
                break;
            case R.id.nav_adduser:
                startActivity(new Intent(DeleteJobsSingleAdmin.this,RegisterAdmin.class));
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
