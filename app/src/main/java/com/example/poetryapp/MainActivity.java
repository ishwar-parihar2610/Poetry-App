package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.adapter.PoetryListAdapter;
import com.example.poetryapp.databinding.ActivityMainBinding;
import com.example.poetryapp.model.PoetryModel;
import com.example.poetryapp.response.getResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    PoetryListAdapter adapter;
    List<PoetryModel> poetryModelArrayList = new ArrayList<>();
    String TAG="MAinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        Retrofit retrofit = ApiClient.getRetrofit();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        binding.addPoemBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,AddPoemActivity.class));
        });


        apiInterface.getPoetry().enqueue(new Callback<getResponse>() {
            @Override
            public void onResponse(Call<getResponse> call, Response<getResponse> response) {
            try {
                if (response!=null){
                    if (response.body().getStatus().equals("1")){
                        poetryModelArrayList=response.body().getData();
                    }
                    adapter = new PoetryListAdapter(poetryModelArrayList,getApplicationContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.poemRecycleView.setLayoutManager(linearLayoutManager);
                    binding.poemRecycleView.setAdapter(adapter);

                }else {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception: "+e.getLocalizedMessage());
            }
            }

            @Override
            public void onFailure(Call<getResponse> call, Throwable t) {
                Log.e(TAG, "failure: "+t.getLocalizedMessage());
            }
        });

    }
}