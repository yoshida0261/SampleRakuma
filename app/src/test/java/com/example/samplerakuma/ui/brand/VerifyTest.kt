package com.example.samplerakuma.ui.brand

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Verifier
import org.assertj.core.api.Assertions.assertThat

class VerifyTest {
    // 使い捨ての無名クラスとして実装
    @Rule @JvmField var verifier: Verifier = object : Verifier() {
        @Throws(Throwable::class)
        override fun verify() {
            assertThat(sut?.value).isEqualTo(0)
        }
    }
    var sut: VerifierExample? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        sut = VerifierExample()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        // do nothing.
    }

    @Test
    @Throws(Exception::class)
    fun clearTest() {
        sut?.set(0)
        sut?.add(140)
        sut?.minus(5)
        assertThat(sut?.value).isEqualTo(135)
        sut?.clear()
    }

}

class VerifierExample {
    var value: Int? = null

    fun set(i: Int) {
        value = i
    }

    fun add(i: Int) {
        value = value?.plus(i)
    }

    operator fun minus(i: Int) {
        value = value?.minus(i)
    }

    fun clear() {
        value = 0
    }
}
