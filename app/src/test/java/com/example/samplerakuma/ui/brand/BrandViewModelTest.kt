package com.example.samplerakuma.ui.brand

import android.os.Looper.getMainLooper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplerakuma.network.response.Brands
import com.example.samplerakuma.repository.BrandRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Verifier
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.robolectric.Shadows.shadowOf
import java.util.stream.Collectors


@RunWith(AndroidJUnit4::class)
class BrandViewModelTest {

    @Mock
    private lateinit var brandRepository: BrandRepository

    private val scheduler = TestScheduler()

    private val data = listOf(Brands("uso data"))

    private lateinit var viewModel: BrandViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(brandRepository.fetchBrands()).thenReturn(Single.just(data).subscribeOn(scheduler))
    }

    @After
    fun tearDown() {
        // do nothing
    }

    @Test
    fun testCreateBrandAdapterItem() {
        // initでBrandName取得処理を起動します
        viewModel = createViewModel()

    }


    // 検証処理はVerifierに記載します
    @Rule
    @JvmField
    val verifier = object : Verifier() {

        override fun verify() {
            // loadingのフラグが立っていることを確認します
            assertThat(viewModel?.isLoading?.get())
                .`as`("ローディング中")
                .isEqualTo(true)

            // triggerActionsを使用してsubscribeの先にスケジューラを進めます
            // see http://robolectric.org/blog/2019/06/04/paused-looper/
            scheduler.triggerActions()

            // メインルーパーの処理を進めます
            shadowOf(getMainLooper()).idle()
            // loadingのフラグが落ちていることを確認します
            assertThat(viewModel?.isLoading?.get())
                .`as`("ローディング終了")
                .isEqualTo(false)

            // viewModelで取得した値が妥当化確認します
            val adapterItems = viewModel?.brandsAdapterItems?.stream()?.collect(Collectors.toList())
            val brands = adapterItems?.map { Brands(it.name) }
            assertThat(brands)
                .`as`("取得したブランド名")
                .isEqualTo(data)

            // repositoryのfetchBrandsが1度呼ばれていることを検証します
            verify(brandRepository, times(1)).fetchBrands()
            // 不要なメソッドが呼ばれていないことを検証します (今回はfetchBrandsしか呼んでいませんが…)
            verifyNoMoreInteractions(brandRepository)
        }

    }


    private fun createViewModel(): BrandViewModel {
        return BrandViewModel(
            ApplicationProvider.getApplicationContext(),
            brandRepository
        )
    }
}

