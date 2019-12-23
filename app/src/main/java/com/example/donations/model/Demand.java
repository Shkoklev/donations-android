package com.example.donations.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "demand",
        foreignKeys = {
                @ForeignKey(
                        entity = DemandCategory.class,
                        parentColumns = "id",
                        childColumns = "demand_category_id",
                        onDelete = CASCADE),
                @ForeignKey(
                        entity = Unit.class,
                        parentColumns = "id",
                        childColumns = "unit_id",
                        onDelete = CASCADE)
        })
public class Demand {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "demand_category_id")
    private int categoryId;

    @ColumnInfo(name = "unit_id")
    private int unitId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
