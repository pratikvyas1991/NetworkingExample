package com.ims.tasol.networkingexample.retrofit;

import com.ims.tasol.networkingexample.model.DataPojo;
import com.ims.tasol.networkingexample.model.GeonameResponse;
import com.ims.tasol.networkingexample.model.ListData;
import com.ims.tasol.networkingexample.model.TaskData;
import com.ims.tasol.networkingexample.utils.HttpConstants;

import java.util.HashMap;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by tasol on 18/3/17.
 */

public interface ServiceInterface {
    @GET(HttpConstants.CITIESJSON)
    Call<GeonameResponse>getCities(@Query("north")double north, @Query("south")double south, @Query("east")double east, @Query("west")double west, @Query("lang")String lang, @Query("username")String username);

    @GET(HttpConstants.USERDATAJSON)
    Call<ListData>taskData(@Query("method")String method,@Query("stdID")int stdID);

    @POST(HttpConstants.USERDATAJSON)
    Call<ListData> saveUser(@Body DataPojo pojo);
}
