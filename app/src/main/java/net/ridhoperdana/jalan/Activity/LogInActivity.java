package net.ridhoperdana.jalan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.interface_retrofit.GetPlace;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn("luffi.as@gmail.com","luffi");
    }

    private void logIn(String email, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ridhoperdana.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetPlace service = retrofit.create(GetPlace.class);
        Call<JsonElement> checkLogIn = service.loggedIn(email, password,"http://192.168.0.111/SearchEngine/test/login");

        checkLogIn.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Log.d("resultLogin",jsonObject.toString());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("resultLogin","gagal"+t.getMessage());

            }
        });
    }
}
