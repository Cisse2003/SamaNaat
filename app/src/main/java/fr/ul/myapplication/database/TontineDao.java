package fr.ul.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.ul.myapplication.models.Tontine;

@Dao
public interface TontineDao {
    @Insert
    void insert(Tontine tontine);

    @Query("SELECT * FROM tontines WHERE creatorId = :userId")
    List<Tontine> getTontinesByUser(String userId);

    @Query("SELECT * FROM tontines WHERE id = :tontineId")
    Tontine getTontineById(int tontineId);
}