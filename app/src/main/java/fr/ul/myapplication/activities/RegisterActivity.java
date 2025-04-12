package fr.ul.myapplication.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import fr.ul.myapplication.R;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.models.User;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText nameField = findViewById(R.id.nameField);
        EditText emailField = findViewById(R.id.emailField);
        EditText phoneField = findViewById(R.id.phoneField);
        EditText passwordField = findViewById(R.id.passwordField);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> {
            String name = nameField.getText().toString();
            String email = emailField.getText().toString();
            String phone = phoneField.getText().toString();
            String password = passwordField.getText().toString();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(email, phone, password, name);
            DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .userDao()
                    .insert(user);

            Toast.makeText(this, R.string.success_register, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}