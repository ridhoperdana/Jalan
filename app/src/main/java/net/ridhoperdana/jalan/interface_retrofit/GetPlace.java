package net.ridhoperdana.jalan.interface_retrofit;

import net.ridhoperdana.jalan.pojo_class.Tempat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by RIDHO on 11/23/2016.
 */

public interface GetPlace {
    @GET
    Call<Tempat> getPlaceResponseFood(@Url String url);

    @GET
    Call<Tempat> getPlaceResponseWorship(@Url String url);
}
