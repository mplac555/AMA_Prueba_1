package ec.edu.epn.prueba1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ec.edu.epn.prueba1.ui.theme.Prueba1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val eventos = mutableListOf<Evento>()
        val cursor = DatabaseHelper(this).getAllPuntosInteres()
        cursor?.use {
            while (it.moveToNext()) {
                val idIndex = it.getColumnIndex(DatabaseHelper.COLUMN_ID)
                val nombreIndex = it.getColumnIndex(DatabaseHelper.COLUMN_NAME)
                val lugarIndex = it.getColumnIndex(DatabaseHelper.COLUMN_PLACE)
                val fechaIndex = it.getColumnIndex(DatabaseHelper.COLUMN_DATE)
                val asistentesIndex = it.getColumnIndex(DatabaseHelper.COLUMN_ASSISTANTS)
                if(
                    idIndex != -1 &&
                    nombreIndex != -1 &&
                    lugarIndex != -1 &&
                    fechaIndex != -1 &&
                    asistentesIndex != -1
                ) {
                    val _id = it.getInt(idIndex)
                    val nombre = it.getString(nombreIndex)
                    val lugar = it.getString(lugarIndex)
                    val fecha = it.getString(fechaIndex)
                    val asistentes = it.getInt(asistentesIndex)
                    eventos.add(Evento(_id, nombre, lugar, fecha, asistentes))
                } else {
                    Log.e("DatabaseError", "Una o más columnas no fueron encontradas en el cursor.")
                }
            }
        }

        setContent { MainContent() }
    }
}

@Composable
fun MainContent() {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(Modifier.background(Color.Gray).padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Eventos en \"San José\" por el feriado de noviembre", fontWeight = FontWeight.Black, fontSize = 15.sp)
                Spacer(Modifier.height(15.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(5) {
                        Tarjeta(
                            modifier = Modifier,
                            nombre = "Trail en el Mirador de la Perdiz",
                            lugar = "Mirador de la Perdiz",
                            fecha = "31/10/2024",
                            asistentes = 30,
                            icono = painterResource(R.drawable.ico_montana)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Tarjeta(modifier: Modifier = Modifier, nombre: String = "", lugar: String = "", fecha: String = "", asistentes: Int = 0, icono: Painter = painterResource(R.drawable.ic_launcher_foreground)) {
    Box(modifier.background(Color.LightGray, RoundedCornerShape(15.dp)).border(0.dp, Color.Transparent, RoundedCornerShape(15.dp)).padding(15.dp)) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    Modifier
                        .height(75.dp)
                        .aspectRatio(1f)
                        .padding(10.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color.Black, CircleShape)
                ) {
                    Icon(icono, "icono del evento",Modifier.align(Alignment.Center).fillMaxSize(.75f))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = nombre, fontWeight = FontWeight.Black)
                    Spacer(Modifier.height(15.dp))
                    Text(lugar, fontWeight = FontWeight.Light, fontStyle = FontStyle.Italic)
                    Text(fecha)
                }
            }
            Spacer(Modifier.height(15.dp))
            Text("$asistentes asistentes", Modifier.padding(start = 5.dp))
        }
    }
}

class Evento(
    val _id : Int,
    var nombre : String,
    var lugar : String,
    var fecha : String,
    var asistentes : Int,
    var imagen_id : Int = R.drawable.ic_launcher_foreground
)

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "EventosTuristicos.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "eventos_noviembre"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "nombre"
        const val COLUMN_PLACE = "lugar"
        const val COLUMN_DATE = "fecha"
        const val COLUMN_ASSISTANTS = "asistentes"
//        const val COLUMN_IMAGE = "imagen"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_PLACE TEXT NOT NULL,
            $COLUMN_DATE TEXT NOT NULL,
            $COLUMN_ASSISTANTS INTEGER NOT NULL
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertPuntoInteres(
        nombre: String,
        lugar: String,
        fecha: String,
        asistentes: Int,
//        imagen: Painter
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nombre)
            put(COLUMN_PLACE, lugar)
            put(COLUMN_DATE, fecha)
            put(COLUMN_ASSISTANTS, asistentes)
//            put(COLUMN_IMAGE, imagen)
        }
        return db.insert(TABLE_NAME, null, values)
    }
    fun getAllPuntosInteres(): Cursor {
        val db = this.readableDatabase
        val projection = arrayOf(
            COLUMN_NAME,
            COLUMN_PLACE,
            COLUMN_DATE,
            COLUMN_ASSISTANTS
//            COLUMN_IMAGE
        )
        return db.query(TABLE_NAME, null, null, null, null, null, null)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainPreview() {
    MainContent()
//    Tarjeta(
//        modifier = Modifier,
//        nombre = "Trail en el Mirador de la Perdiz",
//        lugar = "Mirador de la Perdiz",
//        fecha = "31/10/2024",
//        asistentes = 30,
//        icono = painterResource(R.drawable.ico_montana)
//    )
}