package com.example.cookmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailField, passwordField, usernameField;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialisation de Firebase Auth et Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Liaison des vues
        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        usernameField = findViewById(R.id.username);
        signUpButton = findViewById(R.id.signUpButton);

        // Gestion du clic sur le bouton d'inscription
        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();
            String username = usernameField.getText().toString();
            registerUser(email, password, username);
        });
    }

    // Méthode pour s'inscrire
    private void registerUser(String email, String password, String username) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inscription réussie
                        String userId = mAuth.getCurrentUser().getUid();  // ID de l'utilisateur
                        saveUserData(userId, username);  // Enregistrer le pseudo dans Firestore
                    } else {
                        // Échec de l'inscription
                        Toast.makeText(this, "Échec de l'inscription : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Méthode pour enregistrer le pseudo dans Firestore
    private void saveUserData(String userId, String username) {
        // Créer un objet pour stocker les données de l'utilisateur
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);

        // Enregistrer les données dans Firestore
        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);  // Transmettre le pseudo à HomeActivity
                    startActivity(intent);
                    finish(); // Ferme l'activité d'inscription
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur lors de l'enregistrement des données : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Méthode pour aller à la page de connexion
    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}