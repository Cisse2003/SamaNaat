package fr.ul.myapplication.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);

        saveButton.setOnClickListener(v -> {
            String name = nameField.getText().toString();
            String amountStr = amountField.getText().toString();
            String frequency = frequencySpinner.getSelectedItem().toString();
            String membersStr = membersField.getText().toString();

            if (name.isEmpty() || amountStr.isEmpty() || membersStr.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                return;
            }

            double amount = Double.parseDouble(amountStr);
            int members = Integer.parseInt(membersStr);

            Tontine tontine = new Tontine(name, amount, frequency, members, String.valueOf(userId));
            DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .tontineDao()
                    .insert(tontine);

            Toast.makeText(this, R.string.success_tontine_created, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}