package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

class ViewHostelsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewHostels()
        }
    }
}


@Composable
fun ViewHostels() {
    val context = LocalContext.current as Activity
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.app_bar))
                .padding(horizontal = 12.dp)
                .padding(WindowInsets.systemBars.asPaddingValues()),
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
                text = "Hostel Details",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        )
        {
            if (ResultData.accomodationType == 1) {

                if (ResultData.hotels.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    ) {
                        items(ResultData.hotels.size) { index ->
                            ViewHotelCard(ResultData.hotels[index])
                        }
                    }
                } else {
                    Text(text = "No Hotels Found")
                }
            } else {
                if (ResultData.hostels.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    ) {
                        items(ResultData.hostels.size) { index ->
                            ViewHostelsCard(ResultData.hostels[index])
                        }
                    }
                } else {
                    Text(text = "No Hotels Found")
                }
            }

        }
    }

}

@Composable
fun ViewHostelsCard(hostels: HostelData) {

    val context = LocalContext.current as Activity

    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    )
    {
        Column(
            modifier = Modifier

        ) {
            Text(
                text = hostels.hostelName,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp, top = 6.dp)

            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hostels.hostelName,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable()
                        {

                            ResultData.placeName = hostels.hostelName
                            ResultData.placeCoordinates =
                                LatLng(hostels.latitude, hostels.longitude)

                            context.startActivity(
                                Intent(
                                    context,
                                    PlaceLocationActivity::class.java
                                )
                            )
                        },
                    painter = painterResource(id = R.drawable.location_pin),
                    contentDescription = "Image",
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hostels.hostelDescription,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp)
            )

        }
    }
}

@Composable
fun ViewHotelCard(hostels: HotelData) {

    val context = LocalContext.current as Activity

    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = hostels.hotelName,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp, top = 6.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hostels.hotelLocation,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 12.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .clickable()
                        {

                            ResultData.placeName = hostels.hotelName
                            ResultData.placeCoordinates =
                                LatLng(hostels.latitude, hostels.longitude)

                            context.startActivity(
                                Intent(
                                    context,
                                    PlaceLocationActivity::class.java
                                )
                            )
                        },
                    painter = painterResource(id = R.drawable.location_pin),
                    contentDescription = "Image",
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = hostels.hotelDescription,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp)
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ViewHostelsPreview() {
//    ViewHostelsCard()
}