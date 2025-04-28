package fr.ul.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import fr.ul.myapplication.R;
import fr.ul.myapplication.adapters.TontineAdapter;
import fr.ul.myapplication.database.DatabaseClient;
import fr.ul.myapplication.models.Tontine;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView tontineRecyclerView;
    private TontineAdapter tontineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        int userId = getIntent().getIntExtra("userId", -1);

        tontineRecyclerView = findViewById(R.id.tontineRecyclerView);
        tontineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser ExecutorService et Handler
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        // Charger les tontines dans un thread d'arrière-plan
        executor.execute(() -> {
            List<Tontine> tontines = DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .tontineDao()
                    .getTontinesByUser(String.valueOf(userId));

            // Mettre à jour l'UI sur le thread principal
            handler.post(() -> {
                tontineAdapter = new TontineAdapter(tontines);
                tontineRecyclerView.setAdapter(tontineAdapter);
            });
        });

        Button createTontineButton = findViewById(R.id.createTontineButton);
        createTontineButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateTontineActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_tontines) {
                // À implémenter plus tard (liste complète des tontines)
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userId = getIntent().getIntExtra("userId", -1);

        // Recharger les tontines dans un thread d'arrière-plan
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            List<Tontine> tontines = DatabaseClient.getInstance(getApplicationContext())
                    .getAppDatabase()
                    .tontineDao()
                    .getTontinesByUser(String.valueOf(userId));

            // Mettre à jour l'UI sur le thread principal
            handler.post(() -> {
                tontineAdapter = new TontineAdapter(tontines);
                tontineRecyclerView.setAdapter(tontineAdapter);
            });
        });
    }
}