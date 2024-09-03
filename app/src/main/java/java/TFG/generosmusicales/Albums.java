package java.TFG.generosmusicales;



import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.TFG.generosmusicales.db.ArtistDatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import androidx.palette.graphics.Palette;



public class Albums extends AppCompatActivity {


    ArtistDatabaseHelper dbHelper;
    private final String[] linksArray = {null, null, null, null};

    private WebView webView, webView2, webView3, webView4;
    private final String[] images = {null, null, null, null};
    final ImageView [] imageView = {null, null, null, null};
    private final MaterialCardView [] cardView = {null, null, null, null};

    TextView[] textAlbums = {null, null, null, null};
    String releaseYear;

    int indice = 0;
    TextView[] descriptionAlbums = {null, null, null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);

        setContentView(R.layout.albums);
        String artista = getIntent().getStringExtra("aspecto");
        TextView tituloTextView;
        tituloTextView = findViewById(R.id.titleTextView);
        TitleBackgroundRandomizer.applyRandomTitleBackground(this, tituloTextView);
        TextView parrafoDescripcion = findViewById(R.id.paragraphTextView);
        dbHelper = new ArtistDatabaseHelper(getApplicationContext());
        String[] albums, descriptions;

        String name = dbHelper.getNameWIthoutNormalize(artista);
            tituloTextView.setText(name);
            albums = dbHelper.getAlbumsByName(artista);
            parrafoDescripcion.setText(dbHelper.getText2ByName(artista));
            descriptions = dbHelper.getDescriptionsByName(artista);            // Realizar consultas o actualizaciones en la base de datos

        for (int i = 0; i < textAlbums.length; i++) {
            @SuppressLint("DiscouragedApi") int resId = getResources().getIdentifier("nombreAlbum" + (i + 1), "id", getPackageName());
            @SuppressLint("DiscouragedApi") int resId2 = getResources().getIdentifier("textoAlbum" + (i + 1), "id", getPackageName());
            @SuppressLint("DiscouragedApi") int resId3 = getResources().getIdentifier("imageAlbum" + (i + 1), "id", getPackageName());
            @SuppressLint("DiscouragedApi") int resId4 = getResources().getIdentifier("cardAlbum" + (i + 1), "id", getPackageName());

            textAlbums[i] = findViewById(resId);
            descriptionAlbums[i] = findViewById(resId2);
            textAlbums[i].setText(albums[i]);
            descriptionAlbums[i].setText(descriptions[i]);

            imageView[i] = findViewById(resId3);
            cardView[i] = findViewById(resId4);
        }
        String mAccessToken = AccessTokenManager.getInstance(getApplicationContext()).getAccessToken();

        int i = 0;

