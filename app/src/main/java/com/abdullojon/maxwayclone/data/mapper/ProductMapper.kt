package com.abdullojon.maxwayclone.data.mapper

import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product
import com.abdullojon.maxwayclone.domain.model.ProductUIData

fun Product.toUIData(count:Int=0): ProductUIData {
    return ProductUIData(
        id=this.id,
        categoryID = this.categoryID,
        name=this.name,
        description=this.description,
        image=this.image,
        cost=this.cost,
        count=count
    )
}