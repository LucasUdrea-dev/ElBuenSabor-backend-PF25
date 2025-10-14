package com.buenSabor.BackEnd.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp();
            } else {
                return FirebaseApp.getInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar Firebase. Asegúrate de que GOOGLE_APPLICATION_CREDENTIALS esté configurada.", e);
        }
    }
    
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Verificar si Firebase ya está inicializado
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        if (!firebaseApps.isEmpty()) {
            // Si ya existe, retornar la instancia existente
            return FirebaseApp.getInstance();
        }
        
        // Si no existe, inicializar Firebase
        InputStream serviceAccount = getClass().getResourceAsStream("/firebase-service-account.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
