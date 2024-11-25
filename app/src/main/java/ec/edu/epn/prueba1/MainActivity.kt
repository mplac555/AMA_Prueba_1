package ec.edu.epn.prueba1

import android.graphics.drawable.Drawable
import android.os.Bundle
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