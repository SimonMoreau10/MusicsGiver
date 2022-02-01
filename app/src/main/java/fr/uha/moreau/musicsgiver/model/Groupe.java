package fr.uha.moreau.musicsgiver.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "groupes")
public class Groupe {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String name;
    private Formation formation;
    private int nombre;

    public Groupe() {
        this.id = 0;
    }

    @Ignore
    public Groupe(long id, String name, Formation formation, int nombre) {
        this.id = id;
        this.name = name;
        this.formation = formation;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Formation getFormation() {
        return formation;
    }
    public int getNombre() {
        return nombre;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
