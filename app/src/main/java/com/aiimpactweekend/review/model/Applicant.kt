package com.aiimpactweekend.review.model

data class Applicant(
    val firstName: String,
    val lastName: String,
    val workPosition: String,
    val workName: String,
    val workDuration: Int,
    val schoolDegree: String,
    val schoolName: String,
    val schoolYear: Int,
    val traits: Array<String>,
    val isRecommended: Boolean
    )