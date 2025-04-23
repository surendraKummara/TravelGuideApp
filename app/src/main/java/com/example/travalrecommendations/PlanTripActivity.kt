package com.example.travalrecommendations

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
    val accommodations = listOf("Hotels", "Hostels")

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

    Column(modifier = Modifier.fillMaxSize()) {
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
                    showResults = true

                    if(filteredPlaces.isNotEmpty()) {
                        context.startActivity(Intent(context, SearchResultActivity::class.java))
                    }
                    else{
                        Toast.makeText(context, "No Places Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Search")
            }

            if (showResults) {
                Spacer(modifier = Modifier.height(24.dp))

                if (filteredPlaces.isNotEmpty()) {
                    ResultData.fliterDetails=filteredPlaces

//                    Text("Places to Explore:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//                    filteredPlaces.forEach { place ->
//                        Text(place.placeName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//                        Text(place.placeDescription, fontSize = 12.sp)
//                        Spacer(modifier = Modifier.height(8.dp))
//                    }

                } else {
                    Text("No places found for selected filters.")
                }

                when (accommodationType) {
                    "Hotels" -> {
                        if (filteredHotels.isNotEmpty()) {
//                            ResultData.hotels=filteredHotels

                            Text("Recommended Hotels:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            filteredHotels.forEach { hotel ->
                                Text(hotel.hotelName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Text(hotel.hotelDescription, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        } else {
                            Text("No hotels found in selected destination.")
                        }
                    }
                    "Hostels" -> {
                        if (filteredHostels.isNotEmpty()) {
//                            ResultData.hostels=filteredHostels

                            Text("Recommended Hostels:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            filteredHostels.forEach { hostel ->
                                Text(hostel.hostelName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Text(hostel.hostelDescription, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        } else {
                            Text("No hostels found in selected destination.")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PlanTripPageOld() {
    var selectedCategory by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var accommodationType by remember { mutableStateOf("") }

    val categories = listOf("Adventure & Nature", "Cultural & Historical Spots")
    val destinations = listOf("Middlesbrough", "Redcar", "Northallerton", "Hartlepool")
    val accommodations = listOf("Hotels", "Hostels")

    val calendar = Calendar.getInstance()

    var dateState by remember { mutableStateOf("") }
    var dateEnd by remember { mutableStateOf("") }

    var selYear by remember { mutableIntStateOf(0) }
    var selMonth by remember { mutableIntStateOf(0) }
    var selDOM by remember { mutableIntStateOf(0) }
    var selHOD by remember { mutableIntStateOf(0) }
    var selMinute by remember { mutableIntStateOf(0) }

    var isChecked by remember { mutableStateOf(false) }

    val context = LocalContext.current as Activity

    val dateStartPickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selYear = year
            selMonth = month
            selDOM = dayOfMonth


            Log.e("Test", "Y - $year , M - ${month + 1} , DOM - $dayOfMonth")

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


            Log.e("Test", "Y - $year , M - ${month + 1} , DOM - $dayOfMonth")

            dateEnd = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier
        .fillMaxSize()
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.SkyBlue)
                )
                .padding(horizontal = 12.dp),

            verticalAlignment = Alignment.CenterVertically,

            ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Select Contact",
                tint = Color.Black
            )

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Plan Your Trip",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )
        }

        Column (modifier = Modifier
            .padding(horizontal = 6.dp)
        )
        {

            Spacer(modifier = Modifier.height(16.dp))

            // Categories Dropdown
            DropdownSelector(
                label = "Select Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Destination Dropdown
            DropdownSelector(
                label = "Enter Destination",
                options = destinations,
                selectedOption = destination,
                onOptionSelected = { destination = it }
            )

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = dateState.ifEmpty { "Start Date" },
                        color = if (dateState.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                dateStartPickerDialog.show()
                            },
                        tint = Color.DarkGray
                    )
                }

            }

            Spacer(modifier = Modifier.height(6.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .height(50.dp)
                        .clickable {
                            // Handle the click event, e.g., show a date picker
                        }
                        .background(Color.LightGray, MaterialTheme.shapes.medium)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = dateEnd.ifEmpty { "End Date" },
                        color = if (dateEnd.isEmpty()) Color.Gray else Color.Black
                    )
                    Icon(
                        imageVector = Icons.Default.DateRange, // Replace with your desired icon
                        contentDescription = "Calendar Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable {
                                dateEndPickerDialog.show()
                            },
                        tint = Color.DarkGray
                    )
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            // Accommodation Type Dropdown
            DropdownSelector(
                label = "Accommodation Type",
                options = accommodations,
                selectedOption = accommodationType,
                onOptionSelected = { accommodationType = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Handle submit action
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Search")
            }
        }
    }
}

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
        HostelData(
            hostelId = "1",
            hostelName = "YHA London Central",
            hostelDescription = "Clean, modern, near Oxford Street, private & dorm rooms",
            hostelLocation = "London"
        ),

        HostelData(
            hostelId = "2",
            hostelName = "Euro Hostel Glasgow",
            hostelDescription = "Budget-friendly, private & dorms, near nightlife",
            hostelLocation = "Glasgow"
        ),

        HostelData(
            hostelId = "3",
            hostelName = "Hatters Hostel",
            hostelDescription = "Centrally located, includes breakfast, events ",
            hostelLocation = "Manchester / Liverpool"
        ),

        HostelData(
            hostelId = "4",
            hostelName = "Safestay Hostels",
            hostelDescription = "Trendy urban hostels in cities like London, York, and Edinburgh with stylish décor and lively bar",
            hostelLocation = "London / York / Edinburgh"
        ),

        HostelData(
            hostelId = "5",
            hostelName = "Generator Hostels",
            hostelDescription = "Hip and modern with a focus on social experiences found in London.",
            hostelLocation = "London"
        ),

        HostelData(
            hostelId = "6",
            hostelName = "Clink Hostels",
            hostelDescription = "Quirky and artsy, perfect for solo travelers and creatives especially in London.",
            hostelLocation = "London"
        ),

        HostelData(
            hostelId = "7",
            hostelName = "YHA Whitby",
            hostelDescription = "Next to Whitby Abbey, with sea views",
            hostelLocation = "North Yorkshire Coast"
        ),

        HostelData(
            hostelId = "8",
            hostelName = "Pen-y-Pass",
            hostelDescription = "great for climbers",
            hostelLocation = "Wales"
        ),


        )
}

fun getHotelDetails(): List<HotelData> {
    return listOf(
        HotelData(
            hotelId = "1",
            hotelName = "Premier Inn",
            hotelDescription = "One of the most reliable UK hotel chains. Clean, quiet, and affordable",
            hotelLocation = "All major towns & cities"
        ),
        HotelData(
            hotelId = "2",
            hotelName = "Easy Hotel",
            hotelDescription = "Ultra-budget. Compact rooms, pay-as-you-use extras.",
            hotelLocation = "London, Manchester, Liverpool"
        ),
        HotelData(
            hotelId = "3",
            hotelName = "Holiday Inn / Holiday Inn Express",
            hotelDescription = "Trusted brand, often includes breakfast. Good for families and work trips.",
            hotelLocation = "All major cities"
        ),
        HotelData(
            hotelId = "4",
            hotelName = "Leonardo Hotels",
            hotelDescription = "Contemporary style with a boutique vibe.",
            hotelLocation = "Edinburgh, Glasgow, London"
        ),
        HotelData(
            hotelId = "5",
            hotelName = "The Ritz London",
            hotelDescription = "5-star iconic hotel with classic luxury. Afternoon tea must-try.",
            hotelLocation = "London"
        ),
        HotelData(
            hotelId = "6",
            hotelName = "The Savoy",
            hotelDescription = "Elegant, historic, and glamorous. Close to Covent Garden.",
            hotelLocation = "London"
        ),
        HotelData(
            hotelId = "7",
            hotelName = "Cliveden House",
            hotelDescription = "Country house hotel with history and royal connections.",
            hotelLocation = "Berkshire"
        ),
        HotelData(
            hotelId = "8",
            hotelName = "Inverlochy Castle Hotel",
            hotelDescription = "Luxury castle experience in the Highlands.",
            hotelLocation = "Fort William, Scotland"
        ),

        )
}



@Preview(showBackground = true)
@Composable
fun PlanTripPreview()
{
    PlanTripPage()
}
