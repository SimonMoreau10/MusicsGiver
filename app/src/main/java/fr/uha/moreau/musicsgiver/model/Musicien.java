package fr.uha.moreau.musicsgiver.model;

public class Musicien {
    private int id;
    private String firstName;
    private String lastName;
    // private NiveauSympathie sympathiePrésumée; idée pour plus tard : on ne fait de la musique
    //                                            que si les humains avec lesquels on joue sont de bonnes personnes

    public Musicien(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }


}
