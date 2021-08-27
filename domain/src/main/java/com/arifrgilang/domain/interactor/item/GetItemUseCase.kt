package com.arifrgilang.domain.interactor.item

import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
interface GetItemUseCase {
    fun execute(itemId: Int) : Flow<ItemDomainModel>
}

class GetItemUseCaseImpl(
    private val itemRepository: ItemRepository
) : GetItemUseCase {
    override fun execute(itemId: Int): Flow<ItemDomainModel> =
        itemRepository.getItem(itemId)
}