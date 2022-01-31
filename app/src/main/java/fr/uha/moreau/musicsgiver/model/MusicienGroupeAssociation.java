package fr.uha.moreau.musicsgiver.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "musicienGroupeAssociations",
        primaryKeys = { "gid", "mid" },
        indices = { @Index("gid") }
        )
public class MusicienGroupeAssociation {

    @NonNull
    private long mid;
    @NonNull
    private long gid;

    public MusicienGroupeAssociation() {}

    @Ignore
    public MusicienGroupeAssociation(long mid, long gid) {
        this.mid = mid;
        this.gid = gid;
    }

    public long getGid() {
        return gid;
    }
    public long getMid() {
        return mid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
}
