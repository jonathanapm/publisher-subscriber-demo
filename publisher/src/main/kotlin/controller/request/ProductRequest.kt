package com.project.controller.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nonnull
import java.time.LocalDate

data class ProductRequest(

    @Nonnull
    @JsonProperty("name")
    private val name: String,

    @Nonnull
    @JsonProperty("pricing")
    private val pricing: Long,

    @Nonnull
    @JsonProperty("expiration_date")
    private val expirationDate: LocalDate
)
