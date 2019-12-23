package com.example.donations.persistence.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.donations.model.Organization;
import com.example.donations.model.OrganizationCategory;

import java.util.List;

@Dao
public interface OrganizationDao {

    @Query("SELECT * FROM organization")
    LiveData<List<Organization>> getAllOrganizations();

    @Query("SELECT * FROM organization where category_id=:id")
    LiveData<List<Organization>> getByCategoryId(int id);

    @Query("SELECT * FROM organization_category")
    LiveData<List<OrganizationCategory>> getAllOrganizationCategories();

    @Insert
    void insertOrganization(Organization organization);

    @Insert
    void insertOrganizationCategory(OrganizationCategory organizationCategory);

    @Update
    void update(Organization organization);

    @Query("DELETE from organization")
    void deleteAllOrganizations();

    @Query("DELETE from organization_category")
    void deleteAllOrganizationCategories();


}
