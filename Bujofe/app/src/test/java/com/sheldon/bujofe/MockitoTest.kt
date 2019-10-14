package com.sheldon.bujofe

import android.content.Context
import com.sheldon.bujofe.unittest.getStringFromMockito
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

private const val FAKE_STRING = "HELLO WORLD_hahaha"

@RunWith(MockitoJUnitRunner::class)
class UnitTestSample {

    @Mock
    private lateinit var mockContext: Context

    @Test
    fun readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        `when`(mockContext.getString(R.string.hello_word))
            .thenReturn(FAKE_STRING)

        // ...when the string is returned from the object under test...
        val result: String = getStringFromMockito(mockContext)

        // ...then the result should be the expected one.
        assertThat(result, `is`(FAKE_STRING))
    }
}