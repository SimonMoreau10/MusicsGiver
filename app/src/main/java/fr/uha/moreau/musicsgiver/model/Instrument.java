package fr.uha.moreau.musicsgiver.model;

public class Instrument {
    private int id;
    private ClasseDInstrument classe;
    private String nom;

    public Instrument(int id, ClasseDInstrument classe, String nom) {
        this.id = id;
        this.classe = classe;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }
    public ClasseDInstrument getClasse() {
        return classe;
    }
    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setClasse(ClasseDInstrument classe) {
        this.classe = classe;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
}
