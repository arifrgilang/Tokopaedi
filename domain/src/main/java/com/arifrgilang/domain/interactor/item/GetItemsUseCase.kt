package com.arifrgilang.domain.interactor.item

import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetItemsUseCase {
    fun execute(): Flow<List<ItemDomainModel>>
}

class GetItemsUseCaseImpl(
    private val itemRepository: ItemRepository
) : GetItemsUseCase {
    override fun execute(): Flow<List<ItemDomainModel>> =
        itemRepository.getAllItem()
}