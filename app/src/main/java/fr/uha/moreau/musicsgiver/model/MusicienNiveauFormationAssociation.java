package fr.uha.moreau.musicsgiver.model;

public class MusicienNiveauFormationAssociation {
    public int id;
    public Formation formation;
    public Niveau niveau;
    public int mid;

    public MusicienNiveauFormationAssociation(int id, Formation formation, Niveau niveau, int mid) {
        this.id = id;
        this.formation = formation;
        this.niveau = niveau;
        this.mid = id;
    }

    public int getId() {
        return id;
    }
    public Formation getFormation() {
        return formation;
    }
    public Niveau getNiveau() {
        return niveau;
    }
    public int getMid() {
        return mid;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
}
