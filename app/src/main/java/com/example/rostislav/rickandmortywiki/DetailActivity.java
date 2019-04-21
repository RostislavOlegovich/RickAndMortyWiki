package com.example.rostislav.rickandmortywiki;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageDetail;
    private TextView nameDetail, speciesDetail,
            genderDetail,statusDetail,locationDetail, createdDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        imageDetail = (ImageView)findViewById(R.id.image_detail);
        nameDetail = (TextView) findViewById(R.id.name_detail);
        speciesDetail = (TextView) findViewById(R.id.species_detail);
        genderDetail = (TextView) findViewById(R.id.gender_detail);
        statusDetail = (TextView) findViewById(R.id.status_detail);
        locationDetail = (TextView) findViewById(R.id.location_detail);
        createdDetail = (TextView) findViewById(R.id.created_date);

        nameDetail.setText(getIntent().getStringExtra("name"));
        speciesDetail.setText(getIntent().getStringExtra("species"));
        genderDetail.setText(getIntent().getStringExtra("gender"));
        statusDetail.setText(getIntent().getStringExtra("status"));
        locationDetail.setText(getIntent().getStringExtra("location"));
        createdDetail.setText(getIntent().getStringExtra("created"));


        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .asBitmap()
                .into(imageDetail);


    }
}
