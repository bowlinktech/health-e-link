package com.ut.healthelink.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.healthelink.dao.userDAO;
import com.ut.healthelink.model.User;
import com.ut.healthelink.service.userManager;
import com.ut.healthelink.model.UserActivity;
import com.ut.healthelink.model.siteSections;
import com.ut.healthelink.model.userAccess;

@Service
public class userManagerImpl implements userManager {

    @Autowired
    private userDAO userDAO;

    @Override
    @Transactional
    public Integer createUser(User user) {
        Integer lastId = null;
        lastId = (Integer) userDAO.createUser(user);
        return lastId;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    @Transactional
    public List<User> getUsersByOrganization(int orgId) {
        return userDAO.getUsersByOrganization(orgId);
    }

    @Override
    @Transactional
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    @Override
    @Transactional
    public Long findTotalLogins(int userId) {
        return userDAO.findTotalLogins(userId);
    }

    @Override
    @Transactional
    public void setLastLogin(String username) {
        userDAO.setLastLogin(username);
    }

    @Override
    @Transactional
    public List<siteSections> getSections() {
        return userDAO.getSections();
    }

    @Override
    @Transactional
    public List<userAccess> getuserSections(int userId) {
        return userDAO.getuserSections(userId);
    }

    @Override
    @Transactional
    public List<User> getOrganizationContact(int orgId, int mainContact) {
        return userDAO.getOrganizationContact(orgId, mainContact);
    }

    @Override
    @Transactional
    public Integer getUserByIdentifier(String identifier) {
        return userDAO.getUserByIdentifier(identifier);
    }

    @Override
    @Transactional
    public User getUserByResetCode(String resetCode) {
        return userDAO.getUserByResetCode(resetCode);
    }

    @Override
    @Transactional
    public void insertUserLog(UserActivity userActivity) {
        userDAO.insertUserLog(userActivity);
    }

    @Override
    @Transactional
    public UserActivity getUAById(Integer uaId) {
        return userDAO.getUAById(uaId);
    }

    @Override
    @Transactional
    public List<User> getUserByTypeByOrganization(int orgId) {
        return userDAO.getUserByTypeByOrganization(orgId);
    }

    @Override
    @Transactional
    public List<User> getSendersForConfig(List<Integer> configIds) {
        return userDAO.getSendersForConfig(configIds);
    }

    @Override
    @Transactional
    public List<User> getOrgUsersForConfig(List<Integer> configIds) {
        return userDAO.getOrgUsersForConfig(configIds);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public void updateUserActivity(UserActivity userActivity) {
        userDAO.updateUserActivity(userActivity);
    }

}