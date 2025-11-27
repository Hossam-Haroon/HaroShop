package com.example.e_commerceapp.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.e_commerceapp.data.local.dao.ProductDao
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.data.local.database.RoomDatabase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDaoTest {
    private lateinit var database: RoomDatabase
    private lateinit var productDao: ProductDao
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDatabase::class.java
        ).allowMainThreadQueries().build()
        productDao = database.productDao()
    }
    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun if_upsert_products_exist_in_database_then_should_return_true() = runTest {
        val products = listOf(
            ProductDbEntity(
                "1", 1, 50, "shoe",
                listOf(12312, 123124, 5123123), listOf("s", "m"), "shoe", "cotton",
                "awesome shoe", 0, "egypt", 5f,
                "", 23124124
            )
        )
        productDao.getAllProducts().test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(products)
            val updatedList = awaitItem()
            assertThat(updatedList).containsExactlyElementsIn(products)
            assertThat(updatedList.size).isEqualTo(1)
            cancel()
        }
    }

    @Test
    fun if_database_is_empty_for_not_inserting_any_item_returns_true() = runTest {
        productDao.getAllProducts().test {
            val products = awaitItem()
            assertThat(products).isEmpty()
            cancel()
        }
    }

    @Test
    fun database_should_return_products_if_favouriteCount_bigger_than_zero() = runTest {
        productDao.getPopularProducts().test {
            val initialProducts = awaitItem()
            assertThat(initialProducts).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedProducts = awaitItem()
            assertThat(updatedProducts).contains(POPULAR_PRODUCT)
            assertThat(updatedProducts.size).isEqualTo(1)
            cancel()
        }
    }

    @Test
    fun database_should_return_empty_list_if_favouriteCount_is_zero() = runTest {
        productDao.getPopularProducts().test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(listOf(UNPOPULAR_PRODUCT))
            val updatedProducts = awaitItem()
            assertThat(updatedProducts).isEmpty()
            cancel()
        }
    }

    @Test
    fun database_should_return_products_with_same_entered_categoryName() = runTest{
        productDao.getProductsForCategory("cap").test {
            val initialValue=  awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedProducts = awaitItem()
            assertThat(updatedProducts).containsExactly(CATEGORY_PRODUCT)
            cancel()
        }
    }

    @Test
    fun database_should_return_empty_list_if_categoryName_does_not_exist() = runTest{
        productDao.getProductsForCategory("shirt").test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isEmpty()
            cancel()
        }
    }

    @Test
    fun database_should_return_product_that_matches_id() = runTest{
        productDao.getProductById("2").test {
            val initialValue = awaitItem()
            assertThat(initialValue).isNull()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isEqualTo(POPULAR_PRODUCT)
            cancel()
        }
    }

    @Test
    fun database_should_return_null_when_no_product_matches_id() = runTest{
        productDao.getProductById("4").test {
            val initialValue = awaitItem()
            assertThat(initialValue).isNull()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isNull()
            cancel()
        }
    }

    @Test
    fun database_should_return_flash_sale_products_if_discount_bigger_than_zero() = runTest {
        productDao.getFlashSaleProducts(1).test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).containsExactly(UNPOPULAR_PRODUCT)
            cancel()
        }
    }

    @Test
    fun database_should_return_empty_list_if_discount_equals_zero() = runTest {
        productDao.getFlashSaleProducts(1).test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.insertOrUpdateProduct(POPULAR_PRODUCT)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isEmpty()
            cancel()
        }
    }

    @Test
    fun database_should_return_products_with_searched_keyWord() = runTest {
        productDao.getSearchedProducts("shoe").test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).containsExactly(POPULAR_PRODUCT, UNPOPULAR_PRODUCT)
            cancel()
        }
    }

    @Test
    fun database_should_return_empty_list_if_keyWord_does_not_match() = runTest {
        productDao.getSearchedProducts("shirt").test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isEmpty()
            cancel()
        }
    }

    @Test
    fun database_should_return_products_with_specific_discount_value() = runTest {
        productDao.getProductsWithSpecificDiscountValue(5).test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).containsExactly(UNPOPULAR_PRODUCT)
            cancel()
        }
    }

    @Test
    fun database_should_return_empty_list_with_if_discount_value_does_not_exist() = runTest {
        productDao.getProductsWithSpecificDiscountValue(6).test {
            val initialValue = awaitItem()
            assertThat(initialValue).isEmpty()
            productDao.upsertProducts(TEST_OBJECTS)
            val updatedValue = awaitItem()
            assertThat(updatedValue).isEmpty()
            cancel()
        }
    }

    companion object {
        val TEST_OBJECTS = listOf(
            ProductDbEntity(
                "1", 1, 50, "shoe",
                listOf(12312, 123124, 5123123), listOf("s", "m"), "shoe",
                "cotton", "awesome shoe", 0, "egypt",
                5f, "", 23124124
            ),
            ProductDbEntity(
                "2", 1, 50, "shoe",
                listOf(12312, 123124, 5123123), listOf("s", "m"), "shoe",
                "cotton", "awesome shoe", 2, "egypt",
                0f, "", 23124124
            ),
            ProductDbEntity(
                "3", 1, 50, "cap",
                listOf(12312, 123124, 5123123), listOf("s", "m"), "cap",
                "cotton", "awesome shoe", 0, "egypt",
                0f, "", 23124124
            )
        )
        val POPULAR_PRODUCT = ProductDbEntity(
            "2", 1, 50, "shoe",
            listOf(12312, 123124, 5123123), listOf("s", "m"), "shoe",
            "cotton", "awesome shoe", 2, "egypt",
            0f, "", 23124124
        )
        val CATEGORY_PRODUCT = ProductDbEntity(
            "3", 1, 50, "cap",
            listOf(12312, 123124, 5123123), listOf("s", "m"), "cap",
            "cotton", "awesome shoe", 0, "egypt",
            0f, "", 23124124
        )
        val UNPOPULAR_PRODUCT = ProductDbEntity(
            "1", 1, 50, "shoe",
            listOf(12312, 123124, 5123123), listOf("s", "m"), "shoe",
            "cotton", "awesome shoe", 0, "egypt",
            5f, "", 23124124
        )
    }
}