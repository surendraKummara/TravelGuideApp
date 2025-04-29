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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng

class SearchResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewResults()
        }
    }
}

@Composable
fun ViewResults() {
    val context = LocalContext.current as Activity
    Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.app_bar))
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
                text = "Search Results",
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                items(ResultData.fliterDetails.size) { index ->
                    ViewSearchResultCard(ResultData.fliterDetails[index])
                }
            }


        }
    }

}


@Composable
fun ViewSearchResultCard(placeDetails: PlaceDetails) {
    val context = LocalContext.current as Activity
    Card(
        modifier = Modifier
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    )
    {
        Column(
            modifier = Modifier
                .clickable()
                {
                    ResultData.placesDetails = placeDetails
                    ResultData.placeDetailsOpenedFrom=1
                    PlanSummary.planDetails.placeDetails=placeDetails

                    context.startActivity(Intent(context, PlaceDetailsActivity::class.java))
                }
        ) {
            Image(
                painter = painterResource(id = placeDetails.placeImage),
                contentDescription = "Back",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Text(
                text = placeDetails.placeName,
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),


                )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ViewSearchResultPreview() {
    ViewResults()
}

object ResultData {
    lateinit var fliterDetails: List<PlaceDetails>
    lateinit var placesDetails: PlaceDetails

    var placeDetailsOpenedFrom = 1

    lateinit var hostels: List<HostelData>
    lateinit var hotels: List<HotelData>
    var accomodationType: Int = 1

    var isHotelsFetched = false
    var isHostelsFetched = false

    var placeName = ""
    var placeCoordinates = LatLng(0.0, 0.0)
}