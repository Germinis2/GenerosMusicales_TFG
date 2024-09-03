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
public class TestVarios {

    private String[] genres = {
            "Rock actual", "Pop siglo XX", "Jazz", "Rap de los 90", "Country",
            "Rock siglo XX", "Rap actual en español", "Urbana actual", "Electrónica", "Heavy"
    };
    private String[] artistas = {
            "Rihanna", "Beyoncé", "Aretha Franklin", "Miles Davis", "Mda",
            "Patsy Cline", "Snoop Dogg", "Nirvana", "Elton John", "Miles Davis"
    };
    private Context appContext;

    @Before
    public void setUp() {
        // Inicializa el contexto de la aplicación
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArtistDatabaseHelper dbArtistsHelper = new ArtistDatabaseHelper(appContext);

        dbArtistsHelper.insertTextWithName("Nirvana", "Nirvana, formada en Aberdeen, Washington, en 1987, es una de las bandas más influyentes del grunge y del rock alternativo. Liderada por Kurt Cobain, con Krist Novoselic y Dave Grohl, la banda alcanzó la fama mundial con su segundo álbum, \"Nevermind\". Su música, caracterizada por su crudo sonido y letras introspectivas, resonó con una generación desencantada. La trágica muerte de Cobain en 1994 marcó el fin de la banda, pero su legado sigue vivo a través de su música y su influencia en el rock.", "Nevermind", "In Utero", "Bleach", "MTV Unplugged in New York", "Este álbum catapultó a Nirvana a la fama global con éxitos como \"Smells Like Teen Spirit\" y \"Come as You Are\". Es conocido por su sonido crudo y su energía explosiva, que definieron el grunge y capturaron el espíritu de una generación.", "Una respuesta deliberadamente más abrasiva a su éxito anterior, con temas como \"Heart-Shaped Box\" y \"All Apologies\". El álbum aborda temas de dolor y frustración, y es una muestra de la evolución artística y personal de Cobain.", "El álbum debut de Nirvana, grabado con un presupuesto limitado, muestra un sonido más pesado y sucio. Canciones como \"About a Girl\" y \"Negative Creep\" son ejemplos tempranos de su capacidad para combinar melodía y agresión.", "Una actuación acústica íntima que captura la vulnerabilidad y el talento de Cobain. Con versiones de sus propias canciones y covers de artistas como David Bowie, este álbum es una despedida conmovedora de una de las bandas más importantes del rock.");
        dbArtistsHelper.insertTextWithName("Elton John",
                "Elton John es un cantante, compositor y pianista británico, conocido por su extravagante estilo y su impresionante talento musical. A lo largo de su carrera, ha vendido más de 300 millones de discos, convirtiéndose en uno de los artistas más vendidos de todos los tiempos. John es famoso por sus numerosos éxitos, como 'Rocket Man' y 'Your Song', y ha colaborado con el letrista Bernie Taupin durante más de 50 años. Además de su éxito musical, John es conocido por su trabajo filantrópico, especialmente en la lucha contra el SIDA. Su carrera ha sido reconocida con numerosos premios y honores.",
                "Goodbye Yellow Brick Road", "Captain Fantastic and the Brown Dirt Cowboy", "Madman Across the Water", "Honky Château",
                "'Goodbye Yellow Brick Road' (1973) es un álbum doble icónico que incluye éxitos como 'Bennie and the Jets' y 'Candle in the Wind'. Este álbum consolidó a John como una superestrella internacional y es considerado uno de sus mejores trabajos.",
                "'Captain Fantastic and the Brown Dirt Cowboy' (1975) es un álbum autobiográfico que detalla la historia de John y Taupin. Con canciones como 'Someone Saved My Life Tonight', el álbum fue un éxito crítico y comercial.",
                "'Madman Across the Water' (1971) incluye el clásico 'Tiny Dancer' y muestra la habilidad de John para combinar melodía y letras profundas. El álbum es conocido por su producción rica y emotiva.",
                "'Honky Château' (1972) fue un gran éxito con canciones como 'Rocket Man' y 'Honky Cat'. El álbum marcó el inicio de la era más prolífica de John, con una mezcla de rock, pop y baladas.");

        dbArtistsHelper.insertTextWithName("Patsy Cline",  "Patsy Cline (1932-1963) es una de las figuras más icónicas en la música country y pop, conocida por su emotiva voz y su habilidad para transmitir una profunda tristeza y vulnerabilidad en sus interpretaciones. Nacida como Virginia Patterson Hensley en Winchester, Virginia, Cline comenzó a cantar a una edad temprana y rápidamente se destacó por su talento vocal. Su carrera despegó en la década de 1950 y 1960 con éxitos como \"Walkin' After Midnight,\" \"I Fall to Pieces,\" \"Crazy,\" y \"She's Got You.\" Cline fue pionera en cruzar las barreras entre la música country y el pop, logrando un éxito que trascendió géneros. Su vida y carrera fueron trágicamente interrumpidas por un accidente aéreo en 1963, pero su influencia en la música persiste, y su legado continúa inspirando a artistas en diversos géneros.", "Patsy Cline", "Showcase", "Sentimentally Yours", "The Patsy Cline Story", "Este álbum debut muestra su capacidad para fusionar el country con elementos de pop, blues y honky-tonk. Con canciones como \"Walkin' After Midnight,\" que se convirtió en su primer gran éxito, el álbum destaca su distintiva voz y su habilidad para interpretar con profunda emoción. Esta grabación inicial estableció a Cline como una fuerza emergente en la música country.", "Con el respaldo de The Jordanaires, este álbum incluye algunos de sus mayores éxitos como \"I Fall to Pieces\" y \"Crazy,\" escrito por Willie Nelson. \"Crazy\" es particularmente notable por su melodía suave y la interpretación apasionada de Cline, consolidando su estatus como una de las mejores vocalistas de su tiempo. El álbum muestra su capacidad para interpretar baladas con un estilo único que resonó tanto en la audiencia country como en la pop.", "Este álbum muestra su habilidad para manejar una variedad de estilos musicales, desde el country hasta el pop y el jazz. Con éxitos como \"She's Got You\" y \"Heartaches,\" el álbum destaca la versatilidad vocal de Cline y su capacidad para hacer que cada canción se sienta personal y conmovedora. La producción sofisticada y los arreglos orquestales añadieron una nueva dimensión a su música, haciendo de este álbum un clásico atemporal.", "Un álbum recopilatorio lanzado después de su muerte, que incluye muchos de sus éxitos más grandes como \"Sweet Dreams (Of You)\" y \"Faded Love.\" Esta colección destaca su breve pero impactante carrera, proporcionando una visión completa de su evolución como artista. Es una retrospectiva esencial que captura la esencia de su talento y su legado duradero en la música country y más allá.");
        dbArtistsHelper.insertTextWithName("Snoop Dogg",
                "Snoop Dogg, nacido Calvin Cordozar Broadus Jr. el 20 de octubre de 1971, es un rapero, cantante, productor y actor estadounidense. Descubierto por Dr. Dre, Snoop Dogg se convirtió en uno de los artistas más icónicos del hip-hop de la costa oeste. Con su voz distintiva y estilo relajado, ha dejado una marca indeleble en la música y la cultura popular.",
                "Doggystyle",
                "Tha Doggfather",
                "R&G (Rhythm & Gangsta): The Masterpiece",
                "Tha Blue Carpet Treatment",
                "Doggystyle es el álbum debut de Snoop Dogg, lanzado en 1993, y es considerado un clásico del hip-hop. Producido por Dr. Dre, el álbum incluye éxitos como 'Gin and Juice' y 'Who Am I (What's My Name)?'. Su estilo distintivo y producción impecable lo han convertido en una pieza fundamental del género.",
                "Tha Doggfather es el segundo álbum de Snoop Dogg, lanzado en 1996. Aunque menos exitoso que su debut, este álbum muestra su habilidad para evolucionar y experimentar con nuevos sonidos. Canciones como 'Snoop's Upside Ya Head' y 'Vapors' destacan su versatilidad y creatividad.",
                "R&G (Rhythm & Gangsta): The Masterpiece es un álbum que combina el hip-hop con elementos de R&B, lanzado en 2004. Con éxitos como 'Drop It Like It's Hot' y 'Signs', este álbum refleja la capacidad de Snoop Dogg para mantenerse relevante y continuar innovando en su carrera.",
                "Tha Blue Carpet Treatment, lanzado en 2006, es un álbum que retorna a las raíces del hip-hop de la costa oeste. Con colaboraciones de artistas como Dr. Dre y Ice Cube, y canciones como 'That's That Shit' y 'Vato', este álbum muestra la madurez y el dominio de Snoop Dogg en el género."
        );
        dbArtistsHelper.insertTextWithName("Cruz Cafuné",
                "Robyn Rihanna Fenty, nacida el 20 de febrero de 1988 en Barbados, es una de las artistas más exitosas y versátiles de la música pop y R&B. Descubierta por el productor Evan Rogers, Rihanna firmó con Def Jam Recordings y lanzó su álbum debut \"Music of the Sun\" en 2005. Su estilo musical ha evolucionado a lo largo de los años, abarcando géneros como pop, reggae, hip-hop y EDM. Con éxitos globales como \"Umbrella,\" \"We Found Love\" y \"Diamonds,\" Rihanna ha ganado numerosos premios y se ha establecido como un icono de la moda y empresaria con su línea de cosméticos Fenty Beauty y su marca de lencería Savage X Fenty.",
                "Good Girl Gone Bad", "Rated R", "Loud", "Anti",
                "Este álbum incluye el exitoso sencillo \"Umbrella\" y marcó un cambio en su imagen y sonido hacia un estilo más maduro y atrevido.",
                "Con éxitos como \"Russian Roulette\" y \"Rude Boy\", muestra una producción más experimental.",
                "Con éxitos como \"Only Girl (In the World)\", presenta una mezcla vibrante de pop y dancehall.",
                "Un álbum aclamado por la crítica que incluye el sencillo \"Work\", con géneros como R&B, soul, y hip-hop.");
        dbArtistsHelper.insertTextWithName("Rihanna", "Robyn Rihanna Fenty, nacida el 20 de febrero de 1988 en Barbados, es una de las artistas más exitosas y versátiles de la música pop y R&B. Descubierta por el productor Evan Rogers, Rihanna firmó con Def Jam Recordings y lanzó su álbum debut \"Music of the Sun\" en 2005. Su estilo musical ha evolucionado a lo largo de los años, abarcando géneros como pop, reggae, hip-hop y EDM. Con éxitos globales como \"Umbrella,\" \"We Found Love\" y \"Diamonds,\" Rihanna ha ganado numerosos premios y se ha establecido como un icono de la moda y empresaria con su línea de cosméticos Fenty Beauty y su marca de lencería Savage X Fenty.", "Good Girl Gone Bad", "Rated R", "Loud", "Anti", "Este álbum incluye el exitoso sencillo \"Umbrella\" y marcó un cambio en su imagen y sonido hacia un estilo más maduro y atrevido. Con influencias de pop, R&B y dance, el álbum consolidó su estatus como una estrella internacional.", "Con un tono más oscuro y personal, este álbum incluye éxitos como \"Russian Roulette\" y \"Rude Boy.\" Las letras y la producción reflejan un período de crecimiento personal y profesional para Rihanna, mostrando su capacidad para abordar temas más serios y emocionales.", "Con éxitos como \"Only Girl (In the World)\" y \"What's My Name?\" este álbum presenta una mezcla vibrante de pop y dancehall. Es conocido por su energía y la diversidad de estilos, destacando la capacidad de Rihanna para reinventarse continuamente.", "Un álbum aclamado por la crítica que incluye el sencillo \"Work.\" Con una producción más experimental y una mezcla de géneros que incluyen R&B, soul, y hip-hop, \"Anti\" muestra su madurez artística y su disposición para tomar riesgos creativos.");
        dbArtistsHelper.insertTextWithName("Beyoncé", "Beyoncé Giselle Knowles-Carter, nacida el 4 de septiembre de 1981 en Houston, Texas, es una de las artistas más influyentes y exitosas de la música contemporánea. Comenzó su carrera como la vocalista principal del grupo Destiny's Child en los años 90, alcanzando el estrellato con éxitos como \"Say My Name\" y \"Survivor.\" En 2003, lanzó su carrera en solitario con el álbum \"Dangerously in Love,\" que consolidó su estatus como una superestrella global. Con una voz poderosa y una presencia escénica inigualable, Beyoncé ha sido galardonada con numerosos premios Grammy y ha vendido millones de discos en todo el mundo. Es conocida por su activismo y su trabajo en pro de los derechos de las mujeres y la comunidad afroamericana.", "Dangerously in Love", "B'Day", "Lemonade", "4", "El álbum debut de Beyoncé como solista incluye éxitos como \"Crazy in Love\" y \"Baby Boy.\" Este disco muestra su versatilidad vocal y su capacidad para fusionar R&B con pop, hip-hop y soul. Fue un éxito crítico y comercial, ganando cinco premios Grammy.", "Publicado el día de su cumpleaños, este álbum incluye temas como \"Irreplaceable\" y \"Déjà Vu.\" Es conocido por sus fuertes influencias de funk y soul, así como por su energía y producción sofisticada. Beyoncé ganó varios premios Grammy por este trabajo.", "Este álbum visual es uno de sus trabajos más aclamados, explorando temas de traición, feminismo y empoderamiento negro. Canciones como \"Formation\" y \"Sorry\" destacan en una mezcla de géneros que van desde el R&B hasta el rock y el country. \"Lemonade\" es considerado un hito cultural y musical.", "Un álbum que muestra su madurez artística, con canciones como \"Run the World (Girls)\" y \"Love on Top.\" Fusiona elementos de R&B, soul, funk y pop, y destaca por su producción y la profundidad emocional de las letras.");
        dbArtistsHelper.insertTextWithName("Aretha Franklin", "Aretha Franklin (1942-2018), conocida como \"La Reina del Soul,\" es una de las voces más poderosas y emblemáticas de la música popular. Nacida en Memphis, Tennessee, Franklin comenzó a cantar en la iglesia de su padre, un famoso predicador. Su carrera despegó en los años 60 cuando firmó con Atlantic Records, donde su poderosa voz y talento para el gospel, el blues y el soul la llevaron al estrellato. Franklin es conocida por éxitos como \"Respect,\" \"Chain of Fools,\" y \"Think.\" A lo largo de su carrera, ganó 18 premios Grammy y se convirtió en la primera mujer en ingresar al Salón de la Fama del Rock and Roll. Su legado perdura como símbolo de empoderamiento y talento inigualable.", "I Never Loved a Man the Way I Love You", "Lady Soul", "Young, Gifted and Black", "Amazing Grace", "Incluye su icónico éxito \"Respect,\" que se convirtió en un himno de empoderamiento y lucha por los derechos civiles. Este álbum también cuenta con canciones como \"Dr. Feelgood\" y \"Do Right Woman, Do Right Man,\" que muestran la versatilidad de Franklin para abordar tanto baladas desgarradoras como piezas de ritmo más rápido. Este álbum la catapultó al estrellato y estableció su reputación como la Reina del Soul.", "Con éxitos como \"Chain of Fools\" y \"(You Make Me Feel Like) A Natural Woman,\" este álbum destaca su poderosa voz y habilidad para transmitir emociones profundas. La producción de Jerry Wexler y las contribuciones de músicos de la talla de Eric Clapton en la guitarra hicieron de este álbum un clásico instantáneo que consolidó su estatus en la música soul.", "Este álbum muestra su versatilidad con una mezcla de soul, gospel y funk. Incluye éxitos como \"Rock Steady\" y \"Day Dreaming,\" y recibió elogios por su profundidad lírica y musical. Franklin aborda temas de identidad y orgullo racial, creando un trabajo que es tan relevante socialmente como musicalmente innovador.", "Un álbum en vivo grabado en una iglesia bautista, que destaca su herencia gospel. Es uno de los álbumes de gospel más vendidos de todos los tiempos y muestra su talento innato y conexión espiritual. Con canciones como \"How I Got Over\" y \"Precious Memories,\" este álbum captura la intensidad emocional y la espiritualidad que solo Franklin podía ofrecer en un entorno en vivo.");
        dbArtistsHelper.insertTextWithName("Miles Davis",
                "Miles Davis fue un trompetista, compositor y uno de los músicos más innovadores del siglo XX. Nacido en Illinois en 1926, Davis fue una figura central en el desarrollo de varios estilos de jazz, incluyendo el bebop, el cool jazz y el jazz fusion. Su capacidad para reinventarse y su influencia en generaciones de músicos lo consolidan como una leyenda del jazz.",
                "Kind of Blue", "Bitches Brew", "Birth of the Cool", "Sketches of Spain",
                "'Kind of Blue' (1959) es uno de los álbumes de jazz más vendidos y aclamados de todos los tiempos. Con una alineación estelar, el álbum es conocido por su atmósfera relajada y sus innovadoras improvisaciones modales.",
                "'Bitches Brew' (1970) marcó el comienzo del jazz fusion, combinando jazz, rock y funk. Este álbum doble desafió las convenciones y abrió nuevas direcciones para el jazz moderno.",
                "'Birth of the Cool' (1957) es una recopilación de grabaciones de finales de los años 40 que introdujeron el cool jazz. Con un enfoque más relajado y arreglos complejos, el álbum es fundamental en la historia del jazz.",
                "'Sketches of Spain' (1960) es una colaboración con Gil Evans que fusiona jazz y música española. Con temas como 'Concierto de Aranjuez', el álbum es una obra maestra de la interpretación y el arreglo.");
        dbArtistsHelper.insertTextWithName("Mda", "Mda es un talentoso rapero y productor, conocido por sus letras profundas y su habilidad para contar historias a través de su música. Su estilo fusiona hip-hop clásico con influencias modernas, creando un sonido distintivo que ha capturado la atención de muchos.", "^^7^", "Romántico salvaj3", "Lindo M \"El Indomable\"", "Inteligencia Artificial", "Hacerse notar en la industria musical puede depender de diversos factores, pero cuando posees un sonido real y fresco, solo es cuestión de tiempo para que el mundo te reconozca. Este es el caso de Mda, uno de los referentes de la nueva ola que está por cambiar la escena musical española con su proyecto '^^7'", "Romántico Salvaje 3\" es un álbum de MDA que captura la esencia de su estilo único, combinando la sensibilidad romántica con una actitud rebelde y audaz. Este disco destaca por su habilidad para fusionar elementos del trap, el reguetón y el hip-hop, creando un sonido que es a la vez emotivo y energético. Las letras de MDA en este álbum abordan temas de amor, desamor y la lucha interna, ofreciendo una visión cruda y honesta de sus experiencias personales y su entorno.", "A lo largo del álbum, MDA aborda temas de empoderamiento, superación personal y la realidad de la vida en la calle, todo con una lírica contundente y una producción de alta calidad. Las colaboraciones en \"Lindo M: El Indomable\" destacan la versatilidad de MDA y su habilidad para crear éxitos junto a otros artistas influyentes. Este álbum consolida a MDA como una figura central en la música urbana, demostrando su capacidad para innovar y conectar profundamente con sus oyentes.", "Es un álbum que como su propio nombre indica, da pie de una manera «profética» a una nueva era musical. Como bien sabrán la Inteligencia Artificial además de que busca ser una prolongación del ser humano, es una herramienta con la cual podemos descubrir cosas que van mucho más allá de la capacidad e intelecto de los humanos, es por eso que parte de la población está aterrorizada por si dicha inteligencia nos pudiera sobrepasar y nos exterminará ya que todos sabemos que el ser humano es el mayor virus que existe dentro de la naturaleza");


    }

