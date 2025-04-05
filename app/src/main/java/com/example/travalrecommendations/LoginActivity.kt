package com.example.travalrecommendations

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

@Composable
fun LoginScreen()
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white)),

    ){

        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .padding(24.dp)
        ) {

            Text(
                modifier = Modifier,
                text = "LOGIN / ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )


            Text(
                modifier = Modifier
                    .clickable {
                    },
                text = "Sign Up",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.LightGray
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            )
        )
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            modifier = Modifier
                .clickable {
                    when {
                        email.isEmpty() -> {
//                            Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT).show()
                        }

                        password.isEmpty() -> {
//                            Toast.makeText(context, " Please Enter Password", Toast.LENGTH_SHORT)
//                                .show()
                        }

                        else -> {
                            val posterDetails = TravelerDetails(
                                "",
                                email,
                                "",
                                password
                            )

                            loginUser(posterDetails,context)
                        }

                    }
                }
                .width(180.dp)
                .padding(end = 24.dp)
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
                .align(Alignment.End),
            text = "Login",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

    }

}


fun loginUser(travelerDetails: TravelerDetails, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("TravelerDetails").child(travelerDetails.emailid.replace(".", ","))

    databaseReference.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val donorData = task.result?.getValue(TravelerDetails::class.java)
            if (donorData != null) {
                if (donorData.password == travelerDetails.password) {

                    Toast.makeText(context, "Login Sucessfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Seems Incorrect Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Your account not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    LoginScreen()
}