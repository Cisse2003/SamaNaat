package fr.ul.myapplication.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import fr.ul.myapplication.models.Payment;
import fr.ul.myapplication.models.Tontine;
import fr.ul.myapplication.models.User;


@Database(entities = {User.class, Tontine.class, Payment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract TontineDao tontineDao();
    public abstract PaymentDao paymentDao();
}