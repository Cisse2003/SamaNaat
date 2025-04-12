package fr.ul.myapplication.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import fr.ul.myapplication.R;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.models.User;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        int userId = getIntent().getIntExtra("userId", -1);

        User user = DatabaseClient.getInstance(getApplicationContext())
                .getAppDatabase()
                .userDao()
                .getUserById(userId);

        TextView nameTextView = findViewById(R.id.profileName);
        TextView emailTextView = findViewById(R.id.profileEmail);
        TextView phoneTextView = findViewById(R.id.profilePhone);
        Spinner languageSpinner = findViewById(R.id.languageSpinner);

        nameTextView.setText(user.getName());
        emailTextView.setText(user.getEmail());
        phoneTextView.setText(user.getPhone());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

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