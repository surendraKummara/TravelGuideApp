package com.example.travalrecommendations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.travalrecommendations.ui.theme.TravalRecommendationsTheme
import kotlinx.coroutines.delay

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartUpViewScreen(this )
        }
    }

    private fun navigationLogic(navigationValue: Int) {

        when (navigationValue) {
            1 ->{
                startActivity(Intent(this, TravelDashboardActivity::class.java))

            }
            2 -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
    }
}

@Composable
fun StartUpViewScreen(fragmentActivity: FragmentActivity) {
    var canSplash by remember { mutableStateOf(true) }

    val context = LocalContext.current as Activity

    LaunchedEffect(Unit) {
        delay(3000)
        canSplash = false
    }

    if (canSplash) {
        StartUpView()
    } else {

        val currentStatus = TravalRecommendationData.readLS(context)


        if (currentStatus) {
            val biometricManager = BiometricManager.from(fragmentActivity)
            if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
                val executor = ContextCompat.getMainExecutor(fragmentActivity)
                val biometricPrompt =
                    BiometricPrompt(
                        fragmentActivity,
                        executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                context.startActivity(
                                    Intent(
                                        context,
                                        TravelDashboardActivity::class.java
                                    )
                                )
                            }

                            override fun onAuthenticationError(
                                errorCode: Int,
                                errString: CharSequence
                            ) {
                                super.onAuthenticationError(errorCode, errString)
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun onAuthenticationFailed() {
                                super.onAuthenticationFailed()
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })

                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("FingerPrint Verification")
                    .setSubtitle("Place your finger to continue")
                    .setNegativeButtonText("Close")
                    .build()

                biometricPrompt.authenticate(promptInfo)
            } else {
                Toast.makeText(
                    fragmentActivity,
                    "This device doesn't support fingerprint",
                    Toast.LENGTH_LONG
                ).show()
                context.startActivity(Intent(context, TravelDashboardActivity::class.java))

            }
        } else {
            context.startActivity(Intent(context, LoginActivity::class.java))
            context.finish()
        }


    }
}

@Composable
fun StartUpView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                )
                {
                    Image(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.travel_icon),
                        contentDescription = "Travel Recommendation App",
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Travel Recommendation App\nby Surendra Kummar",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64A70B), // Green color similar to the design
                        fontSize = 26.sp,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }


        }
    }

}