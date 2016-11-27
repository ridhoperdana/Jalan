package net.ridhoperdana.jalan.interface_retrofit;

import com.google.gson.JsonElement;

import net.ridhoperdana.jalan.pojo_class.Tempat;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by RIDHO on 11/23/2016.
 */

public interface GetPlace {
    @GET
    Call<Tempat> getPlaceResponseFood(@Url String url);

    @GET
    Call<Tempat> getPlaceResponseWorship(@Url String url);

    @GET
    Observable<Tempat> getPlaceResponseRx(@Url String url);

    @GET
    Call<JsonElement> getDistance2Location(@Url String url);

    @FormUrlEncoded
    @POST
    Call<JsonElement> loggedIn(@Field("email") String email, @Field("password") String password, @Url String url);
}