        for (; i <= 3; i++) {
            new Albums.SpotifyAlbumSearchTask().execute(mAccessToken, albums[i] + " " + artista, String.valueOf(i));
        }
        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                final String[] resources = request.getResources();
                for (String resource : resources) {
                    if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID.equals(resource)) {
                        request.grant(new String[]{resource});
                        return;
                    }
                }
                super.onPermissionRequest(request);
            }
        });


        webView2 = findViewById(R.id.webView2);
        webView2.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                final String[] resources = request.getResources();
                for (String resource : resources) {
                    if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID.equals(resource)) {
                        request.grant(new String[]{resource});
                        return;
                    }
                }
                super.onPermissionRequest(request);
            }
        });
        webView3 = findViewById(R.id.webView3);
        webView3.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                final String[] resources = request.getResources();
                for (String resource : resources) {
                    if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID.equals(resource)) {
                        request.grant(new String[]{resource});
                        return;
                    }
                }
                super.onPermissionRequest(request);
            }
        });
        webView4 = findViewById(R.id.webView4);
        webView4.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                final String[] resources = request.getResources();
                for (String resource : resources) {
                    if (PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID.equals(resource)) {
                        request.grant(new String[]{resource});
                        return;
                    }
                }
                super.onPermissionRequest(request);
            }
        });

        int[] buttonIds = {
                R.id.rym_button1,
                R.id.rym_button2,
                R.id.rym_button3,
                R.id.rym_button4
        };

        for (int j = 0; j < buttonIds.length; j++) {
            MaterialButton button = findViewById(buttonIds[j]);
            String nombreArtistacod, nombreAlbumcod;
            try {
                nombreArtistacod = URLEncoder.encode(TextUtils.normalize(artista), "UTF-8").replace("+", "-");
                nombreAlbumcod = URLEncoder.encode(TextUtils.normalize(albums[j]), "UTF-8").replace("+", "-");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String url ="https://rateyourmusic.com/release/album/" + nombreArtistacod + "/" + nombreAlbumcod + "/";

            button.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            });
        }

        progressBar.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        WebView[] webViews = {webView, webView2, webView3, webView4};

        for (int i = 0; i < webViews.length; i++) {
            String spotifyUrl = "https://open.spotify.com/embed/album/" + linksArray[i];
            configurarWebView(webViews[i], spotifyUrl);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void configurarWebView(WebView currentWebView, String spotifyUrl) {
        currentWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        currentWebView.getSettings().setJavaScriptEnabled(true);
        currentWebView.setBackgroundColor(Color.BLACK);

        currentWebView.setWebViewClient(new WebViewClient() {
            @SuppressLint("ObsoleteSdkInt")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Método para compatibilidad con versiones anteriores de Android
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    return handleUrl(view, url);
                }
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Método para versiones nuevas de Android
                return handleUrl(view, request.getUrl().toString());
            }

            private boolean handleUrl(WebView view, String url) {
                if (url.startsWith("spotify://")) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException e) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/"));
                        view.getContext().startActivity(intent);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:document.body.style.setProperty(\"background\", \"black\")");  // Set the background of the HTML content to black
            }
        });

        String htmlData = "<html><head><style>body { margin: 0dp; padding: 0; }</style></head>" +
                "<body style=\"margin:0;padding:0;\"><iframe src=\"" + spotifyUrl + "\" width=\"100%\" height=\"100%\" allow=\"encrypted-media;\"></iframe></body></html>";

        currentWebView.setOverScrollMode(WebView.OVER_SCROLL_IF_CONTENT_SCROLLS);

        currentWebView.getSettings().setJavaScriptEnabled(true);
        currentWebView.setScrollContainer(false);
        currentWebView.setScaleY(1.015F);
        currentWebView.setScaleX(1.01F);
        currentWebView.setScrollY(-100);
        currentWebView.setBackgroundColor(Color.TRANSPARENT);

        currentWebView.loadDataWithBaseURL(
                "https://open.spotify.com",
                htmlData,
                "text/html",
                "UTF-8",
                null
        );

        currentWebView.setTag(spotifyUrl); // Guardar la URL cargada

        currentWebView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class SpotifyAlbumSearchTask extends AsyncTask<String, Void, String> {

        String imageUrl;

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length < 2) {
                Log.e("SpotifyAlbumSearchTask", "Se requieren al menos dos parámetros: token y nombre del álbum");
                return null;
            }
            String albumName;
            String token = strings[0];
            try {

                albumName = URLEncoder.encode(strings[1], "UTF-8");
                if(albumName.equals("Hammu+Nation+Delaossa")){
                    albumName = "Hammu+Nation";
                }
                if(strings[1].equals("El Círculo Violadores del Verso")){
                    albumName = "El+C%C3%ADrculo+Kase+O";
                }

                int index = Integer.parseInt(strings[2]);

                String searchUrl = "https://api.spotify.com/v1/search?q=" + albumName + "&type=album";

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
                    String albumId = parseResponse(jsonResponse);
                    reader.close();
                    JSONObject albumJson;
                    try {
                        albumJson = new JSONObject(jsonResponse);
                        JSONObject albumsObject = albumJson.getJSONObject("albums");
                        JSONArray itemsArray = albumsObject.getJSONArray("items");

                        JSONObject firstAlbum = itemsArray.getJSONObject(0);
                        String releaseDate = firstAlbum.getString("release_date");
                        releaseYear = releaseDate.split("-")[0];

                        JSONArray imagesArray = firstAlbum.getJSONArray("images");

                        JSONObject firstImage = imagesArray.getJSONObject(0);
                        imageUrl = firstImage.getString("url");
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
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    linksArray[index] = albumId;

                    return albumId;
                } else {
                    Log.e("SpotifyAlbumSearchTask", "Error al realizar la solicitud HTTP: " + responseCode);
                    return null;
                }

            } catch (IOException e) {
                Log.e("SpotifyAlbumSearchTask", "Error al realizar la solicitud HTTP", e);
                return null;
            }


        }

        @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
        @Override
        protected void onPostExecute(String albumId) {
            if (albumId != null) {

                WebView[] webViews = {webView, webView2, webView3, webView4};
                String spotifyUrl;

                if (indice < webViews.length) {
                    WebView currentWebView = webViews[indice];
                    String spotifyLink = linksArray[indice];
                    spotifyUrl = "https://open.spotify.com/embed/album/" + spotifyLink;

                    // Verificar si la URL ya está cargada
                    if (!spotifyUrl.equals(currentWebView.getTag())) {

                        currentWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                        currentWebView.getSettings().setJavaScriptEnabled(true);
                        currentWebView.setBackgroundColor(Color.BLACK);
                        currentWebView.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                if (url.startsWith("spotify://")) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                        return true;
                                    } catch (ActivityNotFoundException e) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/"));
                                        startActivity(intent);
                                        return true;
                                    }
                                }
                                return false;
                            }
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                view.loadUrl("javascript:document.body.style.setProperty(\"background\", \"black\")");  // Set the background of the HTML content to black
                            }
                        });

                        String htmlData = "<html><head><style>body { margin: 0dp; padding: 0; }</style></head>" +
                                "<body style=\"margin:0;padding:0;\"><iframe src=\"" + spotifyUrl + "\" width=\"100%\" height=\"100%\" allow=\"encrypted-media;\"></iframe></body></html>";

                        currentWebView.setOverScrollMode(WebView.OVER_SCROLL_IF_CONTENT_SCROLLS);

                        currentWebView.setScrollContainer(false);
                        currentWebView.setScaleY(1.015F);
                        currentWebView.setScaleX(1.01F);
                        currentWebView.setScrollY(-100);
                        currentWebView.setBackgroundColor(Color.TRANSPARENT);

                        currentWebView.loadDataWithBaseURL(
                        "https://open.spotify.com",
                        htmlData,
                        "text/html",
                        "UTF-8",
                        null
                        );

                        currentWebView.setTag(spotifyUrl); // Guardar la URL cargada

                        currentWebView.setOnTouchListener((v, event) -> {
                            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                                v.getParent().requestDisallowInterceptTouchEvent(true);
                            }
                            return false;
                        });
                        obtenerColorDominante(imageUrl, indice);

                        Picasso.get()
                                .load(images[indice])
                                .into(imageView[indice]);
                    }

                    String currentText = textAlbums[indice].getText().toString();
                    String updatedText = currentText + " (" + releaseYear + ")";
                    textAlbums[indice].setText(updatedText);

                    indice++;
                } else {
                    Log.d("SpotifyAlbumSearchTask", "Índice fuera de rango");
                }
            } else {
                Log.d("SpotifyAlbumSearchTask", "No se encontró el álbum");
            }
        }

        private String parseResponse(String jsonResponse) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONObject albums = jsonObject.getJSONObject("albums");
                JSONArray items = albums.getJSONArray("items");
                if (items.length() > 0) {
                    JSONObject firstAlbum = items.getJSONObject(0);
                    return firstAlbum.getString("id");
                } else {
                    return null;
                }
            } catch (JSONException e) {
                Log.e("SpotifyAlbumSearchTask", "Error al analizar la respuesta JSON", e);
                return null;
            }
        }
    }

    private void obtenerColorDominante(String imageUrl, int indice) {
        if (isActivityDestroyed()) {
            return; // No iniciar la carga si la actividad está destruida
        }

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(palette -> {
                            if (palette != null) {
                                int colorDominante = palette.getDominantColor(Color.BLACK);
                                if (isColorDark(colorDominante)) {
                                    textAlbums[indice].setTextColor(Color.WHITE);
                                    descriptionAlbums[indice].setTextColor(Color.WHITE);
                                }
                                cardView[indice].setCardBackgroundColor(colorDominante);
                            }
                        });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Handle cleanup if necessary
                    }
                });
    }

    // Método para verificar si la actividad está destruida porque había veces que petaba la aplicación
    private boolean isActivityDestroyed() {
        return isDestroyed() || isFinishing();
    }
    private boolean isColorDark(int color) {
        // Calculate the luminance of the color
        double luminance = (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return luminance < 0.5; // Consider color dark if luminance is less than 0.5
    }


}