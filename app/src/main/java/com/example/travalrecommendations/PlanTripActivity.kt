package com.example.travalrecommendations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlanTripActivity : AppCompatActivity() {
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

            // Travel Dates
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start Date") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End Date") },
                modifier = Modifier.fillMaxWidth()
            )

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

@Preview(showBackground = true)
@Composable
fun PlanTripPreview()
{
    PlanTripPage()
}
