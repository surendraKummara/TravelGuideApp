package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.os.Bundle
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AboutUsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutUs()
        }
    }
}

@Composable
fun AboutUs() {

    val context = LocalContext.current as Activity
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
                text = "About Us",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "About Us :",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
//            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text ="Welcome to the Travel Recommendation App – your ultimate guide to discovering new places and unforgettable experiences!\n" +
                    "Created by Surendra, this app is designed to help you explore the world like never before. Whether you're looking for hidden gems, top tourist destinations, or personalized travel suggestions, we’ve got you covered. \n" +
                    "Thank you for choosing us as your travel companion – let’s make your next adventure unforgettable!",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Contact Us :",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Traveling Recommendation\n" +
                    "Surendra\n" +
                    "Email: kummarasurendra2002@gmail.com\n" +
                    "Student ID: S3287724",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 12.dp)
        )

    }
}