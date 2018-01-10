package ja.fr.localsqlapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ja.fr.localsqlapp.model.Contact;

public class ContactArrayAdapter extends ArrayAdapter {

    private Activity context;
    private List<Contact> data;
    private int resource;
    private LayoutInflater inflator;

    public ContactArrayAdapter(@NonNull Context context, @NonNull List<Contact> data) {

        super(context, 0, data);

        this.data = data;
        this.context = (Activity) context;
        this.inflator = this.context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //instanciation de la vue
        View view = this.inflator.inflate(R.layout.list_view_contact, parent, false);

        //Récupération des données d'une ligne
        Contact contactData = this.data.get(position);

        //Liaison entre les données et la vue
        TextView nameTextView = view.findViewById(R.id.listTextViewName);
        nameTextView.setText(contactData.getName());

        TextView firstNameTextView = view.findViewById(R.id.listTextViewFirstName);
        firstNameTextView.setText(contactData.getFirstName());

        TextView emailTextView = view.findViewById(R.id.listTextViewEmail);
        emailTextView.setText(contactData.getEmail());

        return view;
    }
}
