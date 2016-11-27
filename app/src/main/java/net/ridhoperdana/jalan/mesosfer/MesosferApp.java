package net.ridhoperdana.jalan.mesosfer;

import android.app.Application;

import com.eyro.mesosfer.Mesosfer;

/**
 * Created by Luffi on 27/11/2016.
 */

public class MesosferApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Mesosfer.initialize(this,"latfphIWaj","Md0V6JO8yIlqsiqipoGnlGTgvhQ7hUTn");
    }
}
