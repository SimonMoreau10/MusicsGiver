package fr.uha.moreau.musicsgiver.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import fr.uha.moreau.musicsgiver.database.AppDatabase;

@Entity(
        tableName = "musicienNiveauFormationAssociation",
        primaryKeys = { "formation", "niveau", "mid", "iid" },
        indices = { @Index("formation"), @Index("niveau"), @Index("iid") }
        )
public class MusicienNiveauFormationAssociation {

    @NonNull
    public Formation formation;
    @NonNull
    public Niveau niveau;
    @NonNull
    public long mid;
    @NonNull
    public long iid;

    public MusicienNiveauFormationAssociation() {}

    @Ignore
    public MusicienNiveauFormationAssociation(Formation formation, Niveau niveau, long mid, long iid) {
        this.formation = formation;
        this.niveau = niveau;
        this.mid = mid;
        this.iid = iid;
    }

    public Formation getFormation() {
        return formation;
    }
    public Niveau getNiveau() {
        return niveau;
    }
    public long getMid() {
        return mid;
    }
    public long getIid() {
        return iid;
    }
    public String getNomInstrument() {
        return AppDatabase.get().getInstrumentDao().getById(iid).getValue().getNom();
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
    public void setMid(long mid) {
        this.mid = mid;
    }
    public void setIid(long iid) {
        this.iid = iid;
    }
}
