package com.example.studentregistrationapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth


@Composable
fun RegistrationPage(navHostController: NavHostController) {
    val name = remember { mutableStateOf("") }
    val course = remember { mutableStateOf("") }
    val year = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = remember {
        Room.databaseBuilder(
            context,
            StudentDatabase::class.java,
            "student_database"
        ).build()
    }
    val studentDao = database.studentDao()


    Column(
        modifier = Modifier.fillMaxSize()
        .statusBarsPadding()
        .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Button(onClick = {navHostController.popBackStack()}) {
            Text("Back")
        }

        Text(
            text = "Student Registration",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold
        )
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            placeholder = { Text("Enter student name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(

            value = course.value,
            onValueChange = { course.value = it },
            label = { Text("Course") },
        modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = year.value,
            onValueChange = { year.value = it },
            label = { Text("Year")},
                modifier = Modifier.fillMaxWidth()
            )


        Button(onClick = {

            val yearNumber = year.value.toIntOrNull()
            if (yearNumber != null && yearNumber in 1..4) {

                val student = Student(
                    name = name.value,
                    course = course.value,
                    year = yearNumber
                )


                scope.launch {
                    val newId = studentDao.insertStudent(student)
                    message.value = "Student saved successfully.ID: $newId"

                }

            } else {
                message.value = "Year must be between 1 and 4"
            }

        }) {
            Text("Save Student")
        }
        if(message.value.isNotEmpty()) {
            Text(message.value)
        }
        }
    }











