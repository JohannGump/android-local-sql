package ja.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ja.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView contactListView;
    private List<Map<String,String>> contactList;
    private Integer selectedIndex;
    private Map<String,String> selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);
        contactListInit();
    }

    private void contactListInit() {
        //Récupération de la list des contacts
        contactList = this.getAllContacts();

        //Création d'un contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

        //Définition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);

        //
        contactListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_option_menu,menu);
        return true;
    }


    /*
    * Lancement de l'activité formulaire au clic sur un bouton
    * @param view
    */

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mainMenuOptionDelete:
                this.deleteSelectedContact();
                break;
            case R.id.mainMenuOptionEdit:
                break;
        }

        return true;
    }

    // suppression contact sléectionné

    private void deleteSelectedContact(){
        if(this.selectedIndex != null){

            try {
                // Définition de la requête sql et des paramètres
                String sql = "DELETE FROM contacts WHERE id=?";
                String[] params = {this.selectedPerson.get("id")};
                DatabaseHandler db = new DatabaseHandler(this);
                db.getWritableDatabase().execSQL(sql, params);
                //Réinitialisation de la liste des contacts
                contactListInit();
            } catch (SQLiteException ex) {
                Toast.makeText(this,"impossible de supprimer", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this,"vous devez sélectionner un contact", Toast.LENGTH_LONG).show();
        }
    }


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
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT name, first_name, email, id FROM contacts", null);

        //Instanciation de la liste qui recevra les données
        List<Map<String,String>> contactList = new ArrayList<>();

        //Parcourir les résultats de la requête - parcours du curseur
        while(cursor.moveToNext()){
            Map<String, String> contactCols = new HashMap<>(); // pour ne pas avoir la même ligne répétée
            contactCols.put("name",cursor.getString(0));
            contactCols.put("first_name",cursor.getString(1));
            contactCols.put("email",cursor.getString(2));
            contactCols.put("id",cursor.getString(3));

        //Ajout de la map à la liste
        contactList.add(contactCols);

        }

        return contactList;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.selectedIndex = position;
        this.selectedPerson = contactList.get(position);
        Toast.makeText(this, "Ligne " + position + " cliquée", Toast.LENGTH_LONG).show();

    }
}
