package com.example.clicker.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GameApi {

    class ScorePlain {
        public int score;

        public ScorePlain(int score) {
            this.score = score;
        }
    }

    @POST("/game")
    Call<ScorePlain> sync(@Body ScorePlain scorePlain);
}
