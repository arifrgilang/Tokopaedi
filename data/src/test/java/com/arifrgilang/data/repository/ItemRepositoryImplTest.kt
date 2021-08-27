package com.arifrgilang.data.repository

import com.arifrgilang.data.database.item.ItemDao
import com.arifrgilang.data.database.item.ItemDataMapper
import com.arifrgilang.domain.MainCoroutineRule
import com.arifrgilang.domain.repository.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/**
 * Created by arifrgilang on 8/27/2021
 */
@ExperimentalCoroutinesApi
class ItemRepositoryImplTest {
    private lateinit var sut: ItemRepository

    @MockK
    lateinit var itemMapper: ItemDataMapper
    lateinit var itemDao: ItemDao

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        sut = ItemRepositoryImpl(itemMapper, itemDao)
    }

    @Test
    fun `Should GetAllItem When Invoked`() {
        runBlockingTest {

        }
    }
}