package com.arifrgilang.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arifrgilang.data.MainCoroutineRule
import com.arifrgilang.data.database.OpakuDatabase
import com.arifrgilang.data.database.item.ItemDao
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import kotlin.jvm.Throws


/**
 * Created by arifrgilang on 8/30/2021
 */
@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
class ItemRepositoryImplTest {
    private lateinit var sut: ItemRepository
    private lateinit var db: OpakuDatabase
    private lateinit var itemDao: ItemDao

    @MockK
    lateinit var itemDataMapper: ItemDataMapper

//    @get:Rule
//    var rule: TestRule = InstantTaskExecutorRule()

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val context = getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, OpakuDatabase::class.java
        ).build()
        itemDao = db.itemDao()

        sut = ItemRepositoryImpl(itemDataMapper, itemDao)
    }

    @AfterEach
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `Should getAllItems When getAllItems invoked`() {
        runBlocking {

//            val mockResultsDao = mutableListOf(
//                ItemDataModel(
//                    1,
//                    "Unisex Pajamas Set",
//                    "Pajamas & Socks",
//                    "Unisex",
//                    "Old Navy",
//                    10,
//                    "Set includes tee and pants",
//                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
//                ),
//                ItemDataModel(
//                    2,
//                    "Unisex Pajamas Set",
//                    "Pajamas & Socks",
//                    "Unisex",
//                    "Old Navy",
//                    10,
//                    "Set includes tee and pants",
//                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
//                )
//            )
            val expected = mutableListOf(
                ItemDomainModel(
                    1,
                    "Unisex Pajamas Set",
                    "Pajamas & Socks",
                    "Unisex",
                    "Old Navy",
                    10,
                    "Set includes tee and pants",
                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
                ),
                ItemDomainModel(
                    2,
                    "Unisex Pajamas Set",
                    "Pajamas & Socks",
                    "Unisex",
                    "Old Navy",
                    10,
                    "Set includes tee and pants",
                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
                )
            )
            val mockResults = mutableListOf(
                ItemDomainModel(
                    1,
                    "Unisex Pajamas Set",
                    "Pajamas & Socks",
                    "Unisex",
                    "Old Navy",
                    10,
                    "Set includes tee and pants",
                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
                ),
                ItemDomainModel(
                    2,
                    "Unisex Pajamas Set",
                    "Pajamas & Socks",
                    "Unisex",
                    "Old Navy",
                    10,
                    "Set includes tee and pants",
                    "https://oldnavy.gap.com/webcontent/0019/919/991/cn19919991.jpg"
                )
            )

//            every { db.itemDao() } returns itemDao
//            coEvery { itemDao.getAllItem() } returns mockResultsDao
            coEvery { sut.getAllItem() } returns mockResults

            // When
            val actualValue = sut.getAllItem()

            // Then
            Assert.assertNotNull(actualValue)
            Assert.assertEquals(expected, actualValue)
            coVerify { sut.getAllItem() }
        }
    }
}