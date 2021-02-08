package com.hurriyet.basketapp.viewtest

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.hurriyet.basketapp.R
import com.hurriyet.basketapp.view.ProductDetailFragment
import com.hurriyet.basketapp.view.ProductDetailFragmentDirections
import com.hurriyet.basketapp.view.ProductListFragment
import kotlinx.android.synthetic.main.products_detail_page_fragment.view.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@RunWith(AndroidJUnit4::class)
class ProductDetailTest {

    @Test
    fun testNavigationProductDetailFragmentToProductList() {
        val navController = Mockito.mock(NavController::class.java)
        val mockNavController = mock(NavController::class.java)

        val firstScenario = launchFragmentInContainer<ProductDetailFragment>()

        firstScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(ViewMatchers.withId(R.id.productDetailContinueShopping)).perform(ViewActions.click())
        verify(mockNavController).navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment())
    }

}