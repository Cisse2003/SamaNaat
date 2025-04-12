package fr.ul.myapplication.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

import fr.ul.myapplication.models.Payment;

@Dao
public interface PaymentDao {
    @Insert
    void insert(Payment payment);

    @Query("SELECT * FROM payments WHERE tontineId = :tontineId")
    List<Payment> getPaymentsByTontine(int tontineId);
}