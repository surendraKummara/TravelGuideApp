package surendraKummara.s3287724.travalrecommendations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ManageProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageProfile()
        }
    }
}

@Composable
fun ManageProfile() {
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
                text = "Profile",
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.white))
            )
            {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.iv_profile),
                        contentDescription = "",
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "User Name :")

                    Text(
                        modifier = Modifier.padding(),
                        text = TravalRecommendationData.receiveTravelName(context),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "User Email :")

                    Text(
                        modifier = Modifier.padding(),
                        text = TravalRecommendationData.receiveTravelMail(context),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier
                            .clickable {
                                TravalRecommendationData.recordTravelStatus(context, false)

                                context.startActivity(Intent(context, LoginActivity::class.java))
                                context.finish()
                            }
                            .padding()
                            .background(
                                color = colorResource(R.color.bt_color),
                                shape = RoundedCornerShape(
                                    10.dp
                                )
                            )
                            .border(
                                width = 2.dp,
                                color = colorResource(id = R.color.bt_color),
                                shape = RoundedCornerShape(
                                    10.dp
                                )
                            )
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        text = "Logout",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = colorResource(id = R.color.white),
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                }
            }
        }
    }
}
//
//Row(
//modifier = Modifier.fillMaxWidth()
//) {
//    Image(
//        painter = painterResource(id = R.drawable.iv_profile),
//        contentDescription = "Profile",
//        modifier = Modifier.size(96.dp)
//
//    )
//
//    Spacer(modifier = Modifier.width(12.dp))
//
//    Column(modifier = Modifier.weight(1f)) {
//
//        Text(text = "User Name")
//
//        Text(
//            modifier = Modifier.padding(start = 12.dp),
//            text = TravalRecommendationData.readUserName(context),
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        Text(text = "User Email")
//
//        Text(
//            modifier = Modifier.padding(start = 12.dp),
//            text = TravalRecommendationData.readMail(context),
//            fontWeight = FontWeight.Bold,
//            color = Color.Black
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Text(
//            modifier = Modifier
//                .clickable {
//                    TravalRecommendationData.writeLS(context, false)
//
//                    context.startActivity(Intent(context, LoginActivity::class.java))
//                    context.finish()
//                }
//                .padding(horizontal = 12.dp)
//                .background(
//                    color = colorResource(R.color.bt_color),
//                    shape = RoundedCornerShape(
//                        10.dp
//                    )
//                )
//                .border(
//                    width = 2.dp,
//                    color = colorResource(id = R.color.black),
//                    shape = RoundedCornerShape(
//                        10.dp
//                    )
//                )
//                .padding(vertical = 4.dp, horizontal = 12.dp),
//            text = "Logout",
//            textAlign = TextAlign.Center,
//            style = MaterialTheme.typography.titleMedium.copy(
//                color = colorResource(id = R.color.white),
//            )
//        )
//
//    }
//
//}