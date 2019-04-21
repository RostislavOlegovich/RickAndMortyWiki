package com.example.rostislav.rickandmortywiki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private boolean isCheked = true;
    private TextView characterName, mNoConnection;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFrameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        mNoConnection = (TextView) findViewById(R.id.no_network);
        characterName = (TextView)findViewById(R.id.nameCharacter);


        characterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheked) {
                    characterName.setBackgroundResource(R.drawable.text_off);
                    isCheked = false;
                } else {
                    characterName.setBackgroundResource(R.drawable.text_on);
                    isCheked = true;
                }
                networkRequest();
                }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        getConnectionInfo();


    }


    public void networkRequest(){

        NetworkService networkService = NetworkService.getInstance();

            networkService.getJSONApi()
                    .getCharacters()
                    .enqueue(new Callback<MainPojo>() {
                        @Override
                        public void onResponse(@NonNull Call<MainPojo> call,
                                               @NonNull Response<MainPojo> response) {
                            if (response.isSuccessful()) {
                                MainPojo mainPojo = response.body();
                                List<Result> list = mainPojo.getResults();
                                if(isCheked) {
                                    Collections.sort(list, Result.BY_NAME);
                                }
                                setupRecyclerView(mainPojo,list);
                            }

                        }

                        @Override
                        public void onFailure(Call<MainPojo> call, Throwable t) {

                            t.printStackTrace();

                        }
                    });

    }



    public void setupRecyclerView(MainPojo mainPojo,List<Result> results){

        mMyAdapter = new MyAdapter(this,mainPojo,results);
        mRecyclerView.setAdapter(mMyAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            getConnectionInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getConnectionInfo(){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mNoConnection.setVisibility(View.INVISIBLE);
            networkRequest();
            mFrameLayout.setVisibility(View.VISIBLE);

        }else {
            mFrameLayout.setVisibility(View.INVISIBLE);
            mNoConnection.setVisibility(View.VISIBLE);

        }

    }
}
