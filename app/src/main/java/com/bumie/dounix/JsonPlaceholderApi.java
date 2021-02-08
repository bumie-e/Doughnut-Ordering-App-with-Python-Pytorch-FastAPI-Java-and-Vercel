package com.bumie.dounix;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceholderApi {

    // All product categories
    @GET("doughnut")
    Call<List<Model>> getDoughnuts();

    // All product categories
    @GET("predict/")
    Call<List<MessageModel>> getPrediction();

    @GET("doughnut/categories")
    Call<List<Model>> getDoughnutCategories();

    @GET("doughnut/Speciality")
    Call<List<Model>> getSpecialityDoughnut();

    @POST("wp-json/wc/store/cart")
    @FormUrlEncoded
    Call<Model> saveDoughnut(@Body Model model);

    @POST("doughnut")
    @FormUrlEncoded
    Call<Model> addDoughnut(@Field("name") String name,
                         @Field("image") String image,
                         @Field("description") String description,
                         @Field("price") String price,
                         @Field("category") String category);
    @POST("predict/")
    @FormUrlEncoded
    Call<MessageModel> addText(@Field("message") String message);
}
