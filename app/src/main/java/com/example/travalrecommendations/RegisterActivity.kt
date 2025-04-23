package com.example.travalrecommendations

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            RegisterScreen()
        }
    }
}


@Composable
fun RegisterScreen()
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

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
                text = "Sign Up / ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )


            Text(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        context.finish()
                    },
                text = "Login",
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
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
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
            value = city,
            onValueChange = { city = it },
            label = { Text("City") },
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
                        fullName.isEmpty() -> {
                            Toast.makeText(context, " Please Enter Name", Toast.LENGTH_SHORT).show()
                        }

                        email.isEmpty() -> {
                            Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT)
                                .show()
                        }
                        city.isEmpty() -> {
                            Toast.makeText(context, " Please Enter city", Toast.LENGTH_SHORT)
                                .show()
                        }
                        password.isEmpty() -> {
                            Toast.makeText(context, " Please Enter Password", Toast.LENGTH_SHORT)
                                .show()
                        }


                        else -> {
                            val travelerDetails = TravelerDetails(
                                fullName,
                                email,
                                city,
                                password
                            )
                            registerUser(travelerDetails,context);
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
            text = "Register",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
            )
        )

        Spacer(modifier = Modifier.weight(1f))

    }

}


fun registerUser(travelerDetails: TravelerDetails, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("TravelerDetails")

    databaseReference.child(travelerDetails.emailid.replace(".", ","))
        .setValue(travelerDetails)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()

                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class TravelerDetails(
    var name : String = "",
    var emailid : String = "",
    var city : String = "",
    var password: String = ""
)


@Preview(showBackground = true)
@Composable
fun RegisterScreenSPreview() {
    RegisterScreen()
}