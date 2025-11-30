package com.example.e_commerceapp.data.repositories

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.e_commerceapp.data.local.data.ProductDbEntity
import com.example.e_commerceapp.data.local.dataSources.LocalProductDataSource
import com.example.e_commerceapp.data.local.dataSources.LocalWishListProductsDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteProductDataSource
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.FirebaseFirestoreException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertFailsWith

@RunWith(RobolectricTestRunner::class)
class ProductRepositoryImplTest {

    private val localProductDataSource: LocalProductDataSource = mockk()
    private val remoteProductDataSource: RemoteProductDataSource = mockk()
    private val remoteImageDataSource: RemoteImageDataSource = mockk()
    private val localWishListProductsDataSource: LocalWishListProductsDataSource = mockk()
    private val workManager: WorkManager = mockk()
    private lateinit var productRepositoryImpl: ProductRepositoryImpl

    @Before
    fun setup() {
        coEvery { remoteProductDataSource.getAllProductsStream() } returns flowOf(emptyList())
        coEvery { remoteImageDataSource.getImageUrls(any()) } returns emptyMap()
        coEvery { localProductDataSource.upsertProducts(any()) } coAnswers {}
        every { workManager.enqueueUniqueWork(any(),any(),any<OneTimeWorkRequest>()) }
        productRepositoryImpl = ProductRepositoryImpl(
            localProductDataSource,
            remoteProductDataSource,
            remoteImageDataSource,
            localWishListProductsDataSource,
            workManager
        )
    }

    @Test
    fun `createProduct on success create the product with the data in firestore`() = runTest {
        val product = Product(
            "shirt1",
            5,
            50,
            "shirt",
            listOf(55, 66),
            listOf("s", "m"),
            "shirt",
            "cotton",
            "awesome shirt",
            0,
            "egypt",
            0f,
            ""
        )
        coEvery { remoteProductDataSource.createProduct(product) } coAnswers {}
        productRepositoryImpl.createProduct(product)
        coVerify (exactly = 1){ remoteProductDataSource.createProduct(product) }
    }
    @Test
    fun `createProduct throws exception when adding product to firestore fails`() = runTest {
        val product = Product(
            "shirt1",
            5,
            50,
            "shirt",
            listOf(55, 66),
            listOf("s", "m"),
            "shirt",
            "cotton",
            "awesome shirt",
            0,
            "egypt",
            0f,
            ""
        )
        val firebaseException = FirebaseFirestoreException(
            "Permission denied",
            FirebaseFirestoreException.Code.PERMISSION_DENIED
        )
        coEvery { remoteProductDataSource.createProduct(any()) } throws firebaseException
        assertFailsWith<HaroShopException.UnableToCreateDocumentForModel> {
            productRepositoryImpl.createProduct(product)
        }
    }

    @Test
    fun `updateProduct on success should update the product data on firestore`() = runTest {
        val productId = "product_123"
        val updatedValues = mapOf(("name" to "shirt"))
        coEvery { remoteProductDataSource.updateProduct(productId,updatedValues) } coAnswers {}
        productRepositoryImpl.updateProduct(productId,updatedValues)
        coVerify (exactly = 1){ remoteProductDataSource.updateProduct(productId,updatedValues) }
    }

    @Test
    fun `updateProduct on failure should throw an exception`() = runTest {
        val productId = "product_123"
        val updatedValues = mapOf(("name" to "shirt"))
        val firestoreException = FirebaseFirestoreException(
            "Permission denied",
            FirebaseFirestoreException.Code.PERMISSION_DENIED
        )
        coEvery { remoteProductDataSource.updateProduct(any(),any()) } throws firestoreException
        assertFailsWith<HaroShopException.UnableToUpdateDocumentWithModel> {
            productRepositoryImpl.updateProduct(productId,updatedValues)
        }
    }

    @Test
    fun `getProductById should return required product mapped to domain`() = runTest {
        val productDbEntity = ProductDbEntity(
            "shirt1",
            5,
            50,
            "shirt",
            listOf(55, 66),
            listOf("s", "m"),
            "shirt",
            "cotton",
            "awesome shirt",
            0,
            "egypt",
            0f,
            "",
            12345
        )
        val product = Product(
            "shirt1",
            5,
            50,
            "shirt",
            listOf(55, 66),
            listOf("s", "m"),
            "shirt",
            "cotton",
            "awesome shirt",
            0,
            "egypt",
            0f,
            ""
        )
        every {
            localProductDataSource.getProductById("shirt1")
        } returns flowOf(productDbEntity)
        val productById = productRepositoryImpl.getProductById("shirt1").first()
        assertThat(productById).isEqualTo(product)
    }

    @Test
    fun `getProductsByCategory should return a list of products with category name`() = runTest {
        val dbProducts = listOf(
            ProductDbEntity(
                "shirt1",
                5,
                50,
                "shirt",
                listOf(55, 66),
                listOf("s", "m"),
                "shirt",
                "cotton",
                "awesome shirt",
                0,
                "egypt",
                0f,
                "",
                12345
            ),
            ProductDbEntity(
                "shirt2",
                5,
                50,
                "shirt",
                listOf(55, 66),
                listOf("s", "m"),
                "shirt",
                "cotton",
                "awesome shirt",
                0,
                "egypt",
                0f,
                "",
                12345
            )
        )
        val products = listOf(
            Product(
                "shirt1",
                5,
                50,
                "shirt",
                listOf(55, 66),
                listOf("s", "m"),
                "shirt",
                "cotton",
                "awesome shirt",
                0,
                "egypt",
                0f,
                ""
            ),
            Product(
                "shirt2",
                5,
                50,
                "shirt",
                listOf(55, 66),
                listOf("s", "m"),
                "shirt",
                "cotton",
                "awesome shirt",
                0,
                "egypt",
                0f,
                ""
            )
        )
        every {
            localProductDataSource.getCategoryProducts("shirt")
        } returns flowOf(dbProducts)
        val fetchedProducts = productRepositoryImpl.getProductByCategory("shirt").first()
        assertThat(fetchedProducts).isEqualTo(products)
    }
}