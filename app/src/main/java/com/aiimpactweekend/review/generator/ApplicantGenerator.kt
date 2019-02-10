package com.aiimpactweekend.review.generator

import com.aiimpactweekend.review.constants.*
import com.aiimpactweekend.review.model.Applicant
import com.aiimpactweekend.review.util.getRandom

fun generateApplicant(): Applicant {

    val isFemale = Math.random() < 0.2
    val isRecommended = when {
        isFemale -> Math.random() < 0.9
        else -> Math.random() < 0.2
    }
    val firstName = when {
        isFemale ->  majorityFemaleFirstNames.random()
        else -> majorityMaleFirstNames.random()
    }

    val lastName = majorityLastNames.random()
    val workPosition = workPositions.random()
    val workName = "${companyNames.random()} ${companyTypes.random()}"
    val workDuration = (2..15).random()
    val schoolName = "University of ${englishCities.random()}"
    val schoolDegree = degreeNames.random()
    val schoolYear = 2019 - workDuration - (1..15).random()
    val traits = allTraits.getRandom()

    return Applicant(
        firstName,
        lastName,
        workPosition,
        workName,
        workDuration,
        schoolDegree,
        schoolName,
        schoolYear,
        traits,
        isRecommended
    )
}