    @Test
    public void useAppContext() {
        // Verifica el nombre del paquete de la app bajo prueba
        assertEquals("com.example.generosmusicales", appContext.getPackageName());
    }

    @Test
    public void testTextViewDescriptionsExistence() {
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
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                AutoCompleteTextView searchEditText = activity.searchEditText;

                for (String genre : genres) {
                    searchEditText.setText(genre);
                    searchEditText.performCompletion();
                    assertEquals("La búsqueda debe coincidir con la entrada", genre, searchEditText.getText().toString());
                }
            });
        }
    }

    @Test
    public void testSearchOpensCorrectActivity() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                for (String genre : genres) {
                    activity.search(genre);

                    Intent expectedIntent = new Intent(activity, generosexplicados.class);
                    expectedIntent.putExtra("aspecto", genre);
                    assertEquals("El Intent debería abrir la actividad generosexplicados", expectedIntent.getComponent(), new Intent(activity, generosexplicados.class).getComponent());
                }
            });
        }
    }

    @Test
    public void testDatabaseInsertion() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                ArtistDatabaseHelper dbArtistsHelper = activity.dbArtistsHelper;

                dbArtistsHelper.insertTextWithName("Test Artist",
                        "Description", "Album 1", "Album 2", "Album 3", "Album 4",
                        "Details for album 1", "Details for album 2", "Details for album 3", "Details for album 4");

                String[] albums = dbArtistsHelper.getAlbumsByName("Test Artist");
                assertNotNull("Los álbumes no deberían ser nulos", albums);
                assertEquals("El álbum 1 debería coincidir", "Album 1", albums[0]);
            });
        }
    }

    @Test
    public void testKeywordActivityMapInitialization() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                activity.initializeKeywordActivityMap();

                for (String genre : genres) {
                    activity.addKeyword(genre, generosexplicados.class);
                }

                for (String genre : genres) {
                    assertTrue("El mapa de palabras clave debería contener '" + genre + "'", activity.keywordActivityMap.containsKey(genre.toLowerCase()));
                }
            });
        }
    }

    // Pruebas para generosexplicados
    @Test
    public void testGenerosExplicadosActivityInitialization() {
        // Verifica que la actividad se inicialice correctamente para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    // Verifica que la actividad esté correctamente inicializada
                    assertNotNull("La actividad no debe ser nula para el género: " + genre, activity);
                });
            }
        }
    }

    @Test
    public void testImageViewsInitialization() {
        // Verifica que todas las vistas de imágenes estén inicializadas correctamente para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ImageView[] imageViews = activity.imageView;
                    assertNotNull("El array de ImageView no debe ser nulo para el género: " + genre, imageViews);
                    for (int i = 0; i < 6; i++) {
                        assertNotNull("La vista de imagen " + i + " no debe ser nula para el género: " + genre, imageViews[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testCardViewsExistenceGenerosExplicados() {
        // Verifica que todas las vistas de tarjetas estén inicializadas correctamente para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    assertNotNull("La actividad debería estar cargada para el género: " + genre, activity);

                    MaterialCardView[] cardViews = activity.cardViews;
                    assertNotNull("El array de MaterialCardView no debe ser nulo para el género: " + genre, cardViews);
                    for (int i = 0; i < 6; i++) {
                        assertNotNull("La vista de tarjeta " + i + " no debe ser nula para el género: " + genre, cardViews[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testFollowersTextInitialization() {
        // Verifica que las vistas de texto de seguidores estén inicializadas correctamente para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    TextView[] followersText = activity.followersText;
                    assertNotNull("El array de TextView de seguidores no debe ser nulo para el género: " + genre, followersText);
                    for (int i = 0; i < 6; i++) {
                        assertNotNull("La vista de texto de seguidores " + i + " no debe ser nula para el género: " + genre, followersText[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testGenresTextInitialization() {
        // Verifica que las vistas de texto de géneros estén inicializadas correctamente para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    TextView[] genresText = activity.genresText;
                    assertNotNull("El array de TextView de géneros no debe ser nulo para el género: " + genre, genresText);
                    for (int i = 0; i < 6; i++) {
                        assertNotNull("La vista de texto de géneros " + i + " no debe ser nula para el género: " + genre, genresText[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testImageLoadingAndColorDominance() {
        // Verifica que las imágenes se carguen correctamente y que los colores dominantes sean asignados a las vistas de tarjeta para cada género
        for (String genre : genres) {
            Intent intent = new Intent(appContext, generosexplicados.class);
            intent.putExtra("aspecto", genre);

            try (ActivityScenario<generosexplicados> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ImageView imageView = activity.imageView[0];
                    Drawable drawable = imageView.getDrawable();
                    assertNotNull("La imagen debe estar cargada en imageView[0] para el género: " + genre, imageView);

                    MaterialCardView cardView = activity.cardViews[0];
                    int cardBackgroundColor = cardView.getCardBackgroundColor().getDefaultColor();
                    assertTrue("El color de fondo de la tarjeta debe estar asignado para el género: " + genre, cardBackgroundColor != 0);
                });
            }
        }
    }

    // Pruebas para Albums

    @Test
    public void testAlbumsActivityInitialization() {
        // Verifica que la actividad Albums se inicialice correctamente para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    // Verifica que la actividad esté correctamente inicializada
                    assertNotNull("La actividad no debe ser nula para el artista: " + artista, activity);

                    // Verifica el título del artista
                    TextView artistNameTextView = activity.findViewById(R.id.titleTextView);
                    assertNotNull("El TextView del nombre del artista no debe ser nulo", artistNameTextView);
                    assertEquals("El nombre del artista debe coincidir", artista, artistNameTextView.getText().toString());
                });
            }
        }
    }

    @Test
    public void testAlbumNamesInitialization() {
        // Verifica que los nombres de los álbumes estén correctamente inicializados para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    TextView[] albumTextViews = activity.textAlbums;
                    assertNotNull("El array de TextView de álbumes no debe ser nulo para el artista: " + artista, albumTextViews);
                    for (int i = 0; i < 4; i++) {
                        assertNotNull("El TextView del álbum " + (i + 1) + " no debe ser nulo para el artista: " + artista, albumTextViews[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testAlbumDetailsInitialization() {
        // Verifica que los detalles de los álbumes estén correctamente inicializados para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    TextView[] albumDetailsTextViews = activity.descriptionAlbums;
                    assertNotNull("El array de TextView de detalles de álbumes no debe ser nulo para el artista: " + artista, albumDetailsTextViews);

                    for (int i = 0; i < 4; i++) {
                        assertNotNull("El TextView de detalles del álbum " + (i + 1) + " no debe ser nulo para el artista: " + artista, albumDetailsTextViews[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testAlbumImagesInitialization() {
        // Verifica que las vistas de imágenes de los álbumes estén inicializadas correctamente para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ImageView[] albumImageViews = activity.imageView;
                    assertNotNull("El array de ImageView de álbumes no debe ser nulo para el artista: " + artista, albumImageViews);
                    for (int i = 0; i < 4; i++) {
                        assertNotNull("El ImageView del álbum " + (i + 1) + " no debe ser nulo para el artista: " + artista, albumImageViews[i]);
                    }
                });
            }
        }
    }

    @Test
    public void testAlbumsDatabaseRetrieval() {
        // Verifica la correcta recuperación de álbumes desde la base de datos para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ArtistDatabaseHelper dbArtistsHelper = activity.dbHelper;
                    String[] albums = dbArtistsHelper.getAlbumsByName(artista);

                    assertNotNull("Los álbumes no deberían ser nulos para el artista: " + artista, albums);
                    // Puedes verificar el contenido del primer álbum si está disponible en la base de datos
                    // Por ejemplo:
                    if (albums.length > 0) {
                        assertNotNull("El primer álbum no debe ser nulo para el artista: " + artista, albums[0]);
                    }
                });
            }
        }
    }

    @Test
    public void testAlbumDetailsDatabaseRetrieval() {
        // Verifica la correcta recuperación de detalles de álbumes desde la base de datos para cada artista
        for (String artista : artistas) {
            Intent intent = new Intent(appContext, Albums.class);
            intent.putExtra("aspecto", artista);

            try (ActivityScenario<Albums> scenario = ActivityScenario.launch(intent)) {
                scenario.onActivity(activity -> {
                    ArtistDatabaseHelper dbArtistsHelper = activity.dbHelper;
                    String[] albumDetails = dbArtistsHelper.getDescriptionsByName(artista);

                    assertNotNull("Los detalles del álbum no deberían ser nulos para el artista: " + artista, albumDetails);
                    // Puedes verificar el contenido del primer detalle de álbum si está disponible en la base de datos
                    // Por ejemplo:
                    if (albumDetails.length > 0) {
                        assertNotNull("El detalle del primer álbum no debe ser nulo para el artista: " + artista, albumDetails[0]);
                    }
                });
            }
        }
    }
}
