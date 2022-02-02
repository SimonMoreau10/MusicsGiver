package fr.uha.moreau.musicsgiver.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "instruments")
public class Instrument {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private ClasseDInstrument classe;
    private String nom;

    public Instrument() {
        this.id = 0;
    }

    @Ignore
    public Instrument(long id, ClasseDInstrument classe, String nom) {
        this.id = id;
        this.classe = classe;
        this.nom = nom;
    }

    public long getId() {
        return id;
    }
    public ClasseDInstrument getClasse() {
        return classe;
    }
    public String getNom() {
        return nom;
    }
    public String getClasseAAfficher() {
        return String.valueOf(getClasse());
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setClasse(ClasseDInstrument classe) {
        this.classe = classe;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
}
