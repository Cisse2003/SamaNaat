package fr.ul.myapplication.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import fr.ul.myapplication.R;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.models.User;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userId = getIntent().getIntExtra("userId", -1);

        TextView nameTextView = findViewById(R.id.profileName);
        TextView emailTextView = findViewById(R.id.profileEmail);
        TextView phoneTextView = findViewById(R.id.profilePhone);
        Spinner languageSpinner = findViewById(R.id.languageSpinner);

        // Configurer l'adaptateur du Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_array,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(R.layout.spinner_item);
        languageSpinner.setAdapter(adapter);

        // Charger les données de l'utilisateur dans un thread d'arrière-plan
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                User user = DatabaseClient.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .userDao()
                        .getUserById(userId);

                handler.post(() -> {
                    if (user != null) {
                        nameTextView.setText(user.getName());
                        emailTextView.setText(user.getEmail());
                        phoneTextView.setText(user.getPhone());
                    } else {
                        Toast.makeText(this, R.string.error_user_not_found, Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            } catch (Exception e) {
                handler.post(() -> {
                    Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                });
            }
        });

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String locale = position == 0 ? "fr" : "wo";
                Locale newLocale = new Locale(locale);
                Locale.setDefault(newLocale);
                Configuration config = new Configuration();
                config.locale = newLocale;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}