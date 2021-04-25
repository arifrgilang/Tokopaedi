package com.arifrgilang.domain.common


/**
 * Created by arifrgilang on 4/25/2021
 */
interface MapperUiDomain<Ui, Domain> {
    fun mapUiToDomain(type: Ui): Domain
    fun mapDomainToUi(type: Domain): Ui
}