package com.example.ezybookstore;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApi {

    @GET("staging/book?nim=2201730236&nama=marcellino")
    Call<JsonObject> getObject();
}
