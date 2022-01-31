package fr.uha.moreau.musicsgiver.model;

public class Groupe {
    private int id;
    private Formation formation;
    private int nombre;

    public Groupe(int id, Formation formation, int nombre) {
        this.id = id;
        this.formation = formation;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }
    public Formation getformation() {
        return formation;
    }
    public int getNombre() {
        return nombre;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
