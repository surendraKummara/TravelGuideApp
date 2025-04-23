package com.example.travalrecommendations

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class ReviewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppReviewsScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppReviewsScreen() {

    val context = LocalContext.current as Activity


    val sampleNames = listOf(
        "Alice", "Brandon", "Carla", "David", "Ella",
        "Felix", "Grace", "Hannah", "Ian", "Tejaswi",
        "Kevin", "Lily", "Mike", "Nina", "Oscar",
        "Paula", "Quinn", "Rachel", "Raj", "Tina"
    )

    val sampleComments = listOf(
        "Amazing app, made my trip so easy!",
        "Needs some improvements but overall helpful.",
        "Great UI and very accurate suggestions.",
        "Loved the travel itinerary feature!",
        "Could use more customization options.",
        "Fantastic support and clean design.",
        "Good for planning short trips.",
        "Bit buggy but gets the job done.",
        "Best travel app I’ve used so far!",
        "Helped me discover hidden gems.",
        "Very intuitive and user-friendly.",
        "Some locations were outdated.",
        "Excellent experience overall!",
        "Average, but useful for basics.",
        "Interface is smooth and responsive.",
        "Couldn’t ask for a better travel buddy.",
        "Navigation is a bit confusing.",
        "Loved how organized everything was.",
        "Decent but not great.",
        "Super helpful when traveling abroad."
    )

    val reviews = List(20) {
        Review(
            username = sampleNames[it % sampleNames.size],
            rating = Random.nextInt(2, 4), // 2 or 3 stars
            comment = sampleComments.random()
        )
    }

    Column (modifier = Modifier.fillMaxSize()){

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
                text = "App Reviews",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold

            )
        }

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(6.dp))
        {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(reviews) { review ->
                    ReviewItem(review)
                }
            }
        }

    }

}

@Composable
fun ReviewItem(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = review.username, style = MaterialTheme.typography.titleMedium)
        RatingStars(review.rating)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = review.comment, fontSize = 14.sp)
        Divider(modifier = Modifier.padding(top = 8.dp))
    }
}

@Composable
fun RatingStars(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star Rating",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

data class Review(
    val username: String,
    val rating: Int,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun PreviewAppReviews() {
    AppReviewsScreen()
}

