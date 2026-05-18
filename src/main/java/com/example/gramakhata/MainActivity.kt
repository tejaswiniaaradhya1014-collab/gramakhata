package com.example.gramakhata

import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GramaKhataHome()
        }
    }
}

@Composable
fun GramaKhataHome() {

    var customerName by rememberSaveable {
        mutableStateOf("")
    }

    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    var amount by rememberSaveable {
        mutableStateOf("")
    }

    var savedCustomer by rememberSaveable {
        mutableStateOf("")
    }

    var lastAction by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val sharedPreferences =
        context.getSharedPreferences(
            "GramaKhataPrefs",
            MODE_PRIVATE
        )

    val gson = Gson()

    var customerList by rememberSaveable {

        mutableStateOf(

            gson.fromJson(
                sharedPreferences.getString(
                    "customers",
                    null
                ),

                object :
                    TypeToken<List<Pair<String, String>>>() {}.type

            ) ?: emptyList<Pair<String, String>>()

        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            Text(
                text = "Grama Khata",
                fontSize = 34.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Digital Ledger App",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = lastAction,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = customerName,
                onValueChange = {
                    customerName = it
                },
                label = {
                    Text("Customer Name")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                },
                label = {
                    Text("Phone Number")
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                label = {
                    Text("Enter Amount")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    if (
                        customerName.isNotEmpty() &&
                        amount.isNotEmpty()
                    ) {

                        savedCustomer = customerName

                        customerList =
                            customerList + Pair(customerName, amount)

                        sharedPreferences.edit()
                            .putString(
                                "customers",
                                gson.toJson(customerList)
                            )
                            .apply()

                        customerName = ""
                        phoneNumber = ""
                        amount = ""
                    }

                }
            ) {

                Text(text = "Save Customer")
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Customer Saved: $savedCustomer",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Customer List",
                fontSize = 24.sp
            )

            customerList.forEach { customer ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = customer.first,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "₹${customer.second} Due",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {

                                    val updatedList = customerList.map {

                                        if (it.first == customer.first) {

                                            Pair(
                                                it.first,
                                                (it.second.toInt() + 100).toString()
                                            )

                                        } else {

                                            it
                                        }
                                    }

                                    customerList = updatedList

                                    lastAction = "Credit Added"
                                },

                                modifier = Modifier.weight(1f)
                            ) {

                                Text(text = "Credit")
                            }

                            Button(
                                onClick = {

                                    val updatedList = customerList.map {

                                        if (it.first == customer.first) {

                                            Pair(
                                                it.first,
                                                (it.second.toInt() - 100).toString()
                                            )

                                        } else {

                                            it
                                        }
                                    }

                                    customerList = updatedList

                                    lastAction = "Debit Added"
                                },

                                modifier = Modifier.weight(1f)
                            ) {

                                Text(text = "Debit")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {

                                    val message =
                                        "Hello ${customer.first}, your due amount is ₹${customer.second}"

                                    val intent = Intent(
                                        Intent.ACTION_VIEW
                                    )

                                    intent.data =
                                        Uri.parse(
                                            "https://wa.me/?text=$message"
                                        )

                                    context.startActivity(intent)
                                },

                                modifier = Modifier.weight(1f)
                            ) {

                                Text(text = "Reminder")
                            }

                            Button(
                                onClick = {

                                    val intent = Intent(
                                        Intent.ACTION_DIAL
                                    )

                                    intent.data =
                                        Uri.parse(
                                            "tel:$phoneNumber"
                                        )

                                    context.startActivity(intent)
                                },

                                modifier = Modifier.weight(1f)
                            ) {

                                Text(text = "Call")
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {

                                customerList = customerList - customer

                            }
                        ) {

                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
    }
}