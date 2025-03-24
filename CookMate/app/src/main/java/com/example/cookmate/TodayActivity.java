package com.example.cookmate;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class TodayActivity extends AppCompatActivity {
    private TextView welcomeMessage;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        // Initialisation de Firebase Auth et Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Liaison des vues
        welcomeMessage = findViewById(R.id.welcomeMessage);

        // Récupérer l'ID de l'utilisateur connecté
        String userId = mAuth.getCurrentUser().getUid();

        // Récupérer le pseudo depuis Firestore
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String username = document.getString("username");
                            welcomeMessage.setText("Bonjour " + username + " !");
                        } else {
                            welcomeMessage.setText("Bonjour !");
                        }
                    } else {
                        welcomeMessage.setText("Erreur lors du chargement des données.");
                    }
                });
    }
}