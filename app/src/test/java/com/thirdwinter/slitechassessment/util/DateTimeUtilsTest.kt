package com.thirdwinter.slitechassessment.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateTimeUtilsTest{

    @Test
    fun dateExtractionIsCorrect(){
        val input="1998-05-23T12:23:000z"
        val result=DateTimeUtils.getDate(input)
        val expected="1998-05-23"
        assertThat(expected).isEqualTo(result)

    }
}