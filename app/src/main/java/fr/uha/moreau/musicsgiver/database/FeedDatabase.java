package fr.uha.moreau.musicsgiver.database;

import android.accounts.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.uha.moreau.musicsgiver.model.ClasseDInstrument;
import fr.uha.moreau.musicsgiver.model.Formation;
import fr.uha.moreau.musicsgiver.model.Groupe;
import fr.uha.moreau.musicsgiver.model.Instrument;
import fr.uha.moreau.musicsgiver.model.Musicien;
import fr.uha.moreau.musicsgiver.model.MusicienGroupeAssociation;
import fr.uha.moreau.musicsgiver.model.MusicienNiveauFormationAssociation;
import fr.uha.moreau.musicsgiver.model.Niveau;

public class FeedDatabase {

    private long[] createMusiciens() {
        long[] musiciensIds = new long[10];
        MusicienDao dao = AppDatabase.get().getMusicienDao();
        musiciensIds[0] = dao.upsert(new Musicien(0, "Alfred", "Gentil"));
        musiciensIds[1] = dao.upsert(new Musicien(1, "Renée", "Méchante"));
        musiciensIds[2] = dao.upsert(new Musicien(2, "Denise", "Gentille"));
        musiciensIds[3] = dao.upsert(new Musicien(3, "Freddie", "Mercury"));
        musiciensIds[4] = dao.upsert(new Musicien(4, "Alfred", "Newton"));
        musiciensIds[5] = dao.upsert(new Musicien(5, "Sir", "Ferguson"));
        musiciensIds[6] = dao.upsert(new Musicien(6, "Bono", "Bo"));
        musiciensIds[7] = dao.upsert(new Musicien(7, "Brian", "May"));
        musiciensIds[8] = dao.upsert(new Musicien(8, "John", "Lennon"));
        musiciensIds[9] = dao.upsert(new Musicien(9, "The", "Who"));

        return musiciensIds;
    }

    private long[] createGroupes() {
        long[] groupeIds = new long[2];
        GroupeDao dao = AppDatabase.get().getGroupeDao();
        groupeIds[0] = dao.upsert(new Groupe(0, "bestGroupEver", Formation.ROCK, 5));
        groupeIds[1] = dao.upsert(new Groupe(1, "worstGroupEver", Formation.CLASSIQUE, 15));

        return groupeIds;
    }

    private long[] createInstruments() {
        long[] instrumentsIds = new long[10];
        InstrumentDao dao = AppDatabase.get().getInstrumentDao();
        instrumentsIds[0] = dao.upsert(new Instrument(0, ClasseDInstrument.BOIS, "flûte"));
        instrumentsIds[1] = dao.upsert(new Instrument(1, ClasseDInstrument.CORDES_P, "guitare"));
        instrumentsIds[2] = dao.upsert(new Instrument(2, ClasseDInstrument.CLAVIERS, "piano"));
        instrumentsIds[3] = dao.upsert(new Instrument(3, ClasseDInstrument.CORDES_FROTTEES, "violon"));
        instrumentsIds[4] = dao.upsert(new Instrument(4, ClasseDInstrument.CUIVRES, "trompette"));
        instrumentsIds[5] = dao.upsert(new Instrument(5, ClasseDInstrument.PERCUSSIONS, "tymbales"));
        instrumentsIds[6] = dao.upsert(new Instrument(6, ClasseDInstrument.CUIVRES, "cornet à piston"));
        instrumentsIds[7] = dao.upsert(new Instrument(7, ClasseDInstrument.CUIVRES, "saxophone"));
        instrumentsIds[8] = dao.upsert(new Instrument(8, ClasseDInstrument.PERCUSSIONS, "triangle"));
        instrumentsIds[9] = dao.upsert(new Instrument(9, ClasseDInstrument.CORDES_P, "harpe"));

        return instrumentsIds;
    }

    private void feedMusiciensEtGroupes() {
        MusicienDao mdao = AppDatabase.get().getMusicienDao();
        GroupeDao gdao = AppDatabase.get().getGroupeDao();

        long[] musiciensIds = createMusiciens();
        long[] instrumentsIds = createInstruments();
        long[] groupesIds = createGroupes();

        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.CLASSIQUE, Niveau.CONFIRME, musiciensIds[0], instrumentsIds[2]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.JAZZ, Niveau.DEBUTANT, musiciensIds[1], instrumentsIds[3]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.RAP, Niveau.EXPERT, musiciensIds[2], instrumentsIds[4]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.ROCK, Niveau.INTERMEDIAIRE, musiciensIds[3], instrumentsIds[5]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.FUNK, Niveau.DEBUTANT, musiciensIds[5], instrumentsIds[7]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.BLUES, Niveau.EXPERT, musiciensIds[6], instrumentsIds[8]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.ROCK, Niveau.INTERMEDIAIRE, musiciensIds[7], instrumentsIds[9]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.FUNK, Niveau.CONFIRME, musiciensIds[8], instrumentsIds[0]));
        mdao.addMusicienNiveauFormationAssociation(new MusicienNiveauFormationAssociation(Formation.BLUES, Niveau.CONFIRME, musiciensIds[9], instrumentsIds[1]));

        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[0], groupesIds[0]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[1], groupesIds[1]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[2], groupesIds[0]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[3], groupesIds[1]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[4], groupesIds[0]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[5], groupesIds[1]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[6], groupesIds[0]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[7], groupesIds[1]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[8], groupesIds[0]));
        gdao.addMusicienGroupeAssociation(new MusicienGroupeAssociation(musiciensIds[9], groupesIds[1]));
    }

    public void feed() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                feedMusiciensEtGroupes();
            }
        });
    }
}
