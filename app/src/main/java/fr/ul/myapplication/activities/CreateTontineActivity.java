package fr.ul.myapplication.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.ul.myapplication.R;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.models.Tontine;

public class CreateTontineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tontine);

        int userId = getIntent().getIntExtra("userId", -1);

        EditText nameField = findViewById(R.id.tontineName);
        EditText amountField = findViewById(R.id.tontineAmount);
        Spinner frequencySpinner = findViewById(R.id.tontineFrequency);
        EditText membersField = findViewById(R.id.tontineMembers);
        Button saveButton = findViewById(R.id.saveTontineButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tontine_frequencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);

        saveButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String amountStr = amountField.getText().toString().trim();
            String frequency = frequencySpinner.getSelectedItem().toString();
            String membersStr = membersField.getText().toString().trim();

            if (name.isEmpty() || amountStr.isEmpty() || membersStr.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            int members;
            try {
                amount = Double.parseDouble(amountStr);
                members = Integer.parseInt(membersStr);
                if (amount <= 0 || members <= 0) {
                    Toast.makeText(this, R.string.error_invalid_number, Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, R.string.error_invalid_number, Toast.LENGTH_SHORT).show();
                return;
            }

            Tontine tontine = new Tontine(name, amount, frequency, members, String.valueOf(userId));

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                try {
                    DatabaseClient.getInstance(getApplicationContext())
                            .getAppDatabase()
                            .tontineDao()
                            .insert(tontine);
                    handler.post(() -> {
                        Toast.makeText(this, R.string.success_tontine_created, Toast.LENGTH_SHORT).show();
                        finish();
                    });
                } catch (Exception e) {
                    handler.post(() -> {
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            });
        });
    }
}