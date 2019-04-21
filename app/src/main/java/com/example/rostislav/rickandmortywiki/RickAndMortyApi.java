package com.example.rostislav.rickandmortywiki;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RickAndMortyApi {

    @GET("/api/character/")
    Call<MainPojo> getCharacters();

}
