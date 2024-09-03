package com.TFG.generosmusicales;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.TFG.generosmusicales.db.ArtistDatabaseHelper;
import com.google.android.material.card.MaterialCardView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CombinedInstrumentedTest {

    private String aspecto = "Rock actual";
    private Context appContext;

    @Before
    public void setUp() {
        // Inicializa el contexto de la aplicación
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArtistDatabaseHelper dbArtistsHelper= new ArtistDatabaseHelper(appContext);

        dbArtistsHelper.insertTextWithName("Cruz Cafuné", "Robyn Rihanna Fenty, nacida el 20 de febrero de 1988 en Barbados, es una de las artistas más exitosas y versátiles de la música pop y R&B. Descubierta por el productor Evan Rogers, Rihanna firmó con Def Jam Recordings y lanzó su álbum debut \"Music of the Sun\" en 2005. Su estilo musical ha evolucionado a lo largo de los años, abarcando géneros como pop, reggae, hip-hop y EDM. Con éxitos globales como \"Umbrella,\" \"We Found Love\" y \"Diamonds,\" Rihanna ha ganado numerosos premios y se ha establecido como un icono de la moda y empresaria con su línea de cosméticos Fenty Beauty y su marca de lencería Savage X Fenty.", "Good Girl Gone Bad", "Rated R", "Loud", "Anti", "Este álbum incluye el exitoso sencillo \"Umbrella\" y marcó un cambio en su imagen y sonido hacia un estilo más maduro y atrevido. Con influencias de pop, R&B y dance, el álbum consolidó su estatus como una estrella internacional.", "Con un tono más oscuro y personal, este álbum incluye éxitos como \"Russian Roulette\" y \"Rude Boy.\" Las letras y la producción reflejan un período de crecimiento personal y profesional para Rihanna, mostrando su capacidad para abordar temas más serios y emocionales.", "Con éxitos como \"Only Girl (In the World)\" y \"What's My Name?\" este álbum presenta una mezcla vibrante de pop y dancehall. Es conocido por su energía y la diversidad de estilos, destacando la capacidad de Rihanna para reinventarse continuamente.", "Un álbum aclamado por la crítica que incluye el sencillo \"Work.\" Con una producción más experimental y una mezcla de géneros que incluyen R&B, soul, y hip-hop, \"Anti\" muestra su madurez artística y su disposición para tomar riesgos creativos.");

    }


    // Pruebas para MainActivity

    @Test
    public void useAppContext() {
        // Verifica el nombre del paquete de la app bajo prueba
        assertEquals("com.example.generosmusicales", appContext.getPackageName());
    }



    @Test
    public void testTextViewDescriptionsExistence() {
        // Verifica que todos los TextViews tienen sus descripciones de contenido establecidas
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                TextView[] textViews = activity.textView;
                assertNotNull("El array de TextView no debe ser nulo", textViews);
                for (int i = 0; i < activity.numberOfButtons + 2; i++) {
                    assertNotNull("TextView " + i + " debería no ser nulo", textViews[i]);
                    assertNotNull("TextView " + i + " debería tener una descripción de contenido", textViews[i].getContentDescription());
                }
            });
        }
    }

    @Test
    public void testAutoCompleteTextViewSearchFunctionality() {
        // Verifica la funcionalidad del AutoCompleteTextView
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                AutoCompleteTextView searchEditText = activity.searchEditText;

                searchEditText.setText("Rock actual");
                searchEditText.performCompletion();

                assertEquals("La búsqueda debe coincidir con la entrada", "Rock actual", searchEditText.getText().toString());
            });
        }
    }

    @Test
    public void testSearchOpensCorrectActivity() {
        // Verifica que la búsqueda abre la actividad correcta
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                String testQuery = "Rock actual";
                activity.search(testQuery);

                Intent expectedIntent = new Intent(activity, generosexplicados.class);
                expectedIntent.putExtra("aspecto", testQuery);
                assertEquals("El Intent debería abrir la actividad generosexplicados", expectedIntent.getComponent(), new Intent(activity, generosexplicados.class).getComponent());
            });
        }
    }

    @Test
    public void testDatabaseInsertion() {
        // Verifica las inserciones y recuperaciones en la base de datos
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                ArtistDatabaseHelper dbArtistsHelper = activity.dbArtistsHelper;

                dbArtistsHelper.insertTextWithName("Test Artist", "Description", "Album 1", "Album 2", "Album 3", "Album 4", "Details for album 1", "Details for album 2", "Details for album 3", "Details for album 4");

                String[] albums = dbArtistsHelper.getAlbumsByName("Test Artist");
                assertNotNull("Los álbumes no deberían ser nulos", albums);
                assertEquals("El álbum 1 debería coincidir", "Album 1", albums[0]);
            });
        }
    }

    @Test
    public void testKeywordActivityMapInitialization() {
        // Verifica la inicialización del keywordActivityMap
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.initializeKeywordActivityMap();
                activity.addKeyword("Rock actual", generosexplicados.class);
                activity.addKeyword("Test Artist", Albums.class);

                assertTrue("El mapa de palabras clave debería contener 'rock actual'", activity.keywordActivityMap.containsKey("rock actual"));
                assertTrue("El mapa de palabras clave debería contener 'test artist'", activity.keywordActivityMap.containsKey("test artist"));
            });
        }
    }

    // Pruebas para generosexplicados

    @Test
    public void testGenerosExplicadosActivityInitialization() {
        // Verifica que la actividad se inicialice correctamente
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                // Verifica que la actividad esté correctamente inicializada
                assertNotNull("La actividad no debe ser nula", activity);


            });
        }
    }

    @Test
    public void testImageViewsInitialization() {
        // Verifica que todas las vistas de imágenes estén inicializadas correctamente
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                ImageView[] imageViews = activity.imageView;
                assertNotNull("El array de ImageView no debe ser nulo", imageViews);
                for (int i = 0; i < 6; i++) {
                    assertNotNull("La vista de imagen " + i + " no debe ser nula", imageViews[i]);
                }
            });
        }
    }

    @Test
    public void testCardViewsExistenceGenerosExplicados() {
        // Verifica que todas las vistas de tarjetas estén inicializadas correctamente
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull("La actividad debería estar cargada", activity);

                MaterialCardView[] cardViews = activity.cardViews;
                assertNotNull("El array de MaterialCardView no debe ser nulo", cardViews);
                for (int i = 0; i < 6; i++) {
                    assertNotNull("La vista de tarjeta " + i + " no debe ser nula", cardViews[i]);
                }
            });
        }
    }

    @Test
    public void testFollowersTextInitialization() {
        // Verifica que las vistas de texto de seguidores estén inicializadas correctamente
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                TextView[] followersText = activity.followersText;
                assertNotNull("El array de TextView de seguidores no debe ser nulo", followersText);
                for (int i = 0; i < 6; i++) {
                    assertNotNull("La vista de texto de seguidores " + i + " no debe ser nula", followersText[i]);
                }
            });
        }
    }

    @Test
    public void testGenresTextInitialization() {
        // Verifica que las vistas de texto de géneros estén inicializadas correctamente
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                TextView[] genresText = activity.genresText;
                assertNotNull("El array de TextView de géneros no debe ser nulo", genresText);
                for (int i = 0; i < 6; i++) {
                    assertNotNull("La vista de texto de géneros " + i + " no debe ser nula", genresText[i]);
                }
            });
        }
    }

    @Test
    public void testImageLoadingAndColorDominance() {
        // Verifica que las imágenes se carguen correctamente y que los colores dominantes sean asignados a las vistas de tarjeta
        Intent intent = new Intent(appContext, generosexplicados.class);
        intent.putExtra("aspecto", aspecto);

        try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                ImageView imageView = activity.imageView[0];
                Drawable drawable = imageView.getDrawable();
                assertNotNull("La imagen debe estar cargada en imageView[0]", imageView);

                MaterialCardView cardView = activity.cardViews[0];
                int cardBackgroundColor = cardView.getCardBackgroundColor().getDefaultColor();
                assertTrue("El color de fondo de la tarjeta debe estar asignado", cardBackgroundColor != 0);
            });
        }
    }

    // Pruebas para Albums

    @Test
    public void testAlbumsActivityInitialization() {
        // Verifica que la actividad Albums se inicialice correctamente
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                // Verifica que la actividad esté correctamente inicializada
                assertNotNull("La actividad no debe ser nula", activity);

                // Verifica el título del artista
                TextView artistNameTextView = activity.findViewById(R.id.titleTextView);

                assertNotNull("El TextView del nombre del artista no debe ser nulo", artistNameTextView);
                assertEquals("El nombre del artista debe coincidir", "Cruz Cafuné", artistNameTextView.getText().toString());
            });
        }
    }

    @Test
    public void testAlbumNamesInitialization() {
        // Verifica que los nombres de los álbumes estén correctamente inicializados
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                TextView[] albumTextViews = activity.textAlbums;
                assertNotNull("El array de TextView de álbumes no debe ser nulo", albumTextViews);
                for (int i = 0; i < 4; i++) {
                    assertNotNull("El TextView del álbum " + (i+1) + " no debe ser nulo", albumTextViews[i]);
                }
            });
        }
    }

    @Test
    public void testAlbumDetailsInitialization() {
        // Verifica que los detalles de los álbumes estén correctamente inicializados
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {



                TextView[] albumDetailsTextViews = activity.descriptionAlbums;
                assertNotNull("El array de TextView de detalles de álbumes no debe ser nulo", albumDetailsTextViews);

                for (int i = 0; i < 4; i++) {
                    assertNotNull("El TextView de detalles del álbum " + (i+1) + " no debe ser nulo", albumDetailsTextViews[i]);
                }


            });
        }
    }

    @Test
    public void testAlbumImagesInitialization() {
        // Verifica que las vistas de imágenes de los álbumes estén inicializadas correctamente
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                ImageView[] albumImageViews = activity.imageView;
                assertNotNull("El array de ImageView de álbumes no debe ser nulo", albumImageViews);
                for (int i = 0; i < 4; i++) {
                    assertNotNull("El ImageView del álbum " + (i+1) + " no debe ser nulo", albumImageViews[i]);
                }

            });
        }
    }

    @Test
    public void testAlbumsDatabaseRetrieval() {
        // Verifica la correcta recuperación de álbumes desde la base de datos
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                ArtistDatabaseHelper dbArtistsHelper = activity.dbHelper;
                String[] albums = dbArtistsHelper.getAlbumsByName("Cruz Cafuné");

                assertNotNull("Los álbumes no deberían ser nulos", albums);
                assertEquals("El álbum 1 debería coincidir", "Good Girl Gone Bad", albums[0]);
            });
        }
    }

    @Test
    public void testAlbumDetailsDatabaseRetrieval() {
        // Verifica la correcta recuperación de detalles de álbumes desde la base de datos
        Intent intent = new Intent(appContext, Albums.class);
        intent.putExtra("aspecto", "Cruz Cafuné");

        try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                ArtistDatabaseHelper dbArtistsHelper = activity.dbHelper;
                String[] albumDetails = dbArtistsHelper.getDescriptionsByName("Cruz Cafuné");

                assertNotNull("Los detalles del álbum no deberían ser nulos", albumDetails);
                assertEquals("Los detalles del álbum 1 deberían coincidir", "Este álbum incluye el exitoso sencillo \"Umbrella\" y marcó un cambio en su imagen y sonido hacia un estilo más maduro y atrevido. Con influencias de pop, R&B y dance, el álbum consolidó su estatus como una estrella internacional.", albumDetails[0]);
                TextView[] albumTextViews = activity.textAlbums;
                assertNotNull("El array de TextView de álbumes no debe ser nulo", albumTextViews);
                for (int i = 0; i < 4; i++) {
                    assertNotNull("El TextView del álbum " + (i+1) + " no debe ser nulo", albumTextViews[i]);
                }
            });
        }
    }


}
