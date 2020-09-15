package com.mksherbini.cinema;

import com.mksherbini.cinema.MVCModel.TheMovieDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ITheMovieDBApi {

    @GET("discover/movie?api_key=cb3370d897cc690c5e91ece8dba9279e&language=en-US&sort_by=popularity.desc")
    Call<TheMovieDB> GetDefault();

    @GET("discover/movie?api_key={id}&language=en-US&sort_by=popularity.desc")
    Call<TheMovieDB> GetById(@Path("id") String API_ID);

}
