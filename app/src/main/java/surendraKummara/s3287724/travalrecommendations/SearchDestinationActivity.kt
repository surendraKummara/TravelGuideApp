package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class SearchDestinationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            DestinationSearchScreen(onPlaceDetails = {})
        }
    }
}

@Composable
fun DestinationSearchScreen(
    onPlaceDetails: (placeDetails: PlaceDetails) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current as Activity

    var selectedPlaceCategory by remember { mutableStateOf("All") }

    val places = getPlaceDetails()

//    val places = if (selectedPlaceCategory == "All") {
//        getPlaceDetails()
//    } else {
//        getPlaceDetails().filter { it.category == selectedPlaceCategory }
//    }

    Column(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.app_bar))
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable {
                        context.finish()
                    }
                    .padding(start = 4.dp)

            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.travel_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable { }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Places to visit",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )


        }

        Spacer(modifier = Modifier.height(12.dp))

//        PlaceCategoryDropDown(
//            placecategories = listOf(
//                "All",
//                "Bridges",
//                "Geologic Formations",
//                "Arenas & Stadiums",
//                "Historic Sites",
//                "Farms",
//                "Churches & Cathedrals",
//                "Hiking Trails",
//                "Art Museums",
//                "Farmers Markets",
//                "Religious Sites",
//                "Theatres",
//                "Nature & Wildlife Areas",
//                "Shopping Malls",
//                "Parks",
//                "Game & Entertainment Centres",
//                "Monuments & Statues",
//                "Golf Courses",
//                "Bars & Clubs",
//            ),
//            selPlace = selectedPlaceCategory,
//            onPlaceSelected = { selectedPlaceCategory = it }
//        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp)
        ) {
            items(places.size) { index ->
                PlaceCard(places[index], onPlaceDetails)
            }
        }
    }
}

@Composable
fun PlaceCard(
    placeDetails: PlaceDetails,
    onPlaceDetails: (placeDetails: PlaceDetails) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onPlaceDetails.invoke(placeDetails) },
        elevation = CardDefaults.cardElevation(4.dp),
    )
    {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = painterResource(id = placeDetails.placeImage),

                contentDescription = "Place Image",
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = placeDetails.placeName,

                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 12.dp)

            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = placeDetails.placeLocation
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = placeDetails.placeLocation,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier

                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    modifier = Modifier
                        .size(16.dp),
                    painter = painterResource(id = R.drawable.baseline_access_time_24),
                    contentDescription = "Opening Hours Icon"
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = placeDetails.openingHours,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier

                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row {


                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                )

                Text(
                    text = "View More Details",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )
                Spacer(modifier = Modifier.width(6.dp))

            }

            Spacer(modifier = Modifier.height(6.dp))


        }
    }
}













@Preview(showBackground = true)
@Composable
fun SearchDestinationPreview() {
//    PlaceCard()
}