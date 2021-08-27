package com.arifrgilang.domain.usecase.item

import com.arifrgilang.domain.MainCoroutineRule
import com.arifrgilang.domain.interactor.item.GetItemsUseCase
import com.arifrgilang.domain.interactor.item.GetItemsUseCaseImpl
import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/**
 * Created by arifrgilang on 8/27/2021
 */
@ExperimentalCoroutinesApi
class GetItemsUseCaseImplTest {
    private lateinit var sut: GetItemsUseCase

    @MockK
    lateinit var itemRepository: ItemRepository

    @JvmField
    @Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        sut = GetItemsUseCaseImpl(itemRepository)
    }

    @Test
    fun `Should ReturnListOfItems When Executed`(){
        runBlocking {
            // Given
            val mockResults = listOf(
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
            val expected = listOf(
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
            coEvery { sut.execute() } returns mockResults

            // When
            val actualValue = sut.execute()

            // Then
            assertNotNull(actualValue)
            assertEquals(expected, actualValue)
            coVerify { sut.execute() }
        }
    }

}