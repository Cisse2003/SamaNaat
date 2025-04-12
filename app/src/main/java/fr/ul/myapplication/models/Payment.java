package fr.ul.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payments")
public class Payment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tontineId;
    private int userId;
    private double amount;
    private String date;
    private boolean isPaid;

    public Payment(int tontineId, int userId, double amount, String date, boolean isPaid) {
        this.tontineId = tontineId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTontineId() { return tontineId; }
    public void setTontineId(int tontineId) { this.tontineId = tontineId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { this.isPaid = paid; }
}