package com.arifrgilang.data.common


/**
 * Created by arifrgilang on 4/25/2021
 */
interface MapperDomainData<Domain, Data> {
    fun mapDomainToData(type: Domain): Data
    fun mapDataToDomain(type: Data): Domain
}