package com.evangelos.couchbase.lite.core

import com.evangelos.couchbase.lite.core.converters.DocumentConverter
import com.evangelos.couchbase.lite.core.converters.DocumentConverterGson
import com.evangelos.couchbase.lite.core.util.UserData
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd/MM/yyyy"

class ConverterTest {

    private lateinit var converter: DocumentConverter

    @Before
    fun setUp() {
        val gson = GsonBuilder()
            .setDateFormat(DATE_FORMAT)
            .create()
        converter = DocumentConverterGson(gson)
    }

    @Test
    fun `data to Mutable Document`() = runBlocking {
        val date = Date()
        val user = UserData(
            "email.com",
            11,
            85.62f,
            false,
            date
        )
        val mutableDoc = converter.dataToMutableDocument(user, "user_doc", UserData::class.java) ?: throw Exception()

        assertEquals("email.com", mutableDoc.getString("email"))
        assertEquals(11, mutableDoc.getInt("age"))
        assertEquals(85.62f, mutableDoc.getFloat("balance"))
        assertEquals(false, mutableDoc.getBoolean("enabled"))
        assertEquals(SimpleDateFormat(DATE_FORMAT).format(date), mutableDoc.getString("join_date"))
        assertEquals("user_doc", mutableDoc.getString(TYPE))
    }

}