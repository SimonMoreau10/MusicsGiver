package fr.uha.moreau.musicsgiver.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Musicien {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String firstName;
    private String lastName;
    // private NiveauSympathie sympathiePrésumée; idée pour plus tard : on ne fait de la musique
    //                                            que si les humains avec lesquels on joue sont de bonnes personnes

    public Musicien() {
        this.id = 0;
    }

    @Ignore
    public Musicien(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }


}
