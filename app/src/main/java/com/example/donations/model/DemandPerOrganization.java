package com.example.donations.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "demand_per_organization",
        foreignKeys = {
                @ForeignKey(
                        entity = Organization.class,
                        parentColumns = "id",
                        childColumns = "organization_id",
                        onDelete = CASCADE),
                @ForeignKey(
                        entity = Demand.class,
                        parentColumns = "id",
                        childColumns = "demand_id",
                        onDelete = CASCADE)
        })
public class DemandPerOrganization {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "organization_id")
    private int organizationId;

    @ColumnInfo(name = "demand_id")
    private int demandId;


    @ColumnInfo(name = "quantity")
    private double quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getDemandId() {
        return demandId;
    }

    public void setDemandId(int demandId) {
        this.demandId = demandId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
