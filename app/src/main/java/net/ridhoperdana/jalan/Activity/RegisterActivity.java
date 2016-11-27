package net.ridhoperdana.jalan.activity;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eyro.mesosfer.MesosferException;
import com.eyro.mesosfer.MesosferUser;
import com.eyro.mesosfer.RegisterCallback;

import net.ridhoperdana.jalan.R;
import net.ridhoperdana.jalan.Session.SessionManager;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textEmail, textPassword, textFirstname, textLastName;

    private String email, password, firstname;

    private ProgressDialog loading;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Mesosfer Registration");
        }

        // initialize input form view
        textEmail = (TextInputEditText) findViewById(R.id.email);
        textPassword = (TextInputEditText) findViewById(R.id.password);
        textFirstname = (TextInputEditText) findViewById(R.id.firstName);
        textLastName = (TextInputEditText) findViewById(R.id.lastName);


        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        Button button = (Button )findViewById(R.id.daftar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    public void handleRegister() {
        // get all value from input
        email = textEmail.getText().toString();
        password = textPassword.getText().toString();
        firstname = textFirstname.getText().toString();

        // validating input values
        if (!isInputValid()) {
            // return if there is an invalid input
            return;
        }

        registerUser();
    }

    private boolean isInputValid() {
        // validating all input values if it is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(firstname)) {
            Toast.makeText(this, "First name is empty", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void registerUser() {
        // showing a progress dialog loading
        loading.setMessage("Registering new user...");
        loading.show();

        // create new instance of Mesosfer User
        MesosferUser newUser = MesosferUser.createUser();
        // set default field
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setFirstName(firstname);

        newUser.setData("namaBelakang",textLastName.getText().toString());
        // set custom field

        // execute register user asynchronous
        newUser.registerAsync(new RegisterCallback() {
            @Override
            public void done(MesosferException e) {
                // hide progress dialog loading
                loading.dismiss();

                // setup alert dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setNegativeButton(android.R.string.ok, null);

                // check if there is an exception happen
                if (e != null) {
                    builder.setTitle("Error Happen");
                    builder.setMessage(
                            String.format(Locale.getDefault(), "Error code: %d\nDescription: %s",
                                    e.getCode(), e.getMessage())
                    );
                    dialog = builder.show();
                    return;
                }

                builder.setTitle("Register Succeeded");
                builder.setMessage("Thank you for registering.");
                dialog = builder.show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        // dismiss any resource showing
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        super.onDestroy();
    }
}

