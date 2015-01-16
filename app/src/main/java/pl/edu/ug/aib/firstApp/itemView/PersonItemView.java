package pl.edu.ug.aib.firstApp.itemView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import pl.edu.ug.aib.firstApp.R;
import pl.edu.ug.aib.firstApp.data.Person;

@EViewGroup(R.layout.list_item)
public class PersonItemView extends RelativeLayout {

    @ViewById
    ImageView avatar;

    @ViewById
    TextView name;

    @ViewById
    TextView company;

    public PersonItemView(Context context) {
        super(context);
    }

    public void bind(Person person) {
        name.setText(person.name);
        company.setText(person.company);
        if (person.pictureBytes != null) {
            decodeAndSetImage(person.pictureBytes, avatar);
        } else {
            avatar.setImageDrawable(null);
        }
    }

    private void decodeAndSetImage(String base64bytes, ImageView image) {
        // zamień ciąg tekstowy Base-64 na tablicę bajtów
        byte[] decodedString = Base64.decode(base64bytes, Base64.DEFAULT);
        // utwórz bitmapę na podstawie ciągu bajtów z obrazem JPEG
        Bitmap decodedBytes = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // wstaw bitmapę do komponentu ImageView awatara
        image.setImageBitmap(decodedBytes);
    }

}

