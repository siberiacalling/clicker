package com.example.clicker.data.api;

import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    class UserPlain {
        public int id;
        public String username;
        public int score;

        public UserPlain(int id, String username, int score) {
            this.id = id;
            this.username = username;
            this.score = score;
        }
    }


    @GET("/user?limit=99&offset=0")
    Call<List<UserPlain>> getAll();

    @GET("/user/{id}")
    Call<UserPlain> get(@Path("id") int id);
}
