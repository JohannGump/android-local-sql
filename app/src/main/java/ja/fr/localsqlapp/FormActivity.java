package ja.fr.localsqlapp;

import android.app.ActionBar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onValid (View v){
        Button clickedButton = (Button) v; //je caste ma vue en bouton

        //Récupéréation de la saisie de l'utilisateur
        String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstName = ((EditText) findViewById(R.id.editTextPrenom)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();

        // Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);

        // Définition des données à insérer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name", firstName);
        insertValues.put("name", name);
        insertValues.put("email", email);

        // Insertion des données
        try {
            db.getWritableDatabase().insert("contacts", null, insertValues);
            Toast.makeText(this, "Données enregistrées", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.editTextEmail)).setText("");
            ((EditText) findViewById(R.id.editTextNom)).setText("");
            ((EditText) findViewById(R.id.editTextPrenom)).setText("");

        } catch (SQLiteException ex){
            Log.e("SQL EXCEPTION", ex.getMessage());
        }

    }
}
