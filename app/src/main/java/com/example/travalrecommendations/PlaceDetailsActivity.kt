package com.example.travalrecommendations

import android.R.attr.contentDescription
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class PlaceDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewPlaceDetails(ResultData.placesDetails)
        }
    }
}

@Composable
fun ViewPlaceDetails(placeDetails: PlaceDetails)
{
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.SkyBlue))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                        context.finish()
                    }
            )
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Place Details",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = painterResource(id =placeDetails.placeImage),
            contentDescription = "Image",
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row {
            Text(
                text = placeDetails.placeName,
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 12.dp, top = 6.dp)

            )
            Spacer(modifier = Modifier.width(18.dp))
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable()
                    {
                        context.startActivity(Intent(context, PlaceLocationActivity::class.java))
                    },
                painter = painterResource(id = R.drawable.location_pin),
                contentDescription = "Image",
            )
        }


        Text(
            text = placeDetails.placeLocation,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp, top = 12.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = placeDetails.placeDescription,
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {

            Text(
                modifier = Modifier
                    .clickable {
//                        if(ResultData.hostels.isNotEmpty()) {
//                            context.startActivity(Intent(context, ViewHostelsActivity::class.java))
//                        }
//                        else {
//                            Toast.makeText(context, "No Hostels Found", Toast.LENGTH_SHORT)
//                                .show()
//                        }
                    }
                    .width(180.dp)
                    .padding(start = 12.dp)
                    .background(
                        color = colorResource(id = R.color.bt_color),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.bt_color),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = "View Hostels",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                )
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier
                    .clickable {


                    }
                    .width(180.dp)
                    .padding(end = 12.dp)
                    .background(
                        color = colorResource(id = R.color.bt_color),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.bt_color),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 8.dp)
                    .weight(1f),
                text = "View Hotels",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                )
            )

        }





    }

}



@Preview(showBackground = true)
@Composable
fun ViewPlaceDetailsPreview() {
//    ViewPlaceDetails()
}