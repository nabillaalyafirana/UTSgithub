package com.example.githubuserapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubuserapp.Models.DetailResponse;
import com.example.githubuserapp.R;
import com.example.githubuserapp.Config.ApiConfig;
import com.example.githubuserapp.Config.ApiService;
import com.example.githubuserapp.Models.ItemsItem;
import com.example.githubuserapp.Models.GitHubResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String username = extras.getString("username");
            ApiService apiService = ApiConfig.getApiService();
            Call<DetailResponse> userCall = apiService.getDetailUser(username);

            TextView Nama = findViewById(R.id.tvNama);
            TextView Username = findViewById(R.id.tvUsername);
            TextView Bio = findViewById(R.id.tvBio);
            ImageView imageView = findViewById(R.id.ivProfil);

            showLoading(true);
            userCall.enqueue(new Callback<DetailResponse>() {
                @Override
                public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                    if (response.isSuccessful()){
                        showLoading(false);
                        DetailResponse user = response.body();
                        if (user != null){
                            String nama = " " + user.getName();
                            String username = "Username: " + user.getLogin();
                            String bio = "Bio: " + user.getBio();
                            String gambar = user.getAvatarUrl();

                            Nama.setText(nama);
                            Username.setText(username);
                            Bio.setText(bio);
                            Picasso.get().load(gambar).into(imageView);
                        }else {
                            Toast.makeText(DetailActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DetailResponse> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}