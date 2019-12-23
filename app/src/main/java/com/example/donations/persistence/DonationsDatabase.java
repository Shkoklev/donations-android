package com.example.donations.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.donations.model.Demand;
import com.example.donations.model.DemandCategory;
import com.example.donations.model.DemandPerOrganization;
import com.example.donations.model.Organization;
import com.example.donations.model.OrganizationCategory;
import com.example.donations.model.Unit;
import com.example.donations.persistence.dao.OrganizationDao;

@Database(entities = {Demand.class,
        DemandCategory.class,
        DemandPerOrganization.class,
        Organization.class,
        OrganizationCategory.class,
        Unit.class},
        version = 1, exportSchema = false)
public abstract class DonationsDatabase extends RoomDatabase {

    public abstract OrganizationDao organizationDao();
}
