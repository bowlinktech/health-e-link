package com.ut.healthelink.dao;

import java.util.List;

import com.ut.healthelink.model.Brochure;
import com.ut.healthelink.model.Organization;
import com.ut.healthelink.model.Provider;
import com.ut.healthelink.model.User;
import com.ut.healthelink.model.organizationPrograms;
import org.springframework.stereotype.Repository;

@Repository
public interface organizationDAO {

    Integer createOrganization(Organization organization);

    void updateOrganization(Organization organization);

    Organization getOrganizationById(int orgId);

    List<Organization> getOrganizationByName(String cleanURL);

    List<Organization> getOrganizations();

    List<Organization> getLatestOrganizations(int maxResults);

    List<Organization> getAllActiveOrganizations();

    Long findTotalOrgs();

    Long findTotalUsers(int orgId);

    Long findTotalConfigurations(int orgId);

    List<User> getOrganizationUsers(int orgId);

    void deleteOrganization(int orgId);

    List<Provider> getOrganizationProviders(int orgId);
    
    List<Provider> getOrganizationActiveProviders(int orgId);

    List<Brochure> getOrganizationBrochures(int orgId);

    List<Organization> getAssociatedOrgs(int orgId);
    
    List getPartnerEntriesForMap();
    
    List<Organization> searchCBOOrganizations(Integer programType, String town, String county, String state, String postalCode) throws Exception;
    
    List<Integer> getOrganizationPrograms(int orgId) throws Exception;
    
    void saveOrganizationPrograms(organizationPrograms programs) throws Exception;
    
    void deletOrganizationPrograms(int orgId) throws Exception;
}
