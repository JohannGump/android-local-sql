package ja.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import fr.ja.database.ContactDAO;
import fr.ja.database.DatabaseHandler;
import ja.fr.localsqlapp.model.Contact;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView contactListView;
    private List<Contact> contactList;
    private Integer selectedIndex;
    private Contact selectedPerson;
    private final String LIFE_CYCLE = "cycle de vie";

    private DatabaseHandler db;
    private ContactDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LIFE_CYCLE,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciation de la connexion à l abase de données
        this.db = new DatabaseHandler(this);
        //Instanciation du DAO pour les contacts
        this.dao = new ContactDAO(this.db);

        // Reference au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);
        contactListInit();


        //Récupération des données persistantes dans le Bundle
        if (savedInstanceState != null){
            this.selectedIndex = savedInstanceState.getInt("selectedIndex");
            if(this.selectedIndex != null){
                contactListView.requestFocusFromTouch();
                this.selectedPerson = this.contactList.get(this.selectedIndex);
                contactListView.setSelection(this.selectedIndex);
            }
        }
    }


    private void contactListInit() {
        //Récupération de la list des contacts
        contactList = this.dao.findAll();

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
                this.editSelectedContact();
                break;
        }

        return true;
    }

    // Edition d'un contact sélectionné
    private void editSelectedContact(){
        if (this.selectedIndex != null) {

            //création d'une intention
            Intent intention = new Intent(this, FormActivity.class);

            // Passage des paramètres à l'intention
            intention.putExtra("id", String.valueOf(this.selectedPerson.getId()));
            intention.putExtra("name", this.selectedPerson.getName());
            intention.putExtra("first_name", this.selectedPerson.getFirstName());
            intention.putExtra("email", this.selectedPerson.getEmail());

            //lancement de l'activité FOrmActivity
            startActivityForResult(intention,1);

        }


    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(requestCode ==1 && resultCode == RESULT_OK){
            Toast.makeText(this,"Mise à jour effectuée", Toast.LENGTH_LONG).show();
            //Réinitialisation de la liste
            this.contactListInit();
        }
    }

    // suppression contact sélectionné
    private void deleteSelectedContact(){
        if(this.selectedIndex != null){

            try {
                // Définition de la requête sql et des paramètres
                String sql = "DELETE FROM contacts WHERE id=?";
                String[] params = {String.valueOf(this.selectedPerson.getId())};
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



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.selectedIndex = position;
        this.selectedPerson = contactList.get(position);
        Toast.makeText(this, "Ligne " + position + " cliquée", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LIFE_CYCLE,"onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LIFE_CYCLE,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LIFE_CYCLE,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LIFE_CYCLE,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LIFE_CYCLE,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LIFE_CYCLE,"onDestroy");
    }

    /*persistance des données vant destruction de l'activité'
    /*
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(this.selectedIndex != null){
            outState.putInt("selectedIndex", this.selectedIndex);
        }

        super.onSaveInstanceState(outState);

    }
}
