package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.google.android.gms.maps.model.LatLng

class PlaceDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewPlaceDetails(ResultData.placesDetails)
        }
    }
}

@Composable
fun ViewPlaceDetails(placeDetails: PlaceDetails) {
    val context = LocalContext.current as Activity

    val dbHelper = remember { PlanDatabaseHelper(context) }

    Column(
        modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())
    ) {
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
                text = "Place Details",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = painterResource(id = placeDetails.placeImage),
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
                            ResultData.placeName = placeDetails.placeName
                            ResultData.placeCoordinates =
                                LatLng(placeDetails.latitude, placeDetails.longitude)
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
                            if (ResultData.isHostelsFetched) {
                                if (ResultData.hostels.isNotEmpty()) {
                                    ResultData.accomodationType = 2
                                    context.startActivity(
                                        Intent(
                                            context,
                                            ViewHostelsActivity::class.java
                                        )
                                    )
                                } else {
                                    Toast.makeText(context, "No Hostels Found", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast.makeText(context, "No Hostels Found", Toast.LENGTH_SHORT)
                                    .show()
                            }
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

                            if (ResultData.isHotelsFetched) {
                                if (ResultData.hotels.isNotEmpty()) {
                                    ResultData.accomodationType = 1
                                    context.startActivity(
                                        Intent(
                                            context,
                                            ViewHostelsActivity::class.java
                                        )
                                    )
                                } else {
                                    Toast.makeText(context, "No Hotels Found", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast.makeText(context, "No Hotels Found", Toast.LENGTH_SHORT)
                                    .show()
                            }

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

            if (ResultData.placeDetailsOpenedFrom == 1) {
                SearchSummaryCard(onSaveClick = {

                    val plan = PlanSummary.planDetails
                    val success = dbHelper.insertPlan(plan)

                    if (success) {
                        Toast.makeText(context, "Plan Saved Successfully!", Toast.LENGTH_SHORT)
                            .show()
                        context.finish()
                    } else {
                        Toast.makeText(context, "Failed to Save Plan", Toast.LENGTH_SHORT).show()
                    }

                })
            }


        }
    }

}

@Composable
fun SearchSummaryCard(onSaveClick: (SavePlan) -> Unit) {
    val plan = PlanSummary.planDetails

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "Search Summary",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(16.dp))

            SummaryItem(label = "Trip Category", value = plan.category)
            SummaryItem(label = "Destination", value = plan.destination)
            SummaryItem(label = "Trip Start Date", value = plan.startDate)
            SummaryItem(label = "Trip End Date", value = plan.endDate)
            SummaryItem(label = "Accommodation Type", value = plan.accomodationType)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Like this plan?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF555555)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onSaveClick(plan) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text(text = "Save Plan", color = Color.White)
            }
        }
    }
}

@Composable
fun SummaryItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ViewPlaceDetailsPreview() {
//    ViewPlaceDetails()
}