package java.TFG.generosmusicales;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import java.TFG.generosmusicales.db.DBHelper;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.bumptech.glide.request.transition.Transition;
import java.text.DecimalFormat;


public class generosexplicados extends AppCompatActivity {
    private final String[] images = {null, null, null, null, null, null};
    final ImageView [] imageView = {null, null, null, null, null, null};
    private final List<TextView> names = new ArrayList<>();
    String[] nombres = {null, null, null, null, null, null};
    JSONArray genresArray;
    private int indice = 0;
    int followers, popularity;
    final TextView [] followersText= {null, null, null, null, null, null};
    private final TextView [] popularityText= {null, null, null, null, null, null};
    final TextView [] genresText= {null, null, null, null, null, null};
    String[] textos = {null, null, null, null, null, null};
    private final ImageButton [] favButton = {null, null, null, null, null, null};
    MaterialCardView [] cardViews={null, null, null, null, null, null};

    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generosexplicados);
        String aspecto = getIntent().getStringExtra("aspecto");
        System.out.println(aspecto);
        String texto;
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        TextView titleTextView = findViewById(R.id.titleTextView);
        TitleBackgroundRandomizer.applyRandomTitleBackground(this, titleTextView);

        TextView decriptionTextView = findViewById(R.id.paragraphTextView);
        titleTextView.setText(aspecto);

        String mAccessToken = AccessTokenManager.getInstance(getApplicationContext()).getAccessToken();
        List<TextView> textViews = new ArrayList<>();

        for (int i = 1; i <= textos.length; i++) {
            @SuppressLint("DiscouragedApi") int textViewId = getResources().getIdentifier("textArtist" + i, "id", getPackageName());
            TextView textView = findViewById(textViewId);
            textViews.add(textView);

            @SuppressLint("DiscouragedApi") int imageViewId = getResources().getIdentifier("imagenArtista" + i, "id", getPackageName());
            imageView[i-1] = findViewById(imageViewId);

            @SuppressLint("DiscouragedApi") int textViewId2 = getResources().getIdentifier("nombreArtista" + i, "id", getPackageName());
            TextView textView2 = findViewById(textViewId2);
            names.add(textView2);

            @SuppressLint("DiscouragedApi") int followersViewId = getResources().getIdentifier("followersArtist" + i, "id", getPackageName());

            followersText[i-1]= findViewById(followersViewId);

            @SuppressLint("DiscouragedApi") int genresViewId = getResources().getIdentifier("genresArtist" + i, "id", getPackageName());

            genresText[i-1]= findViewById(genresViewId);

            @SuppressLint("DiscouragedApi") int popularityViewId = getResources().getIdentifier("popularityArtist" + i, "id", getPackageName());

            popularityText[i-1]= findViewById(popularityViewId);

            setBoldText(genresText[i-1], "• Géneros:");
            setBoldText(followersText[i-1], "• Seguidores:");
            setBoldText(popularityText[i-1], "• Popularidad:");

            @SuppressLint("DiscouragedApi") int cardViewId = getResources().getIdentifier("cardArtist" + i, "id", getPackageName());

            cardViews[i-1] = findViewById(cardViewId);

            @SuppressLint("DiscouragedApi") int favButtonId = getResources().getIdentifier("favButton" + i, "id", getPackageName());

            favButton[i-1] = findViewById(favButtonId);
        }
        texto = dbHelper.getTextByName(aspecto);
        nombres = dbHelper.getArtist(aspecto);
        decriptionTextView.setText(texto);
        int index = 0;

        for (; index < 6; index++) {
            if(imageView[index].getDrawable() == null){
                new generosexplicados.SpotifyArtistSearchTask().execute(mAccessToken, nombres[index], String.valueOf(index));
            }
        }

        for (int i = 0; i < textViews.size(); i++) {
            names.get(i).setText(nombres[i]);
        }


        int numberOfCards=6;
        MaterialCardView[] cardViews = new MaterialCardView[numberOfCards];
        int[] nameViewIds = {R.id.nombreArtista1, R.id.nombreArtista2, R.id.nombreArtista3, R.id.nombreArtista4, R.id.nombreArtista5,
                R.id.nombreArtista6};
        for (int i = 0; i < numberOfCards; i++) {
            @SuppressLint("DiscouragedApi") int cardId = getResources().getIdentifier("cardArtist" + (i + 1), "id", getPackageName());
            cardViews[i] = findViewById(cardId);
            final int finalI = i;
            cardViews[i].setOnClickListener(v -> {
                TextView textView = findViewById(nameViewIds[finalI]);
                String nombreArtista = textView.getText().toString();
                abrirActividadConAspecto(nombreArtista);
            });
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class SpotifyArtistSearchTask extends AsyncTask<String, Void, String> {

        String imageUrl;

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length < 2) {
                Log.e("SpotifyArtistSearchTask", "Se requieren al menos dos parámetros: token y nombre del artista");
                return null;
            }

            String token = strings[0];
            try {
                String artistName = URLEncoder.encode(strings[1], "UTF-8");
                int index = Integer.parseInt(strings[2]);
                if(strings[1].equals("Los Alemanes")){
                    artistName = "pxxr+gvng";
                }
                String searchUrl = "https://api.spotify.com/v1/search?q=" + artistName + "&type=artist";

                URL url = new URL(searchUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();

                    String jsonResponse = responseBuilder.toString();
                    imageUrl = parseResponse(jsonResponse);
                    boolean imageLoaded = false;
                    for (String loadedImageUrl : images) {
                        if (loadedImageUrl != null && loadedImageUrl.equals(imageUrl)) {
                            imageLoaded = true;
                            break;
                        }
                    }

                    if (!imageLoaded) {
                        images[index] = imageUrl;
                    }

                    return imageUrl;
                } else {
                    Log.e("SpotifyArtistSearchTask", "Error al realizar la solicitud HTTP: " + responseCode);
                    return null;
                }

            } catch (IOException e) {
                Log.e("SpotifyArtistSearchTask", "Error al realizar la solicitud HTTP", e);
                return null;
            }
        }


        private String parseResponse(String jsonResponse) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject artists = jsonObject.getJSONObject("artists");
                JSONArray items = artists.getJSONArray("items");
                if (items.length() > 0) {
                    JSONObject firstArtist;

                        firstArtist = items.getJSONObject(0);

                        JSONArray imagesArray = firstArtist.getJSONArray("images");
                    if (imagesArray.length() > 0) {
                        JSONObject firstImage;
                            firstImage = imagesArray.getJSONObject(0);

                            genresArray = firstArtist.getJSONArray("genres");
                            followers = firstArtist.getJSONObject("followers").getInt("total");
                            popularity = firstArtist.getInt("popularity");
                            return firstImage.getString("url");
                    }
                }
            } catch (JSONException e) {
                Log.e("SpotifyArtistSearchTask", "Error al analizar la respuesta JSON", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String imageUrl) {

            if (imageUrl != null) {

                    Picasso.get()
                            .load(imageUrl)
                            .into(imageView[indice]);
                    obtenerColorDominante(imageUrl, indice);
                    for(int j=0; j<genresArray.length();j++){
                        try {
                            addNonBoldTextWithBackground(genresText[indice], genresArray.getString(j));

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                String formattedFollowers = decimalFormat.format(followers);

                addTextToTextView(followersText[indice], formattedFollowers);
                addTextToTextView(popularityText[indice], popularity + "/100");
                indice++;
            }
        }
    }


    private void abrirActividadConAspecto(String aspecto) {
        Intent intent = new Intent(generosexplicados.this, Albums.class);
        intent.putExtra("aspecto", aspecto);
        startActivity(intent);
    }
    private void setBoldText(TextView textView, String boldText) {
        SpannableString spannableString = new SpannableString(boldText);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, boldText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
    private void addTextToTextView(TextView textView, String newText) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText());
        spannableStringBuilder.append(" ").append(newText);
        textView.setText(spannableStringBuilder);
    }

    private void addNonBoldTextWithBackground(TextView textView, String newText) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(textView.getText());
        spannableStringBuilder.append(" ");

        SpannableString spannableString = new SpannableString(newText);

        int backgroundColor = Color.LTGRAY;
        int borderColor = Color.BLACK;
        int borderWidth = 2;
        int textColor = Color.BLACK;
        int padding = 16;

        RoundedBackgroundSpan roundedBackgroundSpan = new RoundedBackgroundSpan(backgroundColor, borderColor, borderWidth, textColor, padding);
        spannableString.setSpan(roundedBackgroundSpan, 0, newText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableStringBuilder.append(spannableString);
        textView.setText(spannableStringBuilder);
    }
    private void obtenerColorDominante(String imageUrl, int indice) {

        if (isFinishing() || isDestroyed()) {
            return;
        }
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            if (palette != null) {
                                List<Palette.Swatch> swatches = palette.getSwatches();
                                if (swatches.size() > 1) {
                                    // Crear una nueva lista modificable a partir de los swatches
                                    List<Palette.Swatch> swatchesModificable = new ArrayList<>(swatches);

                                    // Ordenar los swatches por población de colores en orden descendente
                                    swatchesModificable.sort((swatch1, swatch2) -> swatch2.getPopulation() - swatch1.getPopulation());

                                    // Obtener el segundo color más dominante
                                    int colorSegundoDominante = swatchesModificable.get(3).getRgb();

                                    // Usar el segundo color dominante para la UI
                                    if (isColorDark(colorSegundoDominante)) {
                                        followersText[indice].setTextColor(Color.WHITE);
                                        genresText[indice].setTextColor(Color.WHITE);
                                        popularityText[indice].setTextColor(Color.WHITE);
                                        names.get(indice).setTextColor(Color.WHITE);
                                    }
                                    cardViews[indice].setCardBackgroundColor(colorSegundoDominante);
                                } else if (swatches.size() > 0) {
                                    // Si solo hay un color disponible, usarlo como fallback
                                    int colorDominante = swatches.get(0).getRgb();
                                    if (isColorDark(colorDominante)) {
                                        followersText[indice].setTextColor(Color.WHITE);
                                        genresText[indice].setTextColor(Color.WHITE);
                                        popularityText[indice].setTextColor(Color.WHITE);
                                        names.get(indice).setTextColor(Color.WHITE);
                                        favButton[indice].setColorFilter(Color.WHITE);
                                    }
                                    cardViews[indice].setCardBackgroundColor(colorDominante);
                                }
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Handle cleanup if necessary
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        // Maneja la falla de carga aquí, si es necesario
                    }
                });
    }

    private boolean isColorDark(int color) {
        // Calculate the luminance of the color
        double luminance = (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return luminance < 0.5; // Consider color dark if luminance is less than 0.5
    }
}



