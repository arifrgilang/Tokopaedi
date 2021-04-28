package com.arifrgilang.domain.interactor.item

import com.arifrgilang.domain.model.ItemDomainModel
import com.arifrgilang.domain.repository.ItemRepository


/**
 * Created by arifrgilang on 4/25/2021
 */
interface PostItemsUseCase {
    suspend fun execute(items: List<ItemDomainModel>)
}

class PostItemsUseCaseImpl (
    private val itemRepository: ItemRepository
) : PostItemsUseCase {
    override suspend fun execute(items: List<ItemDomainModel>) {
        itemRepository.postItems(items)
    }
}