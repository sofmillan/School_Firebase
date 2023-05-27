package com.example.colegio_sabado;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListarEstudiantesActivity extends AppCompatActivity {

    RecyclerView rvestudiantes;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ClsEstudiantes> alestudiantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_estudiantes);


        getSupportActionBar().hide();
        rvestudiantes = findViewById(R.id.rvlistarestudiantes);
        alestudiantes = new ArrayList<>();
        rvestudiantes.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        rvestudiantes.setHasFixedSize(true);
        cargar_datos();
    }

    private void cargar_datos(){
        db.collection("Estudiantes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ClsEstudiantes estudiante = new ClsEstudiantes();
                                estudiante.setCarnet(document.getString("Carnet"));
                                estudiante.setNombre(document.getString("Nombre"));
                                estudiante.setCarrera(document.getString("Carrera"));
                                estudiante.setSemestre(document.getString("Semestre"));
                                estudiante.setActivo(document.getString("Activo"));
                                alestudiantes.add(estudiante);
                            }

                            AdapterEstudiante adapterEstudiante = new AdapterEstudiante(alestudiantes);
                            rvestudiantes.setAdapter(adapterEstudiante);

                        } else {
                            Toast.makeText(ListarEstudiantesActivity.this, "Documentos no hallados", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }}