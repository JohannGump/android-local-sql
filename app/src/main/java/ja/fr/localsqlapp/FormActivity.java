package ja.fr.localsqlapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.ja.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText firstNameEditText;
    private EditText emailEditText;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Récupération des données
        Intent intention = getIntent();
        String id = intention.getStringExtra("id");
        String name = intention.getStringExtra("name");
        String firstName = intention.getStringExtra("first_name");
        String email = intention.getStringExtra("email");

        //Récupération de l'id dans une variable gloabale
        this.contactId = id;
        // référence aux EditText
        this.emailEditText = findViewById(R.id.editTextEmail);
        this.nameEditText = findViewById(R.id.editTextNom);
        this.firstNameEditText = findViewById(R.id.editTextPrenom);

        //Affichage des données dans les champs de saisie
        this.firstNameEditText.setText(firstName);
        this.nameEditText.setText(name);
        this.emailEditText.setText(email);
    }

    public void onValid (View v){
        Button clickedButton = (Button) v; //je caste ma vue en bouton

        //Récupéréation de la saisie de l'utilisateur
        String name = this.nameEditText.getText().toString();
        String firstName = this.firstNameEditText.getText().toString();
        String email = this.emailEditText.getText().toString();

        // Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);

        // Définition des données à insérer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name", firstName);
        insertValues.put("name", name);
        insertValues.put("email", email);

        // Insertion des données
        try {

            if(this.contactId !=null){
                //mise à jour d'un contact existant
                String[] params = {contactId};
                db.getWritableDatabase().update("contacts",
                                                insertValues,
                                                "id=?",
                                                params);
                setResult(RESULT_OK);
                finish();
            } else {
                //insertion d'un nouveau contact
                db.getWritableDatabase().insert("contacts", null, insertValues);
                Toast.makeText(this, "Données enregistrées", Toast.LENGTH_LONG).show();
                ((EditText) findViewById(R.id.editTextEmail)).setText("");
                ((EditText) findViewById(R.id.editTextNom)).setText("");
                ((EditText) findViewById(R.id.editTextPrenom)).setText("");
            }

        } catch (SQLiteException ex){
            Log.e("SQL EXCEPTION", ex.getMessage());
        }

    }
}
