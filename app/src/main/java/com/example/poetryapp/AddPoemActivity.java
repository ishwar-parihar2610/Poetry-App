package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.databinding.ActivityAddPoemBinding;
import com.example.poetryapp.response.poetryInsertResponse;
import com.example.poetryapp.response.updatePoetryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoemActivity extends AppCompatActivity {
    ActivityAddPoemBinding binding;
    Retrofit retrofit = ApiClient.getRetrofit();
    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_poem);
        if (getIntent().getStringExtra("Poetry_data") != null) {
            binding.poemEdit.setText(getIntent().getStringExtra("Poetry_data"));
            binding.poetEdit.setText(getIntent().getStringExtra("poet_name"));
            binding.addPoemBtn.setText("Update Poem");
        }


        binding.back.setOnClickListener(v -> {
            startActivity(new Intent(AddPoemActivity.this, MainActivity.class));

        });

        binding.addPoemBtn.setOnClickListener(v -> {
            if (getIntent().getStringExtra("id") != null) {
                String id=getIntent().getStringExtra("id");
                updatePoem(id, binding.poemEdit.getText().toString());
            } else {
                insertData(binding.poemEdit.getText().toString(), binding.poetEdit.getText().toString());
            }
        });


    }

    private void insertData(String poetry, String poetName) {
        apiInterface.insertPoetry(poetry, poetName).enqueue(new Callback<poetryInsertResponse>() {
            @Override
            public void onResponse(Call<poetryInsertResponse> call, Response<poetryInsertResponse> response) {
                if (response.body() != null) {
                    Toast.makeText(AddPoemActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPoemActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<poetryInsertResponse> call, Throwable t) {
                Log.d("AddItemActivity", "failed:" + t.getLocalizedMessage());
            }
        });
    }

    private void updatePoem(String id, String poem) {
        apiInterface.updatePoetry(poem, id).enqueue(new Callback<updatePoetryResponse>() {
            @Override
            public void onResponse(Call<updatePoetryResponse> call, Response<updatePoetryResponse> response) {
                if (response.body() != null) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPoemActivity.this,MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<updatePoetryResponse> call, Throwable t) {
                Log.d("AddPoemActivity.php", "Failed :" + t.getLocalizedMessage());
            }
        });
    }

}