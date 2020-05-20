package com.example.SmartWorker.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.SmartWorker.LoginRegister.Login;
import com.example.SmartWorker.Model.MyJobsHolder;
import com.example.SmartWorker.Model.MyJobsModel;
import com.example.SmartWorker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyJobs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    FirebaseRecyclerOptions<MyJobsModel> options;
    FirebaseRecyclerAdapter<MyJobsModel, MyJobsHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jobs);

        drawerLayout = findViewById(R.id.drawer_layout_admin);
        navigationView = findViewById(R.id.nav_view_admin);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewMyView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("My Jobs").child(uid);


        //Image Slider
        ImageSlider imageSlider = findViewById(R.id.slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.benzcar1,"Smart Worker"));
        slideModels.add(new SlideModel(R.drawable.benzcar2,"Smart Worker"));
        slideModels.add(new SlideModel(R.drawable.benzcar3,"Smart Worker"));

        imageSlider.setImageList(slideModels,true);

        //toolbar

        setSupportActionBar(toolbar);


        //navigation drawer menu

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        LoadData();

    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<MyJobsModel>().setQuery(ref,MyJobsModel.class).build();
        adapter = new FirebaseRecyclerAdapter<MyJobsModel, MyJobsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyJobsHolder holder, final int position, @NonNull MyJobsModel model) {
                holder.MjTitle.setText(model.getTitle());
                holder.MJobCategory.setText(model.getCategory());
                holder.MContactnum.setText(model.getContactNumber());
                holder.MJobBudget.setText(model.getBudget());
                holder.MJobLocation.setText(model.getJobLocation());
                holder.MJobDueDate.setText(model.getDueDate());
                Picasso.get().load(model.getImageUrl()).into(holder.MimageView);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyJobs.this,ViewJobsExpand.class);
                        intent.putExtra("JobKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public MyJobsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_myjobs,parent,false);
                return new MyJobsHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
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
                startActivity(new Intent(MyJobs.this,Dashboard.class));
                break;

            case R.id.nav_profile:
                String uid = mAuth.getCurrentUser().getUid();
                Intent intent = new Intent(MyJobs.this, UserProfile.class);
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
