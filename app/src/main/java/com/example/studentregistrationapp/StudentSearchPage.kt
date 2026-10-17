package com.example.studentregistrationapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme





@Composable
fun StudentSearchPage(navController: NavController) {

    val studentId = remember { mutableStateOf("") }
    val studentFound = remember { mutableStateOf<Student?>(null) }
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
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        Text("Find Student")

        OutlinedTextField(
            value = studentId.value,
            onValueChange = { studentId.value = it },
            label = { Text("Student ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val id = studentId.value.toIntOrNull()

                if (id != null) {
                    scope.launch {
                        val result = studentDao.findStudentById(id)

                        studentFound.value = result

                        if (result == null) {
                            message.value = "Student not found"
                        } else {
                            message.value = ""
                        }
                    }
                } else {
                    message.value = "Enter a valid Student ID"
                }
            }
        ) {
            Text("Search")
        }


        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            if (studentFound.value != null) {

                val student = studentFound.value!!

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "Student Details",
                            fontWeight = FontWeight.Bold
                        )

                        Text("ID: ${student.id}")
                        Text("Name: ${student.name}")
                        Text("Course: ${student.course}")
                        Text("Year: ${student.year}")
                    }
                }

            } else if (message.value.isNotEmpty()) {

                Text(
                    text = message.value,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}