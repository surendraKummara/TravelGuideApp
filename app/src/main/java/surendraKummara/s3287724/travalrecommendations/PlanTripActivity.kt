package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

class PlanTripActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanTripPage()
        }
    }
}

@Composable
fun PlanTripPage() {
    var selectedCategory by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var accommodationType by remember { mutableStateOf("") }

    val categories = listOf("Adventure & Nature", "Cultural & Historical Spots")
    val destinations = listOf("Middlesbrough", "Redcar", "Northallerton", "Hartlepool")
    val accommodations = listOf("All", "Hotels", "Hostels")

    val calendar = Calendar.getInstance()
    var dateState by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }

    var selYear by remember { mutableIntStateOf(0) }
    var selMonth by remember { mutableIntStateOf(0) }
    var selDOM by remember { mutableIntStateOf(0) }

    var showResults by remember { mutableStateOf(false) }

    val context = LocalContext.current as Activity

    val dateStartPickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selYear = year
            selMonth = month
            selDOM = dayOfMonth
            dateState = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val dateEndPickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selYear = year
            selMonth = month
            selDOM = dayOfMonth
            dateEnd = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val filteredPlaces = remember(selectedCategory, destination) {
        getPlaceDetails().filter {
            it.category == selectedCategory &&
                    it.placeLocation.contains(destination, ignoreCase = true)
        }
    }

    val filteredHotels = remember(destination) {
        getHotelDetails().filter {
            it.hotelLocation.contains(destination, ignoreCase = true)
        }
    }

    val filteredHostels = remember(destination) {
        getHostelDetails().filter {
            it.hostelLocation.contains(destination, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues())) {

        ResultData.isHotelsFetched = false
        ResultData.isHostelsFetched = false

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
                text = "Plan Your Trip",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(modifier = Modifier.padding(horizontal = 6.dp)) {
            Spacer(modifier = Modifier.height(16.dp))

            DropdownSelector(
                label = "Select Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            DropdownSelector(
                label = "Enter Destination",
                options = destinations,
                selectedOption = destination,
                onOptionSelected = { destination = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(50.dp)
                    .clickable { dateStartPickerDialog.show() }
                    .background(Color.LightGray, MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = dateState.ifEmpty { "Start Date" },
                    color = if (dateState.isEmpty()) Color.Gray else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(50.dp)
                    .clickable { dateEndPickerDialog.show() }
                    .background(Color.LightGray, MaterialTheme.shapes.medium)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = dateEnd.ifEmpty { "End Date" },
                    color = if (dateEnd.isEmpty()) Color.Gray else Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            DropdownSelector(
                label = "Accommodation Type",
                options = accommodations,
                selectedOption = accommodationType,
                onOptionSelected = { accommodationType = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    when {
                        selectedCategory.isEmpty() ->
                            Toast.makeText(context, "Select Category", Toast.LENGTH_SHORT).show()

                        destination.isEmpty()
                            -> Toast.makeText(context, "Select Destination ", Toast.LENGTH_SHORT)
                            .show()

                        dateState.isEmpty() ->
                            Toast.makeText(context, "Select Start Date", Toast.LENGTH_SHORT).show()

                        dateEnd.isEmpty() ->
                            Toast.makeText(context, "Select End Date", Toast.LENGTH_SHORT).show()

                        accommodationType.isEmpty() ->
                            Toast.makeText(context, "Select Accommodation Type", Toast.LENGTH_SHORT)
                                .show()

                        else -> {
                            showResults = true

                            if (filteredPlaces.isNotEmpty()) {

                                var savePlan = SavePlan(
                                    selectedCategory,
                                    destination,
                                    dateState,
                                    dateEnd,
                                    accommodationType
                                )

                                PlanSummary.planDetails = savePlan

                                context.startActivity(
                                    Intent(
                                        context,
                                        SearchResultActivity::class.java
                                    )
                                )
                            } else {
                                Toast.makeText(context, "No Places Found", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    }

                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
                    .height(42.dp) // increase height
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.bt_color) // Set your desired color here
                )
            ) {
                Text("Search", color = Color.White) // Optional: change text color for contrast
            }

            if (showResults) {
                Spacer(modifier = Modifier.height(24.dp))

                if (filteredPlaces.isNotEmpty()) {
                    ResultData.fliterDetails = filteredPlaces

//                    Text("Places to Explore:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//                    filteredPlaces.forEach { place ->
//                        Text(place.placeName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//                        Text(place.placeDescription, fontSize = 12.sp)
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }

                } else {
                    Text(
                        "No places found for selected filters.", modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        )
                    )
                }

                when (accommodationType) {
                    "All" -> {

                        if (filteredHotels.isNotEmpty()) {
                            ResultData.isHotelsFetched = true
                            ResultData.hotels = filteredHotels
                        } else {
                            ResultData.isHotelsFetched = false
                        }

                        if (filteredHostels.isNotEmpty()) {
                            ResultData.isHostelsFetched = true
                            ResultData.hostels = filteredHostels
                        } else {
                            ResultData.isHostelsFetched = false
                        }
                    }

                    "Hotels" -> {
                        if (filteredHotels.isNotEmpty()) {
                            ResultData.isHotelsFetched = true
                            ResultData.hotels = filteredHotels

//                            Text(
//                                "Recommended Hotels:",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp
//                            )
//                            filteredHotels.forEach { hotel ->
//                                Text(
//                                    hotel.hotelName,
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                                Text(hotel.hotelDescription, fontSize = 12.sp)
//                                Spacer(modifier = Modifier.height(8.dp))
//                            }
                        } else {
                            ResultData.isHotelsFetched = false

//                            Text(
//                                "No hotels found in selected destination.",
//                                modifier = Modifier.align(
//                                    Alignment.CenterHorizontally
//                                )
//                            )
                        }
                    }

                    "Hostels" -> {
                        if (filteredHostels.isNotEmpty()) {
                            ResultData.isHostelsFetched = true
                            ResultData.hostels = filteredHostels

//                            Text(
//                                "Recommended Hostels:",
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 18.sp
//                            )
//                            filteredHostels.forEach { hostel ->
//                                Text(
//                                    hostel.hostelName,
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.SemiBold
//                                )
//                                Text(hostel.hostelDescription, fontSize = 12.sp)
//                                Spacer(modifier = Modifier.height(8.dp))
//                            }
                        } else {
                            ResultData.isHostelsFetched = false

//                            Text(
//                                "No hostels found in selected destination.",
//                                modifier = Modifier.align(
//                                    Alignment.CenterHorizontally
//                                )
//                            )
                        }
                    }
                }
            }
        }
    }
}

object PlanSummary {
    var planDetails: SavePlan = SavePlan()
}

data class SavePlan(
    var category: String = "",
    var destination: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var accomodationType: String = "",
    var placeDetails: PlaceDetails = PlaceDetails(),
    var planId : Int = 0
)

data class PlaceDetails(
    val placeName: String="",
    val category: String = "",
    val placeImage: Int=0,
    val placeLocation: String="",
    val openingHours: String="",
    val attractions: String="",
    val activities: String="",
    val contactNumberOne: String="",
    val contactNumberTwo: String="",
    val placeDescription: String="",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0

)


@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun getPlaceDetails(): List<PlaceDetails> {
    return listOf(
        PlaceDetails(
            placeName = "Stonehenge",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.stonehenge,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tBuilt: Around 3000 BC to 2000 BC\n" +
                    "•\tBuilders: Likely Neolithic peoples of Britain, though the exact identity is unknown.\n" +
                    "•\tPurpose: Still debated. It may have served as:\n" +
                    "o\tA ceremonial or religious site\n" +
                    "o\tA burial ground\n" +
                    "o\tA solar calendar aligned with solstices\n"
        ),
        PlaceDetails(
            placeName = "Edinburgh Castle",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.edinburgh_castle,
            placeLocation = "North Yorkshire, England",
            placeDescription = "•\tOrigins: Human settlement dates back to at least the Iron Age (around 2nd century AD).\n" +
                    "•\tThe castle has been a royal residence, military stronghold, prison, and now a symbol of Scottish heritage.\n" +
                    "•\tInvolved in many historic events, especially during the Wars of Scottish Independence and Jacobite rebellions.\n"
        ),
        PlaceDetails(
            placeName = "Roman Baths",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.roman_baths,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tBuilt by the Romans around 70 AD over natural hot springs.\n" +
                    "•\tUsed as a public bathing and socialising complex by Romans for centuries.\n" +
                    "•\tThe site includes the remains of the Temple of Sulis Minerva, sacred to both Celtic and Roman worshippers.\n" +
                    "•\tRediscovered in the 18th century, with many original features still intact.\n"
        ),
        PlaceDetails(
            placeName = "Tower of London",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.tower_of_london,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tFounded: In 1066 AD by William the Conqueror after the Norman invasion.\n" +
                    "•\tOriginally a fortress, it evolved into a royal palace, armoury, treasury, zoo, and infamous prison.\n" +
                    "•\tIt played a central role in English history, especially during the Tudor period.\n"
        ),
        PlaceDetails(
            placeName = "Hadrian’s Wall",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.hadrian_wall,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tBuilt: Starting in 122 AD during the reign of Roman Emperor Hadrian.\n" +
                    "•\tPurpose: To mark the northern boundary of the Roman Empire and control movement between Roman Britannia and the “barbarian” north.\n" +
                    "•\tManned by around 15,000 Roman soldiers, with forts, milecastles, and watchtowers along the length.\n"
        ),
        PlaceDetails(
            placeName = "Canterbury Cathedral",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.canterbury_cathedral,
            placeLocation = "Sydney, Australia",
            placeDescription = "•\tFounded in 597 AD by St. Augustine, sent by Pope Gregory the Great to convert the Anglo-Saxons.\n" +
                    "•\tRebuilt and expanded multiple times, especially after the Norman Conquest (1066).\n" +
                    "•\tBecame a major pilgrimage site after the 1170 murder of Archbishop Thomas Becket, an event that shocked medieval Europe.\n" +
                    "•\tOne of the oldest and most famous Christian structures in England.\n" +
                    "•\tA UNESCO World Heritage Site since 1988.\n"
        ),
        PlaceDetails(
            placeName = "Warwick Castle",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.warwick_castle,
            placeLocation = "North York Moors, England",
            placeDescription = "•\tBuilt in 1068 by William the Conqueror as a motte-and-bailey fortress.\n" +
                    "•\tEvolved over the centuries into a stone stronghold, Tudor residence, and Victorian country house.\n" +
                    "•\tPlayed a role in major events like the Wars of the Roses and the English Civil War.\n" +
                    "•\tOnce home to the powerful Earls of Warwick, known as \"The Kingmakers.\"\n"
        ),
        PlaceDetails(
            placeName = "Stratford-upon-Avon",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.stratford_upon_avon,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tBirthplace and final resting place of William Shakespeare (1564–1616), England’s greatest playwright.\n" +
                    "•\tA town that flourished in the Elizabethan era and has preserved much of its Tudor charm.\n" +
                    "•\tAttracts millions of literary pilgrims and history lovers each year.\n"
        ),
        PlaceDetails(
            placeName = "York Minster",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.york_minster,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tOne of the largest Gothic cathedrals in Northern Europe.\n" +
                    "•\tA center of Christianity in the North of England since the 7th century.\n" +
                    "•\tCurrent structure began construction in 1220 AD and was completed in 1472.\n" +
                    "•\tServes as the seat of the Archbishop of York, the second-highest office in the Church of England (after Canterbury).\n"
        ),
        PlaceDetails(
            placeName = "Stirling Castle",
            category = "Cultural & Historical Spots",
            placeImage = R.drawable.stirling_castle,
            placeLocation = "Middlesbrough, England",
            placeDescription = "•\tOne of Scotland’s most important royal castles.\n" +
                    "•\tStrategically placed between the Highlands and Lowlands — whoever controlled Stirling controlled Scotland.\n" +
                    "•\tScene of countless battles and royal dramas, including:\n" +
                    "o\tWars of Scottish Independence\n" +
                    "o\tThe nearby Battle of Stirling Bridge (1297) – victory for William Wallace.\n" +
                    "o\tHome and coronation site of Mary, Queen of Scots.\n"
        ),


        PlaceDetails(
            placeName = "Lake District",
            category = "Adventure & Nature",
            placeImage = R.drawable.lake_district,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Known for its stunning glacial lakes and rugged fells, the Lake District is a haven for hikers, climbers, and water sports enthusiasts. Activities include wild swimming in Devoke Water and scrambling routes like Sharp Edge on Blencathra."
        ),
        PlaceDetails(
            placeName = "Snowdonia National Park",
            category = "Adventure & Nature",
            placeImage = R.drawable.snowdonia_national_park,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Home to Mount Snowdon, the highest peak in Wales, Snowdonia offers opportunities for hiking, climbing, and exploring picturesque villages."
        ),
        PlaceDetails(
            placeName = "Isle of Skye",
            category = "Adventure & Nature",
            placeImage = R.drawable.isle_skye,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Famous for its dramatic landscapes, including the Quiraing landslip and the Old Man of Storr, the Isle of Skye is ideal for hiking and photography."
        ),
        PlaceDetails(
            placeName = "Peak District",
            category = "Adventure & Nature",
            placeImage = R.drawable.peak_district,
            placeLocation = "Middlesbrough, England",
            placeDescription = "As the UK's first national park, the Peak District offers diverse landscapes for hiking, cycling, and caving. Notable spots include Kinder Scout and the Pennine Way."
        ),
        PlaceDetails(
            placeName = "Ben Nevis",
            category = "Adventure & Nature",
            placeImage = R.drawable.ben_nevis,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Standing as the highest mountain in the UK, Ben Nevis attracts climbers and hikers seeking challenging ascents and panoramic views."
        ),
        PlaceDetails(
            placeName = "Dartmoor National Park",
            category = "Adventure & Nature",
            placeImage = R.drawable.dartmoor_national_park,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Known for its rugged moorlands and granite tors, Dartmoor offers opportunities for hiking, horseback riding, and exploring ancient ruins."
        ),
        PlaceDetails(
            placeName = "Cairngorms National Park",
            category = "Adventure & Nature",
            placeImage = R.drawable.cairngorms_national_park,
            placeLocation = "Middlesbrough, England",
            placeDescription = "As the largest national park in the UK, the Cairngorms boast a range of outdoor activities, including hiking, mountain biking, and wildlife watching."
        ),
        PlaceDetails(
            placeName = "Cornwall",
            category = "Adventure & Nature",
            placeImage = R.drawable.cornwall,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Cornwall's rugged coastline is perfect for surfing, cloistering, and exploring picturesque beaches and fishing villages."
        ),
        PlaceDetails(
            placeName = "Cheddar Gorge",
            category = "Adventure & Nature",
            placeImage = R.drawable.cheddar_gorge,
            placeLocation = "Middlesbrough, England",
            placeDescription = "This limestone gorge offers rock climbing, hiking trails, and cave exploration opportunities. "
        ),
        PlaceDetails(
            placeName = "Dungeness",
            category = "Adventure & Nature",
            placeImage = R.drawable.dungeness,
            placeLocation = "Middlesbrough, England",
            placeDescription = "Known for its unique, desolate landscape, Dungeness is a haven for birdwatchers and those seeking solitude amidst its shingle beach and peculiar architecture."
        ),

        )
}


fun getHostelDetails(): List<HostelData> {
    return listOf(
        // Middlesbrough
        HostelData(
            hostelId = "1",
            hostelName = "Southfield Lodge",
            hostelDescription = "Budget-friendly lodge with shared kitchen facilities and convenient location.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5742,
            longitude = -1.2340
        ),
        HostelData(
            hostelId = "2",
            hostelName = "Sporting Lodge Inn Middlesbrough",
            hostelDescription = "Hotel offering comfortable rooms, leisure facilities, and free parking.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5290,
            longitude = -1.2486
        ),
        HostelData(
            hostelId = "3",
            hostelName = "Le Crescent Rooms",
            hostelDescription = "Capsule-style accommodations with modern amenities and shared kitchen.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5723,
            longitude = -1.2356
        ),
        HostelData(
            hostelId = "4",
            hostelName = "James House by Blue Skies Stays",
            hostelDescription = "Private rooms with shared facilities, suitable for short stays.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5718,
            longitude = -1.2321
        ),
        HostelData(
            hostelId = "5",
            hostelName = "Hoskins House By Horizon Stays",
            hostelDescription = "Clean and modern guesthouse with easy access to city center.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5736,
            longitude = -1.2312
        ),
        HostelData(
            hostelId = "6",
            hostelName = "Entire Modern Home Middlesbrough",
            hostelDescription = "Spacious home suitable for groups, featuring modern amenities.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5730,
            longitude = -1.2385
        ),
        HostelData(
            hostelId = "7",
            hostelName = "99 Abingdon House",
            hostelDescription = "Comfortable accommodations with shared facilities and free Wi-Fi.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5745,
            longitude = -1.2368
        ),
        HostelData(
            hostelId = "8",
            hostelName = "Two Bedroom Entire Apartment in Roman Road",
            hostelDescription = "Fully furnished apartment with free parking and convenient location.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5732,
            longitude = -1.2411
        ),
        HostelData(
            hostelId = "9",
            hostelName = "Holiday Inn Express Middlesbrough - Centre Square",
            hostelDescription = "Modern hotel offering complimentary breakfast and central location.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5761,
            longitude = -1.2348
        ),
        HostelData(
            hostelId = "10",
            hostelName = "Leonardo Hotel Middlesbrough",
            hostelDescription = "Contemporary hotel with comfortable rooms and on-site dining.",
            hostelLocation = "Middlesbrough",
            latitude = 54.5719,
            longitude = -1.2298
        ),

        // Redcar
        HostelData(
            hostelId = "11",
            hostelName = "Armada Guesthouse",
            hostelDescription = "Friendly B&B close to local amenities and minutes from seafront.",
            hostelLocation = "Redcar",
            latitude = 54.6174,
            longitude = -1.0602
        ),
        HostelData(
            hostelId = "12",
            hostelName = "The Beacon Hotel",
            hostelDescription = "Well-rated hotel offering cozy rooms and easy access to local attractions.",
            hostelLocation = "Redcar",
            latitude = 54.6150,
            longitude = -1.0620
        ),
        HostelData(
            hostelId = "13",
            hostelName = "Claxton Hotel",
            hostelDescription = "Comfortable hotel with modern amenities and central location.",
            hostelLocation = "Redcar",
            latitude = 54.6157,
            longitude = -1.0611
        ),
        HostelData(
            hostelId = "14",
            hostelName = "Park Hotel",
            hostelDescription = "Hotel offering sea views, comfortable accommodations, and dining options.",
            hostelLocation = "Redcar",
            latitude = 54.6143,
            longitude = -1.0627
        ),
        HostelData(
            hostelId = "15",
            hostelName = "The Stables",
            hostelDescription = "Charming guesthouse with comfortable rooms and a welcoming atmosphere.",
            hostelLocation = "Redcar",
            latitude = 54.6101,
            longitude = -1.0700
        ),
        HostelData(
            hostelId = "16",
            hostelName = "Highview House Redcar-Seaview Apartments",
            hostelDescription = "Apartments offering sea views and modern amenities.",
            hostelLocation = "Redcar",
            latitude = 54.6165,
            longitude = -1.0588
        ),
        HostelData(
            hostelId = "17",
            hostelName = "Redcar Seaview Apartments",
            hostelDescription = "Self-catering apartments with sea views and convenient location.",
            hostelLocation = "Redcar",
            latitude = 54.6168,
            longitude = -1.0593
        ),
        HostelData(
            hostelId = "18",
            hostelName = "Host & Stay - Bellevue Apartments",
            hostelDescription = "Modern apartments with stylish interiors and close to the beach.",
            hostelLocation = "Redcar",
            latitude = 54.6179,
            longitude = -1.0581
        ),
        HostelData(
            hostelId = "19",
            hostelName = "The Cleveland Apartment",
            hostelDescription = "Comfortable apartment with modern amenities and central location.",
            hostelLocation = "Redcar",
            latitude = 54.6161,
            longitude = -1.0607
        ),
        HostelData(
            hostelId = "20",
            hostelName = "Edwardian House",
            hostelDescription = "Elegant Edwardian guesthouse with period furnishings and warm hospitality.",
            hostelLocation = "Redcar",
            latitude = 54.6153,
            longitude = -1.0614
        ),

        // Northallerton
        HostelData(
            hostelId = "21",
            hostelName = "Cote Ghyll Mill",
            hostelDescription = "Converted mill offering hostel-style accommodations with self-catering facilities.",
            hostelLocation = "Northallerton",
            latitude = 54.3725,
            longitude = -1.3158
        ),
        HostelData(
            hostelId = "22",
            hostelName = "The Village Inn",
            hostelDescription = "Inn providing bright rooms, free Wi-Fi, and an on-site restaurant and pub.",
            hostelLocation = "Northallerton",
            latitude = 54.3689,
            longitude = -1.4347
        ),
        HostelData(
            hostelId = "23",
            hostelName = "The Northallerton Inn",
            hostelDescription = "Modern inn offering comfortable rooms, a restaurant, and free parking.",
            hostelLocation = "Northallerton",
            latitude = 54.3387,
            longitude = -1.4359
        ),
        HostelData(
            hostelId = "24",
            hostelName = "Black Horse Inn",
            hostelDescription = "Charming inn with comfortable rooms, traditional decor, and dining options.",
            hostelLocation = "Northallerton",
            latitude = 54.3487,
            longitude = -1.5048
        ),
        HostelData(
            hostelId = "25",
            hostelName = "The Cleveland Tontine",
            hostelDescription = "Boutique hotel offering stylish rooms and an award-winning bistro.",
            hostelLocation = "Northallerton",
            latitude = 54.4282,
            longitude = -1.3217
        ),
        HostelData(
            hostelId = "26",
            hostelName = "Allerton Court Hotel",
            hostelDescription = "Hotel providing modern accommodations, conference facilities, and dining.",
            hostelLocation = "Northallerton",
            latitude = 54.3432,
            longitude = -1.4320
        ),
        HostelData(
            hostelId = "27",
            hostelName = "The Golden Lion Hotel",
            hostelDescription = "Historic hotel offering comfortable rooms and traditional hospitality.",
            hostelLocation = "Northallerton",
            latitude = 54.3390,
            longitude = -1.4354
        ),
        HostelData(
            hostelId = "28",
            hostelName = "The Wellington Heifer",
            hostelDescription = "Family-run inn with cozy rooms and a popular on-site restaurant.",
            hostelLocation = "Northallerton",
            latitude = 54.3820,
            longitude = -1.4357
        ),
        HostelData(
            hostelId = "29",
            hostelName = "The Black Bull",
            hostelDescription = "Traditional pub offering comfortable accommodations and hearty meals.",
            hostelLocation = "Northallerton",
            latitude = 54.3401,
            longitude = -1.4359
        ),
        HostelData(
            hostelId = "30",
            hostelName = "The Marine Hotel",
            hostelDescription = "Seafront hotel offering comfortable rooms with stunning views.",
            hostelLocation = "Hartlepool",
            latitude = 54.6955,
            longitude = -1.2126
        ),
        HostelData(
            hostelId = "31",
            hostelName = "The Douglas Hotel",
            hostelDescription = "Charming boutique hotel with stylish interiors and excellent service.",
            hostelLocation = "Hartlepool",
            latitude = 54.6840,
            longitude = -1.2119
        ),
        HostelData(
            hostelId = "32",
            hostelName = "The York House Hotel",
            hostelDescription = "Traditional guesthouse with friendly staff and comfortable rooms.",
            hostelLocation = "Hartlepool",
            latitude = 54.6828,
            longitude = -1.2105
        ),
        HostelData(
            hostelId = "33",
            hostelName = "OYO Studiotel Hartlepool",
            hostelDescription = "Affordable modern studio apartments with kitchenettes.",
            hostelLocation = "Hartlepool",
            latitude = 54.6861,
            longitude = -1.2098
        ),
        HostelData(
            hostelId = "34",
            hostelName = "Brafferton Guest House",
            hostelDescription = "Friendly guesthouse offering cozy accommodations near the town center.",
            hostelLocation = "Hartlepool",
            latitude = 54.6865,
            longitude = -1.2173
        ),
        HostelData(
            hostelId = "35",
            hostelName = "Best Western Grand Hotel",
            hostelDescription = "Elegant hotel with comfortable rooms and on-site dining.",
            hostelLocation = "Hartlepool",
            latitude = 54.6851,
            longitude = -1.2115
        ),
        HostelData(
            hostelId = "36",
            hostelName = "Melbourne House Hotel",
            hostelDescription = "Budget hotel offering basic rooms with free Wi-Fi and parking.",
            hostelLocation = "Hartlepool",
            latitude = 54.6869,
            longitude = -1.2179
        ),
        HostelData(
            hostelId = "37",
            hostelName = "Trinity Guest House",
            hostelDescription = "Guesthouse featuring en-suite rooms and a short walk to the Marina.",
            hostelLocation = "Hartlepool",
            latitude = 54.6924,
            longitude = -1.2118
        ),
        HostelData(
            hostelId = "38",
            hostelName = "Altonlea Lodge",
            hostelDescription = "Family-run B&B offering stylish rooms near the coast.",
            hostelLocation = "Hartlepool",
            latitude = 54.6952,
            longitude = -1.2133
        ),
        HostelData(
            hostelId = "39",
            hostelName = "The Windmill Hotel",
            hostelDescription = "Traditional inn offering hearty meals and cozy rooms.",
            hostelLocation = "Hartlepool",
            latitude = 54.6782,
            longitude = -1.2528
        )
    )
}


fun getHotelDetails(): List<HotelData> {
    return listOf(
        // Middlesbrough Hotels
        HotelData(
            hotelId = "1",
            hotelName = "Jurys Inn Middlesbrough",
            hotelDescription = "Modern hotel with gym, restaurant, and spacious rooms in the town centre.",
            hotelLocation = "Middlesbrough",
            latitude = 54.5719,
            longitude = -1.2298
        ),
        HotelData(
            hotelId = "2",
            hotelName = "Holiday Inn Express Middlesbrough - Centre Square",
            hotelDescription = "Popular business hotel offering free breakfast and bright rooms.",
            hotelLocation = "Middlesbrough",
            latitude = 54.5761,
            longitude = -1.2348
        ),
        HotelData(
            hotelId = "3",
            hotelName = "Premier Inn Middlesbrough Town Centre",
            hotelDescription = "Affordable chain hotel offering consistent quality near the shopping district.",
            hotelLocation = "Middlesbrough",
            latitude = 54.5724,
            longitude = -1.2365
        ),
        HotelData(
            hotelId = "4",
            hotelName = "Baltimore Hotel",
            hotelDescription = "Traditional hotel offering free Wi-Fi, restaurant, and parking.",
            hotelLocation = "Middlesbrough",
            latitude = 54.5670,
            longitude = -1.2382
        ),
        HotelData(
            hotelId = "5",
            hotelName = "Travelodge Middlesbrough",
            hotelDescription = "Budget hotel offering great value stays with basic amenities.",
            hotelLocation = "Middlesbrough",
            latitude = 54.5732,
            longitude = -1.2370
        ),

        // Redcar Hotels
        HotelData(
            hotelId = "6",
            hotelName = "Claxton Hotel",
            hotelDescription = "Simple hotel with en suite rooms close to the beach and High Street.",
            hotelLocation = "Redcar",
            latitude = 54.6157,
            longitude = -1.0611
        ),
        HotelData(
            hotelId = "7",
            hotelName = "Park Hotel Redcar",
            hotelDescription = "Seafront hotel offering lovely views, an excellent restaurant, and free Wi-Fi.",
            hotelLocation = "Redcar",
            latitude = 54.6143,
            longitude = -1.0627
        ),
        HotelData(
            hotelId = "8",
            hotelName = "The Beacon Hotel",
            hotelDescription = "Cozy, well-located hotel near Redcar Esplanade.",
            hotelLocation = "Redcar",
            latitude = 54.6150,
            longitude = -1.0620
        ),
        HotelData(
            hotelId = "9",
            hotelName = "Arden House Redcar",
            hotelDescription = "Modern B&B offering cozy rooms with complimentary breakfast.",
            hotelLocation = "Redcar",
            latitude = 54.6170,
            longitude = -1.0605
        ),
        HotelData(
            hotelId = "10",
            hotelName = "Host & Stay - Bellevue Apartments",
            hotelDescription = "Stylish serviced apartments close to Redcar beach.",
            hotelLocation = "Redcar",
            latitude = 54.6179,
            longitude = -1.0581
        ),

        // Northallerton Hotels
        HotelData(
            hotelId = "11",
            hotelName = "Allerton Court Hotel",
            hotelDescription = "Comfortable hotel with a relaxed restaurant and conference rooms.",
            hotelLocation = "Northallerton",
            latitude = 54.3432,
            longitude = -1.4320
        ),
        HotelData(
            hotelId = "12",
            hotelName = "The Golden Lion Hotel",
            hotelDescription = "Historic 18th-century coaching inn with a bar and restaurant.",
            hotelLocation = "Northallerton",
            latitude = 54.3390,
            longitude = -1.4354
        ),
        HotelData(
            hotelId = "13",
            hotelName = "The Black Bull Inn",
            hotelDescription = "Charming country inn offering hearty food and cozy rooms.",
            hotelLocation = "Northallerton",
            latitude = 54.3401,
            longitude = -1.4359
        ),
        HotelData(
            hotelId = "14",
            hotelName = "The Cleveland Tontine",
            hotelDescription = "Boutique hotel with individually designed rooms and a superb restaurant.",
            hotelLocation = "Northallerton",
            latitude = 54.4282,
            longitude = -1.3217
        ),
        HotelData(
            hotelId = "15",
            hotelName = "The Village Inn",
            hotelDescription = "Comfortable inn offering friendly service and a traditional pub.",
            hotelLocation = "Northallerton",
            latitude = 54.3689,
            longitude = -1.4347
        ),

        // Hartlepool Hotels
        HotelData(
            hotelId = "16",
            hotelName = "Best Western Grand Hotel",
            hotelDescription = "Elegant hotel in a Victorian building offering free Wi-Fi and a stylish restaurant.",
            hotelLocation = "Hartlepool",
            latitude = 54.6851,
            longitude = -1.2115
        ),
        HotelData(
            hotelId = "17",
            hotelName = "The Douglas Hotel",
            hotelDescription = "Modern hotel with individually designed rooms and onsite dining.",
            hotelLocation = "Hartlepool",
            latitude = 54.6840,
            longitude = -1.2119
        ),
        HotelData(
            hotelId = "18",
            hotelName = "The Marine Hotel",
            hotelDescription = "Seaside hotel offering beautiful views and upscale rooms.",
            hotelLocation = "Hartlepool",
            latitude = 54.6955,
            longitude = -1.2126
        ),
        HotelData(
            hotelId = "19",
            hotelName = "Travelodge Hartlepool Marina",
            hotelDescription = "Basic hotel overlooking the marina, offering affordable stays.",
            hotelLocation = "Hartlepool",
            latitude = 54.6848,
            longitude = -1.2142
        ),
        HotelData(
            hotelId = "20",
            hotelName = "OYO Studiotel Hartlepool",
            hotelDescription = "Self-catering hotel apartments ideal for longer stays.",
            hotelLocation = "Hartlepool",
            latitude = 54.6861,
            longitude = -1.2098
        )
    )
}


@Preview(showBackground = true)
@Composable
fun PlanTripPreview() {
    PlanTripPage()
}
