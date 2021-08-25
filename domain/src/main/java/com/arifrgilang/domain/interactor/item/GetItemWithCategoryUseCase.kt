package com.arifrgilang.domain.interactor.item

import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetItemWithCategoryUseCase {
    suspend fun execute(itemCategory: String) : List<ItemDomainModel>
}

class GetItemWithCategoryUseCaseImpl(
    private val itemRepository: ItemRepository
) : GetItemWithCategoryUseCase {
    override suspend fun execute(itemCategory: String): List<ItemDomainModel> =
        itemRepository.getItemWithCategory(itemCategory)
}