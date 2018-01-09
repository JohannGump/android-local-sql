package ja.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ja.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private List<Map<String,String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);
        //Récupération de la list des contacts
        contactList = this.getAllContacts();

        //Création d'un contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

        //Définition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);
    }


    /*
    * Lancement de l'activité formulaire au clic sur un bouton
    * @param view
    */

    public void onAddContact(View view) {
        if (view == findViewById(R.id.buttonAddContact)){
            Intent FormIntent = new Intent (this, FormActivity.class);
            startActivity(FormIntent);
        }
    }

    private List<Map<String,String>> getAllContacts(){

        //Instanciation de la connexion à la base données
        DatabaseHandler db = new DatabaseHandler(this);

        //Exécution de la requête de sélection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT name, first_name, email FROM contacts", null);

        //Instanciation de la liste qui recevra les données
        List<Map<String,String>> contactList = new ArrayList<>();

        //Parcourir les résultats de la requête - parcours du curseur
        while(cursor.moveToNext()){
            Map<String, String> contactCols = new HashMap<>(); // pour ne pas avoir la même ligne répétée
            contactCols.put("name",cursor.getString(0));
            contactCols.put("first_name",cursor.getString(1));
            contactCols.put("email",cursor.getString(2));

        //Ajout de la map à la liste
        contactList.add(contactCols);

        }

        return contactList;
    }
}
