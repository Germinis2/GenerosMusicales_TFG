package java.TFG.generosmusicales;
import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import java.TFG.generosmusicales.db.ArtistDatabaseHelper;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import java.TFG.generosmusicales.db.DBHelper;
import com.google.android.material.card.MaterialCardView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MaterialAutoCompleteTextView searchEditText;
    final int numberOfButtons = 22;

    MaterialCardView[] cardViews= new MaterialCardView[numberOfButtons+2];
    Map<String, Class<?>> keywordActivityMap;
    List<String> keywordList = new ArrayList<>();
    ArtistDatabaseHelper dbArtistsHelper;
    String [] album = {null, null, null, null};
    TextView [] descriptionTextView = new TextView[numberOfButtons+2];

    TextView [] textView = new TextView[numberOfButtons+2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        ImageButton[] imageButtons = new ImageButton[numberOfButtons+2];
        int[] textViewIds = {R.id.texto1, R.id.texto2, R.id.texto3, R.id.texto4, R.id.texto5,
                R.id.texto6, R.id.texto7, R.id.texto8, R.id.texto9, R.id.texto10,
                R.id.texto11, R.id.texto12, R.id.texto13, R.id.texto14, R.id.texto15,
                R.id.texto16, R.id.texto17, R.id.texto18, R.id.texto19, R.id.texto20, R.id.texto21, R.id.texto22, R.id.texto23, R.id.texto24};


        SpotifyTokenRequestTask request = new SpotifyTokenRequestTask();
        request.execute();

        int numberOfCards = 22;
        for (int i = 0; i < numberOfCards; i++) {
            @SuppressLint("DiscouragedApi") int cardId = getResources().getIdentifier("card" + (i + 1), "id", getPackageName());
            cardViews[i] = findViewById(cardId);
            final int finalI = i;
            textView[finalI] = findViewById(textViewIds[finalI]);

            cardViews[i].setOnClickListener(v -> {
                String textoTitulo = textView[finalI].getContentDescription().toString();
                abrirActividadConAspecto(textoTitulo, generosexplicados.class);
            });

        }
        for (int i = 22; i < 24; i++) {
            @SuppressLint("DiscouragedApi") int cardId = getResources().getIdentifier("card" + (i+1), "id", getPackageName());
            cardViews[i] = findViewById(cardId);
            final int finalI = i;
            textView[finalI] = findViewById(textViewIds[finalI]);

            cardViews[i].setOnClickListener(v -> {
                String textoTitulo = textView[finalI].getContentDescription().toString();
                abrirActividadConAspecto(textoTitulo, Albums.class);
            });
        }

        for (int i = 0; i < numberOfButtons; i++) {
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("imageButton" + (i + 1), "id", getPackageName());
            imageButtons[i] = findViewById(buttonId);
            @SuppressLint("DiscouragedApi") int descriptionId = getResources().getIdentifier("descripcion" + (i + 1), "id", getPackageName());
            descriptionTextView[i] = findViewById(descriptionId);
            @SuppressLint("DiscouragedApi") int cardId = getResources().getIdentifier("card" + (i + 1), "id", getPackageName());
            cardViews[i]=findViewById(cardId);
            final int finalI = i;

            imageButtons[i].setOnClickListener(v -> {
                textView[finalI] = findViewById(textViewIds[finalI]);
                String textoTitulo = textView[finalI].getContentDescription().toString();
                abrirActividadConAspecto(textoTitulo, generosexplicados.class);
            });
            obtenerColorDominante(imageButtons[i].getDrawable(), i);
        }
        for (int i = 22; i < 24; i++) {
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("imageButton" + (i + 1), "id", getPackageName());
            imageButtons[i] = findViewById(buttonId);
            @SuppressLint("DiscouragedApi") int descriptionId = getResources().getIdentifier("descripcion" + (i + 1), "id", getPackageName());
            descriptionTextView[i] = findViewById(descriptionId);
            @SuppressLint("DiscouragedApi") int cardId = getResources().getIdentifier("card" + (i + 1), "id", getPackageName());
            cardViews[i]=findViewById(cardId);
            final int finalI = i;

            imageButtons[i].setOnClickListener(v -> {
                textView[finalI] = findViewById(textViewIds[finalI]);
                String textoTitulo = textView[finalI].getContentDescription().toString();
                abrirActividadConAspecto(textoTitulo, generosexplicados.class);
            });
            obtenerColorDominante(imageButtons[i].getDrawable(), i);
        }

        obtenerColorDominante(imageButtons[22].getDrawable(), 22);
        obtenerColorDominante(imageButtons[23].getDrawable(), 23);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        dbArtistsHelper= new ArtistDatabaseHelper(getApplicationContext());
        dbHelper.deleteAllData();
        dbArtistsHelper.deleteAllData();

        String texto1 = "\n" +
                "La música clásica ha experimentado una evolución significativa a lo largo de los siglos, desde sus raíces en la música antigua hasta el desarrollo de formas musicales más complejas durante el período barroco, clásico y romántico. Durante la Edad Media y el Renacimiento, la música sacra dominaba el panorama musical, con el canto gregoriano y la polifonía siendo formas prominentes. Sin embargo, con el advenimiento del período barroco, vimos una expansión en la instrumentación, el desarrollo de nuevas formas musicales como la sonata, la suite y el concierto, así como el enfoque en la expresión emocional y la ornamentación.\n" +
                "\n" +
                "La importancia de la música clásica radica en su capacidad para trascender fronteras culturales y temporales, así como en su capacidad para expresar una amplia gama de emociones y experiencias humanas. La música clásica ha influido en todas las formas de música occidental y ha servido como una fuente de inspiración para compositores de diferentes géneros a lo largo de los siglos. Además, la música clásica desempeña un papel fundamental en la educación musical, ya que enseña a los estudiantes sobre la teoría musical, la estructura formal y la historia de la música, y fomenta la apreciación de la belleza y la complejidad de la música..";
        String texto2 = "El country, un género musical profundamente arraigado en la tradición estadounidense, tiene sus raíces en las canciones folclóricas de los primeros colonos europeos, así como en la música de los inmigrantes irlandeses, escoceses y africanos. Surgió en las zonas rurales del sur de Estados Unidos en el siglo XIX, especialmente en los estados sureños como Tennessee, Kentucky y Texas, donde la vida agraria y las vastas extensiones de tierra influenciaron profundamente su desarrollo."
                        +"\n" +
                        "Los primeros estilos del country estaban fuertemente influenciados por el folk y el blues, con letras que narraban historias de la vida cotidiana, amor, pérdida, trabajo en el campo y relaciones personales. Los instrumentos utilizados en estas primeras grabaciones incluían la guitarra acústica, el banjo, el violín, el acordeón y el contrabajo, entre otros. La música country era una forma de expresión para los agricultores y trabajadores del campo, que encontraban en sus letras y melodías una manera de conectarse con sus propias experiencias y con las de su comunidad.";
        String texto3 = "\n" +
                "La música Rhythm and Blues (R&B) y Soul tiene sus raíces en la comunidad afroamericana de Estados Unidos, surgiendo en las décadas de 1940 y 1950 como una fusión de diversos estilos musicales, incluyendo el jazz, el blues y el gospel. Inicialmente conocido como \"race music\" (música de raza), el R&B/Soul se convirtió en una expresión artística poderosa que reflejaba las experiencias y emociones de la comunidad afroamericana en un momento de profundo cambio social. Artistas como Ray Charles, Sam Cooke y Aretha Franklin fueron pioneros en el género, utilizando su talento vocal y su habilidad para transmitir emoción para crear canciones que resonaron con audiencias de todas las razas y clases sociales."
                +"\n" +
                "El R&B/Soul se caracteriza por su ritmo sincopado, su expresión vocal emotiva y sus letras que exploran temas de amor, desamor, esperanza y lucha. A lo largo de las décadas, el género ha evolucionado y se ha ramificado en diferentes estilos, desde el soul clásico y el funk de los años 60 y 70, hasta el R&B contemporáneo y el neo-soul de hoy en día. A pesar de sus cambios, el R&B/Soul sigue siendo una forma de expresión auténtica y poderosa que continúa resonando con audiencias de todo el mundo, conectando a las personas a través de su profunda humanidad y su capacidad para capturar la complejidad de la experiencia humana.";
        String texto4 = "\n" +
                "En los últimos años, la música R&B ha experimentado una evolución significativa, adaptándose a los cambios culturales y tecnológicos de la era contemporánea. Aunque sus raíces se remontan al rhythm and blues de los años 40 y 50, así como al soul de los años 60 y 70, el R&B actual ha incorporado influencias modernas que lo han llevado a nuevas alturas creativas. Desde mediados de la década de 2000 hasta la actualidad, el R&B ha sido moldeado por artistas que fusionan géneros, experimentan con la producción digital y exploran una variedad de temas personales y sociales en sus letras."
                +"\n" +
                "Con el advenimiento de las plataformas de streaming y las redes sociales, los artistas de R&B han encontrado nuevas formas de conectar con su audiencia y distribuir su música de manera independiente, lo que ha permitido una mayor diversidad de sonidos y perspectivas en el género. Además, la influencia del hip-hop y la música electrónica ha llevado a la aparición de subgéneros como el trap&B y el R&B alternativo, que desafían las convenciones del género y amplían su alcance. En resumen, el R&B en la actualidad se caracteriza por su diversidad, innovación y capacidad para reflejar las experiencias de una generación en constante cambio.";
        String texto5 = "\n" +
                "El rap tiene sus raíces en la cultura afroamericana de la década de 1970 en los Estados Unidos, emergiendo como una forma de expresión artística y protesta en las comunidades urbanas marginadas. Originario del Bronx, Nueva York, el rap se desarrolló como una extensión de las tradiciones de la poesía hablada y el spoken word, combinando rimas rítmicas y cadencias vocales con ritmos pulsantes. Pioneros como DJ Kool Herc, Grandmaster Flash y Afrika Bambaataa fueron fundamentales en la creación de los fundamentos del rap, organizando fiestas callejeras donde los MCs improvisaban rimas sobre breaks de batería y samples de música funk y soul."
                +"\n" +
                "A medida que la cultura del rap creció, se convirtió en una poderosa forma de dar voz a las realidades sociales y políticas de las comunidades urbanas, abordando temas como la violencia, la discriminación racial, la pobreza y la desigualdad. Los MCs, o maestros de ceremonias, se convirtieron en narradores de historias urbanas, utilizando su habilidad para improvisar y rimar para transmitir mensajes de empoderamiento y conciencia. El rap no solo se convirtió en un género musical, sino también en un movimiento cultural que inspiró la moda, el arte y el lenguaje de la juventud urbana en todo el mundo.";
        String texto6 = "\n" +
                "En los últimos 20 años, el rap ha experimentado una expansión y diversificación sin precedentes, convirtiéndose en uno de los géneros musicales más influyentes a nivel global. Desde sus modestos inicios en las calles del Bronx en la década de 1970, el rap ha evolucionado enormemente, abarcando una amplia gama de estilos, temáticas y subculturas. En las últimas dos décadas, el rap ha sido moldeado por una serie de innovaciones tecnológicas, como la digitalización de la música y el surgimiento de las redes sociales, que han democratizado la producción y distribución musical, permitiendo que una nueva generación de artistas surja desde comunidades de todo el mundo."
                +"\n" +
                "El rap actual se caracteriza por su diversidad, tanto en términos de sonido como de contenido lírico. Desde el rap consciente y político hasta el trap y el mumble rap, el género abarca una amplia gama de estilos y subgéneros que reflejan las diferentes realidades y experiencias de los artistas y sus seguidores. Además, el rap contemporáneo ha sido moldeado por el activismo social y político, con artistas que utilizan su plataforma para abordar cuestiones como la justicia racial, la desigualdad económica y el cambio climático. En resumen, el rap actual es un reflejo dinámico y vibrante de la cultura contemporánea, que continúa evolucionando y reinventándose a medida que el género sigue siendo una fuerza poderosa en la música y la sociedad.";
        String texto7 = "\n" +
                "El rock en el siglo XX emerge como una fuerza cultural y musical revolucionaria que redefine la industria del entretenimiento y desafía las normas sociales establecidas. Surgido en los Estados Unidos en la década de 1950, el rock and roll fusiona elementos del rhythm and blues, el country, el gospel y el jazz, creando un sonido distintivo caracterizado por sus ritmos enérgicos, sus letras provocativas y su actitud desafiante. Pioneros como Chuck Berry, Little Richard y Elvis Presley introducen el género en la corriente principal, llevando la música rock a una audiencia global y catalizando una revolución cultural que transformaría la sociedad del siglo XX."
                +"\n" +
                "A lo largo del siglo, el rock se diversifica y evoluciona, dando lugar a una multitud de subgéneros y movimientos, desde el rock psicodélico y el folk rock de los años 60 hasta el punk, el heavy metal y el grunge de las décadas posteriores. El rock se convierte en la banda sonora de la contracultura y la lucha por los derechos civiles, canalizando la rebeldía y la angustia de la juventud en todo el mundo. Con su capacidad para reflejar las ansiedades y aspiraciones de cada generación, el rock en el siglo XX trasciende las barreras culturales y se convierte en un fenómeno global que define una era y deja un legado duradero en la música y la sociedad moderna.";
        String texto8 = "\n" +
                "El pop en el siglo XX surge como un fenómeno cultural que refleja los cambios sociales, tecnológicos y políticos de la época, convirtiéndose en una de las formas musicales más dominantes y universales del siglo. Con sus raíces en la música popular de principios del siglo, como el vaudeville, el ragtime y el jazz, el pop evoluciona a lo largo de las décadas, abrazando una amplia gama de estilos y tendencias. En la década de 1950, el pop experimenta un auge masivo con la llegada del rock and roll, personificado por artistas como Elvis Presley y Buddy Holly, que llevan la música pop a una audiencia juvenil global y definen el sonido de una generación."
                +"\n" +
                "A medida que avanza el siglo, el pop continúa evolucionando, adoptando influencias de géneros como el soul, el funk, la música disco, el hip-hop y la electrónica. Desde los íconos del pop como The Beatles y Michael Jackson hasta las estrellas del pop contemporáneo como Beyoncé y Taylor Swift, el género ha sido moldeado por una serie de innovadores que han desafiado las convenciones y empujado los límites de lo que es posible en la música pop. Con su capacidad para reflejar la cultura popular y capturar el espíritu de la época, el pop en el siglo XX se convierte en un fenómeno global que trasciende las fronteras y une a las personas de todo el mundo a través de su melodía pegajosa y su irresistible energía.";
        String texto9 = "\n" +
                "El rock en los últimos años ha experimentado una notable evolución en un panorama musical dominado por la diversidad y la fusión de géneros. Desde sus raíces en el rock clásico, el punk, el grunge y el indie rock de décadas pasadas, el rock contemporáneo ha adoptado una amplia gama de influencias, desde el pop y el hip-hop hasta la electrónica y el folk, creando un paisaje sonoro ecléctico y emocionante. Este período ha sido testigo del surgimiento de nuevas bandas y artistas que desafían las convenciones del género, experimentando con sonidos vanguardistas y temáticas contemporáneas para ofrecer una visión fresca y relevante del rock en el siglo XXI.\n" +
                "\n" +
                "El rock actual se caracteriza por su diversidad estilística y su capacidad para adaptarse a los cambios culturales y tecnológicos de la era digital. Desde el renacimiento del garage rock y el indie revival hasta la aparición del emo rap y el trap rock, el género ha continuado siendo una fuerza creativa y expresiva en la música contemporánea. Aunque algunos críticos argumentan que el dominio del pop y el hip-hop ha eclipsado la relevancia cultural del rock, una nueva generación de artistas ha surgido para desafiar esta percepción, demostrando que el rock sigue siendo un medio poderoso para la autoexpresión y la conexión emocional en un mundo en constante cambio.";
        String texto10 = "\n" +
                "El jazz, nacido a finales del siglo XIX y principios del XX en los Estados Unidos, es un género musical profundamente arraigado en la experiencia afroamericana y en la interacción cultural entre África, Europa y América. Surgiendo en la ciudad de Nueva Orleans, el jazz se nutrió de una rica amalgama de influencias, incluyendo los ritmos africanos, los espirituales negros, el blues y la música popular estadounidense de la época. Con su énfasis en la improvisación, la interacción musical y la expresión personal, el jazz se convirtió en un medio poderoso de autenticidad y libertad creativa, proporcionando un espacio para la experimentación y la innovación musical."
                +"\n" +
                "A lo largo del siglo XX, el jazz experimentó una serie de evoluciones y transformaciones, desde el jazz tradicional y el swing de la década de 1920 y 1930, hasta el bebop, el cool jazz, el hard bop, el free jazz y el jazz fusión de décadas posteriores. Cada uno de estos estilos reflejaba las realidades sociales y culturales de su tiempo, así como la evolución técnica y creativa de los músicos de jazz. Además, el jazz ha sido un puente cultural y diplomático, sirviendo como una forma de comunicación universal que trasciende las barreras lingüísticas y culturales, conectando a personas de diferentes orígenes y experiencias a través de la belleza y la complejidad de su música.";
        String texto11= "\n" +
                "El pop actual, en los últimos años, ha sido moldeado por una fusión de influencias que van desde el hip-hop y la electrónica hasta el indie y el R&B, creando un paisaje sonoro diverso y emocionante. Con sus raíces en la música popular del siglo XX, el pop contemporáneo ha evolucionado a lo largo de las décadas, adaptándose a los cambios tecnológicos y culturales para mantenerse relevante en un mercado musical en constante evolución. Desde sus inicios con artistas como Elvis Presley, The Beatles y Madonna, el pop ha sido una fuerza dominante en la industria musical, caracterizado por su accesibilidad, su enfoque en la melodía pegajosa y sus letras que abordan temas universales como el amor, la felicidad y la juventud."
                +"\n" +
                "En la era digital actual, el pop ha experimentado una mayor diversificación y democratización, con la aparición de artistas independientes y la influencia de las redes sociales y las plataformas de streaming. Además, el pop actual se caracteriza por su fusión de estilos y su capacidad para reflejar la diversidad cultural y musical del mundo contemporáneo. Desde el synth-pop y el dance-pop hasta el pop alternativo y el indie pop, el género continúa siendo una fuerza poderosa en la música popular, conectando a personas de diferentes culturas y generaciones a través de su energía contagiosa y su capacidad para emocionar y entretener.";
        String texto12 = "\n" +
                "El trap es un género de música hip-hop que emergió en la década de 1990 en el sur de Estados Unidos, particularmente en ciudades como Atlanta. Se caracteriza por sus ritmos oscuros, bajos profundos y letras que abordan temas como la violencia, las drogas, el dinero y el estilo de vida callejero. Inspirado en el trap de la droga, que describe la vida en las trampas (lugares donde se vende droga), el género se distingue por su atmósfera sombría y su narrativa cruda y realista. A lo largo de los años, el trap ha evolucionado y se ha ramificado en una variedad de subgéneros, cada uno con su propia identidad y sonido único."
                +"\n" +
                "El trap moderno se caracteriza por sus ritmos pesados, compases lentos, y el uso prominente de bajos profundos y sintetizadores. Las letras del trap a menudo abordan temas como la violencia, las drogas, el dinero y el éxito, y pueden variar desde narrativas autobiográficas hasta fantasías de riqueza y poder. A lo largo de los años, el trap ha ganado una enorme popularidad tanto en la escena underground como en la corriente principal, convirtiéndose en uno de los géneros más influyentes de la música contemporánea. Sus ritmos pegajosos y su estética distintiva han dejado una marca indeleble en la cultura musical global, influyendo no solo en el hip-hop, sino también en géneros como el pop, el R&B y la electrónica.";
        String texto13 = "\n" +
                "La música electrónica tiene sus raíces en el siglo XX, con el surgimiento de la música electrónica experimental a principios del siglo. A lo largo de las décadas de 1950 y 1960, pioneros como Karlheinz Stockhausen, Pierre Schaeffer y Robert Moog exploraron nuevas formas de crear sonido utilizando tecnología electrónica, dando lugar a la aparición de géneros como la música concreta y la música electrónica de vanguardia. En la década de 1970, el movimiento de la música disco popularizó aún más el uso de sintetizadores y cajas de ritmos, sentando las bases para el desarrollo de la música electrónica de baile. Con el advenimiento de la tecnología digital en las décadas de 1980 y 1990, la música electrónica se expandió aún más, dando lugar a una amplia variedad de estilos y subgéneros, como el techno, el house, el trance, el drum and bass y el dubstep."
                +"\n" +
                "Hoy en día, la música electrónica abarca una amplia gama de estilos y tendencias, desde los sonidos minimalistas y experimentales hasta las producciones comerciales y de gran éxito en las pistas de baile de todo el mundo. Con el avance de la tecnología y la accesibilidad de los programas de producción musical, más artistas que nunca tienen la capacidad de crear música electrónica desde cualquier lugar del mundo. Además, la música electrónica ha encontrado su lugar en la cultura popular, influyendo en la música pop, el cine, la moda y el arte, y convirtiéndose en una fuerza poderosa en la industria musical global.";
        String texto14= "\n" +
                "La música heavy, también conocida como heavy metal, surge a finales de la década de 1960 y principios de la década de 1970 como una respuesta al rock and roll convencional, con una mayor énfasis en la distorsión, la velocidad y la intensidad. Influenciado por el blues, el rock psicodélico y el hard rock, el heavy metal se caracteriza por sus poderosos riffs de guitarra, su ritmo contundente y sus letras que a menudo exploran temas oscuros como la guerra, la rebelión y lo sobrenatural. Pioneros como Black Sabbath, Led Zeppelin y Deep Purple sentaron las bases del género, creando un sonido distintivo que resonaría en generaciones futuras."
                +"\n" +
                "A lo largo de los años, el heavy metal ha experimentado una serie de evoluciones y subgéneros, desde el thrash metal y el power metal hasta el death metal y el black metal, cada uno con su propia estética y sonido característicos. Además, el heavy metal ha sido una fuerza cultural y social, proporcionando un sentido de identidad y comunidad para aquellos que se identifican con su estética y mensaje. Con su energía visceral y su capacidad para canalizar emociones intensas, la música heavy ha dejado una marca indeleble en la cultura musical y continúa siendo una forma de expresión poderosa para quienes buscan escapar de la norma y sumergirse en un mundo de sonidos contundentes y provocativos.";
        String texto15= "\n" +
                "La música disco surge en la década de 1970 como una respuesta a la cultura de la discoteca y la demanda de música bailable y energética. Sus raíces se encuentran en la música soul, funk y rhythm and blues, pero se caracteriza por la incorporación de elementos electrónicos y una producción enfocada en ritmos pegajosos y melodías alegres. Los clubes nocturnos de ciudades como Nueva York y Chicago fueron cruciales para el desarrollo del género, donde DJs como DJ Kool Herc, DJ Larry Levan y DJ Frankie Knuckles experimentaron con mezclas de discos de vinilo y efectos de sonido para crear un ambiente de baile vibrante y lleno de energía.\n" +
                "\n" +
                "La música disco alcanzó su apogeo de popularidad en la segunda mitad de la década de 1970, con éxitos masivos como \"Stayin' Alive\" de Bee Gees y \"I Will Survive\" de Gloria Gaynor dominando las listas de éxitos y las pistas de baile en todo el mundo. Sin embargo, el género también enfrentó críticas y controversias, particularmente en Estados Unidos, donde se asociaba con la cultura gay y afroamericana. Aunque la era de la música disco fue breve, su legado perdura en la música pop y dance contemporánea, y su influencia se puede sentir en una amplia gama de géneros musicales y estilos de producción.";

        String texto16= "\n" +
                "La música alternativa actual, representada por artistas como Yung Lean y Bladee, surge en la década de 2010 como una reacción al paisaje musical dominado por el hip-hop tradicional y el pop comercial. Este género se caracteriza por su enfoque en la experimentación sonora, la fusión de géneros y la estética DIY (hazlo tú mismo). Inspirados por una variedad de influencias que van desde el hip-hop y el R&B hasta la electrónica y el punk, los artistas de la música alternativa actual desafían las convenciones musicales y culturales, creando un sonido distintivo y vanguardista que resuena con una audiencia global.\n" +
                "\n" +
                "Los inicios de la música alternativa actual se remontan a la escena underground de Internet, donde artistas como Yung Lean y Bladee comenzaron a ganar notoriedad a través de plataformas de streaming y redes sociales. Utilizando técnicas de producción caseras y promoviendo su música de forma independiente, estos artistas crearon un sonido único y una estética visual que capturó la atención de una generación joven en busca de algo nuevo y diferente. Con letras que exploran temas como la alienación, la ansiedad y la introspección emocional, la música alternativa actual se ha convertido en un espacio de expresión libre y sin restricciones, que desafía las normas y redefine lo que significa ser un artista en la era digital.";
        String texto17= "\n" +
                "El rap español surge a finales de la década de 1980 y principios de la década de 1990 como una expresión cultural y musical de la juventud urbana en España. Influenciado por el hip-hop estadounidense, el rap español inicialmente adopta los elementos fundamentales del género, como el ritmo marcado, la poesía rimada y la narrativa personal, pero pronto desarrolla su propia identidad única, fusionando el rap con la rica diversidad cultural y lingüística de España. Los primeros pioneros del rap español, como MCD, Frank T y SFDK, emergieron en ciudades como Madrid, Barcelona y Sevilla, utilizando el rap como una forma de expresar la realidad de la vida urbana en España, abordando temas como la marginalidad, la desigualdad social y la política.\n" +
                "\n" +
                "A medida que el rap español ganaba popularidad, se diversificaba en una variedad de estilos y subgéneros, desde el rap callejero y el rap consciente hasta el rap festivo y el rap experimental. A finales de la década de 1990 y principios de la década de 2000, el rap español experimenta un auge masivo, con la aparición de artistas como Violadores del Verso, Nach, y La Mala Rodríguez, que alcanzan un éxito comercial y crítico sin precedentes. El rap español no solo se convierte en una forma de expresión artística, sino también en una herramienta de activismo social y cultural, dando voz a una generación de jóvenes españoles y contribuyendo a la diversidad y la riqueza del panorama musical español.";
        String texto18 = "\n" +
                "El rap español actual es el resultado de décadas de evolución y diversificación dentro de la escena musical española. Surgido en los años 80 y 90 como una forma de expresión para la juventud urbana, el rap español ha experimentado un crecimiento exponencial en términos de popularidad y diversidad de estilos en los últimos años. Inicialmente influenciado por el hip-hop estadounidense, el rap español ha encontrado su propia identidad, fusionando la lengua y la cultura española con los elementos característicos del género, como las rimas ingeniosas, los ritmos contagiosos y las letras cargadas de contenido social y político.\n" +
                "\n" +
                "En la actualidad, el rap español abarca una amplia gama de estilos y subgéneros, desde el rap callejero y el trap hasta el rap consciente y el rap experimental. Artistas como C. Tangana, Rosalía, y Natos y Waor han llevado el rap español a nuevas alturas, alcanzando un éxito internacional y colaborando con artistas de renombre mundial. Además, el rap español sigue siendo una voz relevante en la cultura española, abordando temas como la desigualdad social, la inmigración, la identidad y la política, y sirviendo como un reflejo auténtico de la vida contemporánea en España. Con su creciente influencia y diversidad, el rap español continúa desafiando las normas y redefiniendo el panorama musical español en el siglo XXI.";
        String texto19 = "\n" +
                "El pop español actual se ha convertido en un reflejo vibrante y diverso de la cultura musical de España, con una historia que se remonta a décadas atrás. Desde los primeros días del pop español en los años 60, con artistas como Raphael y Massiel, el género ha evolucionado constantemente, influenciado por las tendencias musicales internacionales y los cambios culturales dentro del país. Durante la década de 1980, el pop español experimentó un renacimiento con la Movida Madrileña, un movimiento cultural que revitalizó la escena musical del país y trajo artistas como Alaska y Nacha Pop a la vanguardia.\n" +
                "\n" +
                "En la actualidad, el pop español abarca una amplia gama de estilos y expresiones artísticas, desde el pop indie hasta el pop urbano y el flamenco pop. Artistas como Rosalía, Aitana y C. Tangana han llevado el pop español a nuevos niveles de reconocimiento internacional, fusionando la música tradicional española con influencias contemporáneas y creando un sonido único y distintivo. Además, el pop español sigue siendo una parte integral de la cultura popular del país, proporcionando una banda sonora para la vida cotidiana de los españoles y sirviendo como un medio de expresión para una nueva generación de artistas y oyentes. Con su creatividad, diversidad y relevancia cultural, el pop español actual continúa dejando una marca indeleble en la escena musical española y más allá.";
        String texto20 = "La música urbana actual es un fenómeno cultural que ha emergido como una fuerza dominante en la escena musical global. Con sus raíces en el hip-hop, el reggae, el dancehall, el R&B y otros géneros urbanos, la música urbana comenzó a ganar popularidad en las comunidades urbanas de Estados Unidos y el Caribe en las décadas de 1970 y 1980. A medida que el género se extendía por el mundo, se adaptaba y evolucionaba, incorporando influencias locales y regionales para crear un sonido diverso y ecléctico.\n" +
                "\n" +
                "En la actualidad, la música urbana abarca una amplia variedad de estilos y subgéneros, desde el rap y el trap hasta el reggaetón y el afrobeats. Artistas como Drake, Bad Bunny, Beyoncé y Burna Boy han llevado la música urbana a nuevas alturas de popularidad y éxito comercial, alcanzando audiencias globales y colaborando con artistas de diferentes culturas y géneros musicales. Además, la música urbana ha trascendido las barreras lingüísticas y culturales, convirtiéndose en una forma de expresión universal que resuena con personas de todas las edades, razas y nacionalidades. Con su ritmo contagioso, letras pegajosas y capacidad para reflejar las realidades de la vida urbana moderna, la música urbana continúa siendo una fuerza poderosa en la industria musical contemporánea.";
        String texto21 = "El trap en español es un género musical que surge a principios del siglo XXI como una adaptación del trap estadounidense a la cultura y la lengua hispana. Inicialmente influenciado por el hip-hop y el reggaetón, el trap en español se distingue por sus ritmos oscuros, letras explícitas y producción minimalista. Los primeros exponentes del trap en español, como el puertorriqueño Tempo y el español Don Omar, sentaron las bases del género, fusionando el estilo callejero del rap con la sensualidad y el ritmo del reggaetón.\n" +
                "\n" +
                "En la última década, el trap en español ha experimentado un auge masivo, especialmente con la popularización de artistas como Bad Bunny, Anuel AA y Rosalía. Estos artistas han llevado el género a nuevas alturas de popularidad y reconocimiento internacional, fusionando el trap con una variedad de influencias musicales y culturales para crear un sonido distintivo y global. Además, el trap en español ha servido como una plataforma para abordar temas sociales y políticos, así como para explorar la vida urbana y la experiencia de la juventud contemporánea en los países de habla hispana. Con su ritmo contagioso y su capacidad para conectar con una audiencia diversa, el trap en español continúa siendo una fuerza poderosa en la escena musical actual.";
        String texto22 = "\n" +
                "El rock en español surge a finales de la década de 1950 y principios de la década de 1960 como una respuesta al fenómeno del rock and roll estadounidense que estaba dominando la escena musical mundial. Inicialmente, se manifestó a través de versiones en español de canciones de rock and roll y de la influencia de artistas anglosajones como Elvis Presley y The Beatles. A medida que la década avanzaba, el rock en español comenzó a desarrollar su propia identidad, fusionando elementos del rock anglosajón con ritmos latinos y letras en español que reflejaban las realidades y preocupaciones de la juventud hispanohablante.\n" +
                "\n" +
                "En la década de 1970, el rock en español experimentó un crecimiento significativo con la llegada de bandas como Los Bravos, Los Salvajes y Los Canarios en España, y Los Ángeles Negros, Los Ángeles Azules y Soda Stereo en América Latina. Estas bandas sentaron las bases del rock en español, explorando una variedad de estilos y temáticas que abarcaban desde el rock clásico hasta el rock progresivo y el rock alternativo. Con el paso de los años, el género continuó evolucionando y diversificándose, dando lugar a una rica y vibrante escena musical que sigue siendo relevante en la actualidad, con artistas como Héroes del Silencio, Maná, Café Tacvba, y muchas más dejando una marca indeleble en la cultura musical hispanohablante.";



        dbHelper.insertTextWithName(getString(R.string.rap_en_espanol_actual), texto18, getString(R.string.cruz_cafune), getString(R.string.delaossa), "Natos y Waor",getString(R.string.ill_peke_o_y_ergo_pro),getString(R.string.c_tangana),getString(R.string.dano));
        dbHelper.insertTextWithName(getString(R.string.urbana_actual_en_espanol), texto20, getString(R.string.el_virtual), getString(R.string.sticky_ma), getString(R.string.mda),"Diego900",getString(R.string.saramalacara),getString(R.string.rojuu));
        dbHelper.insertTextWithName(getString(R.string.country), texto2, getString(R.string.willie_nelson), getString(R.string.johnny_cash), getString(R.string.dolly_parton), "Patsy Cline", "Shania Twain", "Hank Williams");
        dbHelper.insertTextWithName(getString(R.string.musica_clasica), texto1, getString(R.string.amadeus_mozart), getString(R.string.beethoven), getString(R.string.bach), getString(R.string.tchaikovsky), getString(R.string.strauss_ii), getString(R.string.vivaldi));
        dbHelper.insertTextWithName(getString(R.string.r_and_b_soul), texto3, getString(R.string.rnbray_charles), getString(R.string.rnbjames_brown), getString(R.string.rnbaretha_franklin), getString(R.string.rnbmarvin_gaye),getString(R.string.rnbstevie_wonder), "Otis Redding");
        dbHelper.insertTextWithName(getString(R.string.r_and_b_actual), texto4, "Beyoncé", "Rihanna", "Usher", "Alicia Keys", "TLC", "The Weeknd");
        dbHelper.insertTextWithName(getString(R.string.rap_de_los_90), texto5, "2Pac", "The Notorious B.I.G.", "Nas", "Snoop Dogg", "Dr. Dre", "Wu-Tang Clan");
        dbHelper.insertTextWithName(getString(R.string.rock_siglo_xx), texto7, "The Beatles", "The Rolling Stones", "Led Zeppelin", "Pink Floyd", "Nirvana", "The Doors");
        dbHelper.insertTextWithName(getString(R.string.pop_siglo_xx), texto8, "Michael Jackson", "Madonna", "Prince", "Whitney Houston", "Elton John", "Celine Dion");
        dbHelper.insertTextWithName(getString(R.string.rock_actual), texto9, "Foo Fighters", "Coldplay", "Muse", "Arctic Monkeys", "Imagine Dragons", "The Black Keys");
        dbHelper.insertTextWithName(getString(R.string.jazz), texto10, "Louis Armstrong", "Duke Ellington", "Miles Davis", "John Coltrane", "Charlie Parker", "Ella Fitzgerald");
        dbHelper.insertTextWithName(getString(R.string.pop_actual), texto11, "Lana Del Rey", "Ed Sheeran", "Ariana Grande", "Billie Eilish", "Justin Bieber", "Dua Lipa");
        dbHelper.insertTextWithName(getString(R.string.trap), texto12, "Young Thug", "Future", "Chief Keef", "Lil Uzi Vert", "Travis Scott", "21 Savage");
        dbHelper.insertTextWithName(getString(R.string.rap_actual), texto6, "Kendrick Lamar", "Drake", "Eminem", "Lil Wayne", "Kanye West", "Jay-Z");
        dbHelper.insertTextWithName(getString(R.string.electronica), texto13, "Daft Punk", "The Chemical Brothers", "Deadmau5", "Skrillex", "Calvin Harris", "Avicii");
        dbHelper.insertTextWithName(getString(R.string.heavy), texto14, "Black Sabbath", "Metallica", "Iron Maiden", "Judas Priest", "Megadeth", "Guns N' Roses");
        dbHelper.insertTextWithName(getString(R.string.disco), texto15, "Bee Gees", "Donna Summer", "Chic", "Gloria Gaynor", "KC and the Sunshine Band", "Earth, Wind & Fire");
        dbHelper.insertTextWithName(getString(R.string.alternativa), texto16, "Yung Lean", "Bladee", "OutKast", "MF DOOM", "A Tribe Called Quest", "Kid Cudi");
        dbHelper.insertTextWithName(getString(R.string.inicios_rap_en_espanol), texto17, "Vico C", "Control Machete", "El General", "Violadores del verso", "7 notas 7 colores", "Mala Rodríguez");
        dbHelper.insertTextWithName(getString(R.string.pop_actual_en_espanol), texto19, "Shakira", "Rosalía", "Aitana", "Enrique Iglesias", "Luis Fonsi", "Sebastián Yatra");
        dbHelper.insertTextWithName(getString(R.string.trap_en_espanol), texto21, "Eladio Carrión", "Los Alemanes", "Duki", "Anuel AA", "Bad Bunny", "Yung Beef");
        dbHelper.insertTextWithName(getString(R.string.rock_en_espanol), texto22, "Soda Stereo", "Héroes del Silencio", "Maná", "Café Tacvba", "Enanitos Verdes", "Extremoduro");

        dbArtistsHelper.insertTextWithName(getString(R.string.cruz_cafune),  "Cruz Cafuné es un destacado rapero y productor originario de Málaga, España, cuyo nombre real es Jaime Alonso. Se ha ganado reconocimiento en la escena musical española e internacional por su estilo distintivo, que combina ritmos melódicos y letras introspectivas con un enfoque emocional y personal. Su música a menudo aborda temas de identidad, amor, desafíos personales y la búsqueda de significado en la vida. Con un flujo suave y un enfoque honesto en sus letras, Cafuné ha ganado el elogio de críticos y fanáticos por igual. Su habilidad para combinar elementos del hip-hop con influencias de otros géneros musicales ha contribuido a su popularidad y lo ha establecido como una figura destacada en el panorama musical contemporáneo.", "Maracucho Bueno Muere Chiquito", "Moonlight922", "Visión Túnel", "Me Muevo Con Dios", "Este álbum es una obra clave en la carrera de Cruz Cafuné. En él, el artista aborda temas profundos y personales, explorando su identidad, sus raíces y su visión del mundo desde la perspectiva de un joven nacido en Maracay, Venezuela.", " Este álbum destaca por su atmósfera introspectiva y emotiva. Cruz Cafuné profundiza en sus experiencias personales y en su crecimiento como artista, ofreciendo una mezcla de ritmos melódicos y letras poéticas que invitan a la reflexión.", "\"Visión Túnel\" es un álbum que refleja la evolución artística de Cruz Cafuné. En este trabajo, el artista muestra una madurez en su estilo y en sus letras, explorando temas como el amor, el éxito, los desafíos personales y la búsqueda de significado en la vida.", "Abarcando géneros como el rap, R&B, reggaetón o estilos como el Jersey Club, el tinerfeño batió todos los récords colocándose en el top de las listas de éxitos con este nuevo trabajo. Este es, sin duda, un disco lleno de colaboraciones en el que podemos encontrar a artistas como Quevedo, Hoke o La Pantera. El disco, de nada más y nada menos que 23 canciones, plasma su identidad bajo el concepto del tiburón, ya que estos necesitan moverse todo el rato, incluso cuando duermen para poder respirar.");
        dbArtistsHelper.insertTextWithName(getString(R.string.delaossa),  "Daniel Martínez de la Ossa, también conocido como Delaossa, es un rapero y compositor originario del barrio malagueño de El Palo, que tiene una fuerte tradición pesquera. Comenzó a interesarse por el hip hop a la temprana edad de 12 años. En 2010, bajo el nombre de Soup, lanzó su primera maqueta llamada “En blanco fácil” junto a Easy y el productor Lápiz. Ese mismo año, formaron el grupo Skill Leaders, que más tarde publicó su primer trabajo grupal titulado “Skillbill” en 2012. Tras un segundo y último álbum como grupo llamado “El Oasis” en 2015, Delaossa fundó el colectivo Space Hammu (también conocido como Space Hammurabi o Los Chicos del Cosmos) en 2015. En los años siguientes, Delaossa continuó lanzando videoclips, singles y colaboraciones con varios artistas.", "Un Perro Andaluz", "Hammu Nation", "La Tour Liffee", "Playa Virginia", "Este es el primer álbum en solitario de Delaossa. En colaboración con el productor J.Moods, presenta un disco de 12 pistas llenas de letras auténticas y beats impactantes. El título hace referencia a la famosa película surrealista de Buñuel y Dalí, y el álbum continúa la narrativa que el colectivo Space Hammu ha estado exponiendo durante años. Destacan temas como “Dicen de Mi” y “Ya Lo Sé”", "Hammu Nation es una declaración de intenciones definitiva. Los artistas se apoyan mutuamente en este proyecto, y la producción se basa en un boombap clásico.\n" +
                "La canción homónima, “Hammu Nation”, cuenta con los mejores coros de la lista y presenta un cierre desbocado de Delaossa que incluso sus propios compañeros tienen que parar.\n" +
                "El colectivo Space Hammu se une para crear un sonido poderoso y auténtico, dejando una huella en la escena del rap español", "Este es un EP (mixtape) de Delaossa con 5 pistas. En él, colabora con artistas como Gese da O, Granuja, Israel B y Cruz Cafuné. La producción está a cargo de J.Moods, Gese Da O y otros productores. Destacan canciones como “Por Nosotros” y “Me Mudé a Madrid”", "En su EP Playa Virginia, Delaossa nos lleva de viaje a su ciudad natal, Málaga. El proyecto incluye 7 canciones, con colaboraciones de Easy-S, Abhir Hathi y otros. La producción está a cargo de varios productores, como Bexnil, Blurred Mirror y Chris Carr. Destacan temas como “No Soy Perfecto” y “Marbella”");
        dbArtistsHelper.insertTextWithName("Ill Pekeño & Ergo Pro",
                "Ill Pekeño & Ergo Pro son un dúo de raperos españoles que han ganado reconocimiento en la escena del hip-hop underground. Con letras afiladas y una producción cruda, su música aborda temas de la vida urbana, la lucha personal y la realidad social.",
                "Ópera Fidelio",
                "Av. Rafaela Ybarra",
                "ANBU",
                "Galerías Deva",
                "Es casi imposible hacer una mención especial a alguno de los temas de ‘Ópera Fidelio’ como sobresaliente por encima de los demás, pasa lo mismo con las rimas. Ergo Pro e Ill Pekeño han conseguido la química perfecta en las dosis justas. Un poco de namedroppin sin ser pedantes, un poco de metarreferencias sin ser superficiales, un poco de historias de barrio sin parecer fantasmas. Como quien no necesita estudiar a última hora para el examen porque lo trae sabido de casa. Como quien no tiene que estudiar demasiado, porque lo ha vivido.",
                "Esta tape, desde la carátula al sexto corte, reflejan lo qué es el rap underground verdadero, descodificando las barras para llevarlas al publico general a nivel nacional. Ill Pequeño es un «emece» que cuida su música, quizá tiene poco repertorio por ello, pero todo es barra por barra y ese rap al final se ve recompensado. Solo hay que ver el feedback que esta teniendo este tap. Desde una de las revelaciones para el rap también underground como Hoke que participa en la última canción, llamada Calle Cortada, hasta el Marqués, aka Israel B. Dos colabos que hablan por si solas, un kie como es el propio Israel o el flow fresco de Hoke, ponen el broche a esta obra de Peke y Bobby Nigeria aka Ergo Pro como guest starring.",
                "\"ANBU\", inspirado en el célebre grupo de ninjas de élite del aclamado anime Naruto, es el título escogido para el nuevo y emocionante EP colaborativo de Saske, Ill Pekeño y Ergo Pro. Este proyecto musical, una fusión de talentos y estilos, consta de cinco tracks donde los artistas despliegan su habilidad lírica y rítmica, comparable a la destreza y precisión de los kunais y shurikens en el mundo ninja.\n" +
                        "\n" +
                        "Este EP es una muestra de la sinergia artística entre Saske, Ill Pekeño y Ergo Pro. Cada tema del álbum sumerge al oyente en una atmósfera distinta pero cohesionada.",
                ""
        );        dbArtistsHelper.insertTextWithName("Dano",
                "Dano, cuyo nombre real es Daniel Álvarez, es un rapero, productor y diseñador gráfico argentino-español conocido por su estilo introspectivo y lírico. Como miembro del colectivo Ziontifik, Dano ha influenciado la escena del hip-hop en España con su enfoque artístico y su habilidad para combinar elementos del rap clásico con una producción contemporánea.",
                "Equilibrio",
                "Kaos Nómada",
                "Inefable",
                "Istmo",
                "'Equilibrio' es uno de los álbumes más destacados de Dano, lanzado en 2009. Con temas como 'Por los Siglos' y 'Capítulo', este álbum se caracteriza por su lirismo profundo y su producción sofisticada, ofreciendo una reflexión sobre el equilibrio personal y artístico.",
                "'Kaos Nómada', lanzado en 2015, explora temas de caos y cambio. Con canciones como 'Libertad' y 'Mi Viaje', el álbum refleja la vida nómada y la constante búsqueda de identidad y propósito, acompañado de una producción rica en matices.",
                "'Inefable', lanzado en 2018, es un trabajo que combina introspección y crítica social. Temas como 'El Dorado' y 'Escarlata' muestran la capacidad de Dano para mezclar storytelling con reflexiones sobre la vida y la sociedad, con una producción cuidada y atmosférica.",
                "'Istmo' es un EP colaborativo con el productor Emelvi, lanzado en 2016. Con pistas como 'Istmo' y 'Aire', este trabajo destaca por su experimentación sonora y su enfoque lírico, explorando la conexión entre lo personal y lo universal."
        );        dbArtistsHelper.insertTextWithName("C. Tangana",
                "C. Tangana, también conocido como Pucho, es un rapero y cantante español que ha logrado gran éxito tanto en el rap como en el pop. Con su estilo versátil y su habilidad para reinventarse, ha influenciado significativamente la música urbana en España.",
                "Ídolo",
                "Avida Dollars",
                "El Madrileño",
                "Bien :( ",
                "'Ídolo' es el álbum que catapultó a C. Tangana a la fama. Con éxitos como 'Mala Mujer' y 'De Pie', el álbum combina trap y pop, mostrando su capacidad para crear hits pegajosos y narrativas personales.",
                "'Avida Dollars' explora temas de fama, éxito y sus costos. Con un sonido más experimental, canciones como 'Llorando en la Limo' y 'No Te Pegas' destacan por su producción innovadora y sus letras reflexivas.",
                "'El Madrileño' es un álbum que fusiona múltiples géneros, desde flamenco hasta reguetón. Con colaboraciones de artistas como Niño de Elche y Andrés Calamaro, este disco demuestra la madurez artística de C. Tangana y su habilidad para cruzar fronteras musicales.",
                "'Bien :( ' es un EP que captura el lado más introspectivo y emocional de C. Tangana. Con temas como 'Nunca Estoy' y 'Demasiadas Mujeres', el EP ofrece una mirada honesta a sus sentimientos y experiencias personales."
        );
        dbArtistsHelper.insertTextWithName("Johnny Cash",  "Johnny Cash (1932-2003) es una figura icónica en la música country y uno de los artistas más influyentes del siglo XX. Conocido como \"The Man in Black\" por su vestimenta característica, Cash es famoso por su voz profunda y grave, y su habilidad para narrar historias a través de sus canciones. Su música abarcó temas de dolor, redención, amor y la lucha de los oprimidos, resonando con una audiencia amplia y diversa. A lo largo de su carrera, Cash luchó con adicciones, pero encontró estabilidad y fuerza en su matrimonio con June Carter. Su legado perdura como un símbolo de la autenticidad y la rebeldía en la música.\n", "At Folsom Prison", "At San Quentin", "American IV: The Man Comes Around", "I Walk the Line", "Grabado en vivo en la prisión de Folsom, este álbum capturó la energía cruda y la conexión genuina de Cash con los reclusos. Con la icónica \"Folsom Prison Blues,\" el álbum revitalizó su carrera y subrayó su empatía por los marginados, destacándose como uno de los más auténticos de su repertorio.", "Otro álbum en vivo en prisión, conocido por la canción \"A Boy Named Sue.\" Captura el espíritu rebelde de Cash y su habilidad para conectar con una audiencia diversa, manteniendo la energía y autenticidad que lo caracterizan. La grabación se convirtió en un éxito comercial y crítico.", "Parte de la serie American Recordings producida por Rick Rubin, este álbum incluye versiones de canciones contemporáneas y clásicos, mostrando una voz envejecida pero emocionalmente potente. Destacan temas como \"Hurt\" y \"Personal Jesus,\" que renovaron su relevancia en la música moderna.", "Este álbum incluye uno de sus mayores éxitos, \"I Walk the Line,\" y es una muestra de su capacidad para combinar diferentes géneros. Las canciones reflejan su lucha personal y profesional, consolidándolo como una figura única en la música country y más allá.");
        dbArtistsHelper.insertTextWithName("Dolly Parton",  "Dolly Parton, nacida en 1946 en Sevierville, Tennessee, es una de las cantautoras y artistas más queridas y exitosas en la historia de la música country. Creció en una familia pobre, pero su talento musical la llevó a la fama rápidamente. Conocida por su voz distintiva y su personalidad vivaz, Parton ha escrito cientos de canciones a lo largo de su carrera, incluyendo clásicos como \"Jolene\" y \"I Will Always Love You.\" Además de su música, Parton es una exitosa actriz y filántropa, dedicando recursos significativos a la educación y otras causas benéficas. Su impacto cultural se extiende más allá de la música, convirtiéndola en un icono global.", "Coat of Many Colors", "Jolene", "Here You Come Again", "Trio", "Inspirado en su infancia en la pobreza, este álbum cuenta con la emotiva canción titular. Es aclamado por su autenticidad y narración, reflejando la habilidad de Parton para transformar experiencias personales en obras de arte universalmente resonantes.", "Este álbum incluye la icónica canción \"Jolene,\" que muestra la capacidad de Parton para capturar emociones complejas en sus letras. Las canciones exploran temas de amor, celos y empoderamiento femenino, consolidando su estatus como una narradora excepcional.", "Marcó su transición hacia el pop, con éxitos como la canción titular y \"Two Doors Down.\" Este álbum demostró su versatilidad como artista, expandiendo su audiencia y mostrando su capacidad para cruzar fronteras musicales sin perder su esencia country.", "Un álbum colaborativo con Emmylou Harris y Linda Ronstadt, aclamado por su armonía y calidad musical. Las voces de las tres artistas se complementan perfectamente, creando un sonido rico y evocador que celebra la tradición del country y el folk.");
        dbArtistsHelper.insertTextWithName("Willie Nelson",  "Willie Nelson, nacido en 1933 en Abbott, Texas, es una leyenda de la música country y uno de los pioneros del movimiento outlaw country, que desafió las normas de la industria de Nashville. Conocido por su voz suave y distintiva y su estilo único de tocar la guitarra, Nelson ha lanzado más de 70 álbumes a lo largo de su extensa carrera. Sus canciones, como \"On the Road Again\" y \"Always on My Mind,\" se han convertido en clásicos. Además de su música, Nelson es conocido por su activismo en pro de la legalización del cannabis y su apoyo a los agricultores estadounidenses. Su influencia perdura en la música y la cultura popular.", "Red Headed Stranger", "Stardust", "Shotgun Willie", "Phases and Stages", "Un álbum conceptual que narra la historia de un fugitivo en el Viejo Oeste. Celebrado por su minimalismo y narración, destaca por su cohesión temática y el innovador uso del espacio musical, convirtiéndolo en una obra maestra del outlaw country.", "Un álbum de versiones de estándares pop y jazz, que demostró la versatilidad de Nelson y se convirtió en un clásico de culto. Con canciones como \"Blue Skies\" y \"Georgia on My Mind,\" muestra su capacidad para reinterpretar clásicos con un toque personal.", "Considerado uno de sus mejores trabajos, combina country, jazz y rock. Este álbum marcó su transición hacia el outlaw country y contiene canciones reflexivas y humorísticas que muestran su habilidad para experimentar con diferentes estilos.", "Un álbum conceptual sobre el ciclo de una relación, visto desde las perspectivas masculina y femenina. Destaca por su estructura narrativa y musical, ofreciendo una exploración profunda y emocional de las dinámicas de las relaciones.");
        dbArtistsHelper.insertTextWithName("Patsy Cline",  "Patsy Cline (1932-1963) es una de las figuras más icónicas en la música country y pop, conocida por su emotiva voz y su habilidad para transmitir una profunda tristeza y vulnerabilidad en sus interpretaciones. Nacida como Virginia Patterson Hensley en Winchester, Virginia, Cline comenzó a cantar a una edad temprana y rápidamente se destacó por su talento vocal. Su carrera despegó en la década de 1950 y 1960 con éxitos como \"Walkin' After Midnight,\" \"I Fall to Pieces,\" \"Crazy,\" y \"She's Got You.\" Cline fue pionera en cruzar las barreras entre la música country y el pop, logrando un éxito que trascendió géneros. Su vida y carrera fueron trágicamente interrumpidas por un accidente aéreo en 1963, pero su influencia en la música persiste, y su legado continúa inspirando a artistas en diversos géneros.", "Patsy Cline", "Showcase", "Sentimentally Yours", "The Patsy Cline Story", "Este álbum debut muestra su capacidad para fusionar el country con elementos de pop, blues y honky-tonk. Con canciones como \"Walkin' After Midnight,\" que se convirtió en su primer gran éxito, el álbum destaca su distintiva voz y su habilidad para interpretar con profunda emoción. Esta grabación inicial estableció a Cline como una fuerza emergente en la música country.", "Con el respaldo de The Jordanaires, este álbum incluye algunos de sus mayores éxitos como \"I Fall to Pieces\" y \"Crazy,\" escrito por Willie Nelson. \"Crazy\" es particularmente notable por su melodía suave y la interpretación apasionada de Cline, consolidando su estatus como una de las mejores vocalistas de su tiempo. El álbum muestra su capacidad para interpretar baladas con un estilo único que resonó tanto en la audiencia country como en la pop.", "Este álbum muestra su habilidad para manejar una variedad de estilos musicales, desde el country hasta el pop y el jazz. Con éxitos como \"She's Got You\" y \"Heartaches,\" el álbum destaca la versatilidad vocal de Cline y su capacidad para hacer que cada canción se sienta personal y conmovedora. La producción sofisticada y los arreglos orquestales añadieron una nueva dimensión a su música, haciendo de este álbum un clásico atemporal.", "Un álbum recopilatorio lanzado después de su muerte, que incluye muchos de sus éxitos más grandes como \"Sweet Dreams (Of You)\" y \"Faded Love.\" Esta colección destaca su breve pero impactante carrera, proporcionando una visión completa de su evolución como artista. Es una retrospectiva esencial que captura la esencia de su talento y su legado duradero en la música country y más allá.");
        dbArtistsHelper.insertTextWithName("Shania Twain",  "Shania Twain, nacida en 1965 en Windsor, Ontario, es una de las artistas femeninas más exitosas en la historia de la música country. Conocida por su fusión innovadora de country y pop, Twain alcanzó la fama mundial en los años 90 con álbumes como \"The Woman in Me\" y \"Come On Over,\" este último siendo el álbum más vendido de una artista femenina en cualquier género. Twain ha ganado numerosos premios, incluidos cinco Grammys, y es conocida por su poderosa voz y carismática presencia escénica. A pesar de enfrentar desafíos personales, incluyendo problemas de salud que afectaron su voz, Twain ha mantenido una exitosa carrera en la música.", "The Woman in Me", "Come On Over", "Up!", "Shania Twain", "Este álbum la catapultó al estrellato con éxitos como \"Any Man of Mine\" y \"Whose Bed Have Your Boots Been Under?\" Marcó el inicio de su colaboración con el productor Mutt Lange, y redefinió el sonido del country-pop.", "El álbum más vendido de una artista femenina, incluye éxitos como \"Man! I Feel Like a Woman!\" y \"You're Still the One.\" La combinación de pop y country, junto con la producción de Mutt Lange, hizo de este álbum un éxito internacional.", "Presentado en tres versiones (pop, country, y world), muestra su versatilidad y alcance global. Canciones como \"I'm Gonna Getcha Good!\" y \"Ka-Ching!\" destacan en este innovador proyecto que atrajo a una audiencia diversa.", "Su álbum debut, menos exitoso que sus trabajos posteriores, pero sentó las bases para su estilo único y su carrera futura. Incluye canciones como \"What Made You Say That\" y \"Dance with the One That Brought You.\"");
        dbArtistsHelper.insertTextWithName("Hank Williams",  "Hank Williams (1923-1953) es considerado una de las figuras más influyentes en la música country. Nacido en Alabama, Williams comenzó su carrera a una edad temprana y rápidamente se hizo famoso por su habilidad para escribir canciones que capturan las luchas humanas y las alegrías simples de la vida. Sus éxitos incluyen clásicos como \"Your Cheatin' Heart,\" \"Hey, Good Lookin',\" y \"I'm So Lonesome I Could Cry.\" A pesar de su corta vida, marcada por problemas de salud y adicción al alcohol, su impacto en la música country es inmenso. Su estilo y letras han inspirado a generaciones de artistas, y su legado sigue vivo en la música y la cultura popular.", "Moanin' the Blues", "Ramblin' Man", "Honky Tonkin'", "Sings", "Este álbum incluye clásicos como \"Long Gone Lonesome Blues\" y \"Lovesick Blues.\" Muestra su talento para expresar dolor emocional y su capacidad para conectar profundamente con su audiencia.", "Lanzado póstumamente, incluye canciones como \"Ramblin' Man\" y \"Take These Chains from My Heart.\" Destaca por su lirismo y la intensidad emocional que caracteriza a Williams.", "Otro lanzamiento póstumo que incluye éxitos como \"Honky Tonk Blues\" y \"I'm a Long Gone Daddy.\" Refleja su influencia duradera en el honky-tonk y el country tradicional.", "Este álbum consolidó su estatus en la música country, con canciones como \"Cold, Cold Heart\" y \"Hey, Good Lookin'.\" Presenta una colección de sus éxitos más perdurables y muestra su habilidad única para la narración musical.");

        dbArtistsHelper.insertTextWithName("Aretha Franklin", "Aretha Franklin (1942-2018), conocida como \"La Reina del Soul,\" es una de las voces más poderosas y emblemáticas de la música popular. Nacida en Memphis, Tennessee, Franklin comenzó a cantar en la iglesia de su padre, un famoso predicador. Su carrera despegó en los años 60 cuando firmó con Atlantic Records, donde su poderosa voz y talento para el gospel, el blues y el soul la llevaron al estrellato. Franklin es conocida por éxitos como \"Respect,\" \"Chain of Fools,\" y \"Think.\" A lo largo de su carrera, ganó 18 premios Grammy y se convirtió en la primera mujer en ingresar al Salón de la Fama del Rock and Roll. Su legado perdura como símbolo de empoderamiento y talento inigualable.", "I Never Loved a Man the Way I Love You", "Lady Soul", "Young, Gifted and Black", "Amazing Grace", "Incluye su icónico éxito \"Respect,\" que se convirtió en un himno de empoderamiento y lucha por los derechos civiles. Este álbum también cuenta con canciones como \"Dr. Feelgood\" y \"Do Right Woman, Do Right Man,\" que muestran la versatilidad de Franklin para abordar tanto baladas desgarradoras como piezas de ritmo más rápido. Este álbum la catapultó al estrellato y estableció su reputación como la Reina del Soul.", "Con éxitos como \"Chain of Fools\" y \"(You Make Me Feel Like) A Natural Woman,\" este álbum destaca su poderosa voz y habilidad para transmitir emociones profundas. La producción de Jerry Wexler y las contribuciones de músicos de la talla de Eric Clapton en la guitarra hicieron de este álbum un clásico instantáneo que consolidó su estatus en la música soul.", "Este álbum muestra su versatilidad con una mezcla de soul, gospel y funk. Incluye éxitos como \"Rock Steady\" y \"Day Dreaming,\" y recibió elogios por su profundidad lírica y musical. Franklin aborda temas de identidad y orgullo racial, creando un trabajo que es tan relevante socialmente como musicalmente innovador.", "Un álbum en vivo grabado en una iglesia bautista, que destaca su herencia gospel. Es uno de los álbumes de gospel más vendidos de todos los tiempos y muestra su talento innato y conexión espiritual. Con canciones como \"How I Got Over\" y \"Precious Memories,\" este álbum captura la intensidad emocional y la espiritualidad que solo Franklin podía ofrecer en un entorno en vivo.");
        dbArtistsHelper.insertTextWithName("Marvin Gaye", "Marvin Gaye (1939-1984) fue un pionero del soul y el R&B, conocido por su voz suave y sus letras profundas. Nacido en Washington, D.C., Gaye comenzó su carrera en la década de 1960 con Motown Records. A lo largo de su carrera, abordó temas como el amor, la política y los derechos civiles, convirtiéndose en una voz poderosa de su generación. Con éxitos como \"What's Going On,\" \"Sexual Healing,\" y \"Let's Get It On,\" Gaye redefinió la música soul. Su trágica muerte a manos de su padre en 1984 conmocionó al mundo, pero su legado perdura como uno de los artistas más influyentes en la música popular.", "What's Going On", "Let's Get It On", "I Want You", "Here, My Dear", "Un álbum conceptual que aborda temas sociales y políticos, ofreciendo una reflexión sobre la guerra, la pobreza y el medio ambiente. Con canciones como \"Mercy Mercy Me (The Ecology)\" y \"Inner City Blues (Make Me Wanna Holler),\" este álbum es considerado una obra maestra del soul consciente. La producción innovadora y las letras introspectivas marcaron un cambio significativo en la música popular de la época.", "Un álbum sensual que explora el amor y la intimidad con una mezcla de soul y funk. La canción titular se convirtió en un clásico del soul, mientras que otras pistas como \"Come Get to This\" y \"Distant Lover\" muestran la habilidad de Gaye para combinar sensualidad con profundidad emocional. Este álbum es conocido por su producción impecable y su lirismo sincero", "Con una producción innovadora y temas de amor y deseo, este álbum muestra una evolución en su sonido. Incluye la canción titular \"I Want You,\" que es una mezcla de funk y soul con una atmósfera íntima y personal. La producción de Leon Ware y el uso de sintetizadores lo hacen destacar como una obra maestra del soul moderno.", "Un álbum personal que narra su divorcio de Anna Gordy. Con una mezcla de dolor y reflexión, este álbum es una mirada cruda y honesta a su vida personal y artística. Canciones como \"When Did You Stop Loving Me, When Did I Stop Loving You\" y \"Anger\" destacan por su sinceridad y su capacidad para transformar el sufrimiento personal en arte conmovedor.");
        dbArtistsHelper.insertTextWithName("Stevie Wonder", "Stevie Wonder, nacido en 1950 en Saginaw, Michigan, es un prodigio musical y una de las figuras más influyentes en la música soul y pop. Ciego desde la infancia, Wonder firmó con Motown Records a la edad de 11 años y rápidamente se convirtió en una estrella. Conocido por su virtuosismo en varios instrumentos, su voz distintiva y su habilidad para componer, Wonder ha lanzado numerosos álbumes exitosos. Sus letras abordan temas de amor, justicia social y espiritualidad. A lo largo de su carrera, ha ganado 25 premios Grammy y ha influido en generaciones de músicos con su innovación y pasión.", "Songs in the Key of Life", "Innervisions", "Talking Book", "Fulfillingness' First Finale", "Un álbum doble que abarca una variedad de géneros y temas, desde la espiritualidad hasta las cuestiones sociales. Con éxitos como \"Sir Duke,\" \"I Wish,\" y \"Isn't She Lovely,\" este álbum es ampliamente considerado su obra maestra. La complejidad de las composiciones y la riqueza de los arreglos muestran su genio musical y su compromiso con la excelencia artística.", "Con canciones como \"Living for the City\" y \"Higher Ground,\" este álbum aborda temas de desigualdad y justicia social. Es conocido por su producción innovadora y letras poderosas. Wonder utiliza sintetizadores y efectos de estudio para crear un sonido distintivo que resuena con el espíritu de la época, marcando un hito en la música soul.", "Este álbum incluye clásicos como \"Superstition\" y \"You Are the Sunshine of My Life.\" Muestra su habilidad para fusionar el soul con elementos de funk y rock. La producción sofisticada y las melodías memorables lo convierten en un hito en su carrera, destacando su capacidad para innovar y mantenerse relevante.", "Un álbum introspectivo con éxitos como \"Boogie On Reggae Woman\" y \"You Haven't Done Nothin'.\" Explora temas personales y sociales, consolidando su estatus como un narrador musical profundo. Este trabajo ganó varios premios Grammy y es elogiado por su cohesión temática y la profundidad emocional de sus composiciones.");
        dbArtistsHelper.insertTextWithName("Otis Redding", "Otis Redding (1941-1967) fue una de las voces más emotivas y poderosas del soul. Nacido en Dawson, Georgia, Redding comenzó su carrera en la música gospel antes de mudarse al soul y R&B. Conocido por su capacidad para transmitir una profundidad emocional en sus interpretaciones, Redding alcanzó la fama con canciones como \"Respect,\" \"Try a Little Tenderness,\" y \"(Sittin' On) The Dock of the Bay.\" Su carrera fue trágicamente interrumpida por su muerte en un accidente aéreo a los 26 años. A pesar de su corta vida, su impacto en la música es inmenso, y su legado sigue inspirando a artistas de todo el mundo.", "Otis Blue: Otis Redding Sings Soul", "The Soul Album", "Complete & Unbelievable: The Otis Redding Dictionary of Soul", "The Dock of the Bay", "Incluye clásicos como \"Respect\" y \"A Change Is Gonna Come.\" Este álbum muestra su habilidad para interpretar tanto canciones originales como versiones, destacando su voz única y emocional. La intensidad de su interpretación y la calidad de los arreglos musicales hacen de este álbum una joya del soul clásico.", "Con canciones como \"Just One More Day\" y \"Cigarettes and Coffee,\" este álbum destaca por su producción refinada y su interpretación apasionada. Redding combina elementos de soul, R&B y gospel para crear un sonido que es a la vez profundo y accesible, consolidando su estatus en la música soul.", "Incluye \"Try a Little Tenderness\" y \"Fa-Fa-Fa-Fa-Fa (Sad Song).\" Es una muestra de su versatilidad y su habilidad para fusionar diferentes estilos dentro del soul. Este álbum es elogiado por su innovación musical y la capacidad de Redding para expresar una amplia gama de emociones a través de su poderosa voz.", "Publicado póstumamente, incluye \"(Sittin' On) The Dock of the Bay,\" que se convirtió en su canción más icónica. El álbum captura su habilidad para mezclar melancolía y optimismo en su música. Las canciones reflejan su madurez como artista y su capacidad para conectar con el oyente en un nivel profundo y personal.");
        dbArtistsHelper.insertTextWithName("James Brown", "James Brown (1933-2006), conocido como \"El Padrino del Soul,\" es una de las figuras más influyentes en la música funk y soul. Nacido en Barnwell, Carolina del Sur, Brown superó una infancia difícil para convertirse en un icono de la música. Conocido por su enérgico estilo de actuación y su innovadora música, Brown es responsable de éxitos como \"I Got You (I Feel Good),\" \"Papa's Got a Brand New Bag,\" y \"Sex Machine.\" Su música y su estilo han dejado una marca indeleble en el soul, el funk y la música popular en general, influenciando a innumerables artistas. Además de su carrera musical, Brown fue un activista por los derechos civiles.", "Live at the Apollo", "Papa's Got a Brand New Bag", "The Payback", "Sex Machine", "Un álbum en vivo que captura la energía y el carisma de Brown en el escenario. Con canciones como \"I'll Go Crazy,\" es considerado uno de los mejores álbumes en vivo de todos los tiempos. La intensidad de su actuación y la reacción del público hacen de este álbum una experiencia auditiva electrizante que muestra a Brown en su mejor momento.", "Con el éxito titular, este álbum marcó una evolución en su sonido hacia el funk. Es conocido por su ritmo innovador y su influencia duradera en la música funk. La combinación de ritmos sincopados y la energía inagotable de Brown en temas como \"I Got You (I Feel Good)\" y \"It's a Man's Man's Man's World\" revolucionaron la música popular.", "Un álbum conceptual que explora temas de venganza y justicia. Con canciones como \"The Payback\" y \"Doing the Best I Can,\" es uno de sus trabajos más aclamados. La producción rica en detalles y los ritmos potentes muestran la habilidad de Brown para contar historias complejas a través de su música, consolidando su estatus como un pionero del funk.", "Incluye el icónico \"Get Up (I Feel Like Being a) Sex Machine.\" Este álbum muestra su habilidad para fusionar ritmos pegajosos con una energía imparable, solidificando su legado en el funk. La interacción dinámica entre Brown y su banda, The J.B.'s, en pistas como \"Super Bad\" y \"Soul Power,\" subraya su impacto duradero en la música.");
        dbArtistsHelper.insertTextWithName("Ray Charles", "Ray Charles (1930-2004), conocido como \"El Genio,\" es uno de los músicos más versátiles y talentosos de todos los tiempos. Nacido en Albany, Georgia, Charles quedó ciego a los siete años, pero eso no detuvo su pasión por la música. Comenzó su carrera tocando el piano en pequeños clubes antes de firmar con Atlantic Records, donde fusionó el jazz, el blues, el gospel y el R&B en un estilo único que revolucionó la música popular. Con éxitos como \"What'd I Say,\" \"Georgia on My Mind,\" y \"Hit the Road Jack,\" Charles ganó numerosos premios Grammy y se convirtió en un ícono cultural. Su habilidad para cruzar géneros y su emotiva interpretación lo han asegurado un lugar en la historia de la música.", "Ray Charles", "The Genius of Ray Charles", "Modern Sounds in Country and Western Music", "Genius Loves Company", "Este álbum debut con Atlantic Records incluye clásicos como \"I've Got a Woman\" y \"Hallelujah I Love Her So.\" Es una fusión de R&B, gospel y blues que estableció a Charles como un innovador musical. Las canciones muestran su habilidad para combinar influencias diversas en un sonido cohesivo y emocionante.", "Este álbum es una muestra de su versatilidad, con una cara dedicada al jazz y otra al big band. Incluye versiones de estándares como \"Come Rain or Come Shine\" y \"Let the Good Times Roll,\" y destaca su capacidad para adaptarse a diferentes estilos musicales con igual maestría.", "Un álbum innovador que mezcla country y soul, desafiando las convenciones musicales de la época. Incluye éxitos como \"I Can't Stop Loving You\" y \"You Don't Know Me,\" y es ampliamente considerado uno de los álbumes más importantes de su carrera, mostrando su habilidad para trascender géneros.", "Este álbum de duetos, lanzado poco antes de su muerte, presenta colaboraciones con artistas como Norah Jones y Elton John. Con versiones de clásicos como \"Here We Go Again\" y \"Sorry Seems to Be the Hardest Word,\" este trabajo es una celebración de su legado musical y su influencia perdurable.");

        dbArtistsHelper.insertTextWithName("Beyoncé", "Beyoncé Giselle Knowles-Carter, nacida el 4 de septiembre de 1981 en Houston, Texas, es una de las artistas más influyentes y exitosas de la música contemporánea. Comenzó su carrera como la vocalista principal del grupo Destiny's Child en los años 90, alcanzando el estrellato con éxitos como \"Say My Name\" y \"Survivor.\" En 2003, lanzó su carrera en solitario con el álbum \"Dangerously in Love,\" que consolidó su estatus como una superestrella global. Con una voz poderosa y una presencia escénica inigualable, Beyoncé ha sido galardonada con numerosos premios Grammy y ha vendido millones de discos en todo el mundo. Es conocida por su activismo y su trabajo en pro de los derechos de las mujeres y la comunidad afroamericana.", "Dangerously in Love", "B'Day", "Lemonade", "4", "El álbum debut de Beyoncé como solista incluye éxitos como \"Crazy in Love\" y \"Baby Boy.\" Este disco muestra su versatilidad vocal y su capacidad para fusionar R&B con pop, hip-hop y soul. Fue un éxito crítico y comercial, ganando cinco premios Grammy.", "Publicado el día de su cumpleaños, este álbum incluye temas como \"Irreplaceable\" y \"Déjà Vu.\" Es conocido por sus fuertes influencias de funk y soul, así como por su energía y producción sofisticada. Beyoncé ganó varios premios Grammy por este trabajo.", "Este álbum visual es uno de sus trabajos más aclamados, explorando temas de traición, feminismo y empoderamiento negro. Canciones como \"Formation\" y \"Sorry\" destacan en una mezcla de géneros que van desde el R&B hasta el rock y el country. \"Lemonade\" es considerado un hito cultural y musical.", "Un álbum que muestra su madurez artística, con canciones como \"Run the World (Girls)\" y \"Love on Top.\" Fusiona elementos de R&B, soul, funk y pop, y destaca por su producción y la profundidad emocional de las letras.");
        dbArtistsHelper.insertTextWithName("Rihanna", "Robyn Rihanna Fenty, nacida el 20 de febrero de 1988 en Barbados, es una de las artistas más exitosas y versátiles de la música pop y R&B. Descubierta por el productor Evan Rogers, Rihanna firmó con Def Jam Recordings y lanzó su álbum debut \"Music of the Sun\" en 2005. Su estilo musical ha evolucionado a lo largo de los años, abarcando géneros como pop, reggae, hip-hop y EDM. Con éxitos globales como \"Umbrella,\" \"We Found Love\" y \"Diamonds,\" Rihanna ha ganado numerosos premios y se ha establecido como un icono de la moda y empresaria con su línea de cosméticos Fenty Beauty y su marca de lencería Savage X Fenty.", "Good Girl Gone Bad", "Rated R", "Loud", "Anti", "Este álbum incluye el exitoso sencillo \"Umbrella\" y marcó un cambio en su imagen y sonido hacia un estilo más maduro y atrevido. Con influencias de pop, R&B y dance, el álbum consolidó su estatus como una estrella internacional.", "Con un tono más oscuro y personal, este álbum incluye éxitos como \"Russian Roulette\" y \"Rude Boy.\" Las letras y la producción reflejan un período de crecimiento personal y profesional para Rihanna, mostrando su capacidad para abordar temas más serios y emocionales.", "Con éxitos como \"Only Girl (In the World)\" y \"What's My Name?\" este álbum presenta una mezcla vibrante de pop y dancehall. Es conocido por su energía y la diversidad de estilos, destacando la capacidad de Rihanna para reinventarse continuamente.", "Un álbum aclamado por la crítica que incluye el sencillo \"Work.\" Con una producción más experimental y una mezcla de géneros que incluyen R&B, soul, y hip-hop, \"Anti\" muestra su madurez artística y su disposición para tomar riesgos creativos.");
        dbArtistsHelper.insertTextWithName("Usher", "Usher Raymond IV, nacido el 14 de octubre de 1978 en Dallas, Texas, es uno de los artistas más exitosos del R&B contemporáneo. Descubierto a una edad temprana, Usher lanzó su álbum debut homónimo en 1994, pero fue con su segundo álbum, \"My Way\" (1997), que alcanzó el estrellato. Con una carrera que abarca más de dos décadas, Usher ha vendido millones de discos y ha ganado numerosos premios, incluidos varios Grammy. Es conocido por su voz suave, sus habilidades de baile y su capacidad para fusionar R&B con pop y hip-hop. Además de su carrera musical, Usher ha trabajado como actor y mentor en programas de talento.", "My Way", "8701", "Confessions", "Raymond v. Raymond", "Este álbum incluye éxitos como \"You Make Me Wanna...\" y \"Nice & Slow.\" Fue un gran éxito comercial y ayudó a establecer a Usher como una estrella del R&B. La producción y las letras reflejan una mezcla perfecta de sensualidad y destreza vocal.", "Con éxitos como \"U Got It Bad\" y \"U Don't Have to Call,\" este álbum muestra su evolución artística y madurez. La producción innovadora y la profundidad emocional de las canciones consolidaron su estatus en la industria musical.\n", "Uno de los álbumes más vendidos y aclamados de su carrera, incluye éxitos como \"Yeah!\" y \"Burn.\" Este álbum es conocido por sus letras confesionales y su producción impecable, explorando temas de amor y arrepentimiento.", "Un álbum que aborda temas personales y complejos, con éxitos como \"OMG\" y \"There Goes My Baby.\" La producción moderna y las letras introspectivas muestran su capacidad para reinventarse y mantenerse relevante en la industria musical.");
        dbArtistsHelper.insertTextWithName("Alicia Keys", "Alicia Keys, nacida Alicia Augello Cook el 25 de enero de 1981 en Nueva York, es una cantautora, pianista y actriz de renombre. Desde temprana edad, mostró un talento excepcional para la música, componiendo canciones y tocando el piano. Su álbum debut \"Songs in A Minor\" (2001) fue un éxito inmediato, ganando cinco premios Grammy y estableciéndola como una estrella emergente. Conocida por su fusión de R&B, soul y pop, y su habilidad para interpretar baladas emotivas, Keys ha vendido millones de discos en todo el mundo. Además de su carrera musical, es una activista comprometida con diversas causas sociales y humanitarias.", "Songs in A Minor", "The Diary of Alicia Keys", "As I Am", "Girl on Fire", "Su álbum debut incluye el éxito \"Fallin'\" y es una mezcla de R&B, soul y jazz. Fue un éxito comercial y crítico, ganando cinco premios Grammy y estableciendo a Keys como una de las artistas más talentosas de su generación.", "Este álbum incluye éxitos como \"You Don't Know My Name\" y \"If I Ain't Got You.\" La producción refinada y las letras introspectivas consolidaron su estatus como una de las principales voces del R&B contemporáneo.", "Con éxitos como \"No One,\" este álbum muestra su evolución artística y su habilidad para combinar pop y R&B de manera efectiva. La producción pulida y las letras emotivas destacan su talento como compositora e intérprete.", "Incluye el éxito titular \"Girl on Fire,\" una poderosa balada que muestra su fuerza vocal y su capacidad para inspirar a través de su música. El álbum mezcla elementos de R&B, soul y pop, y refleja su madurez artística.");
        dbArtistsHelper.insertTextWithName("TLC",
                "TLC es un grupo estadounidense de R&B formado en Atlanta, Georgia, en 1990. Compuesto por Tionne 'T-Boz' Watkins, Lisa 'Left Eye' Lopes y Rozonda 'Chilli' Thomas, el grupo se hizo famoso por su estilo innovador y su impacto en la música y la cultura popular de los 90. Con su mezcla de R&B, hip-hop y funk, TLC ha vendido millones de discos y ha ganado numerosos premios.",
                "Ooooooohhh... On the TLC Tip",
                "CrazySexyCool",
                "FanMail",
                "3D",
                "Ooooooohhh... On the TLC Tip es el álbum debut de TLC, lanzado en 1992. Con éxitos como 'Ain't 2 Proud 2 Beg' y 'What About Your Friends', el álbum introduce al grupo con una mezcla fresca de R&B y hip-hop, estableciendo su estilo distintivo y enérgico.",
                "CrazySexyCool, lanzado en 1994, es uno de los álbumes más icónicos de TLC. Incluye éxitos como 'Creep', 'Waterfalls' y 'Red Light Special'. Este álbum combina R&B suave con mensajes poderosos y producción sofisticada, y es considerado un clásico del género.",
                "FanMail, lanzado en 1999, muestra una evolución en el sonido de TLC con una mezcla de R&B y pop futurista. Con éxitos como 'No Scrubs' y 'Unpretty', el álbum aborda temas de autoempoderamiento y amor propio. Es un testimonio de la versatilidad y relevancia del grupo.",
                "3D, lanzado en 2002, es el primer álbum de TLC después de la trágica muerte de Lisa 'Left Eye' Lopes. Con canciones como 'Girl Talk' y 'Damaged', el álbum es una celebración del legado de Left Eye y una muestra de la resiliencia del grupo. Combina el R&B clásico de TLC con nuevos elementos, manteniendo su esencia intacta."
        );

        dbArtistsHelper.insertTextWithName("The Weeknd", "Abel Makkonen Tesfaye, conocido profesionalmente como The Weeknd, nació el 16 de febrero de 1990 en Toronto, Canadá. Comenzó su carrera musical en 2010 al subir canciones anónimamente a YouTube y ganó rápidamente popularidad. Conocido por su estilo distintivo que mezcla R&B, pop, y elementos electrónicos, The Weeknd ha lanzado múltiples álbumes exitosos que han recibido aclamación de la crítica y premios, incluidos los Grammy. Su música a menudo explora temas oscuros como la adicción, el amor y la desilusión, y es famoso por su voz etérea y su habilidad para crear atmósferas sonoras únicas. Además de su carrera musical, ha trabajado como productor y colaborado con diversos artistas.", "House of Balloons", "Beauty Behind the Madness", "Starboy", "After Hours", "Este mixtape debut fue lanzado de forma gratuita y es ampliamente aclamado por su producción innovadora y su atmósfera oscura y emocional. Canciones como \"Wicked Games\" y \"High for This\" destacan por su lirismo crudo y su sonido único, estableciendo a The Weeknd como un pionero del PBR&B.", "Este álbum catapultó a The Weeknd al estrellato mundial, con éxitos como \"Can't Feel My Face\" y \"The Hills.\" El álbum mezcla R&B, pop y elementos de rock, y recibió críticas positivas por su producción y la profundidad emocional de sus letras. Ganó varios premios y consolidó su lugar en la música mainstream.", "Con la colaboración de Daft Punk, este álbum incluye éxitos como \"Starboy\" y \"I Feel It Coming.\" Es conocido por su mezcla de R&B y electropop, y recibió elogios por su producción y la evolución artística de The Weeknd. El álbum ganó un Grammy y se destacó por su exploración de temas como la fama y la identidad.", "Un álbum conceptual que incluye éxitos como \"Blinding Lights\" y \"Heartless.\" Con una producción que fusiona synth-pop, R&B y new wave, el álbum es aclamado por su cohesión y la madurez de su narrativa. \"After Hours\" es un viaje sonoro y emocional que destaca la capacidad de The Weeknd para reinventarse y seguir siendo relevante en la industria musical.");
        dbArtistsHelper.insertTextWithName("2Pac", "Tupac Shakur, conocido como 2Pac, nació el 16 de junio de 1971 en East Harlem, Nueva York. Es uno de los raperos más influyentes de todos los tiempos, conocido por su estilo lírico y su capacidad para abordar temas sociales y políticos en su música. A lo largo de su carrera, Tupac lanzó numerosos álbumes exitosos y protagonizó varias películas. Su vida estuvo marcada por controversias y conflictos, incluyendo una notoria rivalidad con The Notorious B.I.G. Tupac fue trágicamente asesinado en un tiroteo en Las Vegas el 13 de septiembre de 1996, dejando un legado perdurable en la música y la cultura pop.", "All Eyez on Me", "Me Against the World", "The Don Killuminati: The 7 Day Theory", "Strictly 4 My N.I.G.G.A.Z.", "Este doble álbum es considerado uno de los mejores trabajos de 2Pac y una joya del rap. Incluye éxitos como \"California Love\" y \"How Do U Want It\". Es una celebración de su éxito y una declaración de su fuerza artística, abarcando temas desde la fiesta hasta la reflexión personal.", "Escrito durante un período de encarcelamiento, este álbum es introspectivo y revela el lado más vulnerable de Tupac. Canciones como \"Dear Mama\" y \"So Many Tears\" destacan su capacidad para combinar introspección y crítica social con una sensibilidad poética única.", "Lanzado bajo su alias Makaveli, este álbum fue completado poco antes de su muerte y presenta una atmósfera más oscura y confrontativa. \"Hail Mary\" y \"To Live & Die in L.A.\" son algunas de las canciones que destacan su aguda percepción y su estilo lírico inimitable.", "Este álbum solidificó a 2Pac como una voz poderosa en el rap, con letras que abordan la injusticia social y la vida en los barrios marginales. Canciones como \"Keep Ya Head Up\" y \"I Get Around\" muestran su habilidad para mezclar mensajes serios con éxitos comerciales.");
        dbArtistsHelper.insertTextWithName("The Notorious B.I.G.", "Christopher Wallace, conocido como The Notorious B.I.G. o Biggie Smalls, nació el 21 de mayo de 1972 en Brooklyn, Nueva York. Conocido por su distintiva voz y habilidad para contar historias, Biggie se convirtió rápidamente en una figura central en el hip-hop. Su vida estuvo marcada por su meteórico ascenso al estrellato y su participación en la infame rivalidad de la Costa Este y Oeste. Biggie lanzó solo dos álbumes antes de ser asesinado el 9 de marzo de 1997 en Los Ángeles. Su legado perdura a través de su música, que sigue siendo influyente y relevante.", "Ready to Die", "Life After Death", "Born Again", "Duets: The Final Chapter", "El álbum debut de Biggie es un clásico del hip-hop, que narra su vida desde la pobreza hasta el éxito. Con éxitos como \"Juicy\" y \"Big Poppa\", este álbum combina una narrativa cruda con una producción brillante, consolidando su estatus como una leyenda.", "Lanzado póstumamente, este álbum doble es una obra maestra del hip-hop. Incluye éxitos como \"Hypnotize\" y \"Mo Money Mo Problems\", mostrando la capacidad de Biggie para combinar letras introspectivas con himnos comerciales, redefiniendo el género.", "Este álbum póstumo reúne material inédito y colaboraciones con otros artistas. Aunque no tan aclamado como sus trabajos anteriores, proporciona una mirada adicional al talento de Biggie y su impacto continuo en el hip-hop.", "Un álbum póstumo que presenta colaboraciones con artistas contemporáneos. A pesar de la controversia sobre las remezclas, el álbum mantiene viva la influencia de Biggie en el hip-hop moderno, destacando su versatilidad y capacidad de trascender generaciones.");
        dbArtistsHelper.insertTextWithName("Nas", "Nasir Jones, conocido como Nas, nació el 14 de septiembre de 1973 en Brooklyn, Nueva York. Criado en el barrio de Queensbridge, Nas es considerado uno de los mejores letristas del hip-hop, conocido por su narrativa y su habilidad para capturar la vida urbana en sus rimas. Desde su aclamado debut \"Illmatic\" en 1994, Nas ha lanzado numerosos álbumes que han sido elogiados tanto por la crítica como por sus seguidores. Su carrera ha estado marcada por su capacidad para evolucionar y mantenerse relevante en el cambiante paisaje del hip-hop.", "Illmatic", "It Was Written", "Stillmatic", "God's Son", "Considerado uno de los mejores álbumes de hip-hop de todos los tiempos, \"Illmatic\" es una obra maestra de narrativa y producción. Con clásicos como \"N.Y. State of Mind\" y \"The World Is Yours\", este álbum captura la vida en Queensbridge con una precisión poética.", "Este álbum consolidó a Nas como una estrella del hip-hop, con éxitos como \"If I Ruled the World (Imagine That)\". Con una producción más pulida y narrativas complejas, Nas demostró su habilidad para evolucionar sin perder su esencia lírica.", "Un regreso triunfal, \"Stillmatic\" incluye el famoso disstrack \"Ether\" dirigido a Jay-Z. Este álbum es una afirmación de su talento y relevancia en el hip-hop, con una mezcla de temas introspectivos y sociales.", "Este álbum es una reflexión profunda sobre la vida, la muerte y la espiritualidad. Con éxitos como \"Made You Look\" y \"I Can\", Nas muestra su versatilidad y profundidad emocional, consolidando su estatus como una voz influyente en el hip-hop.");
        dbArtistsHelper.insertTextWithName("Dr. Dre", "Andre Romelle Young, conocido como Dr. Dre, nació el 18 de febrero de 1965 en Compton, California. Es un productor, rapero y empresario que ha jugado un papel crucial en la evolución del hip-hop. Como miembro fundador de N.W.A y luego como solista, Dre ha sido una fuerza impulsora detrás del gangsta rap y el G-funk. Además de su propia música, ha lanzado las carreras de artistas como Snoop Dogg, Eminem y 50 Cent. Su producción impecable y su visión empresarial lo han hecho una leyenda en la industria musical.", "The Chronic", "2001", "Compton", "I Need a Doctor", "Este álbum debut revolucionó el hip-hop con su sonido G-funk y producción innovadora. Con clásicos como \"Nuthin' but a 'G' Thang\" y \"Let Me Ride\", \"The Chronic\" estableció a Dre como un maestro de la producción y un narrador lírico.", "Con éxitos como \"Still D.R.E.\" y \"Forgot About Dre\", este álbum solidificó el legado de Dre como productor y artista. Es conocido por su producción sofisticada y colaboraciones con artistas de primer nivel, redefiniendo el sonido del hip-hop para una nueva generación.", "Inspirado por la película \"Straight Outta Compton\", este álbum es un tributo a sus raíces y presenta colaboraciones con artistas contemporáneos. Con una producción impecable y una narrativa profunda, \"Compton\" es una muestra de su evolución y relevancia continua en el hip-hop.", "Uno de sus singles más famosos.");
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
        dbArtistsHelper.insertTextWithName("Wu-Tang Clan", "El Wu-Tang Clan es un grupo de hip-hop formado en Staten Island, Nueva York, en 1992. Compuesto por nueve miembros fundadores, incluyendo RZA, GZA, Method Man, Raekwon, Ghostface Killah, y Ol' Dirty Bastard, el grupo es conocido por su estilo distintivo y líricas complejas. Su enfoque en la producción y sus estrategias empresariales innovadoras los han convertido en una fuerza duradera en el hip-hop. Han lanzado numerosos álbumes aclamados y cada miembro ha tenido carreras solistas exitosas, contribuyendo a su estatus legendario.", "Enter the Wu-Tang (36 Chambers)", "Wu-Tang Forever", "Liquid Swords", "Only Built 4 Cuban Linx", "El álbum debut del Wu-Tang Clan es una obra maestra del hip-hop que presenta un estilo crudo y líricas complejas. Con clásicos como \"C.R.E.A.M.\" y \"Protect Ya Neck\", este álbum estableció al grupo como una fuerza innovadora y revolucionaria en el género.", "Este álbum doble es una expansión ambiciosa de su sonido, con una producción más pulida y temas más variados. Con éxitos como \"Triumph\", \"Wu-Tang Forever\" muestra la evolución del grupo y su capacidad para mantenerse relevante en el cambiante panorama del hip-hop.", "Disco del integrante del grupo GZA. Si desde fuera se podía pensar que Wu-Tang era todo calle y trapis, en esa obra magna llamada ‘Liquid Swords’, GZA demostró que podían ser reflexivos, cerebrales y analíticos… y además que la vida es como una partida de ajedrez.\n" +
                "\n" +
                "Incluso a día de hoy, el álbum es absolutamente maravilloso desde la primera hasta la última canción.", "La perfección del sonido Wu-Tang y el máximo exponente de la habilidad de sus miembros en el rap. Raekwon se convertía en una especie de mafioso y entregaba uno de los mejores discos en solitario (si no el mejor) de la historia del clan.\n" +
                        "\n" +
                        "RZA estaba en estado de gracia en la producción (probablemente nunca se superó) y los raps de Raekwon son amplificados por la compañía de Ghostface Killah, ‘Only Built 4 Cuban Linx’ es sencillamente uno de los mejores discos de la historia de la música.");

        dbArtistsHelper.insertTextWithName("The Beatles", "The Beatles, originarios de Liverpool, Inglaterra, se formaron en 1960. Integrado por John Lennon, Paul McCartney, George Harrison y Ringo Starr, el grupo se convirtió en una de las bandas más influyentes y exitosas de todos los tiempos. Con su mezcla innovadora de rock, pop y elementos psicodélicos, The Beatles transformaron la música popular y la cultura. Desde sus inicios en The Cavern Club hasta su disolución en 1970, lanzaron una serie de álbumes icónicos que siguen siendo reverenciados. Su legado ha perdurado a través de generaciones, inspirando a innumerables músicos y fanáticos.", "Sgt. Pepper's Lonely Hearts Club Band", "Revolver", "Abbey Road", "The Beatles", "Este álbum revolucionó la música pop con su enfoque experimental y cohesión conceptual. Incluye clásicos como \"Lucy in the Sky with Diamonds\" y \"A Day in the Life\". Es conocido por su innovadora producción y la integración de múltiples géneros, estableciendo un nuevo estándar para los álbumes de rock.", "Considerado uno de los mejores álbumes de rock, \"Revolver\" muestra la transición de The Beatles hacia un sonido más experimental. Con canciones como \"Eleanor Rigby\" y \"Tomorrow Never Knows\", el álbum explora nuevas técnicas de grabación y temas líricos profundos.", "Con su icónica portada, \"Abbey Road\" es una mezcla de rock, pop y blues, destacando la habilidad de la banda para crear música innovadora. Canciones como \"Come Together\" y el medley del lado B son ejemplos de su maestría en la composición y producción.", "Oficialmente titulado \"The Beatles\", este álbum doble es una colección ecléctica de canciones que abarcan varios estilos y géneros. Con temas como \"While My Guitar Gently Weeps\" y \"Blackbird\", refleja tanto la diversidad musical del grupo como sus tensiones internas.");
        dbArtistsHelper.insertTextWithName("The Rolling Stones", "Formados en Londres en 1962, The Rolling Stones son una de las bandas de rock más longevas y exitosas. Con Mick Jagger en la voz y Keith Richards en la guitarra, el grupo se destacó por su estilo provocador y su energía en el escenario. A lo largo de su carrera, han lanzado numerosos álbumes que han definido el rock and roll. Su sonido ha evolucionado, incorporando elementos de blues, rock, y otros géneros, y han influido a generaciones de músicos. Aún activos, The Rolling Stones continúan llenando estadios y lanzando nueva música.", "Exile on Main St.", "Let It Bleed", "Sticky Fingers", "Beggars Banquet", "Considerado uno de los mejores álbumes de rock, \"Exile on Main St.\" es una mezcla de rock, blues y soul. Grabado en una villa en Francia, el álbum incluye clásicos como \"Tumbling Dice\" y \"Rocks Off\", mostrando el lado más crudo y creativo de la banda.", "Con canciones emblemáticas como \"Gimme Shelter\" y \"You Can't Always Get What You Want\", este álbum refleja la transición de la banda hacia un sonido más maduro y experimental. Es conocido por su mezcla de rock y blues, y su producción innovadora.", "Este álbum es famoso tanto por su música como por su icónica portada diseñada por Andy Warhol. Con temas como \"Brown Sugar\" y \"Wild Horses\", \"Sticky Fingers\" combina rock con influencias de country y blues, consolidando el estilo distintivo de la banda.", "Marcando un regreso a sus raíces de blues, \"Beggars Banquet\" incluye clásicos como \"Sympathy for the Devil\" y \"Street Fighting Man\". El álbum es una crítica social y política, y muestra la habilidad de la banda para fusionar diferentes estilos musicales.");
        dbArtistsHelper.insertTextWithName("Led Zeppelin", "Led Zeppelin, formada en 1968, es una de las bandas más icónicas del rock. Compuesta por Robert Plant, Jimmy Page, John Paul Jones y John Bonham, el grupo es conocido por su sonido pesado y su innovadora fusión de rock, blues y folk. A lo largo de su carrera, lanzaron álbumes que redefinieron el rock y sentaron las bases para el heavy metal. La banda se disolvió en 1980 tras la muerte de Bonham, pero su legado perdura, y su influencia se siente en innumerables bandas de rock y metal.", "Led Zeppelin IV", "Physical Graffiti", "Houses of the Holy", "Led Zeppelin II", "Con su icónica portada sin título, este álbum incluye algunos de los mayores éxitos de la banda, como \"Stairway to Heaven\" y \"Black Dog\". Es conocido por su diversidad musical y su producción innovadora, consolidando a Led Zeppelin como titanes del rock.", "Este álbum doble es una obra maestra que muestra la versatilidad y creatividad de la banda. Con canciones como \"Kashmir\" y \"Trampled Under Foot\", abarca una variedad de estilos y géneros, desde el rock pesado hasta el folk y el blues.", "Un álbum que explora nuevas direcciones musicales, con temas como \"The Song Remains the Same\" y \"No Quarter\". Es una mezcla de rock progresivo, funk y reggae, y muestra la habilidad de la banda para innovar y evolucionar.", "Este álbum consolidó el estatus de la banda con éxitos como \"Whole Lotta Love\" y \"Ramble On\". Es conocido por su sonido pesado y su producción innovadora, que sentó las bases para el hard rock y el heavy metal");
        dbArtistsHelper.insertTextWithName("Pink Floyd", "Pink Floyd, formada en Londres en 1965, es una de las bandas de rock progresivo más influyentes. Con miembros como Syd Barrett, Roger Waters, David Gilmour, Richard Wright y Nick Mason, la banda es conocida por sus álbumes conceptuales y sus elaborados espectáculos en vivo. Su música aborda temas filosóficos y políticos, y su sonido innovador ha dejado una marca indeleble en la historia del rock. Tras varias transformaciones y conflictos internos, Pink Floyd dejó un legado perdurable a través de su discografía y su impacto cultural.", "The Dark Side of the Moon", "Wish You Were Here", "The Wall", "Animals", "Uno de los álbumes más icónicos de la historia del rock, conocido por su producción impecable y su exploración de temas como la vida, la muerte y la locura. Con canciones como \"Time\" y \"Money\", es una obra maestra del rock progresivo.", "Este álbum rinde homenaje a Syd Barrett y explora temas de ausencia y alienación. Con canciones como \"Shine On You Crazy Diamond\" y \"Wish You Were Here\", es una obra profundamente emotiva y musicalmente innovadora.", "Un álbum conceptual y una ópera rock que narra la historia de Pink, un personaje que construye un muro metafórico a su alrededor. Con éxitos como \"Another Brick in the Wall\" y \"Comfortably Numb\", es una crítica a la sociedad y a la educación.", "Inspirado por \"Rebelión en la granja\" de George Orwell, este álbum es una crítica feroz a la sociedad contemporánea. Con largas piezas como \"Dogs\" y \"Pigs (Three Different Ones)\", muestra la habilidad de la banda para combinar narrativa y música compleja.");
        dbArtistsHelper.insertTextWithName("Nirvana", "Nirvana, formada en Aberdeen, Washington, en 1987, es una de las bandas más influyentes del grunge y del rock alternativo. Liderada por Kurt Cobain, con Krist Novoselic y Dave Grohl, la banda alcanzó la fama mundial con su segundo álbum, \"Nevermind\". Su música, caracterizada por su crudo sonido y letras introspectivas, resonó con una generación desencantada. La trágica muerte de Cobain en 1994 marcó el fin de la banda, pero su legado sigue vivo a través de su música y su influencia en el rock.", "Nevermind", "In Utero", "Bleach", "MTV Unplugged in New York", "Este álbum catapultó a Nirvana a la fama global con éxitos como \"Smells Like Teen Spirit\" y \"Come as You Are\". Es conocido por su sonido crudo y su energía explosiva, que definieron el grunge y capturaron el espíritu de una generación.", "Una respuesta deliberadamente más abrasiva a su éxito anterior, con temas como \"Heart-Shaped Box\" y \"All Apologies\". El álbum aborda temas de dolor y frustración, y es una muestra de la evolución artística y personal de Cobain.", "El álbum debut de Nirvana, grabado con un presupuesto limitado, muestra un sonido más pesado y sucio. Canciones como \"About a Girl\" y \"Negative Creep\" son ejemplos tempranos de su capacidad para combinar melodía y agresión.", "Una actuación acústica íntima que captura la vulnerabilidad y el talento de Cobain. Con versiones de sus propias canciones y covers de artistas como David Bowie, este álbum es una despedida conmovedora de una de las bandas más importantes del rock.");
        dbArtistsHelper.insertTextWithName("The Doors", "The Doors, formados en Los Ángeles en 1965, son conocidos por su sonido psicodélico y el carisma enigmático de su líder, Jim Morrison. Con Ray Manzarek, Robby Krieger y John Densmore, la banda creó música que mezclaba rock, blues y poesía. Sus actuaciones en vivo y su música oscura y provocativa dejaron una marca duradera en el rock. Tras la muerte de Morrison en 1971, la banda continuó brevemente antes de disolverse. The Doors siguen siendo influyentes, y su música continúa resonando con nuevas generaciones.", "The Doors", "Strange Days", "L.A. Woman", "Morrison Hotel", "El álbum debut que incluye clásicos como \"Light My Fire\" y \"Break On Through (To the Other Side)\". Es conocido por su innovador uso de teclados y las letras poéticas de Morrison, estableciendo a la banda como pioneros del rock psicodélico.", "Un álbum que expande el sonido y la visión de su debut, con canciones como \"People Are Strange\" y \"Love Me Two Times\". Es una mezcla de rock, psicodelia y letras introspectivas, consolidando su estatus como una de las bandas más innovadoras de la época.", "El último álbum con Jim Morrison, incluye éxitos como \"Riders on the Storm\" y \"Love Her Madly\". Es una mezcla de rock y blues, y refleja el crecimiento musical y la turbulencia personal de la banda y de Morrison en particular.", "Un regreso a un sonido más crudo y blues, con canciones como \"Roadhouse Blues\" y \"Peace Frog\". Es conocido por su energía y su autenticidad, mostrando la habilidad de la banda para reinventarse mientras mantienen su esencia.");

        dbArtistsHelper.insertTextWithName("Michael Jackson",
                "Michael Jackson, conocido como el 'Rey del Pop', fue un cantante, compositor y bailarín estadounidense que revolucionó la música popular con su talento excepcional y su innovadora presencia en el escenario. Desde su debut como niño prodigio en los Jackson 5 hasta su carrera en solitario, Jackson lanzó numerosos álbumes icónicos y rompió récords de ventas. Su influencia en la música, el baile y la moda es innegable, y a pesar de las controversias personales, su legado perdura. Jackson falleció en 2009, pero su impacto en la cultura pop sigue siendo monumental.",
                "Thriller", "Bad", "Off the Wall", "Dangerous",
                "'Thriller' (1982) es el álbum más vendido de todos los tiempos, con éxitos como 'Billie Jean', 'Beat It' y 'Thriller'. Este álbum redefinió la música pop y ganó un récord de ocho premios Grammy, solidificando la fama de Jackson.",
                "'Bad' (1987) muestra a Jackson en su apogeo creativo, con hits como 'Bad', 'The Way You Make Me Feel' y 'Man in the Mirror'. El álbum consolidó su estatus como superestrella global y tuvo una exitosa gira mundial.",
                "'Off the Wall' (1979) marcó el inicio de la carrera en solitario de Jackson, con un sonido fresco que mezclaba pop, funk y disco. Incluye éxitos como 'Don't Stop 'Til You Get Enough' y 'Rock with You', y es considerado un clásico.",
                "'Dangerous' (1991) es un álbum innovador que combina pop, rock y R&B, con éxitos como 'Black or White', 'Remember the Time' y 'Heal the World'. El álbum refleja el crecimiento artístico de Jackson y su compromiso con causas humanitarias.");

        dbArtistsHelper.insertTextWithName("Madonna",
                "Madonna, conocida como la 'Reina del Pop', es una cantante, compositora y actriz estadounidense que ha redefinido la música pop y la cultura popular con su imagen camaleónica y su capacidad para reinventarse. Desde sus inicios en la década de 1980, ha lanzado numerosos álbumes que han sido tanto comercialmente exitosos como críticamente aclamados. Madonna ha vendido más de 300 millones de discos en todo el mundo, convirtiéndose en la artista femenina más vendida de todos los tiempos. Su influencia se extiende más allá de la música, impactando la moda y la cultura en general.",
                "Like a Virgin", "True Blue", "Like a Prayer", "Ray of Light",
                "'Like a Virgin' (1984) catapultó a Madonna a la fama mundial con hits como 'Material Girl' y 'Like a Virgin'. Este álbum consolidó su imagen provocadora y su presencia en la música pop, y se convirtió en un icono cultural de los años 80.",
                "'True Blue' (1986) muestra una evolución en el sonido de Madonna, con éxitos como 'Papa Don't Preach', 'Live to Tell' y 'Open Your Heart'. El álbum fue un éxito comercial y crítico, reflejando su madurez como artista.",
                "'Like a Prayer' (1989) es conocido por su mezcla de temas religiosos y personales, con canciones como 'Like a Prayer' y 'Express Yourself'. El álbum es considerado uno de sus mejores trabajos y generó tanto aclamación como controversia.",
                "'Ray of Light' (1998) marcó un cambio hacia un sonido más electrónico y espiritual. Con éxitos como 'Frozen' y 'Ray of Light', el álbum recibió aclamación crítica, ganó varios premios Grammy y mostró una nueva faceta de Madonna.");

        dbArtistsHelper.insertTextWithName("Prince",
                "Prince fue un músico, cantante, compositor y productor estadounidense, conocido por su estilo ecléctico y su prodigioso talento musical. A lo largo de su carrera, fusionó rock, funk, pop y soul para crear un sonido único. Prince es famoso por su virtuosismo en la guitarra, su capacidad para tocar múltiples instrumentos y su energía en el escenario. Su música y su estilo innovador han influido profundamente en la cultura popular. Prince falleció en 2016, pero su legado perdura a través de su extensa discografía y su impacto en la música.",
                "Purple Rain", "Sign o' the Times", "1999", "Parade",
                "'Purple Rain' (1984) es una banda sonora revolucionaria que incluye éxitos como 'When Doves Cry' y 'Let's Go Crazy'. El álbum, junto con la película del mismo nombre, consolidó a Prince como un icono cultural y ganó un Oscar.",
                "'Sign o' the Times' (1987) es considerado una obra maestra que abarca una amplia gama de géneros y temas. Con canciones como 'Sign o' the Times' y 'If I Was Your Girlfriend', el álbum muestra la versatilidad y el genio de Prince.",
                "'1999' (1982) fue el primer gran éxito comercial de Prince, con hits como '1999' y 'Little Red Corvette'. El álbum fusiona funk y rock con letras apocalípticas y festivas, estableciendo a Prince como una fuerza dominante en la música.",
                "'Parade' (1986) es la banda sonora de la película 'Under the Cherry Moon' y presenta el éxito 'Kiss'. El álbum es una mezcla ecléctica de pop, funk y jazz, mostrando la habilidad de Prince para reinventar su sonido.");

        dbArtistsHelper.insertTextWithName("Whitney Houston",
                "Whitney Houston fue una cantante y actriz estadounidense conocida por su poderosa voz y su presencia escénica. Houston alcanzó la fama en la década de 1980 y se convirtió en una de las artistas más vendidas de todos los tiempos. Su interpretación de 'I Will Always Love You' es icónica, y su carrera incluyó numerosos éxitos número uno. Además de su éxito en la música, Houston también tuvo una carrera en el cine, protagonizando películas como 'The Bodyguard'. Aunque su vida estuvo marcada por luchas personales, su legado musical sigue siendo fuerte. Houston falleció en 2012.",
                "Whitney Houston", "Whitney", "I'm Your Baby Tonight", "My Love Is Your Love",
                "'Whitney Houston' (1985) es el álbum debut que incluye clásicos como 'Saving All My Love for You' y 'How Will I Know'. El álbum mostró su increíble rango vocal y la catapultó al estrellato mundial.",
                "'Whitney' (1987) consolidó su estatus como superestrella con éxitos como 'I Wanna Dance with Somebody' y 'Didn't We Almost Have It All'. El álbum fue un éxito masivo, mostrando su versatilidad y emotividad.",
                "'I'm Your Baby Tonight' (1990) presenta un sonido más urbano y contemporáneo, con éxitos como 'I'm Your Baby Tonight' y 'All the Man That I Need'. El álbum refleja su evolución artística y capacidad para adaptarse a nuevos estilos.",
                "'My Love Is Your Love' (1998) marcó un regreso triunfal con un sonido moderno y colaboraciones con artistas como Wyclef Jean. Canciones como 'It's Not Right but It's Okay' y 'My Love Is Your Love' revitalizaron su carrera.");

        dbArtistsHelper.insertTextWithName("Elton John",
                "Elton John es un cantante, compositor y pianista británico, conocido por su extravagante estilo y su impresionante talento musical. A lo largo de su carrera, ha vendido más de 300 millones de discos, convirtiéndose en uno de los artistas más vendidos de todos los tiempos. John es famoso por sus numerosos éxitos, como 'Rocket Man' y 'Your Song', y ha colaborado con el letrista Bernie Taupin durante más de 50 años. Además de su éxito musical, John es conocido por su trabajo filantrópico, especialmente en la lucha contra el SIDA. Su carrera ha sido reconocida con numerosos premios y honores.",
                "Goodbye Yellow Brick Road", "Captain Fantastic and the Brown Dirt Cowboy", "Madman Across the Water", "Honky Château",
                "'Goodbye Yellow Brick Road' (1973) es un álbum doble icónico que incluye éxitos como 'Bennie and the Jets' y 'Candle in the Wind'. Este álbum consolidó a John como una superestrella internacional y es considerado uno de sus mejores trabajos.",
                "'Captain Fantastic and the Brown Dirt Cowboy' (1975) es un álbum autobiográfico que detalla la historia de John y Taupin. Con canciones como 'Someone Saved My Life Tonight', el álbum fue un éxito crítico y comercial.",
                "'Madman Across the Water' (1971) incluye el clásico 'Tiny Dancer' y muestra la habilidad de John para combinar melodía y letras profundas. El álbum es conocido por su producción rica y emotiva.",
                "'Honky Château' (1972) fue un gran éxito con canciones como 'Rocket Man' y 'Honky Cat'. El álbum marcó el inicio de la era más prolífica de John, con una mezcla de rock, pop y baladas.");

        dbArtistsHelper.insertTextWithName("Celine Dion",
                "Celine Dion es una cantante canadiense conocida por su poderosa voz y su capacidad para emocionar a audiencias de todo el mundo. Dion alcanzó la fama internacional en la década de 1990 con su álbum 'Falling into You' y la canción 'My Heart Will Go On' de la película 'Titanic'. Ha vendido más de 200 millones de discos en todo el mundo y ha ganado numerosos premios, incluidos varios Grammy. Dion es conocida por sus impresionantes actuaciones en vivo y su habilidad para interpretar una amplia gama de géneros musicales. Su carrera sigue siendo fuerte, con residencias en Las Vegas y giras mundiales.",
                "Falling into You", "Let's Talk About Love", "The Colour of My Love", "A New Day Has Come",
                "'Falling into You' (1996) es uno de los álbumes más exitosos de Dion, con éxitos como 'Because You Loved Me' y 'It's All Coming Back to Me Now'. El álbum ganó varios premios Grammy y consolidó su estatus como superestrella internacional.",
                "'Let's Talk About Love' (1997) incluye el éxito mundial 'My Heart Will Go On', tema principal de 'Titanic'. El álbum es una mezcla de baladas emocionales y canciones pop, y es uno de los más vendidos de todos los tiempos.",
                "'The Colour of My Love' (1993) presenta éxitos como 'The Power of Love' y 'Think Twice'. Este álbum ayudó a Dion a establecerse como una fuerza dominante en la música pop y consolidó su fama mundial.",
                "'A New Day Has Come' (2002) marca el regreso de Dion después de un descanso, con éxitos como 'A New Day Has Come' y 'I'm Alive'. El álbum refleja su madurez artística y su capacidad para reinventarse.");

        dbArtistsHelper.insertTextWithName("Foo Fighters",
                "Foo Fighters es una banda de rock estadounidense formada en 1994 por Dave Grohl, ex baterista de Nirvana. Conocidos por su energía en vivo y su sonido contundente, la banda ha lanzado múltiples álbumes aclamados y ha ganado numerosos premios Grammy. A lo largo de los años, han evolucionado su sonido, incorporando diferentes elementos del rock. A pesar de los cambios en la formación, Grohl ha mantenido la visión y la dirección de la banda, consolidando su lugar en la historia del rock.",
                "The Colour and the Shape", "There Is Nothing Left to Lose", "Wasting Light", "Concrete and Gold",
                "'The Colour and the Shape' (1997) incluye clásicos como 'Everlong' y 'Monkey Wrench'. Este álbum consolidó a Foo Fighters como una fuerza en el rock alternativo, con un sonido pulido y poderoso.",
                "'There Is Nothing Left to Lose' (1999) ganó el Grammy al Mejor Álbum de Rock. Con canciones como 'Learn to Fly' y 'Breakout', el álbum presenta un sonido más melódico y accesible.",
                "'Wasting Light' (2011) fue grabado en el garaje de Dave Grohl y producido por Butch Vig. Con éxitos como 'Rope' y 'Walk', el álbum capturó la energía cruda de la banda y recibió elogios críticos.",
                "'Concrete and Gold' (2017) fusiona rock duro con elementos pop y psicodélicos. Canciones como 'Run' y 'The Sky Is a Neighborhood' destacan en este álbum ambicioso y experimental.");

        dbArtistsHelper.insertTextWithName("Coldplay",
                "Coldplay es una banda de rock británica formada en 1996. Conocidos por sus melodías emotivas y letras introspectivas, han vendido más de 100 millones de discos en todo el mundo. Liderados por Chris Martin, han experimentado con diferentes estilos musicales a lo largo de su carrera, desde el rock alternativo hasta el pop y la electrónica. Su éxito global incluye numerosos premios Grammy y Brit Awards. Coldplay también es conocido por su activismo y apoyo a diversas causas humanitarias.",
                "Parachutes", "A Rush of Blood to the Head", "Viva la Vida or Death and All His Friends", "Mylo Xyloto",
                "'Parachutes' (2000) es el álbum debut que incluye el exitoso sencillo 'Yellow'. Con su sonido melancólico y atmosférico, el álbum estableció a Coldplay como una banda prometedora en la escena del rock alternativo.",
                "'A Rush of Blood to the Head' (2002) ganó el Grammy al Mejor Álbum de Música Alternativa. Con éxitos como 'Clocks' y 'The Scientist', el álbum recibió elogios por su profundidad emocional y producción.",
                "'Viva la Vida or Death and All His Friends' (2008) marcó un cambio hacia un sonido más experimental y orquestal. El álbum incluye el hit 'Viva la Vida' y fue un éxito comercial y crítico.",
                "'Mylo Xyloto' (2011) es un álbum conceptual con influencias de pop y electrónica. Con canciones como 'Paradise' y 'Every Teardrop Is a Waterfall', el álbum muestra la capacidad de Coldplay para reinventarse.");

        dbArtistsHelper.insertTextWithName("Muse",
                "Muse es una banda de rock británica formada en 1994. Conocidos por su estilo musical que combina rock alternativo, electrónica y música clásica, han ganado numerosos premios y vendido millones de álbumes. La banda, liderada por Matthew Bellamy, es famosa por sus espectaculares shows en vivo y sus letras que abordan temas como la política, la ciencia ficción y la paranoia. A lo largo de los años, Muse ha mantenido una base de seguidores leales y ha influido a muchas bandas contemporáneas.",
                "Origin of Symmetry", "Absolution", "Black Holes and Revelations", "The Resistance",
                "'Origin of Symmetry' (2001) es un álbum innovador que incluye éxitos como 'Plug In Baby' y 'New Born'. Con su mezcla de rock alternativo y influencias clásicas, el álbum consolidó a Muse como una banda innovadora.",
                "'Absolution' (2003) aborda temas de apocalipsis y paranoia, con éxitos como 'Time Is Running Out' y 'Hysteria'. El álbum es aclamado por su intensidad y complejidad musical.",
                "'Black Holes and Revelations' (2006) fusiona rock con electrónica y política, con canciones como 'Supermassive Black Hole' y 'Knights of Cydonia'. El álbum muestra la capacidad de Muse para innovar y evolucionar.",
                "'The Resistance' (2009) es un álbum conceptual que explora la resistencia contra la opresión. Con éxitos como 'Uprising' y 'Resistance', el álbum ganó un Grammy al Mejor Álbum de Rock.");

        dbArtistsHelper.insertTextWithName("Arctic Monkeys",
                "Arctic Monkeys es una banda de rock británica formada en 2002. Conocidos por su estilo crudo y sus letras ingeniosas, alcanzaron la fama con su álbum debut 'Whatever People Say I Am, That's What I'm Not'. La banda, liderada por Alex Turner, ha evolucionado su sonido a lo largo de los años, experimentando con diferentes géneros. Han recibido numerosos premios y son una de las bandas más influyentes de su generación.",
                "Whatever People Say I Am, That's What I'm Not", "Favourite Worst Nightmare", "AM", "Tranquility Base Hotel & Casino",
                "'Whatever People Say I Am, That's What I'm Not' (2006) es el álbum debut más vendido en la historia del Reino Unido. Con canciones como 'I Bet You Look Good on the Dancefloor', el álbum capturó la energía y el ingenio de la banda.",
                "'Favourite Worst Nightmare' (2007) incluye éxitos como 'Brianstorm' y 'Fluorescent Adolescent'. El álbum muestra un sonido más maduro y consolidó la reputación de la banda como una fuerza en el rock británico.",
                "'AM' (2013) es un álbum aclamado que fusiona rock, R&B y hip hop, con éxitos como 'Do I Wanna Know?' y 'R U Mine?'. El álbum es conocido por su estilo oscuro y su producción pulida.",
                "'Tranquility Base Hotel & Casino' (2018) es un álbum conceptual que explora temas futuristas y retro, con un sonido más experimental y melódico. Canciones como 'Four Out of Five' destacan en este innovador trabajo.");

        dbArtistsHelper.insertTextWithName("Imagine Dragons",
                "Imagine Dragons es una banda de rock estadounidense formada en 2008. Conocidos por su sonido innovador que mezcla rock, pop y electrónica, han lanzado varios éxitos internacionales. Liderada por Dan Reynolds, la banda ganó prominencia con su sencillo 'It's Time' y alcanzó el éxito global con 'Radioactive'. Han vendido millones de discos y ganado numerosos premios, incluidos varios American Music Awards y un Grammy. Imagine Dragons sigue siendo una de las bandas más populares y exitosas de la última década.",
                "Night Visions", "Smoke + Mirrors", "Evolve", "Origins",
                "'Night Visions' (2012) es el álbum debut que incluye éxitos como 'Radioactive' y 'Demons'. Con su sonido innovador y pegajoso, el álbum catapultó a Imagine Dragons a la fama internacional.",
                "'Smoke + Mirrors' (2015) muestra un sonido más experimental y diverso, con éxitos como 'I Bet My Life' y 'Shots'. El álbum fue un éxito comercial, consolidando su lugar en la música rock contemporánea.",
                "'Evolve' (2017) incluye hits como 'Believer' y 'Thunder'. Con un sonido más pop y electrónico, el álbum fue aclamado por su energía y producción pulida.",
                "'Origins' (2018) presenta una mezcla de estilos y temas, con canciones como 'Natural' y 'Bad Liar'. El álbum muestra la capacidad de la banda para evolucionar y experimentar con nuevos sonidos.");

        dbArtistsHelper.insertTextWithName("The Black Keys",
                "The Black Keys es un dúo de rock estadounidense formado en 2001 por Dan Auerbach y Patrick Carney. Conocidos por su estilo de blues rock crudo y enérgico, han lanzado varios álbumes aclamados y han ganado numerosos premios Grammy. Su éxito comercial llegó con 'Brothers' y 'El Camino', que los establecieron como una de las bandas de rock más importantes de su generación. A lo largo de los años, han mantenido un sonido auténtico y una sólida base de seguidores.",
                "Brothers", "El Camino", "Turn Blue", "Let's Rock",
                "'Brothers' (2010) es un álbum que combina blues, rock y soul, con éxitos como 'Tighten Up' y 'Howlin' for You'. El álbum ganó tres premios Grammy y fue un gran éxito comercial.",
                "'El Camino' (2011) incluye hits como 'Lonely Boy' y 'Gold on the Ceiling'. Con su sonido enérgico y pegajoso, el álbum fue aclamado por la crítica y consolidó su éxito mundial.",
                "'Turn Blue' (2014) muestra un sonido más psicodélico y melódico, con canciones como 'Fever' y 'Gotta Get Away'. El álbum refleja la evolución de la banda y su capacidad para experimentar.",
                "'Let's Rock' (2019) es un regreso a sus raíces de rock crudo y directo, con éxitos como 'Lo/Hi' y 'Go'. El álbum fue bien recibido y demostró que la banda aún puede sorprender y deleitar a sus fans.");

        dbArtistsHelper.insertTextWithName("Louis Armstrong",
                "Louis Armstrong, conocido como 'Satchmo', fue un trompetista, cantante y uno de los músicos de jazz más influyentes del siglo XX. Nacido en Nueva Orleans en 1901, su estilo innovador y su carisma en el escenario lo convirtieron en una figura icónica. Armstrong dejó un legado duradero a través de sus grabaciones, actuaciones y su papel en la popularización del jazz en todo el mundo.",
                "What a Wonderful World", "Louis Armstrong Plays W.C. Handy", "Ella and Louis", "Satchmo at Symphony Hall",
                "'What a Wonderful World' (1968) es uno de los álbumes más conocidos de Armstrong, que incluye la icónica canción del mismo nombre. Este álbum captura el optimismo y el calor de la interpretación de Armstrong.",
                "'Louis Armstrong Plays W.C. Handy' (1954) es un tributo al 'Padre del Blues'. Con temas como 'St. Louis Blues', el álbum muestra la habilidad de Armstrong para interpretar y revitalizar clásicos del blues.",
                "'Ella and Louis' (1956) es una colaboración con Ella Fitzgerald que presenta una selección de estándares de jazz. La química entre Armstrong y Fitzgerald brilla en cada pista, haciendo de este álbum un clásico.",
                "'Satchmo at Symphony Hall' (1951) captura una de las legendarias actuaciones en vivo de Armstrong. Con interpretaciones enérgicas y carismáticas, este álbum muestra a Armstrong en su mejor momento en el escenario.");

        dbArtistsHelper.insertTextWithName("Duke Ellington",
                "Duke Ellington fue un compositor, pianista y líder de banda estadounidense, considerado uno de los más grandes músicos de jazz de todos los tiempos. Nacido en Washington D.C. en 1899, Ellington escribió miles de piezas durante su carrera y lideró su orquesta durante más de 50 años. Su trabajo innovador y su sofisticación musical dejaron una marca indeleble en el mundo del jazz.",
                "Ellington at Newport", "Money Jungle", "Such Sweet Thunder", "Far East Suite",
                "'Ellington at Newport' (1956) es un álbum en vivo que capturó una de las actuaciones más famosas de Ellington. Incluye una electrizante versión de 'Diminuendo and Crescendo in Blue' que revitalizó su carrera.",
                "'Money Jungle' (1963) es una colaboración con Charles Mingus y Max Roach. Este álbum de trío de jazz es conocido por su intensidad y la interacción dinámica entre los tres legendarios músicos.",
                "'Such Sweet Thunder' (1957) es un álbum conceptual inspirado en las obras de Shakespeare. Cada pista evoca un personaje o tema de las obras del dramaturgo, mostrando la habilidad de Ellington para la narración musical.",
                "'Far East Suite' (1967) es un álbum que refleja las influencias y experiencias de la gira de Ellington por el Medio Oriente y Asia. Con composiciones exóticas y ricas, es uno de sus trabajos más innovadores.");

        dbArtistsHelper.insertTextWithName("Miles Davis",
                "Miles Davis fue un trompetista, compositor y uno de los músicos más innovadores del siglo XX. Nacido en Illinois en 1926, Davis fue una figura central en el desarrollo de varios estilos de jazz, incluyendo el bebop, el cool jazz y el jazz fusion. Su capacidad para reinventarse y su influencia en generaciones de músicos lo consolidan como una leyenda del jazz.",
                "Kind of Blue", "Bitches Brew", "Birth of the Cool", "Sketches of Spain",
                "'Kind of Blue' (1959) es uno de los álbumes de jazz más vendidos y aclamados de todos los tiempos. Con una alineación estelar, el álbum es conocido por su atmósfera relajada y sus innovadoras improvisaciones modales.",
                "'Bitches Brew' (1970) marcó el comienzo del jazz fusion, combinando jazz, rock y funk. Este álbum doble desafió las convenciones y abrió nuevas direcciones para el jazz moderno.",
                "'Birth of the Cool' (1957) es una recopilación de grabaciones de finales de los años 40 que introdujeron el cool jazz. Con un enfoque más relajado y arreglos complejos, el álbum es fundamental en la historia del jazz.",
                "'Sketches of Spain' (1960) es una colaboración con Gil Evans que fusiona jazz y música española. Con temas como 'Concierto de Aranjuez', el álbum es una obra maestra de la interpretación y el arreglo.");

        dbArtistsHelper.insertTextWithName("John Coltrane",
                "John Coltrane fue un saxofonista y compositor de jazz, conocido por su virtuosismo y su espíritu innovador. Nacido en Carolina del Norte en 1926, Coltrane fue una figura clave en el desarrollo del jazz modal y el free jazz. Su búsqueda espiritual y su enfoque exploratorio en la música lo convirtieron en una figura icónica del jazz.",
                "A Love Supreme", "Blue Train", "Giant Steps", "My Favorite Things",
                "'A Love Supreme' (1965) es una suite de cuatro partes que representa la búsqueda espiritual de Coltrane. Este álbum es considerado una de las mayores obras maestras del jazz, con una profunda resonancia emocional y musical.",
                "'Blue Train' (1957) es el único álbum que Coltrane grabó como líder para Blue Note Records. Con su mezcla de hard bop y blues, el álbum es un clásico y una piedra angular en su discografía.",
                "'Giant Steps' (1960) muestra la habilidad técnica y la innovación armónica de Coltrane. Con composiciones complejas y solos virtuosos, el álbum es una referencia esencial en el estudio del jazz.",
                "'My Favorite Things' (1961) presenta la famosa reinterpretación de la canción de Rodgers y Hammerstein. Este álbum popularizó el uso del modo en el jazz y mostró la capacidad de Coltrane para reinventar estándares.");

        dbArtistsHelper.insertTextWithName("Charlie Parker",
                "Charlie Parker, también conocido como 'Bird', fue un saxofonista y compositor de jazz revolucionario. Nacido en Kansas City en 1920, Parker fue uno de los pioneros del bebop, un estilo de jazz caracterizado por tempos rápidos y complejidad armónica. Su virtuosismo y su enfoque innovador cambiaron para siempre el curso del jazz.",
                "Bird and Diz", "Charlie Parker with Strings", "The Complete Savoy and Dial Sessions", "Now's the Time",
                "'Bird and Diz' (1952) es una colaboración con el trompetista Dizzy Gillespie, un compañero pionero del bebop. Este álbum es un testimonio de la energía y la creatividad de dos de los más grandes músicos de jazz.",
                "'Charlie Parker with Strings' (1950) presenta a Parker acompañado de una sección de cuerdas. Este álbum combina el virtuosismo de Parker con arreglos orquestales, creando un sonido único y accesible.",
                "'The Complete Savoy and Dial Sessions' (1978) es una recopilación esencial de las grabaciones de Parker para los sellos Savoy y Dial. Estas sesiones capturan algunos de los momentos más innovadores y cruciales de su carrera.",
                "'Now's the Time' (1952) es una colección de grabaciones que muestra la maestría de Parker en el blues. Con su interpretación apasionada y su técnica impecable, este álbum es un clásico del jazz.");

        dbArtistsHelper.insertTextWithName("Ella Fitzgerald",
                "Ella Fitzgerald, conocida como la 'Primera Dama de la Canción', fue una de las vocalistas más queridas y respetadas del jazz. Nacida en Virginia en 1917, Fitzgerald ganó 13 premios Grammy y vendió más de 40 millones de discos. Su versatilidad, su rango vocal y su habilidad para el scat la hicieron famosa en todo el mundo. Su carrera abarcó más de seis décadas, durante las cuales dejó una huella indeleble en la música.",
                "Ella Fitzgerald Sings the Cole Porter Songbook", "Ella Fitzgerald Sings the Duke Ellington Songbook", "Ella and Louis", "Ella in Berlin: Mack the Knife",
                "'Ella Fitzgerald Sings the Cole Porter Songbook' (1956) es el primer álbum de la serie Songbook, que se convirtió en un estándar para el jazz vocal. Con interpretaciones elegantes y emotivas, el álbum destaca la versatilidad de Fitzgerald.",
                "'Ella Fitzgerald Sings the Duke Ellington Songbook' (1957) es una colaboración con el propio Ellington. Este álbum doble es una celebración del trabajo de Ellington y muestra la química entre los dos artistas.",
                "'Ella and Louis' (1956) es una colaboración con Louis Armstrong. Este álbum es conocido por la armonía perfecta entre las voces de Fitzgerald y Armstrong, creando un clásico del jazz vocal.",
                "'Ella in Berlin: Mack the Knife' (1960) captura una actuación en vivo en Berlín. Con su famosa interpretación de 'Mack the Knife', donde olvidó las letras pero improvisó magistralmente, el álbum es un testimonio de su genialidad.");

        dbArtistsHelper.insertTextWithName("Lana Del Rey",
                "Lana Del Rey, nacida Elizabeth Woolridge Grant en 1985, es una cantante y compositora estadounidense conocida por su estilo musical cinematográfico y melancólico. Desde su debut en 2011 con 'Born to Die', ha explorado temas de amor, glamour y decadencia en sus álbumes. Su voz etérea y su estética vintage la han convertido en una figura icónica del pop alternativo.",
                "Born to Die", "Ultraviolence", "Honeymoon", "Norman Fucking Rockwell!",
                "'Born to Die' (2012) es el álbum debut de Lana Del Rey que la catapultó a la fama. Con éxitos como 'Video Games' y 'Summertime Sadness', el álbum mezcla pop barroco y trip-hop.",
                "'Ultraviolence' (2014) muestra un sonido más oscuro y crudo, producido por Dan Auerbach de The Black Keys. Con temas como 'West Coast' y 'Brooklyn Baby', es una exploración profunda del dolor y el romance.",
                "'Honeymoon' (2015) es un regreso al estilo cinematográfico de sus primeros trabajos. Con canciones como 'High by the Beach' y 'Freak', el álbum es una mezcla de jazz, pop barroco y dream pop.",
                "'Norman Fucking Rockwell!' (2019) es aclamado por la crítica como su mejor trabajo hasta la fecha. Producido por Jack Antonoff, incluye temas introspectivos y melancólicos como 'Mariners Apartment Complex' y 'Venice Bitch'.");

        dbArtistsHelper.insertTextWithName("Ed Sheeran",
                "Ed Sheeran, nacido en 1991, es un cantautor británico conocido por sus baladas acústicas y su capacidad para escribir éxitos pop. Su carrera despegó con su álbum debut '+' en 2011. Con su guitarra y su estilo lírico confesional, Sheeran ha vendido millones de discos y se ha convertido en una de las estrellas más grandes del pop contemporáneo.",
                "+ (Plus)", "x (Multiply)", "÷ (Divide)", "No.6 Collaborations Project",
                "'+ (Plus)' (2011) es el álbum debut de Sheeran, que incluye éxitos como 'The A Team' y 'Lego House'. El álbum combina folk, pop y hip hop, y estableció a Sheeran como un talento emergente.",
                "'x (Multiply)' (2014) consolidó su éxito con temas como 'Sing' y 'Thinking Out Loud'. Producido por Pharrell Williams y Rick Rubin, el álbum muestra una mayor variedad de estilos y experimentación.",
                "'÷ (Divide)' (2017) es uno de sus álbumes más exitosos, con éxitos globales como 'Shape of You' y 'Castle on the Hill'. El álbum mezcla pop, folk y R&B, mostrando la versatilidad de Sheeran.",
                "'No.6 Collaborations Project' (2019) es un álbum de colaboraciones con artistas como Justin Bieber, Cardi B y Eminem. Este proyecto diverso y experimental destaca la habilidad de Sheeran para adaptarse a diferentes géneros.");

        dbArtistsHelper.insertTextWithName("Ariana Grande",
                "Ariana Grande, nacida en 1993, es una cantante, compositora y actriz estadounidense. Comenzó su carrera en Broadway y en la televisión antes de convertirse en una estrella del pop. Con una poderosa voz y una serie de éxitos, Grande ha sido elogiada por su habilidad vocal y su capacidad para mezclar R&B, pop y elementos trap en sus canciones.",
                "Yours Truly", "My Everything", "Sweetener", "Thank U, Next",
                "'Yours Truly' (2013) es el álbum debut de Grande, con un sonido influenciado por el R&B de los 90. Incluye éxitos como 'The Way' y 'Baby I', y muestra su impresionante rango vocal.",
                "'My Everything' (2014) consolidó su estatus de estrella pop con éxitos como 'Problem' y 'Break Free'. El álbum mezcla pop, EDM y R&B, y cuenta con colaboraciones con Iggy Azalea y Zedd.",
                "'Sweetener' (2018) muestra una evolución artística, con la producción de Pharrell Williams. Con temas como 'No Tears Left to Cry' y 'God is a Woman', el álbum explora sonidos más experimentales y personales.",
                "'Thank U, Next' (2019) es un álbum introspectivo que aborda temas de amor y pérdida. Incluye éxitos como '7 rings' y 'thank u, next', y es considerado uno de sus trabajos más personales y maduros.");

        dbArtistsHelper.insertTextWithName("Billie Eilish",
                "Billie Eilish, nacida en 2001, es una cantante y compositora estadounidense conocida por su estilo musical oscuro y minimalista. Saltó a la fama con su sencillo 'Ocean Eyes' en 2015. Eilish ha sido aclamada por su voz única, letras introspectivas y su capacidad para innovar dentro del pop contemporáneo, a menudo trabajando con su hermano Finneas.",
                "When We All Fall Asleep, Where Do We Go?", "Happier Than Ever", "Don't Smile at Me", "Hit Me Hard and Soft",
                "'When We All Fall Asleep, Where Do We Go?' (2019) es el álbum debut de Eilish, conocido por su estilo oscuro y letras introspectivas. Con éxitos como 'bad guy' y 'bury a friend', redefinió el pop moderno.",
                "'Happier Than Ever' (2021) es un álbum introspectivo y emocional, explorando temas de fama, amor y autoaceptación. Con canciones como 'Your Power' y 'NDA', muestra un sonido más maduro y complejo.",
                "'Don't Smile at Me' (2017) es un EP que incluye éxitos como 'Ocean Eyes' y 'Bellyache'. Este trabajo presentó a Eilish como una nueva voz en el pop, con su estilo distintivo y letras profundas.",
                "Un viaje inolvidable a las emociones de Billie Eilish. Un álbum exquisitamente producido, con un sonido elegante, vibrante por momentos y misterioso en otros, siempre acompañado de arreglos vocales estupendos. Los temas más tranquilos evocan romanticismo, tristeza y aceptación, emocionan mucho. Los más experimentales presentan cambios de ritmo, incluso de estilo, y lo hacen con una facilidad enorme. Hay una producción muy cuidada que permite cada salto dentro de cada canción, impresionando estar escuchando otra canción dentro de la misma.");

        dbArtistsHelper.insertTextWithName("Justin Bieber",
                "Justin Bieber, nacido en 1994, es un cantante y compositor canadiense que saltó a la fama a una edad temprana con su sencillo 'Baby'. Con una carrera que abarca más de una década, Bieber ha evolucionado de un ídolo adolescente a un artista pop maduro. Ha vendido millones de discos y ganado numerosos premios, consolidando su lugar en la música pop.",
                "My World 2.0", "Believe", "Purpose", "Justice",
                "'My World 2.0' (2010) es el álbum debut de Bieber, que incluye éxitos como 'Baby' y 'Somebody to Love'. Este álbum lo estableció como una estrella del pop adolescente.",
                "'Believe' (2012) muestra una madurez en su sonido, con influencias de pop y R&B. Con éxitos como 'Boyfriend' y 'As Long As You Love Me', el álbum marcó su transición a un público más amplio.",
                "'Purpose' (2015) es considerado uno de sus mejores trabajos, con éxitos como 'Sorry' y 'Love Yourself'. El álbum aborda temas de redención y crecimiento personal, con un sonido fresco y contemporáneo.",
                "'Justice' (2021) es un álbum que combina pop, R&B y elementos electrónicos. Con éxitos como 'Peaches' y 'Anyone', Bieber explora temas de justicia social y amor, mostrando su evolución como artista.");

        dbArtistsHelper.insertTextWithName("Dua Lipa",
                "Dua Lipa, nacida en 1995, es una cantante y compositora británica que ha ganado fama internacional con su música pop y dance. Con su álbum debut en 2017, Lipa ha sido aclamada por su voz potente y su habilidad para crear éxitos pegajosos. Ha ganado numerosos premios, incluyendo Grammys, y se ha establecido como una de las estrellas más brillantes del pop contemporáneo.",
                "Dua Lipa", "Future Nostalgia", "Club Future Nostalgia", "The Moonlight Edition",
                "'Dua Lipa' (2017) es el álbum debut de Lipa, que incluye éxitos como 'New Rules' y 'IDGAF'. Con una mezcla de pop y R&B, el álbum estableció su presencia en la industria musical.",
                "'Future Nostalgia' (2020) es un álbum que combina sonidos retro con pop moderno. Con éxitos como 'Don't Start Now' y 'Physical', el álbum ha sido aclamado por su producción y estilo contagioso.",
                "'Club Future Nostalgia' (2020) es un álbum de remixes que reinventa 'Future Nostalgia' con la colaboración de DJ The Blessed Madonna. Con versiones frescas de sus canciones, es una celebración de la música dance.",
                "'The Moonlight Edition' (2021) es una re-edición de 'Future Nostalgia' con nuevas canciones. Incluye éxitos como 'We're Good' y muestra la versatilidad y creatividad continua de Lipa.");

        dbArtistsHelper.insertTextWithName(
                "Kendrick Lamar",
                "Kendrick Lamar es un rapero, compositor y productor estadounidense, considerado uno de los artistas más influyentes del hip-hop contemporáneo. Con letras profundas y una narrativa única, ha ganado múltiples premios Grammy y ha dejado una marca indeleble en la música.",
                "good kid, m.A.A.d city",
                "To Pimp a Butterfly",
                "DAMN.",
                "Mr. Morale & the Big Steppers",
                "Este disco consigue a Lamar narrando su propia juventud en Compton, la violencia de su entorno, la brutalidad de la policía y la presión por participar en las pandillas que lo rodeaban.\n El resultado es una foto bastante honesta de la juventud afroamericana en el barrio donde creció Kendrick. Hay clásicos absolutos aquí desde Swimming Pools (Drank) o la canción mAAd City. Es uno de los trabajos más honestos sobre la cultura afroamericana, un espejo en el que Lamar ve sus propios defectos y los de su comunidad y los enfrenta, pero en el que no deja de pensar en el futuro con esperanza.",
                "Un trabajo en el que la música negra desde el jazz hasta el hip hop se encuentran en un solo sitio y en el que Kendrick habla abiertamente de la diáspora africana, el racismo en Estados Unidos, las marcas históricas de la esclavitud y su propia batalla contra la ansiedad y la depresión. Puede ser la carga política de King Kunta o Blacker the berry o las piezas instrospectivas como U o I, pero lo cierto es que el disco puede saltar entre estos temas sin dejar de ser accesible. ",
                "Damn es probablemente el disco más oscuro de la carrera de Kendrick. Es la primera vez que el rapero se siente como el GOAT después del éxito critico y comercial de sus últimos dos discos. Desde el primer sencillo, la explosiva Humble, era evidente que estábamos ante un artista en la cúspide de su poder presumiendo  el mismo.\n" + "A pesar de todo, no deja de lado sus temas sociales; aquí ya muestra una parte evidente del imaginario de Kendrick, y pasa en canciones como Duckworth o DNA, y al mismo tiempo se deja seducir por sonidos más pop en Element y Loyalty en las que colabora con Rihanna.",
                "Líricamente, habla de los años cuando el rapero estuvo ausente del pop, pero además se abre a dramas interpersonales en canciones como We Cry Together o a hablar de la aceptación de los individuos LGBTQ en piezas como Auntie Diaries. Es un Lamar que ha madurado bastante, que se despide de su discográfica Top Dawg Entertaiment con un trabajo, que sin ser una revolución musical, se le considera uno de sus mejores trabajos líricos."
        );

        dbArtistsHelper.insertTextWithName(
                "Drake",
                "Drake, nacido Aubrey Drake Graham, es un rapero, cantante y actor canadiense que ha dominado las listas de éxitos globales con su estilo melódico y líricas introspectivas. Ha sido una fuerza dominante en la música desde su debut y ha influido en una generación de artistas.",
                "What A Time To Be Alive",
                "Take Care",
                "Nothing Was the Same",
                "If You're Reading This It´s Too Late",
                "Este proyecto es una colaboración entre Drake y Future y es uno de los álbumes más influyentes del último lustro. El álbum cuenta con producciones de Metro Boomin, Southside, entre otros, y se caracteriza por su sonido innovador y vanguardista. What A Time To Be Alive es un proyecto lleno de bangers y una experiencia auditiva única.",
                "Take Care es el segundo álbum de estudio de Drake y es considerado por muchos como el proyecto que lo catapultó al éxito. El álbum cuenta con colaboraciones de Rihanna, Lil Wayne y The Weeknd, y cuenta con una producción excepcional a cargo de 40, T-Minus, Boi-1da y Just Blaze. Take Care es un álbum emotivo y honesto que muestra la vulnerabilidad de Drake y su habilidad para crear canciones de éxito.",
                "El tercer álbum de estudio de Drake es considerado por muchos como su obra maestra. El álbum cuenta con producciones de 40, Boi-1da, Hudson Mohawke, entre otros, y colaboraciones de Jay-Z y Majid Jordan. El sonido de Nothing Was The Same es muy coherente y maduro, lo que lo convierte en una experiencia auditiva única y emocionante.",
                "El cuarto proyecto de Drake fue lanzado sin previo aviso en 2015 y se convirtió en uno de sus trabajos más importantes. Este álbum presenta un sonido más agresivo y oscuro en comparación con los proyectos anteriores de Drake, gracias a las producciones de Boi-1da, Vinylz, y 40. If You’re Reading This It’s Too Late es un álbum lleno de bangers que han sido muy influyentes en la escena del hip hop."
        );

        dbArtistsHelper.insertTextWithName(
                "Eminem",
                "Eminem, cuyo nombre real es Marshall Bruce Mathers III, es un rapero, compositor y productor estadounidense conocido por su estilo lírico rápido y provocativo. Con una carrera que abarca más de dos décadas, es considerado uno de los mejores raperos de todos los tiempos.",
                "The Slim Shady LP",
                "The Marshall Mathers LP",
                "The Eminem Show",
                "The Marshall Mathers LP2",
                "Hablar de ‘The Slim Shady LP’ es hablar de una de las grandes obras maestras del género. En este brillante álbum, Eminem se disfraza de Slim Shady, su álter-ego, un tipo ofensivo, grandilocuente y tosco, que nos termina dejando algunos de los mejores momentos de toda la carrera del de Detroit y del rap en general.",
                "La gran obra maestra de Eminem. La joya de la corona de uno de los más grandes creadores de las últimas décadas. Uno de los discos que más gente ha introducido el mundo del rap. Una colección de letras apabullantes, de ritmos sublimes confeccionados en su mayoría por un Dr. Dre en uno de sus mejores momentos.",
                "‘The Eminem Show’ nos muestra la faceta más introspectiva de Marshall Bruce Mathers, algo que siempre es una buena noticia. En este disco, Eminem desgrana con precisión maestra todas y cada una de sus inquietudes, revelando una minuciosa fotografía de la persona que se encuentra tras el artista.",
                "Suele decirse que «las segundas partes nunca fueron buenas». Pues bien, aunque ‘The Marshall Mathers LP 2’ no alcance las cotas de grandeza de su predecesor, nos encontramos con un discazo tremendo, que más de uno quisiera haber escrito. Un clásico dentro de la discografía de Eminem. Un álbum al alcance de muy pocos."
        );

        dbArtistsHelper.insertTextWithName("Lil Wayne",
                "Lil Wayne es un rapero, cantante y productor discográfico estadounidense. Nacido en Nueva Orleans, Louisiana, Lil Wayne es considerado uno de los artistas más influyentes en la historia del hip hop.",
                "Tha Carter II: Screwed and Chopped",
                "Tha Carter III",
                "Tha Carter IV",
                "Tha Carter V",
                "Aunque Tha Carter III se considera como el hijo pródigo de la familia Carter, no se puede negar el impacto que Tha Carter II tuvo en el curso del hip-hop. Después de los polémicos temas de camisetas blancas y canciones pegajosas que caracterizaron a Tha Carter, Wayne sintió que era hora de tomar una dirección diferente para la secuela de su clásico regional. Gracias a la mezcla gratuita presentada por DJ Drama llamada The Dedication, las masas comenzaron a darse cuenta de las habilidades líricas de Wayne. La mezcla estableció el tono para lo que se convertiría en el intento de Wayne de expandirse más allá del sur y establecer su dominio en el trono.",
                "Tha Carter III es el sexto álbum de estudio de Lil Wayne, lanzado en 2008. Es ampliamente reconocido como uno de los mejores álbumes de rap de la década, con éxitos como 'Lollipop' y 'A Milli'.",
                "Tha Carter IV es el noveno álbum de estudio de Lil Wayne, lanzado en 2011. Presenta colaboraciones con artistas como Drake y Nicki Minaj, y fue un éxito comercial y crítico.",
                "Tha Carter V es el duodécimo álbum de estudio de Lil Wayne, lanzado en 2018 después de varios retrasos. Fue bien recibido por la crítica y los fanáticos, mostrando la habilidad lírica de Lil Wayne y su versatilidad como artista."

        );

        dbArtistsHelper.insertTextWithName("Kanye West",
                "Kanye West es un rapero, productor musical y diseñador de moda estadounidense. Conocido por su innovación musical y su influencia en la cultura pop, West ha sido una figura destacada en la industria de la música durante más de una década.",
                "The College Dropout",
                "My Beautiful Dark Twisted Fantasy",
                "Yeezus",
                "Graduation",
                "Kanye West, que llevaba varios años ejerciendo de productor para Roc-A-Fella, discográfica de Jay-Z o Memphis Bleek entre otros durante los 2000s, decidió jugársela y dedicarse también a rapear.\n. No sólo quería que lo conociesen como un productor, ahora quería ser respetado como MC. Por eso, durante más de 20 temas decidió rapear como el mejor, alejándose de la estética y contenido violento y callejero que abundaba en la época y optando por un estilo divertido, desenfadado e irónico, sin dejar atrás el mensaje.",
                "Ye Viajó a Hawái y se instaló allí durante meses con la intención de crear su nuevo disco. Allí, se juntó con los mejores raperos, productores y ejecutivos para crear un disco legendario\n .El resultado es una de sus obras maestras, un álbum que lo reúne todo: una producción excelente, unos grandes versos, unos estribillos reconocibles y pegadizos, unas colaboraciones estelares y una musicalidad excepcional, mezclando hip-hop, R&B y pop de una forma y tino que pocas veces en la historia se han visto.",
                "Kanye quiso lanzarse a la piscina en un movimiento que nadie se esperaba.\n ‘Yeezus’ es el álbum más rompedor y experimental de su carrera. Con él, dio un giro de 180º con respecto a su sonido, dejando atrás el soul, el pop o el R&B y arriesgándose con música electrónica, sintetizadores, sonidos robóticos y repetitivos y un estilo muy agresivo.",
                "Gracias a ‘Graduation’ pudimos ver por primera vez un cambio en la trayectoria de Kanye West. Con él pasaba definitivamente de rapero a superestrella mundial. Su tercer álbum de estudio le permitió experimentar con sonidos más mainstream y rodearse de colaboradores externos al hip-hop para reinventar su sonido y captar aún más público."
        );

        dbArtistsHelper.insertTextWithName("Jay-Z",
                "Jay-Z es un rapero, empresario y filántropo estadounidense. Con una carrera que abarca varias décadas, Jay-Z es ampliamente considerado uno de los mejores raperos de todos los tiempos y uno de los empresarios más exitosos en la industria del entretenimiento.",
                "The Blueprint (Explicit Version)",
                "The Black Album",
                "4:44",
                "Reasonbable Doubt",
                "The Blueprint’ trazó un plan de juego ganador para otros MCs que definitivamente inspiró a muchos de los raperos que brillan en estos días. Just Blaze y Kanye West regalaron a Jay paisajes sonoros definitorios que acompañaron sus canciones más memorables: ‘Takeover’, ‘Izzo (H.O.V.A.)’, ‘U Don’t Know’ y ‘Heart of the City (Ain’t No Love)’ proporcionan toda la prueba del mundo de esa colaboración indiscutible",
                "Uno de los mejores álbumes de la historia del hip-hop apenas se ubica en el top 3 del trabajo de Jay, por esa razón, el neoyorquino debería considerarse uno de los mejores que hay. En The Black Album, Jay marcó un hito que, incluso hasta el día de hoy, pocos han igualado. Con algunos de los nombres más grandes del juego, incluidos Timbaland, los Neptunes, Eminem y Kanye, solo hay una falla en todo el álbum y cuanto menos se diga sobre ‘Justify My Thug’, mejor",
                "4:44 es el decimotercer álbum de estudio de Jay-Z, lanzado en 2017. Es notable por su honestidad y vulnerabilidad, abordando temas personales como el arrepentimiento, la familia y la riqueza.",
                "Reasonable Doubt presentó al mundo a Jay-Z, el traficante que dejaba atrás la vida, pero no antes de deleitarnos con historias de los altibajos de la vida en la calle con una mezcla suave de humor y arrepentimiento”, escribe Hip Hop Golden Age. “Era arrogante pero aún estaba fundamentado, su caracterización nunca se convertía en caricatura, su flujo conversacional hacía sentir como si estuviera hablando con cada uno de nosotros individualmente"
        );

        dbArtistsHelper.insertTextWithName("Daft Punk",
                "Daft Punk fue un dúo francés de música electrónica formado por Thomas Bangalter y Guy-Manuel de Homem-Christo. Conocidos por sus innovadoras producciones y su estética robótica, Daft Punk se ha convertido en un ícono de la música electrónica.",
                "Homework",
                "Discovery",
                "Human After All",
                "Random Access Memories",
                "Homework es el álbum debut de Daft Punk. Con éxitos como 'Da Funk' y 'Around the World', el álbum es una obra maestra del house francés. Este álbum sentó las bases para la música electrónica moderna y mostró la capacidad del dúo para fusionar diferentes géneros en un sonido único.",
                "Discovery es considerado uno de los mejores álbumes de Daft Punk. Con un sonido más melódico y accesible, incluye clásicos como 'One More Time' y 'Harder, Better, Faster, Stronger'. Este álbum marcó un cambio en su estilo, adoptando un enfoque más pop y retro, y sigue siendo un hito en su carrera.",
                "Human After All presenta un sonido más crudo y minimalista. Aunque inicialmente recibió críticas mixtas, ha ganado reconocimiento por su influencia en el electro y el rock electrónico. Con temas como 'Robot Rock' y 'Technologic', el álbum explora temas de tecnología y humanidad.",
                "Random Access Memories es una celebración de la música disco y el funk. Ganador de varios premios Grammy, incluye el éxito mundial 'Get Lucky' con Pharrell Williams. Este álbum destaca por sus colaboraciones con artistas de renombre y su homenaje a la música de los años 70 y 80."
        );

        dbArtistsHelper.insertTextWithName("The Chemical Brothers",
                "The Chemical Brothers es un dúo británico de música electrónica compuesto por Tom Rowlands y Ed Simons. Pioneros del big beat, han dejado una huella indeleble en la música electrónica con su sonido innovador y sus espectaculares shows en vivo.",
                "Exit Planet Dust",
                "Dig Your Own Hole",
                "Surrender",
                "Push the Button",
                "Exit Planet Dust es el álbum debut de The Chemical Brothers. Con una mezcla de breakbeat y house, el álbum incluye éxitos como 'Leave Home' y 'Chemical Beats'. Este álbum estableció su firma sonora y les permitió destacarse en la escena electrónica emergente de los años 90.",
                "Dig Your Own Hole consolidó la reputación del dúo en la escena electrónica. Con éxitos como 'Block Rockin' Beats' y 'Setting Sun', el álbum es un clásico del big beat. Su sonido innovador y energía intensa lo convierten en una referencia obligada para los aficionados del género.",
                "Surrender muestra una evolución en el sonido del dúo, incorporando más elementos de pop y rock. Incluye hits como 'Hey Boy Hey Girl' y 'Let Forever Be'. Este álbum refleja su capacidad para adaptarse y evolucionar, manteniéndose relevantes en un paisaje musical en constante cambio.",
                "Push the Button es conocido por su diversidad estilística y colaboraciones. Con temas como 'Galvanize' y 'Believe', el álbum ganó el Grammy al Mejor Álbum de Música Electrónica/Dance. Este trabajo demuestra su habilidad para fusionar diferentes géneros y mantener su sonido fresco e innovador."
        );

        dbArtistsHelper.insertTextWithName("Deadmau5",
                "Deadmau5, cuyo nombre real es Joel Zimmerman, es un productor y DJ canadiense de música electrónica. Conocido por su casco de ratón y sus innovadoras producciones, Deadmau5 ha sido una figura clave en la escena electrónica desde mediados de los 2000.",
                "Random Album Title",
                "For Lack of a Better Name",
                "4×4=12",
                ">Album Title Goes Here<",
                "Random Album Title es uno de los álbumes más influyentes de Deadmau5. Con éxitos como 'Faxing Berlin' y 'I Remember', el álbum ayudó a definir el sonido del progressive house moderno. La mezcla de melodías suaves y ritmos contundentes hizo que este álbum se destacara en la escena EDM.",
                "For Lack of a Better Name incluye algunos de los mayores éxitos de Deadmau5, como 'Ghosts 'n' Stuff' y 'Strobe'. Este álbum muestra su habilidad para crear paisajes sonoros atmosféricos y contundentes, consolidando su lugar como uno de los productores más innovadores de su generación.",
                "4×4=12 es conocido por su diversidad de estilos, desde el electro hasta el techno. Con temas como 'Sofi Needs a Ladder' y 'Raise Your Weapon', el álbum recibió críticas positivas y consolidó su estatus en la escena EDM. Cada pista ofrece una experiencia auditiva única, mostrando su versatilidad.",
                ">Album Title Goes Here< presenta colaboraciones con artistas como Gerard Way y Cypress Hill. El álbum explora una amplia gama de géneros y muestra la versatilidad de Deadmau5 como productor. Temas como 'Professional Griefers' destacan su habilidad para fusionar estilos diversos en una obra coherente."
        );

        dbArtistsHelper.insertTextWithName("Skrillex",
                "Skrillex, cuyo nombre real es Sonny John Moore, es un productor y DJ estadounidense. Pionero del dubstep, Skrillex ha revolucionado la música electrónica con su estilo agresivo y su innovadora producción.",
                "Scary Monsters and Nice Sprites",
                "Bangarang",
                "Recess",
                "More Monsters and Sprites",
                "Scary Monsters and Nice Sprites es el EP que catapultó a Skrillex a la fama. Con su característico sonido dubstep, incluye éxitos como 'Scary Monsters and Nice Sprites' y 'Kill Everybody'. Este EP redefinió el dubstep y llevó la música electrónica a nuevas audiencias en todo el mundo.",
                "Bangarang es otro EP que consolidó el estatus de Skrillex en la escena electrónica. Con colaboraciones y éxitos como 'Bangarang' y 'Kyoto', el EP ganó dos premios Grammy. Este trabajo muestra su capacidad para mezclar géneros y crear música enérgica y emotiva que resuena con los fans.",
                "Recess es el primer álbum de estudio de Skrillex. Experimentando con diversos géneros, incluye temas como 'All Is Fair in Love and Brostep' y 'Ragga Bomb'. Este álbum destaca por su innovación y la habilidad de Skrillex para empujar los límites de la música electrónica.",
                "More Monsters and Sprites es un EP que incluye remixes y nuevas versiones de temas del EP anterior. Destacan canciones como 'First of the Year (Equinox)' y 'Ruffneck (Full Flex)'. Este EP solidificó su reputación como uno de los artistas más creativos y dinámicos de su tiempo."
        );

        dbArtistsHelper.insertTextWithName("Calvin Harris",
                "Calvin Harris es un DJ, productor y cantante escocés. Conocido por sus éxitos en la música dance y electrónica, Harris ha colaborado con numerosos artistas de renombre y ha dominado las listas de éxitos mundiales.",
                "I Created Disco",
                "Ready for the Weekend",
                "18 Months",
                "Motion",
                "I Created Disco es el álbum debut de Calvin Harris. Con un sonido electro-funk, incluye éxitos como 'Acceptable in the 80s' y 'The Girls'. Este álbum marcó su entrada en la escena musical y estableció su estilo distintivo que combina ritmos pegajosos con letras ingeniosas.",
                "Ready for the Weekend consolidó la popularidad de Harris. Con temas como 'I'm Not Alone' y 'Ready for the Weekend', el álbum muestra su habilidad para crear éxitos de pista de baile. La mezcla de elementos electrónicos y pop hizo que este álbum fuera un favorito entre los fans.",
                "18 Months es uno de los álbumes más exitosos de Harris, con colaboraciones de artistas como Rihanna y Florence Welch. Incluye éxitos como 'We Found Love' y 'Feel So Close'. Este álbum dominó las listas de éxitos y mostró la capacidad de Harris para crear música que resuena globalmente.",
                "Motion presenta una serie de colaboraciones con artistas como Ellie Goulding y Big Sean. Con temas como 'Summer' y 'Blame', el álbum continuó el éxito de Harris en la música dance. Cada pista del álbum muestra su habilidad para producir canciones que son a la vez innovadoras y comercialmente exitosas."
        );

        dbArtistsHelper.insertTextWithName("Avicii",
                "Avicii, cuyo nombre real era Tim Bergling, fue un DJ y productor sueco. Reconocido por sus innovaciones en la música electrónica, Avicii dejó un legado duradero con sus melodías contagiosas y su estilo único.",
                "True",
                "Stories",
                "Tim",
                "The Days / Nights EP",
                "True es el álbum debut de Avicii. Con una mezcla de EDM y música folk, incluye éxitos como 'Wake Me Up' y 'Hey Brother'. Este álbum redefinió el sonido de la música electrónica, fusionando géneros y mostrando la habilidad de Avicii para crear música accesible y emocional.",
                "Stories muestra la evolución de Avicii como productor. Con colaboraciones de artistas como Chris Martin y Zac Brown, el álbum incluye temas como 'Waiting for Love' y 'For a Better Day'. Este álbum destaca por su diversidad estilística y su capacidad para conectar con una amplia audiencia.",
                "Tim, lanzado póstumamente en 2019, es un testamento al talento y la creatividad de Avicii. Con éxitos como 'SOS' y 'Heaven', el álbum muestra su habilidad para crear música emotiva y memorable. Cada pista del álbum refleja su pasión por la música y su legado duradero en la escena electrónica.",
                "The Days / Nights EP incluye dos de los mayores éxitos de Avicii, 'The Days' y 'The Nights'. El EP es una muestra de su capacidad para crear himnos de pista de baile que resuenan a nivel mundial. Estos temas se han convertido en favoritos eternos de los fans y en clásicos de la música electrónica."
        );

        dbArtistsHelper.insertTextWithName("Black Sabbath",
                "Black Sabbath es una banda británica de heavy metal formada en 1968. Considerados pioneros del heavy metal, su sonido oscuro y pesado ha influido a generaciones de bandas. Liderados por Ozzy Osbourne y Tony Iommi, han dejado un legado imborrable en la música rock.",
                "Black Sabbath",
                "Paranoid",
                "Master of Reality",
                "Heaven and Hell",
                "Black Sabbath es el álbum debut homónimo de la banda. Con su sonido oscuro y pesado, este álbum es considerado uno de los primeros del género heavy metal. Incluye clásicos como 'Black Sabbath' y 'N.I.B.', y estableció a la banda como pionera del metal.",
                "Paranoid es el segundo álbum de Black Sabbath y uno de los más influyentes en la historia del heavy metal. Con temas icónicos como 'Paranoid', 'Iron Man' y 'War Pigs', el álbum consolidó su reputación y sigue siendo un referente esencial del género.",
                "Master of Reality profundiza en el sonido pesado y oscuro de la banda. Con canciones como 'Sweet Leaf' y 'Children of the Grave', este álbum es conocido por sus afinaciones bajas y sus letras introspectivas. Es una pieza fundamental en la discografía de Black Sabbath.",
                "Heaven and Hell marca una nueva era para Black Sabbath con la incorporación de Ronnie James Dio como vocalista. Con temas como 'Heaven and Hell' y 'Neon Knights', el álbum revitalizó a la banda y demostró su capacidad para evolucionar y mantenerse relevantes en la escena del metal."
        );

        dbArtistsHelper.insertTextWithName("Metallica",
                "Metallica es una banda estadounidense de heavy metal formada en 1981. Conocidos por su velocidad y complejidad técnica, han sido una de las bandas más influyentes y exitosas en la historia del metal. Sus álbumes han vendido millones de copias en todo el mundo.",
                "Kill 'Em All",
                "Ride the Lightning",
                "Master of Puppets",
                "Metallica",
                "Kill 'Em All es el álbum debut de Metallica. Este álbum sentó las bases del thrash metal con su velocidad y agresividad. Incluye clásicos como 'Seek & Destroy' y 'Whiplash'. La crudeza y energía del álbum lo han convertido en un hito del género.",
                "Ride the Lightning mostró una evolución en el sonido de Metallica. Con canciones como 'Fade to Black' y 'For Whom the Bell Tolls', el álbum combina complejidad técnica con letras más maduras. Este álbum consolidó a Metallica como una fuerza innovadora en el metal.",
                "Master of Puppets es ampliamente considerado uno de los mejores álbumes de metal de todos los tiempos. Con temas como 'Master of Puppets' y 'Battery', el álbum es una obra maestra de la composición y la ejecución técnica. Es un pilar en la discografía de Metallica.",
                "Metallica (The Black Album) es el álbum más exitoso de la banda. Con éxitos como 'Enter Sandman' y 'Nothing Else Matters', el álbum marcó un cambio hacia un sonido más accesible y comercial. Su producción pulida y su amplio atractivo lo han convertido en un clásico del rock."
        );

        dbArtistsHelper.insertTextWithName("Iron Maiden",
                "Iron Maiden es una banda británica de heavy metal formada en 1975. Conocidos por sus épicos conciertos y su icónico logotipo, han sido una de las bandas más influyentes del género. Su distintivo estilo melódico y letras temáticas han capturado la imaginación de millones de fans.",
                "Iron Maiden",
                "The Number of the Beast",
                "Powerslave",
                "Seventh Son of a Seventh Son",
                "Iron Maiden es el álbum debut homónimo de la banda. Con su sonido crudo y energético, incluye clásicos como 'Running Free' y 'Phantom of the Opera'. Este álbum estableció a Iron Maiden como una nueva fuerza en el heavy metal británico.",
                "The Number of the Beast es uno de los álbumes más emblemáticos de Iron Maiden. Con temas como 'Run to the Hills' y 'Hallowed Be Thy Name', el álbum marcó el debut de Bruce Dickinson como vocalista y consolidó la reputación de la banda en la escena del metal.",
                "Powerslave es conocido por su temática egipcia y su épica composición. Incluye clásicos como 'Aces High' y '2 Minutes to Midnight'. El álbum es una muestra del virtuosismo de la banda y su capacidad para combinar melodías memorables con letras profundas.",
                "Seventh Son of a Seventh Son es un álbum conceptual que explora temas de misticismo y profecía. Con canciones como 'The Evil That Men Do' y 'Can I Play with Madness', el álbum muestra la habilidad de Iron Maiden para contar historias complejas a través de su música."
        );

        dbArtistsHelper.insertTextWithName("Judas Priest",
                "Judas Priest es una banda británica de heavy metal formada en 1969. Con su distintivo estilo y poderosa presencia en el escenario, han sido una de las bandas más influyentes del metal. Conocidos por su look de cuero y tachuelas, han definido la estética del género.",
                "British Steel",
                "Screaming for Vengeance",
                "Defenders of the Faith",
                "Painkiller",
                "British Steel es uno de los álbumes más icónicos de Judas Priest. Con éxitos como 'Breaking the Law' y 'Living After Midnight', el álbum es conocido por su sonido accesible y pegadizo. Este trabajo ayudó a llevar el heavy metal a una audiencia más amplia.",
                "Screaming for Vengeance es un álbum que consolidó la reputación de Judas Priest en la escena del metal. Con temas como 'You've Got Another Thing Comin'' y 'Electric Eye', el álbum combina potencia y melodía de manera magistral, convirtiéndose en un clásico del género.",
                "Defenders of the Faith continúa la racha de éxitos de la banda. Incluye canciones como 'Freewheel Burning' y 'Some Heads Are Gonna Roll'. Este álbum muestra la habilidad de Judas Priest para mantener su energía y creatividad a lo largo de los años.",
                "Painkiller es uno de los álbumes más aclamados de Judas Priest. Con su sonido agresivo y rápido, incluye éxitos como 'Painkiller' y 'A Touch of Evil'. Este álbum es un testamento a la longevidad y relevancia de la banda en la escena del heavy metal."
        );

        dbArtistsHelper.insertTextWithName("Megadeth",
                "Megadeth es una banda estadounidense de thrash metal formada en 1983 por Dave Mustaine. Conocidos por sus complejas estructuras musicales y letras políticas, han sido una de las bandas más influyentes del género. Su música ha dejado una marca indeleble en la historia del metal.",
                "Peace Sells... but Who's Buying?",
                "Rust in Peace",
                "Countdown to Extinction",
                "Youthanasia",
                "Peace Sells... but Who's Buying? es uno de los álbumes más influyentes de Megadeth. Con temas como 'Peace Sells' y 'Wake Up Dead', el álbum combina velocidad y complejidad técnica con letras socialmente conscientes. Este trabajo es un pilar del thrash metal.",
                "Rust in Peace es considerado una obra maestra del thrash metal. Con canciones como 'Holy Wars... The Punishment Due' y 'Hangar 18', el álbum muestra la virtuosidad de la banda y su habilidad para crear composiciones complejas y memorables. Es un clásico esencial del género.",
                "Countdown to Extinction es uno de los álbumes más exitosos de Megadeth. Con éxitos como 'Symphony of Destruction' y 'Sweating Bullets', el álbum combina agresividad con melodía, logrando un amplio atractivo. Este trabajo consolidó su estatus en la cima del thrash metal.",
                "Youthanasia muestra una evolución en el sonido de Megadeth. Con temas como 'A Tout Le Monde' y 'Train of Consequences', el álbum presenta un enfoque más melódico sin perder su intensidad característica. Es una prueba de la versatilidad y la longevidad de la banda."
        );

        dbArtistsHelper.insertTextWithName("Guns N' Roses",
                "Guns N' Roses es una banda estadounidense de hard rock formada en Los Ángeles, California, en 1985. Con Axl Rose como vocalista principal y Slash en la guitarra, la banda alcanzó gran éxito en los años 80 y 90 con su estilo único y enérgico. Conocidos por su actitud rebelde y sus actuaciones electrizantes, se han convertido en una de las bandas más icónicas del rock.",
                "Appetite for Destruction",
                "Use Your Illusion I",
                "Use Your Illusion II",
                "G N' R Lies",
                "Appetite for Destruction es el álbum debut de Guns N' Roses y es considerado uno de los mejores álbumes de rock de todos los tiempos. Con éxitos como 'Welcome to the Jungle', 'Sweet Child o' Mine' y 'Paradise City', el álbum se caracteriza por su energía cruda y su sonido innovador, estableciendo a la banda como una fuerza dominante en el rock.",
                "Use Your Illusion I muestra una evolución en el sonido de Guns N' Roses, combinando hard rock con baladas épicas. Canciones como 'November Rain' y 'Don't Cry' destacan en este álbum, mostrando la versatilidad y el talento compositivo de la banda. El disco fue un éxito comercial y crítico, solidificando su estatus en la música.",
                "Use Your Illusion II, lanzado simultáneamente con Use Your Illusion I, contiene éxitos como 'Civil War' y 'You Could Be Mine'. Este álbum continúa explorando temas más complejos y sonidos diversos, desde hard rock hasta blues. Juntos, ambos álbumes representan un punto culminante en la carrera de Guns N' Roses.",
                "G N' R Lies es un álbum que mezcla grabaciones en vivo con material de estudio. Con canciones como 'Patience' y 'Used to Love Her', el álbum muestra una faceta más acústica y lírica de la banda. Aunque no tan exitoso como otros trabajos, G N' R Lies ofrece una perspectiva única del talento y la creatividad de Guns N' Roses."
        );

        dbArtistsHelper.insertTextWithName(
                "Bee Gees",
                "Bee Gees fueron un grupo musical británico-australiano de música disco y pop formado por los hermanos Barry, Robin y Maurice Gibb. Ganaron fama internacional en los años 70 y 80 con su distintivo estilo vocal y canciones icónicas. Son conocidos por su contribución a la música disco y su influencia perdura hasta hoy.",
                "Saturday Night Fever (The Original Movie SoundTrack)",
                "Main Course",
                "Spirits Having Flown",
                "Children of the World",
                "Este álbum banda sonora de la película homónima es uno de los discos más icónicos de la era disco, con éxitos como 'Stayin' Alive' y 'Night Fever'.",
                "Marcó el cambio de los Bee Gees al sonido disco con éxitos como 'Jive Talkin'' y 'Nights on Broadway', siendo clave para su éxito posterior.",
                "Contiene éxitos como 'Too Much Heaven', 'Tragedy' y 'Love You Inside Out', consolidando su estatus en la música disco.",
                "Incluye el éxito mundial 'You Should Be Dancing', destacando su estilo rítmico y energético que definió la era disco."
        );

        dbArtistsHelper.insertTextWithName(
                "Donna Summer",
                "Donna Summer fue una cantante estadounidense conocida como la 'Reina del Disco' durante los años 70. Su poderosa voz y su talento para la música dance la convirtieron en una figura central de la era disco, con numerosos éxitos que aún son populares.",
                "Bad Girls",
                "Love to Love You Baby",
                "On the Radio: Greatest Hits Volumes I & II",
                "I Remember Yesterday",
                "Este álbum doble incluye éxitos como 'Hot Stuff' y 'Bad Girls', siendo uno de los discos más representativos de la era disco.",
                "Con su título seductor, este álbum y su canción homónima establecieron a Donna Summer como una fuerza en la música disco.",
                "Greatest Hits Volumes I & II (1979): Compilación de sus mayores éxitos, mostrando la evolución y el impacto de su carrera en la música disco.",
                "Incluye el éxito 'I Feel Love', una innovadora pista que combinó la música disco con la electrónica, influenciando el futuro de la música dance."
        );

        dbArtistsHelper.insertTextWithName(
                "Chic",
                "Chic es una banda estadounidense de música disco y funk, formada por Nile Rodgers y Bernard Edwards en los años 70. Conocidos por su sofisticado estilo y su habilidad para crear grooves pegajosos, Chic tuvo un gran impacto en la música dance y pop.",
                "C'est Chic",
                "Risque",
                "Chic (2018 Remaster)",
                "Take It Off",
                "Incluye el éxito mundial 'Le Freak', uno de los sencillos más vendidos de todos los tiempos, y 'I Want Your Love'.",
                "Con éxitos como 'Good Times', este álbum tuvo un gran impacto en la música disco y el hip hop, influyendo en muchos artistas.",
                "El álbum debut incluye 'Dance, Dance, Dance (Yowsah, Yowsah, Yowsah)' y 'Everybody Dance', estableciendo su sonido característico.",
                "Aunque no tan exitoso como sus anteriores, sigue mostrando la habilidad del grupo para crear música dance sofisticada."
        );

        dbArtistsHelper.insertTextWithName(
                "Gloria Gaynor",
                "Gloria Gaynor es una cantante estadounidense, conocida principalmente por su éxito disco de los años 70, 'I Will Survive'. Su poderosa voz y su presencia escénica la han convertido en un ícono de la música disco.",
                "Love Tracks",
                "Never Can Say Goodbye",
                "I Have a Right",
                "Experience Gloria Gaynor",
                "Contiene 'I Will Survive', un himno de empoderamiento y uno de los mayores éxitos de la era disco.",
                "Incluye el primer éxito disco del mismo nombre, y otros como 'Reach Out, I'll Be There'.",
                "Este álbum continuó el éxito de Gaynor con canciones que muestran su poderosa voz y estilo disco.",
                "Con éxitos como 'Casanova Brown', solidificó su lugar en la escena disco."
        );

        dbArtistsHelper.insertTextWithName(
                "KC and the Sunshine Band",
                "KC and the Sunshine Band es una banda estadounidense de música disco y funk, formada en 1973. Conocidos por sus ritmos pegajosos y su energía en el escenario, tuvieron varios éxitos durante la era disco que aún se escuchan hoy.",
                "KC and the Sunshine Band",
                "Part 3",
                "Do You Wanna Go Party",
                "Who Do Ya (Love)",
                "Incluye éxitos como 'That's the Way (I Like It)' y 'Get Down Tonight', que definieron el sonido disco.",
                "Con éxitos como '(Shake, Shake, Shake) Shake Your Booty' y 'I'm Your Boogie Man', continuaron su dominio en las listas de éxitos.",
                "El sencillo principal alcanzó un gran éxito, mostrando su capacidad para seguir creando música disco pegajosa.",
                "Aunque no tan exitoso como los anteriores, sigue siendo un buen ejemplo de su estilo funky y bailable."
        );

        dbArtistsHelper.insertTextWithName(
                "Earth, Wind & Fire",
                "Earth, Wind & Fire es una banda estadounidense de música funk, soul y disco, formada en 1969. Conocidos por su estilo innovador, fusión de géneros y espectáculos en vivo, han dejado una marca indeleble en la música popular.",
                "That's the Way of the World",
                "All 'n All",
                "I Am",
                "Spirit",
                "Considerado uno de sus mejores álbumes, incluye 'Shining Star' y la canción principal, mostrando su mezcla de funk, soul y jazz.",
                "Con éxitos como 'Serpentine Fire' y 'Fantasy', es un álbum clave en su carrera y la música funk.",
                "Incluye 'Boogie Wonderland' y 'After the Love Has Gone', fusionando disco y soul de manera magistral.",
                "Con canciones como 'Getaway' y 'Saturday Nite', sigue siendo un testimonio de su creatividad y talento musical."
        );

        dbArtistsHelper.insertTextWithName(
                "Yung Lean",
                "Yung Lean, nombre real Jonatan Leandoer Håstad, es un rapero y cantante sueco conocido por su estilo único de rap cloud y sus letras surrealistas. Surgió en la escena musical en 2013 y ha ganado reconocimiento internacional por su música innovadora y su impacto en la cultura del rap.",
                "Unknown Memory",
                "Stranger",
                "Warlord",
                "Starz",
                "Este álbum marcó el ascenso de Yung Lean en la escena del rap, con canciones como 'Yoshi City' y 'Kyoto', que fusionan elementos de rap, trap y música electrónica.",
                "Considerado su álbum más maduro, presenta una variedad de estilos musicales y líricos, explorando temas como la fama y la soledad.",
                "Con un sonido más oscuro y agresivo, este álbum muestra la evolución de Yung Lean como artista, con colaboraciones destacadas y una producción innovadora.",
                "Ofrece una mirada introspectiva a la mente de Yung Lean, con canciones emotivas y experimentales que muestran su crecimiento artístico."
        );

        dbArtistsHelper.insertTextWithName(
                "Bladee",
                "Bladee, nombre real Benjamin Reichwald, es un rapero y productor sueco asociado con el colectivo musical Drain Gang. Conocido por su estilo único de rap emo y sus letras introspectivas, ha ganado seguidores leales en la escena del rap underground.",
                "Eversince",
                "Red Light",
                "Exeter",
                "333",
                "Este álbum muestra la habilidad de Bladee para fusionar el rap con la música electrónica y el pop, con canciones melódicas y letras emotivas.",
                "Con una producción más experimental, este álbum explora temas como el amor, la pérdida y la ansiedad, con un enfoque único en la estética y el sonido.",
                "Ofrece un sonido más minimalista y atmosférico, con letras que reflexionan sobre la vida y la mortalidad, mostrando la evolución artística de Bladee.",
                "Este álbum presenta una combinación de estilos musicales, desde el rap hasta el pop y la música electrónica, con letras que exploran la espiritualidad y la autoaceptación."
        );

        dbArtistsHelper.insertTextWithName(
                "Outkast",
                "Outkast fue un dúo de hip hop estadounidense formado por André 3000 y Big Boi. Conocidos por su innovación musical y letras ingeniosas, han sido aclamados como una de las mejores parejas de rap de todos los tiempos.",
                "Southernplayalisticadillacmuzik",
                "ATLiens",
                "Aquemini",
                "Stankonia",
                "Su álbum debut estableció a Outkast como líderes del rap sureño, con ritmos pegajosos y letras que exploran la vida en Atlanta.",
                "Considerado uno de los mejores álbumes de rap de la década de 1990, presenta un sonido más futurista y líricas introspectivas sobre la fama y el éxito.",
                "Este álbum muestra la versatilidad de Outkast, con una fusión única de rap, soul y funk, y letras que abordan temas sociales y personales.",
                "Ganador del premio Grammy al Mejor Álbum de Rap, es un viaje musical diverso que abarca desde el rap hardcore hasta el pop experimental, con canciones como 'Ms. Jackson' y 'B.O.B'."
        );

        dbArtistsHelper.insertTextWithName(
                "MF DOOM",
                "MF DOOM, nombre real Daniel Dumile, fue un rapero británico conocido por su enigmática personalidad y su estilo lírico único. Surgió en la escena del rap underground en la década de 1990 y ha sido aclamado por la crítica y los fanáticos por su habilidad para contar historias y su habilidad en el micrófono.",
                "Operation: Doomsday",
                "Mm.. Food",
                "Madvillainy",
                "Born Like This",
                "Su álbum debut en solitario, estableció a MF DOOM como un innovador del rap alternativo, con un estilo de producción lo-fi y letras abstractas.",
                "Con un concepto único basado en la comida, este álbum muestra la genialidad lírica de MF DOOM y su habilidad para tejer historias a través de sus rimas.",
                "Colaboración con el productor Madlib, este álbum es un clásico instantáneo, con ritmos eclécticos y líricas ingeniosas que desafían las convenciones del rap.",
                "Con un enfoque más oscuro y crudo, este álbum presenta a MF DOOM en su estado más puro, con ritmos contundentes y letras que exploran temas como la fama y la mortalidad."
        );

        dbArtistsHelper.insertTextWithName(
                "A Tribe Called Quest",
                "A Tribe Called Quest fue un influyente grupo de hip hop estadounidense formado en Queens, Nueva York, en 1985. Conocidos por su innovación musical y sus letras conscientes, han sido aclamados como una de las figuras más importantes en la historia del hip hop.",
                "People's Instinctive Travels and the Paths of Rhythm",
                "The Low End Theory",
                "Midnight Marauders",
                "We Got It from Here... Thank You 4 Your Service",
                "Su álbum debut estableció a A Tribe Called Quest como pioneros del jazz rap, con ritmos fluidos y líricas introspectivas.",
                "Considerado un clásico del hip hop, este álbum es una obra maestra de la producción y la lírica, con canciones como 'Scenario' y 'Check the Rhime'.",
                "Con un sonido más maduro y sofisticado, este álbum muestra la evolución de A Tribe Called Quest como artistas, con colaboraciones destacadas y ritmos contagiosos.",
                "Su último álbum de estudio es un regreso triunfal, con canciones que abordan temas sociales y políticos con energía y urgencia."
        );

        dbArtistsHelper.insertTextWithName(
                "Kid Cudi",
                "Kid Cudi, nombre real Scott Ramon Seguro Mescudi, es un rapero, cantante y actor estadounidense conocido por su estilo único y su enfoque introspectivo de la música. Ha ganado reconocimiento por su honestidad y vulnerabilidad en sus letras, así como por su influencia en la música hip hop y alternativa.",
                "Man on the Moon: The End of Day",
                "Man on the Moon II: The Legend of Mr. Rager",
                "Indicud",
                "Man on the Moon III: The Chosen",
                "Su álbum debut es una obra conceptual que explora la lucha interna de Kid Cudi con la fama y la depresión, con canciones como 'Day 'N' Nite' y 'Pursuit of Happiness'.",
                "Continuación de su primer álbum, este proyecto profundiza en los temas de la soledad y la adicción, con una producción atmosférica y letras emotivas.",
                "Con un enfoque más experimental, este álbum muestra la versatilidad de Kid Cudi como artista, con una variedad de géneros musicales y colaboraciones destacadas.",
                "Regreso a la saga Man on the Moon, este álbum es una culminación de la carrera de Kid Cudi, con canciones que reflejan su viaje personal y su evolución artística."
        );

        dbArtistsHelper.insertTextWithName(
                "Vico C",
                "Vico C, nombre real Luis Armando Lozada Cruz, es un rapero y pionero del rap en español. Conocido como 'La Voz del Pueblo', ha sido una figura influyente en la música latina, abordando temas sociales y políticos en sus letras.",
                "Éxitos",
                "La Recta Final",
                "Con Poder",
                "Desahogo",
                "Recopilación de sus mejores temas, este álbum es un viaje por la carrera de Vico C, con canciones icónicas que reflejan su impacto en la escena del rap latino.",
                "Con un enfoque en la introspección y la superación personal, este álbum muestra la madurez artística de Vico C, con letras emotivas y una producción sólida.",
                "Considerado uno de sus álbumes más influyentes, presenta un sonido fresco y letras que abordan temas como la desigualdad y la injusticia social.",
                "Con colaboraciones destacadas y un enfoque en la autenticidad y la sinceridad, este álbum muestra a Vico C en su forma más cruda y honesta."
        );

        dbArtistsHelper.insertTextWithName(
                "Control Machete",
                "Control Machete fue un grupo de hip hop mexicano formado por Toy Hernández, Pato Machete y Fermin IV. Conocidos por su fusión de rap, cumbia y música electrónica, han sido una influencia importante en la escena del rap latino.",
                "Mucho Barato",
                "Artillería Pesada, Presenta",
                "Uno, Dos: Bandera",
                "Singles",
                "Su álbum debut es una explosión de energía y ritmo, con letras que reflejan la realidad de la vida en México y una fusión única de estilos musicales.",
                "Con colaboraciones de artistas destacados, este álbum muestra la versatilidad de Control Machete y su capacidad para mezclar diferentes géneros musicales.",
                "Bandera: Considerado uno de los mejores álbumes de rap en español, presenta ritmos pegajosos y líricas inteligentes que exploran temas como la identidad y la cultura mexicana.",
                "Recopilación de singles que no llegaron a entrar en ningún disco."
        );

        dbArtistsHelper.insertTextWithName("El General",
                "El General, cuyo nombre real es Edgardo Franco, es un cantante panameño conocido como uno de los pioneros del reguetón y el reggae en español. Nacido el 27 de septiembre de 1964, se hizo famoso en la década de 1990 con su estilo único que mezclaba reggae, dancehall y ritmos latinos. Su música ha influenciado a numerosas generaciones y artistas del género.",
                "Estas Buena",
                "Originales",
                "Es Mundial",
                "Grandes Exitos",
                "'Estas Buena' es uno de los primeros álbumes de El General, lanzado en 1991. Este disco incluye una mezcla de ritmos reggae y letras pegajosas que ayudaron a establecer su carrera y a popularizar el reggae en español. Es considerado un clásico del género y muestra el talento temprano de El General para crear hits.",
                "Originales, lanzado en 1991, contiene algunos de los mayores éxitos de El General y consolidó su fama internacional. Con una producción energética y ritmos bailables, este álbum muestra su habilidad para crear canciones que se convirtieron en himnos de fiestas en toda América Latina.",
                "'Es Mundial', lanzado en 1995, refleja el alcance global de la música de El General. Este álbum mezcla ritmos tropicales con letras pegajosas que celebran la vida y la fiesta. Es una muestra de su versatilidad y talento para crear hits internacionales, fortaleciendo su influencia en la música latina.",
                "'Grandes Exitos' es una recopilación lanzada en 1998 que incluye los mayores éxitos de El General a lo largo de su carrera. Este álbum ofrece una visión completa de su impacto en el género y su legado duradero en la música latina, con canciones que capturan la esencia de su estilo y su contribución al reggae en español."
        );


        dbArtistsHelper.insertTextWithName(
                "Violadores del Verso",
                "Violadores del Verso fue un grupo de rap español formado por Kase.O, Lírico, Sho-Hai y R de Rumba. Conocidos por su habilidad lírica y su energía en el escenario, han sido una influencia importante en la escena del rap en español.",
                "Vicios y Virtudes",
                "Vivir para Contarlo",
                "Genios",
                "El Círculo",
                "Historias de la calle y la vida urbana se entrelazan en este álbum, con rimas afiladas y una producción innovadora que estableció a Violadores del Verso como referentes del rap español.",
                "Con un sonido más maduro y una producción innovadora, este álbum muestra la evolución de Violadores del Verso como grupo, con letras que reflexionan sobre la vida y la muerte.",
                "Con discos como «Genios» uno puede respirar tranquilo. La historia del hip hop nacional ya debe pasar, obligatoriamente, por el portentoso debut largo de Violadores Del Verso. Abrumador, epatante, incendiario y extremadamente poderoso, «Genios» supone uno de los álbumes más impactantes que nos ha dado este país. Un inmenso trabajo que se desenvuelve a un altísimo ritmo. Funden jazz, funk y soul con beats arrolladores, pianos siniestros y bajos de acero, y de esa mixtura surge un sonido personal, propio y ya influyente.",
                "“El Círculo” se aleja del funk y el hardcore para adentrarse en una música mucho más orgánica e instrumental, donde reinan los tempos lentos y los estribillos melódicos. El sonido de Kase-O, su rap, ya no es el de antes y al principio puede costar digerirlo, pero estas instrumentales tan experimentales e inusuales han servido para comprobar que sigue manteniendo su inigualable dominio del micro. Canciones como \"Mazas y Catapultas\", un auténtico experimento musical con el que pone a prueba su versatilidad y su voz , demuestran que su dinamismo, su estilo, sus excelentes letras y su flow se mantienen intactos e incluso suben un escalón en “El Círculo”, devolviendo a Javier Ibarra el trono que nadie ha conseguido arrebatarle aún en el hiphop."
        );

        dbArtistsHelper.insertTextWithName(
                "7 Notas 7 Colores",
                "7 Notas 7 Colores fue un grupo de rap español formado por Mucho Muchacho y Dive Dibosso. Conocidos por su estilo único y sus letras crudas y directas, han sido una influencia importante en la escena del rap en español.",
                "Hecho, es Simple",
                "La Mami Internacional",
                "77",
                "Chulería (Mucho Muchacho)",
                "Tanto Mucho Mu como Dive ponen toda la carne en el asador. El primero tiene rimas «como rinocerontes» para dar y tomar. El cuaderno de notas de Mucho Muchacho parece no acabarse nunca. Si éste hubiese sido el único disco de 7 Notas 7 Colores, el MC no hubiese dicho todo lo que tenía que decir, pero casi. Por otro lado, Dive Dibosso hace una labor ejemplar como productor: bases minimalistas, precisas, perfectas. «En el fuego» es una muestra de la simbiosis de la que son capaces uno y otro. Dive ilustra el vivaracho flow de Mucho («Empezaste a mamar mi ombligo y luego, así así, sigue miel«) con un bajo y un órgano Hammond juguetón que se contestan el uno al otro.",
                "Su álbum debut estableció a 7 Notas 7 Colores como una fuerza en el rap español, con letras directas y un sonido innovador que capturó la atención del público.",
                "Con colaboraciones de artistas destacados, este álbum llevó el rap español a nuevas alturas, con ritmos contagiosos y líricas que celebran la cultura urbana.",
                "Considerado uno de los mejores álbumes de rap en español, presenta ritmos pegajosos y líricas inteligentes que exploran temas como la identidad y la lucha."
        );

        dbArtistsHelper.insertTextWithName(
                "Mala Rodríguez",
                "La Mala Rodríguez, nombre real María Rodríguez Garrido, es una rapera española conocida por su estilo único y su voz distintiva. Ha sido una figura destacada en la escena del rap en español, abordando temas como la feminidad, la identidad y la política en sus letras.",
                "Lujo Ibérico",
                "Malamarismo",
                "Bruja",
                "La Niña (USA)",
                "Su álbum debut estableció a La Mala Rodríguez como una fuerza en el rap español, con letras directas y un sonido innovador que capturó la atención del público.",
                "Con colaboraciones de artistas destacados, este álbum llevó el rap español a nuevas alturas, con ritmos contagiosos y líricas que celebran la cultura urbana.",
                "Considerado uno de sus mejores álbumes, presenta un sonido fresco y líricas que abordan temas como el amor, el desamor y la lucha.",
                "Con un enfoque en la autenticidad y la sinceridad, este álbum muestra la esencia de La Mala Rodríguez, con ritmos frescos y líricas que celebran la vida y la libertad."
        );

        dbArtistsHelper.insertTextWithName(
                "Yung Lean",
                "Yung Lean, nacido Jonatan Leandoer Håstad, es un rapero y cantante sueco conocido por su estilo melancólico y su influencia en el cloud rap. Surgió en la escena musical en 2013 con el éxito viral 'Ginseng Strip 2002' y ha lanzado varios álbumes aclamados, solidificando su lugar en la música alternativa.",
                "Unknown Memory",
                "Stranger",
                "Starz",
                "Warlord",
                "Este álbum consolidó a Yung Lean como una figura central del cloud rap, con su sonido atmosférico y letras introspectivas, explorando temas de tristeza y soledad con una producción minimalista que resuena profundamente con sus oyentes.",
                "Muestra una evolución en su estilo, con temas más oscuros y personales. El álbum está lleno de paisajes sonoros etéreos y letras que profundizan en su psique, destacando canciones como 'Red Bottom Sky'.",
                "Un álbum que combina elementos de pop y trap, con colaboraciones notables y una producción pulida. Las letras abordan la fama y las luchas personales, ofreciendo una visión introspectiva y emocional.",
                "Presenta un sonido más agresivo y experimental, con canciones destacadas como 'Afghanistan' y 'Highway Patrol'. Este álbum explora temas de poder y vulnerabilidad, mostrando una faceta más dura de Yung Lean."
        );

        dbArtistsHelper.insertTextWithName(
                "Bladee",
                "Bladee, nacido Benjamin Reichwald, es un artista sueco conocido por su estilo distintivo que mezcla el cloud rap con elementos de pop y electrónica. Parte del colectivo Drain Gang, Bladee ha ganado un seguimiento de culto por su enfoque experimental y su lírica emocional.",
                "Eversince",
                "Red Light",
                "333",
                "Exeter",
                "Su álbum debut que presenta un sonido etéreo y letras introspectivas, estableciendo su estilo único en la escena. La producción atmosférica y las melodías pegajosas hacen de este álbum una pieza fundamental en su discografía.",
                "Un álbum que explora temas de alienación y espiritualidad, con una producción atmosférica y futurista. Las letras profundas y las texturas sonoras crean una experiencia auditiva inmersiva y única.",
                "Destaca por su sonido experimental y colaboraciones con otros miembros de Drain Gang, mostrando su evolución artística. Este álbum combina melodías pegajosas con letras introspectivas y una producción innovadora.",
                "Un EP producido por Gud que combina melodías pegajosas con una producción minimalista y emotiva. Las letras introspectivas y las atmósferas sonoras crean un paisaje sonoro íntimo y cautivador."
        );

        dbArtistsHelper.insertTextWithName(
                "Outkast",
                "Outkast es un dúo de hip hop estadounidense formado por André 3000 y Big Boi. Conocidos por su estilo innovador y ecléctico, combinaron elementos de funk, jazz, y soul con el rap. Surgieron en la escena en los años 90 y se convirtieron en una de las figuras más influyentes del hip hop.",
                "Aquemini",
                "Stankonia",
                "Speakerboxxx/The Love Below",
                "ATLiens",
                "Fusiona el funk y el hip hop con letras introspectivas y narrativas complejas, incluyendo éxitos como 'Rosa Parks'. Este álbum es un viaje musical a través de las experiencias y reflexiones de los miembros del grupo.",
                "Presenta un sonido más experimental y diverso, con éxitos como 'Ms. Jackson' y 'B.O.B'. Este álbum es conocido por su producción innovadora y sus letras provocativas, explorando temas sociales y personales.",
                "Un álbum doble que explora estilos variados, ganando múltiples premios Grammy y produciendo hits como 'Hey Ya!'. Cada disco ofrece una perspectiva única, mostrando la versatilidad y la creatividad de los miembros de Outkast.",
                "Combina el rap sureño con elementos de ciencia ficción y soul, destacando por su creatividad y originalidad. Este álbum es una mezcla de ritmos pegajosos y letras reflexivas, mostrando la evolución del grupo."
        );

        dbArtistsHelper.insertTextWithName(
                "MF DOOM",
                "MF DOOM, nacido Daniel Dumile, fue un rapero y productor británico-estadounidense conocido por su lírica compleja y su alter ego enmascarado. Con una carrera que abarca varias décadas, MF DOOM se convirtió en una figura legendaria del underground hip hop, influyendo a generaciones de artistas.",
                "Madvillainy",
                "Mm..Food",
                "Operation: Doomsday",
                "Born Like This",
                "Colaboración con Madlib, considerado un clásico del hip hop underground por su producción innovadora y letras ingeniosas. Cada pista es una obra maestra de la narración lírica y la creatividad musical.",
                "Un álbum conceptual centrado en la comida, mostrando su habilidad lírica y sentido del humor único. Las complejas rimas y la producción sampleada crean una experiencia auditiva única y entretenida.",
                "Su álbum debut que introduce su alter ego Doom, con un sonido crudo y líricas introspectivas. Este álbum establece su estilo distintivo y su habilidad para contar historias a través del rap.",
                "Una exploración de temas oscuros y personales, con producciones complejas y un enfoque lírico característico. Este álbum muestra su evolución como artista y su capacidad para abordar temas profundos y oscuros."
        );

        dbArtistsHelper.insertTextWithName(
                "A Tribe Called Quest",
                "A Tribe Called Quest es un influyente grupo de hip hop estadounidense formado en 1985, conocido por su fusión de jazz y rap. Compuesto por Q-Tip, Phife Dawg, Ali Shaheed Muhammad, y Jarobi White, el grupo ha dejado una marca indeleble en el género con sus letras inteligentes y producción innovadora.",
                "The Low End Theory",
                "Midnight Marauders",
                "Beats, Rhymes and Life",
                "We Got It from Here... Thank You 4 Your Service",
                "Combina elementos de jazz con ritmos de hip hop, creando un sonido único y atemporal. Este álbum es conocido por su profunda influencia en el desarrollo del jazz rap.",
                "Un álbum icónico que equilibra letras conscientes con producción sofisticada. Las complejas estructuras líricas y las melodías pegajosas lo convierten en un clásico del hip hop.",
                "Muestra una maduración en su sonido, con temas más profundos y producción más pulida. Las colaboraciones y la cohesión entre los miembros del grupo hacen de este álbum una obra destacada.",
                "Un regreso triunfal con colaboraciones estelares y una producción impecable. Este álbum final ofrece una despedida poderosa y emotiva del grupo, abordando temas sociales y personales."
        );

        dbArtistsHelper.insertTextWithName(
                "Kid Cudi",
                "Kid Cudi, nacido Scott Mescudi, es un rapero, cantante y actor estadounidense conocido por su influencia en la música hip hop y su estilo introspectivo. Surgió en la escena en 2009 con su sencillo 'Day 'n' Nite' y ha lanzado varios álbumes aclamados, explorando temas de depresión, soledad y autodescubrimiento.",
                "Man on the Moon: The End of Day",
                "Man on the Moon II: The Legend of Mr. Rager",
                "Indicud",
                "Passion, Pain & Demon Slayin'",
                "Un álbum debut innovador que explora temas de soledad y autodescubrimiento con una producción ecléctica. Las letras introspectivas y la producción innovadora establecen su estilo único.",
                "Continúa la narrativa del primer álbum, con una exploración más profunda de sus luchas personales. Las melodías oscuras y las letras honestas hacen de este álbum una experiencia emocionalmente intensa.",
                "Muestra una mayor experimentación sonora y colaboraciones notables, manteniendo su estilo introspectivo. Este álbum destaca por su producción innovadora y su habilidad para mezclar géneros.",
                "Combina elementos de rap, rock y electrónica, con letras que abordan su viaje personal y emocional. Este álbum muestra su crecimiento artístico y su capacidad para crear música profundamente conmovedora."
        );

        dbArtistsHelper.insertTextWithName("Young Thug",
                "Young Thug, cuyo nombre real es Jeffery Lamar Williams, es un rapero y cantante estadounidense de Atlanta, Georgia. Conocido por su estilo único, su versatilidad vocal y su estética andrógina, Young Thug ha influido significativamente en el hip-hop contemporáneo. Comenzó a ganar atención en 2013 con sus sencillos 'Stoner' y 'Danny Glover', y desde entonces ha lanzado una serie de mixtapes y álbumes que han sido aclamados por la crítica.",
                "So Much Fun",
                "Jeffery",
                "Barter 6",
                "Slime Season 3",
                "Este disco de 2019 es uno de los más accesibles y exitosos comercialmente de Young Thug. Contiene colaboraciones con artistas como Future, J. Cole y Travis Scott, y presenta una mezcla de trap bangers y canciones más melódicas.",
                "Lanzado en 2016, 'Jeffery' es conocido por su innovador enfoque en el trap y su portada icónica donde Young Thug aparece vestido con un vestido de diseñadores japoneses. El álbum incluye éxitos como 'Pick Up the Phone' y 'Wyclef Jean'.",
                "'Barter 6' de 2015 fue un lanzamiento polémico debido a su título, pero ayudó a consolidar a Young Thug como una fuerza importante en el trap. Con canciones como 'Check' y 'With That', el álbum destaca su estilo vocal y lírico único.",
                "Parte de una serie de mixtapes, 'Slime Season 3' (2016) es altamente apreciado por sus fans y críticos. Incluye éxitos como 'Digits' y 'With Them', mostrando su habilidad para mezclar estilos vocales y melodías innovadoras."
        );

        dbArtistsHelper.insertTextWithName("Future",
                "Future, cuyo nombre real es Nayvadius DeMun Wilburn, es un rapero, cantante y compositor estadounidense de Atlanta, Georgia. Conocido por su distintiva voz autotuneada y sus temas líricos centrados en el hedonismo y la autodestrucción, Future ha sido una figura central en la evolución del trap y el hip-hop moderno. Ha lanzado numerosos álbumes y mixtapes que han dominado las listas de éxitos.",
                "DS2",
                "Hndrxx",
                "Future",
                "The WIZRD",
                "Lanzado en 2015, 'DS2' es considerado uno de los mejores trabajos de Future. Con éxitos como 'Where Ya At' y 'Fuck Up Some Commas', el álbum muestra su talento para crear himnos de club y canciones introspectivas.",
                "Este álbum de 2017 es conocido por su tono más melódico y personal. Canciones como 'Coming Out Strong' y 'Selfish' muestran a Future explorando temas de amor y vulnerabilidad.",
                "Publicado en 2017 junto con 'Hndrxx', 'Future' es un álbum más orientado al trap clásico. Contiene éxitos como 'Mask Off' y 'Draco', destacando su estilo característico y producción innovadora.",
                "Lanzado en 2019, 'The WIZRD' es otro gran éxito en la carrera de Future. Con temas como 'Crushed Up' y 'First Off', el álbum ofrece una mezcla de sus sonidos característicos y experimentaciones nuevas."
        );

        dbArtistsHelper.insertTextWithName("Chief Keef",
                "Chief Keef, cuyo nombre real es Keith Farrelle Cozart, es un rapero y productor musical estadounidense de Chicago, Illinois. Es conocido como una figura pionera en el subgénero del drill, que surgió a principios de la década de 2010. Con su estilo crudo y lírico, Keef capturó la atención del público con su sencillo 'I Don't Like', y ha influido en una generación de raperos.",
                "Finally Rich",
                "Bang 3",
                "Almighty So (DJ Scream)",
                "Dedication",
                "El álbum debut de Chief Keef, lanzado en 2012, es un hito en la música drill. Con éxitos como 'I Don't Like' y 'Love Sosa', el álbum capturó la esencia de la vida en las calles de Chicago y la juventud rebelde de Keef.",
                "Lanzado en dos partes en 2015, 'Bang 3' muestra a Keef experimentando con nuevos sonidos y colaboraciones, incluyendo a artistas como A$AP Rocky y Mac Miller.",
                "Publicado en 2013, 'Almighty So' es uno de los mixtapes más icónicos de Keef. Con su estilo distintivo y producción minimalista, el álbum solidifica su estatus en el mundo del drill.",
                "Lanzado en 2017, 'Dedication' es un testimonio de la evolución artística de Keef. Con canciones como 'Kills' y 'Mailbox', muestra una madurez en su enfoque lírico y musical."
        );

        dbArtistsHelper.insertTextWithName("Lil Uzi Vert",
                "Lil Uzi Vert, cuyo nombre real es Symere Bysil Woods, es un rapero, cantante y compositor estadounidense de Filadelfia, Pensilvania. Conocido por su estilo excéntrico y su habilidad para mezclar rap y rock, Uzi se ha convertido en una de las figuras más influyentes en la música contemporánea. Saltó a la fama con su sencillo 'Money Longer' y ha lanzado varios álbumes exitosos.",
                "Luv Is Rage 2",
                "Eternal Atake",
                "Lil Uzi Vert vs. The World",
                "The Perfect LUV Tape",
                "Lanzado en 2017, 'Luv Is Rage 2' es uno de los álbumes más exitosos de Uzi. Con éxitos como 'XO TOUR Llif3' y 'The Way Life Goes', el álbum muestra su capacidad para crear canciones pegajosas y emocionalmente resonantes.",
                "Este álbum de 2020 fue muy anticipado y no decepcionó. 'Eternal Atake' presenta una mezcla de trap y sonidos futuristas, con éxitos como 'Baby Pluto' y 'P2'.",
                "Publicado en 2016, este mixtape ayudó a establecer a Uzi como una estrella en ascenso. Con canciones como 'Money Longer' y 'You Was Right', el álbum muestra su estilo distintivo y energía vibrante.",
                "Lanzado en 2016, 'The Perfect LUV Tape' es otro mixtape clave en la carrera de Uzi. Con canciones como 'Do What I Want' y 'Seven Million', muestra su versatilidad y creatividad."
        );

        dbArtistsHelper.insertTextWithName("Travis Scott",
                "Travis Scott, cuyo nombre real es Jacques Bermon Webster II, es un rapero, cantante, productor y diseñador estadounidense de Houston, Texas. Conocido por su estilo hipnótico y sus conciertos enérgicos, Scott ha sido una fuerza dominante en la música hip-hop moderna. Ha lanzado varios álbumes que han sido aclamados por la crítica y han alcanzado el éxito comercial.",
                "Astroworld",
                "Rodeo",
                "Birds in the Trap Sing McKnight",
                "Huncho Jack, Jack Huncho",
                "Lanzado en 2018, 'Astroworld' es un álbum conceptual que rinde homenaje al parque temático desaparecido de Houston. Con éxitos como 'SICKO MODE' y 'STARGAZING', el álbum destaca su habilidad para crear paisajes sonoros complejos.",
                "Este álbum de 2015 es el debut de estudio de Scott y lo consolidó como una estrella emergente. Con canciones como 'Antidote' y '3500', 'Rodeo' muestra su estilo innovador y producción vanguardista.",
                "Publicado en 2016, este álbum presenta una mezcla de trap y sonidos atmosféricos. Con éxitos como 'goosebumps' y 'pick up the phone', el álbum solidifica su lugar en el hip-hop moderno.",
                "Lanzado en 2017 en colaboración con Quavo de Migos, este álbum presenta una mezcla de trap y melodías pegajosas. Con canciones como 'Modern Slavery' y 'Saint', el álbum muestra la química entre los dos artistas."
        );

        dbArtistsHelper.insertTextWithName("21 Savage",
                "21 Savage, cuyo nombre real es Shéyaa Bin Abraham-Joseph, es un rapero, compositor y productor estadounidense de Atlanta, Georgia. Conocido por su estilo sombrío y sus letras crudas sobre la vida en la calle, 21 Savage ha sido una figura importante en la escena del trap. Gano reconocimiento con su mixtape 'The Slaughter Tape' y ha lanzado varios álbumes exitosos.",
                "Issa Album",
                "I Am > I Was",
                "Whithout Warning",
                "Savage Mode II",
                "Este álbum de 2017 es el debut de estudio de 21 Savage. Con éxitos como 'Bank Account' y 'Numb', el álbum muestra su estilo oscuro y letras introspectivas.",
                "Lanzado en 2018, 'I Am > I Was' es aclamado por su diversidad lírica y producción. Con colaboraciones de artistas como J. Cole y Childish Gambino, el álbum explora temas de crecimiento personal y redención.",
                "\"Without Warning\" es un álbum colaborativo lanzado por 21 Savage, Offset y el productor Metro Boomin el 31 de octubre de 2017. Este proyecto sorprendente se caracteriza por su lanzamiento inesperado y su atmósfera oscura y ominosa, perfecta para la fecha de Halloween en que fue lanzado. El álbum fusiona los estilos únicos de 21 Savage y Offset con las producciones sombrías y contundentes de Metro Boomin.",
                "Lanzado en 2020, esta secuela de 'Savage Mode' continúa la colaboración con Metro Boomin. Con éxitos como 'Runnin' y 'Mr. Right Now', el álbum solidifica su química y ofrece una evolución en su sonido."
        );

        dbArtistsHelper.insertTextWithName("Soda Stereo",
                "Soda Stereo fue una banda de rock argentina formada en Buenos Aires en 1982 por Gustavo Cerati, Zeta Bosio y Charly Alberti. Es considerada una de las bandas más influyentes e importantes del rock en español. Su sonido evolucionó a lo largo de los años, desde el new wave y post-punk hasta el rock alternativo y el pop rock.",
                "Signos",
                "Doble Vida",
                "Canción Animal",
                "Sueño Stereo",
                "Lanzado en 1986, 'Signos' es uno de los discos más emblemáticos de Soda Stereo. Contiene éxitos como 'Signos' y 'Persiana Americana', y marca una evolución en su sonido hacia un estilo más maduro y complejo.",
                "Este álbum de 1988 fue grabado en Nueva York con el productor Carlos Alomar. Incluye canciones icónicas como 'En la Ciudad de la Furia' y 'Lo Que Sangra (La Cúpula)', consolidando su popularidad en toda América Latina.",
                "Publicado en 1990, 'Canción Animal' es considerado uno de los mejores álbumes de rock en español. Con éxitos como 'De Música Ligera' y 'Cae el Sol', el álbum representa el punto culminante de su carrera.",
                "Lanzado en 1995, 'Sueño Stereo' es conocido por su mezcla de rock y electrónica. Contiene canciones memorables como 'Ella Usó Mi Cabeza Como un Revólver' y 'Zoom', mostrando su continua evolución musical."
        );

        dbArtistsHelper.insertTextWithName("Héroes del Silencio",
                "Héroes del Silencio fue una banda de rock española formada en Zaragoza en 1984. Liderada por Enrique Bunbury, la banda ganó reconocimiento por sus letras poéticas y su estilo influenciado por el rock gótico y el hard rock. Se separaron en 1996, dejando un legado duradero en la música en español.",
                "Senderos de Traición",
                "El Espíritu del Vino",
                "Avalancha",
                "El Mar No Cesa",
                "Lanzado en 1990, 'Senderos de Traición' es uno de los discos más exitosos de la banda. Con éxitos como 'Entre Dos Tierras' y 'Maldito Duende', el álbum consolidó su popularidad en España y América Latina.",
                "Publicado en 1993, 'El Espíritu del Vino' muestra una evolución hacia un sonido más experimental. Con canciones como 'La Herida' y 'Flor de Loto', el álbum destaca por su profundidad lírica y musical.",
                "Este álbum de 1995 es el último de estudio de la banda antes de su separación. Con éxitos como 'Iberia Sumergida' y 'La Chispa Adecuada', 'Avalancha' es considerado uno de sus mejores trabajos.",
                "Lanzado en 1988, 'El Mar No Cesa' es el álbum debut de la banda. Incluye canciones icónicas como 'Mar Adentro' y 'Flor Venenosa', y muestra sus influencias del rock gótico y el post-punk."
        );

        dbArtistsHelper.insertTextWithName("Maná",
                "Maná es una banda de rock mexicana formada en Guadalajara en 1986. Conocidos por su estilo que mezcla rock, pop y ritmos latinos, Maná ha logrado un gran éxito comercial en América Latina y el mundo hispanohablante. Su música aborda temas de amor, política y conciencia social.",
                "¿Dónde Jugarán los Niños?",
                "Sueños Líquidos",
                "Amar es Combatir",
                "Drama y Luz",
                "Lanzado en 1992, '¿Dónde Jugarán los Niños?' es uno de los álbumes más exitosos de Maná. Con éxitos como 'Oye Mi Amor' y 'Vivir Sin Aire', el álbum aborda temas sociales y ambientales.",
                "Publicado en 1997, 'Sueños Líquidos' ganó un Grammy y consolidó a Maná como una de las bandas más importantes de rock en español. Con canciones como 'Clavado en un Bar' y 'En el Muelle de San Blas', el álbum muestra su habilidad para fusionar diferentes estilos.",
                "Lanzado en 2006, 'Amar es Combatir' es conocido por su mezcla de rock y pop latino. Incluye éxitos como 'Labios Compartidos' y 'Bendita Tu Luz', y aborda temas de amor y lucha social.",
                "Este álbum de 2011 es uno de los más ambiciosos de Maná. Con canciones como 'Lluvia al Corazón' y 'El Verdadero Amor Perdona', el álbum combina su estilo característico con letras profundas y emotivas."
        );

        dbArtistsHelper.insertTextWithName("Café Tacvba",
                "Café Tacvba es una banda de rock alternativo mexicana formada en Ciudad Satélite en 1989. Conocidos por su eclecticismo musical y letras innovadoras, han sido pioneros en la mezcla de rock con música tradicional mexicana y otros géneros. Son una de las bandas más influyentes de América Latina.",
                "Re",
                "Avalancha de Éxitos",
                "Cuatro Caminos",
                "Sino",
                "Lanzado en 1994, 'Re' es considerado uno de los mejores álbumes de rock en español. Con una mezcla de géneros y estilos, incluye canciones icónicas como 'La Ingrata' y 'El Baile y el Salón'.",
                "Publicado en 1996, 'Avalancha de Éxitos' es un álbum de covers que muestra la versatilidad de la banda. Incluye reinterpretaciones de canciones clásicas mexicanas y latinoamericanas.",
                "Este álbum de 2003 ganó varios premios Grammy y Grammy Latino. Con éxitos como 'Eres' y 'Cero y Uno', 'Cuatro Caminos' muestra una evolución hacia un sonido más rock y electrónico.",
                "Lanzado en 2007, 'Sino' es conocido por su profundidad lírica y musical. Con canciones como 'Volver a Comenzar' y 'Esta Vez', el álbum explora temas de amor y existencia."
        );

        dbArtistsHelper.insertTextWithName("Enanitos Verdes",
                "Enanitos Verdes es una banda de rock argentina formada en Mendoza en 1979. Conocidos por su estilo de rock pop y baladas románticas, han sido una de las bandas más populares de América Latina desde los años 80. Su música ha sido una constante en la radio y listas de éxitos.",
                "Contra Reloj",
                "Habitaciones Extrañas",
                "Big Bang",
                "Guerra Gaucha",
                "Lanzado en 1986, 'Contra Reloj' es uno de los álbumes más exitosos de Enanitos Verdes. Con éxitos como 'La Muralla Verde' y 'Cada Vez Que Digo Adiós', el álbum consolidó su popularidad en América Latina.",
                "Publicado en 1987, 'Habitaciones Extrañas' incluye canciones icónicas como 'Lamento Boliviano' y 'Te Vi en un Tren'. El álbum es un clásico del rock en español.",
                "Este álbum de 1994 muestra una evolución en su sonido hacia un estilo más maduro. Con canciones como 'Mi Primer Día Sin Ti' y 'Tú Cárcel', 'Big Bang' es uno de sus trabajos más importantes.",
                "Lanzado en 1999, 'Guerra Gaucha' es conocido por su mezcla de rock y baladas. Con éxitos como 'Amores Lejanos' y 'Luz de Día', el álbum destaca por su profundidad lírica y musical."
        );

        dbArtistsHelper.insertTextWithName("Extremoduro",
                "Extremoduro fue una banda de rock español formada en Plasencia en 1987 por Roberto Iniesta, conocido como Robe. Conocidos por sus letras poéticas y su estilo crudo, han sido una de las bandas más influyentes del rock en español. Se disolvieron en 2019, dejando un legado duradero en la música.",
                "Agila",
                "Somos unos Animales",
                "Deltoya",
                "La Ley Innata",
                "Lanzado en 1996, 'Agila' es uno de los álbumes más exitosos de Extremoduro. Con éxitos como 'So Payaso' y 'Buscando una Luna', el álbum combina letras poéticas con un sonido potente.",
                "Publicado en 1991, 'Somos unos Animales' muestra su estilo crudo y directo. Con canciones como 'Decidí' y 'Pepe Botika', el álbum es un clásico del rock urbano español.",
                "Este álbum de 1992 es conocido por sus letras provocativas y su sonido intenso. Con éxitos como 'Jesucristo García' y 'Deltoya', es uno de los trabajos más importantes de la banda.",
                "Lanzado en 2008, 'La Ley Innata' es un álbum conceptual que muestra una evolución en su sonido. Con canciones como 'Dulce Introducción al Caos' y 'Segundo Movimiento: Lo de Fuera', el álbum es aclamado por su profundidad lírica y musical."
        );

        dbArtistsHelper.insertTextWithName("Shakira",
                "Shakira es una cantante, compositora y productora colombiana nacida en Barranquilla en 1977. Conocida por su versatilidad musical y su habilidad para mezclar diferentes géneros, ha logrado un éxito internacional masivo desde sus primeros discos en español hasta sus incursiones en el mercado angloparlante.",
                "Pies Descalzos",
                "¿Dónde Están los Ladrones?",
                "Laundry Service",
                "Fijación Oral, Vol. 1",
                "Lanzado en 1995, 'Pies Descalzos' es el tercer álbum de estudio de Shakira y su primer gran éxito comercial. Contiene éxitos como 'Estoy Aquí' y 'Pies Descalzos, Sueños Blancos', y marca el comienzo de su ascenso en la música latina.",
                "Publicado en 1998, '¿Dónde Están los Ladrones?' consolidó su fama en América Latina y España. Con canciones icónicas como 'Ciega, Sordomuda' y 'Ojos Así', el álbum recibió aclamación crítica y varios premios.",
                "Este álbum de 2001 es su debut en el mercado angloparlante. Con éxitos como 'Whenever, Wherever' y 'Underneath Your Clothes', 'Laundry Service' la estableció como una estrella internacional.",
                "Lanzado en 2005, 'Fijación Oral, Vol. 1' ganó varios premios Grammy y Grammy Latino. Con canciones como 'La Tortura' y 'No', el álbum muestra una mezcla de pop latino y rock."
        );

        dbArtistsHelper.insertTextWithName("Rosalía",
                "Rosalía es una cantante y compositora española nacida en San Esteban Sasroviras en 1992. Conocida por su fusión de flamenco con otros géneros como el reguetón, el trap y el pop, ha logrado un éxito internacional y es reconocida por su estilo innovador y su poderosa voz.",
                "Los Ángeles",
                "El Mal Querer",
                "Motomami",
                "Con Altura (Single)",
                "Lanzado en 2017, 'Los Ángeles' es el álbum debut de Rosalía, donde reinterpreta el flamenco tradicional con un enfoque contemporáneo. Este trabajo llamó la atención por su originalidad y técnica vocal.",
                "Publicado en 2018, 'El Mal Querer' es un álbum conceptual basado en una novela medieval. Con éxitos como 'Malamente' y 'Pienso en Tu Mirá', el álbum recibió aclamación crítica y varios premios.",
                "Este álbum de 2022 muestra una evolución hacia un sonido más global y experimental. Con canciones como 'La Fama' y 'Saoko', 'Motomami' mezcla diversos géneros y estilos, consolidando su posición en la música internacional.",
                "Lanzado en 2019, 'Con Altura' es un sencillo en colaboración con J Balvin. La canción fue un éxito global y ayudó a Rosalía a ganar mayor reconocimiento en el mercado latinoamericano y mundial."
        );

        dbArtistsHelper.insertTextWithName("Aitana",
                "Aitana es una cantante y actriz española nacida en Barcelona en 1999. Se hizo famosa tras participar en el concurso de talentos Operación Triunfo en 2017. Desde entonces, ha lanzado varios éxitos y se ha consolidado como una de las artistas jóvenes más prometedoras de España.",
                "Tráiler",
                "Spoiler",
                "11 Razones",
                "Alpha",
                "Lanzado en 2018, 'Tráiler' es el primer EP de Aitana. Incluye el exitoso sencillo 'Teléfono', que la catapultó a la fama en el ámbito del pop español.",
                "Publicado en 2019, 'Spoiler' es el álbum debut de Aitana. Con éxitos como 'Nada Sale Mal' y 'Vas a Quedarte', el álbum consolidó su carrera y la estableció como una de las principales figuras del pop español.",
                "Este álbum de 2020 muestra una evolución en su estilo musical. Con canciones como 'Más de lo que Aposté' y 'Corazón Sin Vida', '11 Razones' combina pop con influencias de otros géneros.",
                "Lanzado en 2023, 'Alpha' es su segundo álbum de estudio. Con éxitos como 'Los Ángeles' y 'Otra Vez', el álbum muestra su madurez artística y su capacidad para experimentar con nuevos sonidos."
        );

        dbArtistsHelper.insertTextWithName("Enrique Iglesias",
                "Enrique Iglesias es un cantante y compositor español nacido en Madrid en 1975. Hijo del famoso cantante Julio Iglesias, ha logrado una exitosa carrera internacional con una mezcla de pop, dance y reguetón. Es uno de los artistas latinos más vendidos de todos los tiempos.",
                "Enrique Iglesias",
                "Escape",
                "Insomniac",
                "Euphoria",
                "Lanzado en 1995, 'Enrique Iglesias' es su álbum debut, que incluye éxitos como 'Si Tú Te Vas' y 'Experiencia Religiosa'. El álbum fue un gran éxito en América Latina y España.",
                "Publicado en 2001, 'Escape' es uno de sus álbumes más exitosos, con éxitos como 'Hero' y 'Escape'. Este álbum consolidó su fama en el mercado angloparlante.",
                "Este álbum de 2007 muestra una mezcla de pop y dance. Con éxitos como 'Do You Know? (The Ping Pong Song)' y 'Somebody's Me', 'Insomniac' destaca por su sonido moderno y pegajoso.",
                "Lanzado en 2010, 'Euphoria' incluye colaboraciones con artistas como Pitbull y Usher. Con éxitos como 'I Like It' y 'Tonight (I'm Lovin' You)', el álbum mezcla pop, dance y reguetón."
        );

        dbArtistsHelper.insertTextWithName("Luis Fonsi",
                "Luis Fonsi es un cantante, compositor y actor puertorriqueño nacido en San Juan en 1978. Conocido por su poderosa voz y su habilidad para interpretar baladas y música pop, ha logrado éxito internacional, especialmente con su megaéxito 'Despacito'.",
                "Comenzaré",
                "Palabras del Silencio",
                "8",
                "Vida",
                "Lanzado en 1998, 'Comenzaré' es el álbum debut de Luis Fonsi. Con éxitos como 'Si Tú Quisieras' y 'Perdóname', el álbum lo estableció como una nueva voz en la música latina.",
                "Publicado en 2008, 'Palabras del Silencio' incluye el éxito 'No Me Doy por Vencido'. Este álbum consolidó su fama y recibió varios premios.",
                "Este álbum de 2014 muestra una evolución en su estilo musical, combinando pop y baladas. Con canciones como 'Corazón en la Maleta' y 'Llegaste Tú', '8' es uno de sus trabajos más sólidos.",
                "Lanzado en 2019, 'Vida' incluye el megaéxito 'Despacito' y otras canciones como 'Échame la Culpa' y 'Calypso'. El álbum fue un éxito comercial y destacó por sus colaboraciones internacionales."
        );

        dbArtistsHelper.insertTextWithName("Sebastián Yatra",
                "Sebastián Yatra es un cantante y compositor colombiano nacido en Medellín en 1994. Conocido por su versatilidad musical, ha logrado éxito en géneros como pop, reguetón y balada. Yatra ha colaborado con varios artistas internacionales y es una figura destacada en la música latina contemporánea.",
                "Mantra",
                "Fantasía",
                "Dharma",
                "Yatra Yatra (Single)",
                "Lanzado en 2018, 'Mantra' es el álbum debut de Sebastián Yatra. Con éxitos como 'Traicionera' y 'Sutra', el álbum lo estableció como una de las nuevas estrellas del pop y reguetón latino.",
                "Publicado en 2019, 'Fantasía' muestra un enfoque más hacia las baladas. Con canciones como 'Cristina' y 'Un Año', el álbum destaca por su emotividad y profundidad lírica.",
                "Este álbum de 2022 mezcla pop, reguetón y otros géneros. Con éxitos como 'Tacones Rojos' y 'Pareja del Año', 'Dharma' muestra su versatilidad musical y creatividad.",
                "Lanzado en 2023, 'Yatra Yatra' es un sencillo que muestra su capacidad para fusionar diferentes estilos y colaborar con otros artistas. La canción ha sido bien recibida por su frescura y originalidad."
        );

        dbArtistsHelper.insertTextWithName("Eladio Carrión", "Eladio Carrión es un rapero, cantante y compositor puertorriqueño nacido el 14 de noviembre de 1994. Comenzó su carrera en el entretenimiento como comediante en redes sociales antes de dedicarse a la música. Su estilo combina reguetón, trap y hip-hop, y es conocido por su habilidad lírica y su versatilidad musical.", "Sauce Boyz", "SEN2 KBRN Vol. 1", "SEN2 KBRN Vol. 2","3MEN2 KBRN",  "Este disco, 'Sauce Boyz', marcó el debut de Eladio Carrión como solista, mostrando su habilidad para combinar el trap con elementos de reguetón y hip-hop. Con colaboraciones de artistas de renombre, el álbum destaca por su frescura y originalidad en la escena urbana.", "\"'SEN2 KBRN Vol. 1' es una prueba del talento lírico de Eladio Carrión, con letras que abordan temas personales y sociales con una honestidad cruda. Este disco reafirma su posición como una de las voces más auténticas y relevantes en la música urbana latina.", "'SEN2 KBRN Vol. 2' es una continuación de la serie de álbumes de Eladio Carrión que demuestra su habilidad lírica y su talento para el storytelling. El álbum aborda temas de lucha, éxito y las realidades de la vida en la calle, todo envuelto en una producción pulida que mezcla trap y reguetón con una sensibilidad moderna. Con colaboraciones de alto nivel y una mezcla de ritmos pegajosos y letras introspectivas, este disco consolida a Eladio como una figura central en la música urbana latina.", "\"'3MEN2 KBRN' continúa la serie de álbumes de Eladio Carrión, destacando por su profundidad lírica y su capacidad para abordar temas complejos como la lucha por el éxito, la vida en la calle y las experiencias personales. Con una producción impecable que mezcla trap, reguetón y otros géneros urbanos, este disco incluye colaboraciones destacadas y muestra la evolución constante de Eladio como uno de los artistas más influyentes en la música urbana latina. '3MEN2 KBRN' es una obra que resalta la honestidad y la introspección del artista, consolidando su estatus en la industria.");


        dbArtistsHelper.insertTextWithName("Los Alemanes",
                "Los Alemanes es un grupo de trap español formado por la unión de los Santos, también conocidos como pxxr gvng (Kaidy Cain, Khaled, Yung Beef, Steve Lean) y Takers(Israel B., Kaidy Cain, Marko Italia, M. Ramirez, CaballodeRally, Gipsy La Fe), también tiene otras colaboraciones con Hakim y M.Ramirez, fue uno de los grupos pioneros en llevar al trap a la escena mainstream. Como no tienen discos aquí se incluyen los más importantes de sus miembros.",
                "Ladrones para siempre",
                "Who Is Israel B.?",
                "TRVP Jinxx (Mixtape)",
                "Pobres",
                "\"Lanzado en 2016, 'Ladrones para Siempre' es el único álbum conjunto del grupo Takers, compuesto por Israel B., Kaidy Cain, Marko Italia, M. Ramirez, CaballodeRally y Gipsy La Fe. Con éxitos como 'Plata y Plomo' y 'Nada', este álbum se convirtió en un clásico del trap español. Su estilo crudo y líricas directas reflejan la vida urbana y las experiencias personales de sus miembros, consolidándose como una referencia en la escena del trap en español.\"",
                "Lanzado en 2020, 'Who is Israel B?' es un álbum que muestra la versatilidad y habilidad lírica de Israel B. Con éxitos como 'Me Comen' y 'Chándal', el álbum destaca por su estilo crudo y narrativo. Las letras abordan temas como la vida en las calles y las experiencias personales de Israel B., consolidándolo como una figura importante en la música urbana española.",
                "'Trap Jinxx' es un álbum en solitario de Kaydy Cain, conocido por su estilo crudo y directo. Con una combinación de trap y rap callejero, el álbum incluye éxitos como 'Contando Dinero' y 'Pégate'. Las letras abordan temas como la vida en las calles y las experiencias personales de Kaydy Cain, destacando su versatilidad y habilidad lírica.",
                "Lanzado en 2015, 'Los Pobres' es el único álbum de estudio del grupo Pxxr Gvng. Con canciones como 'Tu Coño Es Mi Droga' y 'Malianteo 420', este álbum se convirtió en un referente del trap en español. El álbum destaca por su estilo crudo y narrativo, reflejando las experiencias y la vida urbana de sus miembros, y ha influenciado enormemente la escena del trap en España."
        );

        dbArtistsHelper.insertTextWithName("Duki",
                "Duki es un cantante y rapero argentino nacido en Buenos Aires en 1996. Conocido por su influencia en la escena del trap latinoamericano, ha logrado un gran éxito con su estilo único y su capacidad para fusionar trap, reguetón y otros géneros urbanos.",
                "Súper Sangre Joven",
                "Desde el Fin del Mundo",
                "Temporada de Reggaetón",
                "Antes de Ameri",
                "Lanzado en 2019, 'Súper Sangre Joven' es el álbum debut de Duki. El álbum lo estableció como una de las principales figuras del trap latinoamericano.",
                "Publicado en 2021, 'Desde el Fin del Mundo' muestra una evolución en su estilo musical. Con colaboraciones de artistas como Khea y Bizarrap, incluye canciones como 'Muero de Fiesta este Finde' y 'Cascada'.",
                "Temporada De Reggaetón llega cargado, como bien indica su título, de canciones de reggaetón para así convertirse en las protagonistas de cualquier fiesta. El artista, que siempre se ha inclinado por un estilo más trap, renueva su repertorio y muestra su versatilidad.",
                "Con este álbum, Duki demuestra una vez más su versatilidad y evolución artística, consolidando su posición como referente indiscutible del trap argentino. Sus seguidores han recibido con entusiasmo esta nueva propuesta, y se espera que “Antes de Ameri” continúe cosechando éxitos y se convierta en otro hito en la carrera del talentoso músico."
        );

        dbArtistsHelper.insertTextWithName("Anuel AA",
                "Anuel AA es un cantante y rapero puertorriqueño nacido en Carolina en 1992. Conocido por ser uno de los pioneros del trap en español, ha logrado un éxito masivo con su estilo lírico crudo y su mezcla de trap y reguetón.",
                "Real Hasta la Muerte",
                "Emmanuel",
                "Los Dioses",
                "Las Leyendas Nunca Mueren",
                "Lanzado en 2018, 'Real Hasta la Muerte' es el álbum debut de Anuel AA. Con éxitos como 'Brindemos' y 'Ella Quiere Beber', el álbum lo estableció como una figura clave del trap y reguetón.",
                "Publicado en 2020, 'Emmanuel' muestra su evolución artística. Con colaboraciones de artistas como Bad Bunny y Lil Wayne, incluye canciones como 'Fútbol y Rumba' y 'Hasta Que Dios Diga'.",
                "Este álbum de 2021, 'Los Dioses', es una colaboración con Ozuna. Con éxitos como 'Antes' y 'RD', el álbum destaca por su mezcla de trap y reguetón.",
                "Lanzado en 2021, 'Las Leyendas Nunca Mueren' incluye éxitos como 'Dictadura' y 'Subelo'. Este álbum muestra su habilidad para fusionar trap, reguetón y otros géneros urbanos."
        );

        dbArtistsHelper.insertTextWithName("Bad Bunny",
                "Bad Bunny es un cantante y rapero puertorriqueño nacido en Vega Baja en 1994. Conocido por su estilo versátil y su capacidad para innovar dentro de los géneros urbanos, ha logrado un éxito internacional masivo con su mezcla de trap, reguetón y otros géneros.",
                "X 100PRE",
                "YHLQMDLG",
                "El Último Tour del Mundo",
                "Un Verano Sin Ti",
                "Lanzado en 2018, 'X 100PRE' es el álbum debut de Bad Bunny. Con éxitos como 'Estamos Bien' y 'Mía', el álbum lo estableció como una figura clave en la música urbana.",
                "Publicado en 2020, 'YHLQMDLG' incluye éxitos como 'Safaera' y 'Vete'. Este álbum recibió aclamación crítica y consolidó su fama internacional.",
                "Este álbum de 2020 muestra una evolución hacia un sonido más experimental. Con éxitos como 'Dákiti' y 'Yo Perreo Sola', 'El Último Tour del Mundo' destaca por su innovación.",
                "Lanzado en 2022, 'Un Verano Sin Ti' incluye éxitos como 'Me Porto Bonito' y 'Titi Me Preguntó'. El álbum mezcla trap, reguetón y otros géneros, y ha sido un éxito comercial y crítico."
        );

        dbArtistsHelper.insertTextWithName("Yung Beef",
                "El pionero y rey del trap en español necesitaba una sesión para él solo. Yung Beef es un rapero y cantante español nacido en Granada en 1990. Conocido por su estilo crudo y su capacidad para fusionar trap, reguetón y otros géneros urbanos, ha sido una figura influyente en la escena del trap en español.",
                "A.D.R.O.M.I.C.F.M.S 1",
                "Ganster Original",
                "ADROMICFMS 4",
                "El Plugg 2",
                "A.D.R.O.M.I.C.F.M.S se erige como la revelación de una verdad oculta a plena vista, como contar un secreto que ya todos conocen. La obra en su totalidad es un reflejo y a la vez una crítica de la sociedad actual (con claras influencias del mensaje que quieren transmitir géneros musicales alternativos como Witchouse o Vaporwave) tratando para ello una serie de temas, que resultan cotidianos para todos: el amor, la pobreza, la vida en el barrio, la fiesta, incluso la vacuidad de la vida o la muerte. ",
                "’Gangster Original’ es, además, un álbum conceptual y donde el concepto es el propio Yung Beef en este momento vital; un trabajo expresionista y naturalista al mismo tiempo: no estamos ante un trabajo que crece hacia fuera, sino que va desplegando un mundo interior tema tras tema que entronca directamente con el Yung Beef dosmilcatorcista, tan maravilloso y tan lejano que ya creíamos muerto.",
                "En “ADROMICFMS 4”, sí ha profundizado en los lazos personales. Y lo ha sumado a temas como la caída individual, tópico que arrastra desde hace algo más. “Rosas azules” o “EFFY” ponen la guinda a una mixtape más arriesgada en los textos. Y donde Beef pega duro con su malditismo. No se queden en la anécdota: el tiempo pasa tan rápido hoy día que El Seco ya tiene la voz de Joaquín Sabina (“Brazy”). ",
                "‘El Plugg 2’ nos trae de vuelta al Yung Beef más camaleónico. En cada uno de los 24 tracks que lo componen, Fernando sondea todas y cada una de sus facetas, llevándonos de la mano en un viaje visceral en el que explora, al completo, el espectro de emociones que oscilan entre la rabia y la tristeza."
        );

        dbArtistsHelper.insertTextWithName("Amadeus Mozart", "Wolfgang Amadeus Mozart (1756-1791) fue un compositor austriaco del período clásico. Es uno de los músicos más influyentes y prolíficos de la historia, con más de 600 obras en su catálogo que abarcan todos los géneros musicales de su tiempo. Sus composiciones incluyen sinfonías, conciertos, música de cámara, óperas y obras corales.", "Las bodas de Fígaro", "Sinfonía No. 40", "Requiem", "Don Giovanni", "Este disco presenta una de las óperas más famosas de Mozart, una comedia en cuatro actos que narra las peripecias de Fígaro y Susana, y destaca por su sofisticada estructura musical y su profundidad emocional.", "Una de las sinfonías más célebres de Mozart, esta obra en sol menor se caracteriza por su intensidad emocional y su riqueza melódica, y es una de las favoritas en el repertorio sinfónico.", "Una de las últimas composiciones de Mozart, este disco inacabado al momento de su muerte es una obra maestra del repertorio sacro, llena de misticismo y dramatismo.", "Otra ópera fundamental de Mozart, esta obra dramática cuenta la historia de Don Giovanni y su eventual descenso al infierno, con una música profundamente expresiva y compleja.");

        dbArtistsHelper.insertTextWithName("Beethoven", "Ludwig van Beethoven (1770-1827) fue un compositor y pianista alemán, una figura crucial en la transición del clasicismo al romanticismo en la música. Su obra abarca una amplia gama de géneros y ha tenido un impacto duradero en la música occidental. Pese a su progresiva sordera, compuso algunas de sus obras más importantes en sus últimos años.", "Sinfonía No. 9", "Sinfonía No. 5", "Sonata para piano No. 14 (Claro de Luna)", "Misa Solemnis", "Este disco incluye la monumental novena sinfonía de Beethoven, famosa por su final coral 'Oda a la Alegría', un símbolo universal de fraternidad y esperanza.", "La quinta sinfonía de Beethoven es una de las obras más conocidas del repertorio clásico, famosa por su motivo inicial de cuatro notas que se ha convertido en un ícono cultural.", "La Sonata para piano No. 14, conocida como 'Claro de Luna', es una de las sonatas más queridas de Beethoven, destacando por su belleza melódica y su atmósfera introspectiva.", "La Misa Solemnis es una de las obras corales más ambiciosas de Beethoven, una obra profunda y espiritual que refleja su fe y su búsqueda de lo sublime.");

        dbArtistsHelper.insertTextWithName("Bach", "Johann Sebastian Bach (1685-1750) fue un compositor y músico alemán del periodo barroco. Su música es reconocida por su complejidad contrapuntística y su riqueza armónica. Aunque no fue ampliamente conocido en vida, su obra ha tenido un impacto monumental en la música occidental.", "Las Variaciones Goldberg", "El arte de la fuga", "La Pasión según San Mateo", "Conciertos de Brandeburgo", "Este disco presenta las Variaciones Goldberg, una serie de variaciones para teclado que son una muestra sublime de la técnica y la creatividad de Bach.", "El arte de la fuga es una obra compleja y enigmática, compuesta por una serie de cánones y fugas que muestran el dominio contrapuntístico de Bach.", "La Pasión según San Mateo es una de las obras corales más importantes de Bach, un oratorio que narra la Pasión de Cristo con una profundidad emocional y espiritual impresionante.", "Los Conciertos de Brandeburgo son seis conciertos que representan la cumbre del estilo barroco, llenos de vitalidad, virtuosismo y belleza.");

        dbArtistsHelper.insertTextWithName("Tchaikovsky", "Pyotr Ilyich Tchaikovsky (1840-1893) fue un compositor ruso del periodo romántico. Es conocido por sus ballets, sinfonías, conciertos y óperas, y por su capacidad de combinar la música tradicional rusa con las formas occidentales. Su música es famosa por su emotividad y sus melodías memorables.", "El lago de los cisnes", "La bella durmiente", "El cascanueces", "Sinfonía No. 6 (Patética)", "Este disco incluye el famoso ballet 'El lago de los cisnes', una historia de amor, traición y redención que se ha convertido en un pilar del repertorio de ballet.", "La bella durmiente es otro ballet clásico de Tchaikovsky, conocido por su hermosa música y su rica orquestación, basado en el cuento de hadas homónimo.", "El cascanueces es un ballet navideño por excelencia, lleno de encanto y magia, con melodías que se han convertido en parte integral de la celebración navideña.", "La Sinfonía No. 6, también conocida como 'Patética', es una de las sinfonías más emotivas de Tchaikovsky, llena de pasión y desesperación, y es considerada su obra maestra final.");

        dbArtistsHelper.insertTextWithName("Johann Strauss II", "Johann Strauss II (1825-1899), también conocido como 'El Rey del Vals', fue un compositor austriaco famoso por sus valses, polcas y operetas. Continuó el legado musical de su padre y elevó el vals a nuevas alturas de popularidad y sofisticación.", "El Danubio Azul", "Cuentos de los bosques de Viena", "El murciélago", "Vida de artista", "Este disco presenta 'El Danubio Azul', posiblemente el vals más famoso de todos los tiempos, un himno no oficial de Viena y una pieza fundamental del repertorio de Strauss.", "Cuentos de los bosques de Viena es otro hermoso vals que capta la esencia romántica y melódica de la música vienesa.", "El murciélago es una opereta llena de ingenio y melodías pegajosas, una de las obras más populares de Strauss en el género.", "Vida de artista es un vals encantador que celebra la vida bohemia y artística, con una melodía alegre y elegante.");

        dbArtistsHelper.insertTextWithName("Vivaldi", "Antonio Vivaldi (1678-1741) fue un compositor y violinista italiano del periodo barroco, conocido principalmente por sus conciertos, especialmente 'Las cuatro estaciones'. Su música se caracteriza por su energía rítmica y su vivacidad melódica.", "Las cuatro estaciones", "Conciertos para flauta", "Gloria", "Conciertos para mandolina", "Este disco presenta 'Las cuatro estaciones', una serie de conciertos para violín que describen cada una de las estaciones del año con vívida musicalidad y virtuosismo.", "Los Conciertos para flauta son una muestra brillante de la habilidad de Vivaldi para escribir música para solistas, llenos de frescura y elegancia.", "Gloria es una obra coral sacra que destaca por su alegría y su energía, una de las piezas más queridas del repertorio de música sacra.", "Los Conciertos para mandolina muestran la versatilidad de Vivaldi y su capacidad para componer música vivaz y técnicamente deslumbrante para distintos instrumentos.");

        dbArtistsHelper.insertTextWithName("El Virtual", "El Virtual es un artista emergente en la escena musical urbana, conocido por su estilo único que combina trap, reguetón y elementos electrónicos. Su enfoque innovador y su habilidad para crear melodías pegajosas lo han llevado a ganar rápidamente seguidores en el panorama musical.", "El Disco Que no Quería Dedicarte (EDQNQD)", "El 3 Pt.I-La Decisión", "El 3 Pt.II-El Camino", "Mente Virtual", "Ha pasado más de un año desde que comenzara con el proyecto, teniendo como objetivo relatar una parte muy intensa de su vida. Y es que el caos, a veces, es el mejor punto de partida para que activar la creatividad. El Virtual ha conseguido que ‘El disco que no quería dedicarte’ formara parte de un cambio, que signifique un morir para renacer en forma de cartas de amor, nostalgia, rabia y redención.", "El 3 es un trabajo dividido en 3 partes de 11 temas cada una. La primera parte fue lanzada el 3/3/2019.\n" +
                "El número 3 es un tema recurrente en las canciones de El Virtual, según él, porque ha tenido muchas coincidencias en su vida que se relacionan con el número 3. Incluso se ha tatuado “333” en la cara. El 3 representa el lado artistico de las personas y en algunas culturas se relaciona con el fuego.", "En El 3 Pt. II cuento un poco como me siento 3 años después de decidir dedicarme a esto [La música] en cuerpo y alma.\n" +
                "La verdad que no recuerdo mucho el proceso creativo del disco porque no fue una época en al que me encontrase muy lúcido (sobrio) que digamos…\n" +
                "Pero quedaron registradas una vez más mis aventurillas en esta shit.", "El álbum mantiene las constantes de Guille, el nombre del cantante y rapero, un hombre hecho en las calles de Nueva Málaga y Camino de San Rafael que habla abiertamente de sus problemas con las drogas y su salud mental, la búsqueda de la espiritualidad y el sentido de una vida plena en un momento como el actual, marcado por la hipervelocidad y lo coyuntural.");

        dbArtistsHelper.insertTextWithName("Mda", "Mda es un talentoso rapero y productor, conocido por sus letras profundas y su habilidad para contar historias a través de su música. Su estilo fusiona hip-hop clásico con influencias modernas, creando un sonido distintivo que ha capturado la atención de muchos.", "^^7^", "Romántico salvaj3", "Lindo M \"El Indomable\"", "Inteligencia Artificial", "Hacerse notar en la industria musical puede depender de diversos factores, pero cuando posees un sonido real y fresco, solo es cuestión de tiempo para que el mundo te reconozca. Este es el caso de Mda, uno de los referentes de la nueva ola que está por cambiar la escena musical española con su proyecto '^^7'", "Romántico Salvaje 3\" es un álbum de MDA que captura la esencia de su estilo único, combinando la sensibilidad romántica con una actitud rebelde y audaz. Este disco destaca por su habilidad para fusionar elementos del trap, el reguetón y el hip-hop, creando un sonido que es a la vez emotivo y energético. Las letras de MDA en este álbum abordan temas de amor, desamor y la lucha interna, ofreciendo una visión cruda y honesta de sus experiencias personales y su entorno.", "A lo largo del álbum, MDA aborda temas de empoderamiento, superación personal y la realidad de la vida en la calle, todo con una lírica contundente y una producción de alta calidad. Las colaboraciones en \"Lindo M: El Indomable\" destacan la versatilidad de MDA y su habilidad para crear éxitos junto a otros artistas influyentes. Este álbum consolida a MDA como una figura central en la música urbana, demostrando su capacidad para innovar y conectar profundamente con sus oyentes.", "Es un álbum que como su propio nombre indica, da pie de una manera «profética» a una nueva era musical. Como bien sabrán la Inteligencia Artificial además de que busca ser una prolongación del ser humano, es una herramienta con la cual podemos descubrir cosas que van mucho más allá de la capacidad e intelecto de los humanos, es por eso que parte de la población está aterrorizada por si dicha inteligencia nos pudiera sobrepasar y nos exterminará ya que todos sabemos que el ser humano es el mayor virus que existe dentro de la naturaleza");

        dbArtistsHelper.insertTextWithName("Diego900", "Diego900 es un artista urbano conocido por su estilo agresivo y sus letras crudas. Con una mezcla de trap y hip-hop, Diego900 ha construido una base de seguidores leales gracias a su autenticidad y su capacidad para transmitir emociones a través de su música.", "El Hotel de Las Luces", "Hypnocil", "Gas Platino", "La Espalda del Sol", "El Hotel de las Luces\" es un álbum de Diego900 que encapsula su habilidad para contar historias a través de una mezcla vibrante de trap y hip-hop. En este disco, Diego900 lleva a los oyentes a un viaje a través de su mundo, explorando temas de lucha, éxito, y la vida nocturna en la ciudad. Las letras crudas y honestas reflejan las realidades de su entorno, mientras que las producciones sonoras crean una atmósfera envolvente y cinematográfica. Con ritmos oscuros y colaboraciones destacadas, \"El Hotel de las Luces\" destaca por su autenticidad y su capacidad para conectar con una audiencia diversa. ", "En este álbum, Diego900 explora temas de lucha y triunfo, resonando con muchos de sus oyentes.", "Diego 900 nos demuestra en Gas Platino la capacidad de realizar uno de los mejores proyectos que se han hecho en el país en estos últimos años sin tener los mejores recursos. Un storytelling que dura poco más de media hora, compuesto por once temas de los cuales tan solo dos tienen productor, Miki. El resto, al parecer, son beats de Youtube.\n" +
                "En unas letras profundas y con un mensaje tan poético donde nos muestra la realidad de una persona con complejidades emocionales, hace que el oyente tenga la capacidad de conectar fácilmente y sentirse muy identificado con los desafíos de la vida cotidiana que se plantean en Gas Platino.", " La Espalda del Sol es un proyecto que conoce muy bien su mensaje, lo que hace que sea muchísimo más cercano para su público y que cada frase consiga transmitir su sentimiento como si estuviera escrita para cada una de las personas que la escuchan. Y la verdad, que esto es algo muy a valorar y que posicionan a La Espalda del Sol como uno de los lanzamientos del 2023.");

        dbArtistsHelper.insertTextWithName("Rojuu", "Rojuu es un joven artista español conocido por su estilo melancólico y su capacidad para fusionar géneros como el trap y el emo. Su música a menudo aborda temas de amor, desamor y existencialismo, conectando profundamente con una audiencia joven y diversa.", "OOO", "Roku Roku", "KOR KOR LAKE", "Los Sueños de Nube", " Rojuu ha optado por ofrecer dos versiones diferentes de sí mismo. El álbum, compuesto por once canciones tiene una fractura clara cuando han transcurrido la mitad de ellas. En la primera vemos al Rojuu de “Children Of God”, por citar uno de sus anteriores proyectos, más oscuro, más lento y cadencioso, más emo y dark… En definitiva, llevado por sus señas de identidad hasta la fecha. En la segunda mitad, la que viene desde “Molly Digital” hasta “4everyoung”, mira descaradamente a artistas ya asentados de la escena española como Sticky M.A., que es, sin duda, la inspiración principal para mucho de lo que podemos escuchar en la “cara B”", "\"Roku Roku\" es un álbum de Rojuu que destaca por su mezcla de géneros y su enfoque introspectivo. En este disco, Rojuu explora temas de juventud, desamor y la búsqueda de identidad a través de letras sinceras y emotivas. El sonido del álbum combina elementos de trap, emo y música electrónica, creando una atmósfera melancólica y envolvente. La producción es rica en texturas sonoras, con sintetizadores etéreos y ritmos contundentes que complementan la voz distintiva de Rojuu. \"Roku Roku\" es un viaje emocional que refleja la sensibilidad artística de Rojuu, consolidándolo como una de las voces más interesantes y auténticas de la escena musical contemporánea.", "“Kor Kor Lake” es un abrazo de Rojuu al sonido indie pero no se deja llevar por él. Al contrario, lo actualiza como pocos y le imprime su sentido estético y su personalidad que, al mismo tiempo, es la manifestación musical más certera de su generación. “Kor Kor Lake” es la hibridación de dos generaciones. Un juego de contrastes exitoso que también lo es de estilos: de la electrónica dosmilera al indie, pasando por algunos toques del urbano que cada vez aparece más como detalle que como base en la música de Rojuu –para bien–, todo ello unido armónicamente y con buen gusto.", "“Los Sueños de Nube” relata una historia ambientada en el Reino de Aura, donde seguimos a Nube y Viento, quienes conforman El Escuadrón Arcus. Su misión es encontrar a un misterioso ser conocido como El Cuentacuentos, cuyos relatos están sembrando la confusión en todo el reino. \n" +
                "Los dos trabajos abordan el mismo tema y avanzan cronológicamente al mismo ritmo. La intención es que puedas disfrutarlos por separado sin dificultad, y luego, si decides consumir ambos, obtendrás una visión panorámica de la obra en su conjunto.");

        dbArtistsHelper.insertTextWithName("Saramalacara", "Saramalacara es una artista destacada en la escena musical urbana, conocida por su estilo audaz y su habilidad para mezclar géneros como el reguetón y el trap. Su música a menudo aborda temas de empoderamiento y lucha personal.", "USB IDOL", "eclips3", "Heráldica", "Water", "Su estilo no es fácilmente etiquetable, puesto que escuchamos cortes más urbanos con ritmos de trap, mucha influencia de la electrónica en otros e incluso cosas del pop. En gran parte es esto lo que hace su música especial. En este EP encontramos mayoritariamente lo segundo; la electrónica se apodera de los ritmos de dos de estos tres cortes que componen “USB IDOL”.", "Con la producción en conjunto de Evar y Dayvan este trabajo se encuentra conformado por seis canciones con una sonoridad pop/electrónica acompañados con beatsbailables y estribillos pegadizos que le dan forma a esta nueva propuesta.\n" +
                "Uno de los tracks cuenta con la colaboración del artista español Rojuu, con quien la artista ha trabajado con anterioridad. En esta nueva propuesta podemos encontrarnos con profunda presencia de referencias al eurodance, clubes y catedrales, condensando diversos estados de ánimo el EP y que desarrolla una estética neogótica que le da forma al universo que está siendo transitado por la integrante de la Ripgang.", "En su LP debut, Sara logra sacar una fotografía de una época en la que la euforia, la sensación de vacío y la falta de fe se entremezclan. Lo hace rindiéndole culto a todo aquello en lo que verdaderamente cree: sus lugares de pertenencia -en el mundo físico: el barrio de Mataderos; en el virtual: la cultura digital Otaku-, sus valores, recuerdos, fantasías, y todo aquello que la constituyó como artista en sus 23 años de edad.", "Lamentablemente sólo tiene 3 trabajos publicados hasta la fecha pero aquí hay un single de sus más famosos que ayudó a catapultarla.");

        dbArtistsHelper.insertTextWithName("Sticky M.A.", "Sticky M.A. es un artista español conocido por su estilo versátil y su capacidad para mezclar trap, reguetón y elementos de la música electrónica. Su música es conocida por sus letras ingeniosas y sus ritmos contagiosos.", "Las Pegajosas Aventuras de Sticky M.A.", "5ta Dimensión", "Konbanwa", "Corazón Verde", "\"Las Pegajosas Aventuras de Sticky M.A.\" es un álbum que captura la esencia juguetona y experimental del artista. Este disco destaca por su combinación de trap, reguetón y elementos electrónicos, creando un sonido ecléctico y vibrante. Las letras son ingeniosas y llenas de referencias culturales, llevando al oyente a un viaje musical lleno de sorpresas y aventuras sonoras. Este álbum establece a Sticky M.A. como un innovador en la escena musical, siempre dispuesto a explorar nuevos territorios y desafiar las expectativas.", "En \"5ta Dimensión\", Sticky M.A. lleva a sus oyentes a una experiencia más introspectiva y psicodélica. Este álbum explora temas de espiritualidad, amor y la búsqueda de significado en un mundo caótico. Las producciones son atmosféricas y etéreas, incorporando sintetizadores y ritmos que transportan al oyente a una dimensión sonora única. \"5ta Dimensión\" muestra una faceta más profunda y reflexiva de Sticky M.A., consolidando su versatilidad como artista.", "\"Konbanwa\" es un álbum influenciado por la cultura japonesa y su estética nocturna. Con un sonido que mezcla trap, lo-fi y elementos de la música tradicional japonesa, este disco ofrece una experiencia auditiva única y envolvente. Las letras de Sticky M.A. en este álbum abordan temas de soledad, introspección y el contraste entre la vida urbana y la calma espiritual. \"Konbanwa\" es una obra que destaca por su originalidad y su capacidad para fusionar diferentes culturas y estilos musicales.", "\"Corazón Verde\" es un álbum que celebra la naturaleza y la conexión del ser humano con el entorno natural. Con una mezcla de ritmos latinos, trap y melodías relajantes, este disco es una oda a la vida al aire libre y la paz interior. Las letras de Sticky M.A. en \"Corazón Verde\" son poéticas y evocadoras, abordando temas de amor, ecología y la búsqueda de armonía. Este álbum muestra la madurez artística de Sticky M.A. y su compromiso con mensajes positivos y conscientes.");

        dbArtistsHelper.insertTextWithName("Natos y Waor",
                "Natos y Waor son un dúo de raperos españoles que han logrado un gran impacto en la escena del hip-hop. Con su estilo directo y sus letras que abordan la vida en las calles, han cultivado una base de seguidores leales y han lanzado varios álbumes exitosos.",
                "Martes 13",
                "Cicatrices",
                "Hijos de la Ruina Vol. 3",
                "Luna Llena",
                "'Martes 13' es uno de los álbumes más icónicos de Natos y Waor. Con éxitos como 'Martes 13' y 'Generación Perdida', el álbum destaca por su crudeza lírica y su producción sólida, capturando la esencia del rap urbano.",
                "'Cicatrices' explora temas de dolor y superación. Con canciones como 'Cicatrices' y 'Caminaré', el álbum ofrece una visión introspectiva de las experiencias difíciles y las lecciones aprendidas.",
                "'Hijos de la Ruina Vol. 3' es una colaboración con Recycled J que mezcla estilos y géneros. Con pistas como 'Nosotros' y 'Sudores Fríos', el álbum muestra la versatilidad y la química entre los artistas.",
                "'Luna Llena' es un álbum que aborda la dualidad de la vida nocturna y las luchas personales. Con temas como 'Luna Llena' y 'A Golpes', el disco destaca por su narrativa intensa y su producción envolvente."
        );

        dbArtistsHelper.insertTextWithName("Albums extra 1", "Una colección de discos importantes que son de artistas que no tienen demasiados discos o no demasiado relevantes.", "The Misseducation of Lauryn Hill", "The Velvet Underground (The Velvet Underground and Nico)", "Straight Outta Compton (N.W.A)", "Back in Black (AC/DC)", "Lanzado en 1998, este álbum debut en solitario de Lauryn Hill fusiona hip-hop, R&B, soul y reggae, explorando temas de amor, espiritualidad y maternidad. Con éxitos como \"Doo Wop (That Thing)\" y \"Ex-Factor\", ganó cinco premios Grammy, incluido el de Álbum del Año, y se destaca por su profundidad lírica y emocional.", "Lanzado en 1967, este álbum es un clásico del rock experimental y de vanguardia. Producido por Andy Warhol, incluye canciones icónicas como \"Sunday Morning\" y \"Heroin\". Su estilo innovador y su contenido lírico provocador influyeron profundamente en el desarrollo del rock alternativo.", "Lanzado en 1988, este álbum de N.W.A es pionero del gangsta rap, con letras explícitas sobre la vida en Compton, la brutalidad policial y la violencia de las pandillas. Canciones como \"Straight Outta Compton\" y \"F*** tha Police\" desafían la censura y presentan una narrativa cruda de la realidad urbana, influyendo en la cultura del hip-hop.", "Lanzado en 1980, \"Back in Black\" es un tributo al fallecido vocalista Bon Scott. Con Brian Johnson como nuevo vocalista, el álbum incluye éxitos como \"Hells Bells\" y \"You Shook Me All Night Long\". Es uno de los álbumes más vendidos de todos los tiempos, conocido por su sonido de hard rock potente y enérgico.");
        dbArtistsHelper.insertTextWithName("Albums extra 2", "Una colección de discos importantes que son de artistas que no tienen demasiados discos o no demasiado relevantes.", "SOS (SZA)", "Vespertine (Björk)", "Blonde (Frank Ocean)", "Back to Black (Amy Whinehouse)", "Lanzado en 2022, \"SOS\" es el segundo álbum de estudio de SZA. Fusiona R&B, hip-hop y pop, explorando temas de vulnerabilidad, empoderamiento y relaciones. Con colaboraciones de Travis Scott y Phoebe Bridgers, el álbum se destaca por su profundidad emocional y producción innovadora.", "Lanzado en 2001, \"Vespertine\" es un álbum introspectivo y etéreo de Björk, con sonidos minimalistas y arreglos electrónicos delicados. Canciones como \"Hidden Place\" y \"Pagan Poetry\" exploran temas de intimidad y amor, creando una atmósfera mágica y envolvente.", "Lanzado en 2016, \"Blonde\" es un álbum introspectivo y experimental que mezcla R&B, pop y soul. Frank Ocean explora temas de identidad, amor y pérdida con una producción minimalista y emotiva. Canciones como \"Nikes\" y \"Ivy\" destacan por su lirismo poético y su enfoque innovador.", "Lanzado en 2006, \"Back to Black\" es un álbum de soul y R&B que muestra la voz distintiva de Amy Winehouse. Con éxitos como \"Rehab\" y \"Back to Black\", el álbum aborda temas de amor, desamor y autodestrucción, ganando múltiples premios Grammy y siendo aclamado por su autenticidad y emoción cruda.");



        searchEditText = findViewById(R.id.searchEditText);


        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = searchEditText.getText().toString();
                search(query);
                return true;
            }
            return false;
        });

        initializeKeywordActivityMap();
        ArrayAdapter<String> keywordAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, keywordList);
        searchEditText.setAdapter(keywordAdapter);
        searchEditText.setOnItemClickListener((parent, view, position, id) -> {
            String keyword = (String) parent.getItemAtPosition(position);
            search(keyword);
        });
        List<String> todosArtistas = dbArtistsHelper.getAllArtists();
        List<String> todosGeneros = dbHelper.getAllGenres();

        for (String genres : todosGeneros) {
            addKeyword(genres, generosexplicados.class);
        }
        for (String artist : todosArtistas) {
            addKeyword(artist, Albums.class);
            album = dbArtistsHelper.getAlbumsByName(artist);
            addKeyword(album[0] + ", " + artist);
            addKeyword(album[1] + ", " + artist);
            addKeyword(album[2] + ", " + artist);
            addKeyword(album[3] + ", " + artist);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SpotifyTokenRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String client_id = "2b021faa29694592a10c0fef750a2c11";
            String client_secret = "1c549f508b0f4551a8abc74cd6e5d360"; //datos obtenidos de la págimna de Spotify para desarrolladores

            String requestBody = "grant_type=client_credentials";

            try {
                URL url = new URL("https://accounts.spotify.com/api/token");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes()));
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                os.write(requestBody.getBytes());
                os.flush();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    String token = jsonResponse.getString("access_token");
                    AccessTokenManager.getInstance(getApplicationContext()).saveAccessToken(token);

                    return token;
                } else {
                    Log.e("MainActivity", "Error al obtener el token de acceso. Código de respuesta: " + responseCode);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                Log.d("MainActivity", "Token de acceso: " + token);


            } else {
                Log.e("MainActivity", "No se pudo obtener el token de acceso.");
            }
        }
    }

    private void abrirActividadConAspecto(String aspecto, Class activityClass) {
        Intent intent = new Intent(MainActivity.this, activityClass);
        intent.putExtra("aspecto", aspecto);
        startActivity(intent);
    }

    void initializeKeywordActivityMap() {
        keywordActivityMap = new HashMap<>();
    }

    private void addKeyword(String keyword) {
        keywordList.add(keyword);
    }

    void addKeyword(String keyword, Class activityClass) {
        keywordActivityMap.put(keyword.toLowerCase(), activityClass);
        keywordList.add(keyword);
    }

    void search(String query) {
        if (query.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese una palabra clave", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean found = false;
        String normalizedQuery = TextUtils.normalize(query);

        for (Map.Entry<String, Class<?>> entry : keywordActivityMap.entrySet()) {
            String normalizedKey = TextUtils.normalize(entry.getKey());
            if (normalizedQuery.equals(normalizedKey)) {
                abrirActividadConAspecto(query, entry.getValue());
                found = true;
                break;
            }
        }

        if (!found) {
            // Intentar encontrar el artista por nombre de álbum
            String albumName = query.split(",")[0].trim();
            String artist = dbArtistsHelper.getArtistByAlbum(albumName);

            if (artist != null) {
                Class<?> activityClass = keywordActivityMap.get(TextUtils.normalize(artist));
                if (activityClass != null) {
                    abrirActividadConAspecto(artist, activityClass);
                    found = true;
                }
            }
        }

        if (!found) {
            // Mostrar mensaje de error si no se encontró ninguna coincidencia
            Toast.makeText(this, "No se encontró respuesta para la búsqueda: " + query, Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerColorDominante(Drawable drawable, int indice) {
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Palette.from(bitmap).generate(palette -> {
                if (palette != null) {
                    List<Palette.Swatch> swatches = palette.getSwatches();
                    if (swatches.size() > 1) {
                        List<Palette.Swatch> swatchesModificable = new ArrayList<>(swatches);
                        swatchesModificable.sort((swatch1, swatch2) -> swatch2.getPopulation() - swatch1.getPopulation());

                        int colorSegundoDominante = swatchesModificable.get(3).getRgb();
                        if (isColorDark(colorSegundoDominante)) {
                            textView[indice].setTextColor(Color.WHITE);
                            descriptionTextView[indice].setTextColor(Color.WHITE);
                        }
                        cardViews[indice].setCardBackgroundColor(colorSegundoDominante);
                    } else if (swatches.size() > 0) {
                        int colorDominante = swatches.get(0).getRgb();
                        if (isColorDark(colorDominante)) {
                            textView[indice].setTextColor(Color.WHITE);
                            descriptionTextView[indice].setTextColor(Color.WHITE);
                        }
                        cardViews[indice].setCardBackgroundColor(colorDominante);
                    }
                }
            });
        } else {
            Log.e("obtenerColorDominante", "El drawable no es un BitmapDrawable");
        }
    }

    private boolean isColorDark(int color) {
        double luminance = (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return luminance < 0.5; // Consider color dark if luminance is less than 0.5
    }

}
