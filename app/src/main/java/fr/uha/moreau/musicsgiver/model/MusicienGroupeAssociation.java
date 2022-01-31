package fr.uha.moreau.musicsgiver.model;

public class MusicienGroupeAssociation {
    private int id;
    private int mid;
    private int gid;

    public MusicienGroupeAssociation(int id, int mid, int gid) {
        this.id = id;
        this.mid = mid;
        this.gid = gid;
    }

    public int getId() {
        return id;
    }
    public int getGid() {
        return gid;
    }
    public int getMid() {
        return mid;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setGid(int gid) {
        this.gid = gid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
}
