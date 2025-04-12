package fr.ul.myapplication.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tontines")
public class Tontine {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;
    private String frequency;
    private int membersCount;
    private String creatorId;

    public Tontine(String name, double amount, String frequency, int membersCount, String creatorId) {
        this.name = name;
        this.amount = amount;
        this.frequency = frequency;
        this.membersCount = membersCount;
        this.creatorId = creatorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public int getMembersCount() { return membersCount; }
    public void setMembersCount(int membersCount) { this.membersCount = membersCount; }
    public String getCreatorId() { return creatorId; }
    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }
}