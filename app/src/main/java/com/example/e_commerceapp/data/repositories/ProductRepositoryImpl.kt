package com.example.e_commerceapp.data.repositories

import com.example.e_commerceapp.data.remote.data.ProductEntity
import com.example.e_commerceapp.core.Utils.DISCOUNT_FIELD_IN_PRODUCT
import com.example.e_commerceapp.core.Utils.PRODUCT
import com.example.e_commerceapp.data.mappers.toDomain
import com.example.e_commerceapp.core.Utils.BACK4APP_CREATED_AT_FIELD
import com.example.e_commerceapp.core.Utils.BACK4APP_PRODUCTS_CLASS
import com.example.e_commerceapp.core.Utils.FAVOURITE_COUNT
import com.example.e_commerceapp.core.Utils.FAVOURITE_PRODUCTS
import com.example.e_commerceapp.core.Utils.PRODUCT_IMAGE_URL
import com.example.e_commerceapp.core.Utils.SEARCH_KEYWORDS
import com.example.e_commerceapp.data.local.dataSources.LocalProductDataSource
import com.example.e_commerceapp.data.mappers.toDbEntity
import com.example.e_commerceapp.data.remote.dataSources.RemoteImageDataSource
import com.example.e_commerceapp.data.remote.dataSources.RemoteProductDataSource
import com.example.e_commerceapp.domain.model.HaroShopException
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.domain.repositories.ProductRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Transaction
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val localProductDataSource: LocalProductDataSource,
    private val remoteProductDataSource: RemoteProductDataSource,
    private val remoteImageDataSource: RemoteImageDataSource
) : ProductRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        startBackgroundSync()
    }


    private fun startBackgroundSync() {
        scope.launch {
            remoteProductDataSource.getAllProductsStream().collect { productEntities ->
                val imagesId = productEntities.map { it.productImage }
                val imagesMap = remoteImageDataSource.getImageUrls(imagesId)
                val dbEntities = productEntities.map {
                    val back4appInfo = imagesMap[it.productImage]
                    it.toDbEntity(
                        back4appInfo?.imageUrl ?: "",
                        back4appInfo?.createdAt ?: 0
                    )
                }
                localProductDataSource.upsertProducts(dbEntities)
            }
        }
    }

    override suspend fun createProduct(product: Product) {
        remoteProductDataSource.createProduct(product)
       /* try {
            val documentId = firestore.collection(PRODUCT).document()
            val userEntity = product.copy(productId = documentId.id).toEntity()
            documentId.set(userEntity).await()
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToCreateDocumentForModel(
                "unable to create product"
            )
        }*/
    }

    override suspend fun updateProduct(productId: String, data: Map<String, Any>) {
        remoteProductDataSource.updateProduct(productId, data)
        /*try {
            firestore.collection(PRODUCT)
                .document(productId)
                .update(data)
                .await()
        } catch (e: FirebaseFirestoreException) {
            HaroShopException.UnableToUpdateDocumentWithModel(
                "unable to update product with the entered data."
            )
        }*/
    }

    override suspend fun getProductById(productId: String): Product? {
        try {
            val productDbEntity = localProductDataSource.getProductById(productId)
            /*val query = firestore.collection(PRODUCT)
                .document(productId)
                .get()
                .await()
            val product = query.toObject(ProductEntity::class.java)?.toDomain()*/
            return productDbEntity.toDomain()
        } catch (e: FirebaseFirestoreException) {
            throw HaroShopException.UnableToFetchProduct(
                "unable to fetch required product to product screen"
            )
        }
    }

    override fun getProductByCategory(productType: String): Flow<List<Product>> {
        return localProductDataSource.getCategoryProducts(productType).map { it.toDomain() }
       /* return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .whereEqualTo(PRODUCT_TYPE, productType)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        ProductEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }*/
    }

    override fun getFlashSaleProducts(limit: Long?): Flow<List<Product>> {
        return localProductDataSource.getFlashSaleProducts(limit).map {
            it.toDomain()
        }
        /*return callbackFlow {
            var query = firestore.collection(PRODUCT)
                .orderBy(DISCOUNT_FIELD_IN_PRODUCT, Query.Direction.DESCENDING)
                .whereGreaterThan(DISCOUNT_FIELD_IN_PRODUCT, 0)
            if (limit != null) {
                query = query.limit(limit)
            }
            val listener = query.addSnapshotListener { snapShot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val flashSaleProducts = snapShot?.toObjects(
                    ProductEntity::class.java
                )?.toDomain() ?: emptyList()
                trySend(flashSaleProducts)
            }
            awaitClose { listener.remove() }
        }*/
    }

    override fun getNewestTenProducts(): Flow<Result<List<Pair<String, String>>>> {
        return flow {
            try {
                val query = ParseQuery.getQuery<ParseObject>(BACK4APP_PRODUCTS_CLASS)
                query.orderByDescending(BACK4APP_CREATED_AT_FIELD)
                query.limit = 10
                val results = query.find()
                emit(Result.success(results.mapNotNull {
                    val id = it.objectId
                    val url = (it.get("file") as ParseFile).url
                    Pair(id, url)
                }))
            } catch (e: ParseException) {
                emit(
                    Result.failure(
                        HaroShopException.UnableToFetchProduct(
                            "unable to fetch images ids"
                        )
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProductDataUsingImageId(imageId: String): Result<Product?> {
        return try {
            val query = firestore.collection(PRODUCT)
                .whereEqualTo(PRODUCT_IMAGE_URL, imageId)
                .get()
                .await()
            val product = query.documents.firstOrNull()?.toObject(
                ProductEntity::class.java
            )?.toDomain()
            Result.success(product)
        } catch (e: FirebaseFirestoreException) {
            Result.failure(
                HaroShopException.UnableToFetchProduct(
                    "unable to load product data using image id"
                )
            )
        }
    }

    override fun getMostPopularProducts(): Flow<List<Product>> {
        return localProductDataSource.getPopularProducts().map {
            it.toDomain()
        }
        /*return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .whereGreaterThan("favouriteCount", 0)
                .orderBy(FAVOURITE_COUNT, Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        trySend(Result.failure(error))
                        return@addSnapshotListener
                    }
                    val favouriteProducts = snapShot?.toObjects(
                        ProductEntity::class.java
                    )?.map {
                        it.toDomain()
                    } ?: emptyList()
                    trySend(Result.success(favouriteProducts))
                }
            awaitClose { listener.remove() }
        }*/
    }

    override fun getStoryProducts(): Flow<List<Product>> {
        return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .limit(5)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        ProductEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }

    override fun justForYouProducts(): Flow<List<Product>> {
        return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .limit(6)
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        ProductEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }

    override fun getProductsWithSpecificDiscount(discountValue: String): Flow<List<Product>> {
        return callbackFlow {
            val listener = firestore.collection(PRODUCT)
                .whereEqualTo(DISCOUNT_FIELD_IN_PRODUCT, discountValue.toInt())
                .addSnapshotListener { snapShot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val products = snapShot?.toObjects(
                        ProductEntity::class.java
                    )?.toDomain() ?: emptyList()
                    trySend(products)
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun getSearchedProducts(keyword: String): List<Product> {
        val snapShot = firestore.collection(PRODUCT)
            .whereArrayContains(SEARCH_KEYWORDS, keyword)
            .get()
            .await()
        val products = snapShot.toObjects(ProductEntity::class.java).toDomain()
        return products
    }

    override suspend fun toggleFavouriteProducts(productId: String, userRef: DocumentReference) {
        val productRef = firestore.collection(PRODUCT).document(productId)
        useFireStoreRunTransactionToSyncUserWithProduct(productRef, userRef, productId)
    }

    private suspend fun useFireStoreRunTransactionToSyncUserWithProduct(
        productRef: DocumentReference,
        userRef: DocumentReference,
        productId: String
    ) {
        firestore.runTransaction { transaction ->
            val productSnap = transaction.get(productRef)
            val userSnap = transaction.get(userRef)
            val favouriteCount = productSnap.getLong(FAVOURITE_COUNT) ?: 0L
            val favouriteProducts = (userSnap.get(
                FAVOURITE_PRODUCTS
            ) as? List<String>)?.toMutableList() ?: mutableListOf()
            checkProductLikedOrNot(
                favouriteProducts, favouriteCount, productId, transaction, userRef, productRef
            )
        }.await()
    }

    private fun checkProductLikedOrNot(
        favouriteProducts: MutableList<String>,
        favouriteCount: Long,
        productId: String,
        transaction: Transaction,
        userRef: DocumentReference,
        productRef: DocumentReference
    ) {
        if (!favouriteProducts.contains(productId)) {
            favouriteProducts.add(productId)
            transaction.update(userRef, FAVOURITE_PRODUCTS, favouriteProducts)
            transaction.update(productRef, FAVOURITE_COUNT, favouriteCount + 1)
        } else {
            favouriteProducts.remove(productId)
            transaction.update(userRef, FAVOURITE_PRODUCTS, favouriteProducts)
            transaction.update(productRef, FAVOURITE_COUNT, maxOf(0, favouriteCount - 1))
        }
    }
}