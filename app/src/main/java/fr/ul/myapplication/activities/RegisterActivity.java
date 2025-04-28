package fr.ul.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.ul.myapplication.R;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.database.UserDao;
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

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

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
            executor.execute(() -> {
                try {
                    UserDao userDao = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().userDao();
                    userDao.insert(user);

                    // Vérifier les utilisateurs dans la base
                    java.util.List<User> users = userDao.getAllUsers();
                    StringBuilder debugInfo = new StringBuilder("Utilisateurs dans la base :\n");
                    for (User u : users) {
                        debugInfo.append("ID: ").append(u.getId())
                                .append(", Email: ").append(u.getEmail())
                                .append(", Password: ").append(u.getPassword())
                                .append("\n");
                    }

                    handler.post(() -> {
                        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                        // Afficher les données pour déboguer
                        Toast.makeText(this, debugInfo.toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } catch (Exception e) {
                    handler.post(() -> {
                        Toast.makeText(this, "Erreur lors de l'inscription : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });

            Toast.makeText(this, R.string.success_register, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}