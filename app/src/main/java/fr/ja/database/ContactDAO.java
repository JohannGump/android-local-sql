package fr.ja.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import ja.fr.localsqlapp.model.Contact;

public class ContactDAO {

    private DatabaseHandler db;

    public ContactDAO(DatabaseHandler db) {
        this.db = db;
    }
    /*Récupération d'un contact ne fionction de sa clé primaire
    *@param id
    *@retrun
    */

    public Contact findOneById(Long id) throws SQLiteException {
        //Exécution de la requête
        String[] params = {String.valueOf(id)};
        String sql = "SELECT id, name, first_name, email FROM contacts WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, params);
        // Instanciation d'un contact
        Contact contact = new Contact();

        //Hydratation du contact
        if(cursor.moveToNext()){
            contact.setId(cursor.getLong(0));
            contact.setName(cursor.getString(1));
            contact.setFirstName(cursor.getString(2));
            contact.setEmail(cursor.getString(1));
        }

        //Fermeture du curseur
        cursor.close();

        return contact;
    }
}
