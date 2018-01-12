package fr.ja.database;

import android.database.sqlite.SQLiteException;

import java.util.List;

import ja.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 11/01/2018.
 */

interface DAOInterface <DTO> {
    Contact findOneById(Long id) throws SQLiteException;

    List<DTO> findAll() throws SQLiteException;

    void deleteOneById(Long id) throws SQLiteException;

    void persist(DTO entity);
}
