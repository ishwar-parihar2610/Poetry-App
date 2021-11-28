package com.example.poetryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.AddPoemActivity;
import com.example.poetryapp.Api.ApiClient;
import com.example.poetryapp.Api.ApiInterface;
import com.example.poetryapp.R;
import com.example.poetryapp.databinding.PoemRecycleViewBinding;
import com.example.poetryapp.model.PoetryModel;
import com.example.poetryapp.response.deleteResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryListAdapter extends RecyclerView.Adapter<PoetryListAdapter.viewHolder> {
    List<PoetryModel> poetryModelArrayList;
    LayoutInflater inflater;
    ApiInterface apiInterface;
    Context context;

    public PoetryListAdapter(List<PoetryModel> poetryModelArrayList,Context context) {
        this.poetryModelArrayList = poetryModelArrayList;
        Retrofit retrofit = ApiClient.getRetrofit();
        apiInterface = retrofit.create(ApiInterface.class);
        this.context=context;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new viewHolder(DataBindingUtil.inflate(inflater, R.layout.poem_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PoetryModel poetryModel = poetryModelArrayList.get(position);
        holder.binding.poetNameTextview.setText(poetryModel.getPoet_name());
        holder.binding.poemTv.setText(poetryModel.getPoetry_data());
        holder.binding.dateTextview.setText(poetryModel.getDate_time());

        holder.binding.deletePoem.setOnClickListener(v -> {
            deletePoetry(poetryModel.getId() + "",position);
        });

       holder.binding.editPoem.setOnClickListener(v -> {
           Intent intent=new Intent(context, AddPoemActivity.class);
           intent.putExtra("Poetry_data",poetryModel.getPoetry_data());
           intent.putExtra("poet_name",poetryModel.getPoet_name());
           String id= String.valueOf(poetryModel.getId());
           intent.putExtra("id",id);
           context.startActivity(intent);
       });
    }

    @Override
    public int getItemCount() {
        return poetryModelArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        PoemRecycleViewBinding binding;

        public viewHolder(PoemRecycleViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deletePoetry(String id,int positionId) {
        apiInterface.deletePoetry(id).enqueue(new Callback<deleteResponse>() {
            @Override
            public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                try {
                    if (response != null) {
                        Toast.makeText(inflater.getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if (response.body().getStatus().equals("1")){
                        poetryModelArrayList.remove(positionId);
                        notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    Log.e("exp", "Exception : " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<deleteResponse> call, Throwable t) {
                Log.d("PoetryAdapter.java", "Fail :" + t.getLocalizedMessage());
            }
        });
    }
}
