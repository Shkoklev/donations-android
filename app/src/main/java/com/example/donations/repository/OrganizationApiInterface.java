package com.example.donations.repository;

import com.example.donations.model.Organization;
import com.example.donations.model.OrganizationCategory;
import com.example.donations.model.response.OrganizationsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OrganizationApiInterface {

    @GET("/organizations")
    Call<OrganizationsResponse> getOrganizations();

    @GET("/organization_categories")
    Call<List<OrganizationCategory>> getOrganizationCategories();
}
