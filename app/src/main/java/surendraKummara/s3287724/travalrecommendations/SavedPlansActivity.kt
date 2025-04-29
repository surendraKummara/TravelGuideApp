package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SavedPlansActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dbHelper = PlanDatabaseHelper(this)
            SavedPlansScreen(dbHelper)
        }
    }
}


@Composable
fun SavedPlansScreen(dbHelper: PlanDatabaseHelper) {
    val context = LocalContext.current as Activity
    val plans = remember { mutableStateOf(dbHelper.getAllPlans()) }

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
                text = "Saved Plan",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            if (plans.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(plans.value) { plan ->
                        PlanItem(plan = plan, onDeleteClick = {
                            val success = dbHelper.deletePlan(plan.planId)
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Plan Deleted Successfully!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                plans.value = dbHelper.getAllPlans() // Refresh the list
                            } else {
                                Toast.makeText(context, "Failed to Delete Plan", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                    }
                }
            } else {
                Text("Save some plans to see here")
            }
        }
    }
}

@Composable
fun PlanItem(plan: SavePlan, onDeleteClick: () -> Unit) {

    val context = LocalContext

    val appContext = LocalContext.current

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Category: ${plan.category}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Destination: ${plan.destination}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Start Date: ${plan.startDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "End Date: ${plan.endDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Accommodation: ${plan.accomodationType}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

//                Text(
//                    modifier = Modifier
//                        .clickable {
//                            ResultData.placesDetails = plan.placeDetails
//                            ResultData.placeDetailsOpenedFrom = 2
//                            PlanSummary.planDetails.placeDetails = plan.placeDetails
//
//                            appContext.startActivity(
//                                Intent(
//                                    appContext,
//                                    PlaceDetailsActivity::class.java
//                                )
//                            )
//                        }
//                        .background(
//                            color = colorResource(id = R.color.black),
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .border(
//                            width = 1.dp,
//                            color = colorResource(id = R.color.black),
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                        .padding(horizontal = 8.dp, vertical = 4.dp),
//                    text = "View place",
//                    color = Color.White
//                )

                Spacer(modifier = Modifier.weight(1f))
                // Delete icon
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Plan",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSavedPlans() {
    val dbHelper = PlanDatabaseHelper(LocalContext.current)
    SavedPlansScreen(dbHelper = dbHelper)
}

