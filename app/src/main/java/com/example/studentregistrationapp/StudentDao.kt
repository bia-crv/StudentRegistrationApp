package com.example.studentregistrationapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface StudentDao{

    @Insert
    suspend fun insertStudent(student: Student): Long

    @Query("SELECT * FROM students WHERE id = :studentId")
    suspend fun findStudentById(studentId:  Int) :Student?
}