package fr.uha.moreau.musicsgiver.model;

import fr.uha.moreau.musicsgiver.database.InstrumentDao;
import fr.uha.moreau.musicsgiver.database.MusicienDao;

public class MusicienWithDetails {

    private String nom;
    private String prenom;
    private long id;
    private Instrument instrument;
    private Niveau niveau;
    private Formation formation;

    public MusicienWithDetails(long id, MusicienDao musicienDao, InstrumentDao instrumentDao) {
        Musicien m = musicienDao.getById(id);
        MusicienNiveauFormationAssociation mnfa = musicienDao.getMnfaByMid(id);
        this.id = id;
        this.nom = m.getLastName();
        this.prenom = m.getFirstName();
        this.instrument = instrumentDao.getById(mnfa.getIid());
        this.niveau = mnfa.getNiveau();
        this.formation = mnfa.getFormation();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public long getId() {
        return id;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Formation getFormation() {
        return formation;
    }
}


