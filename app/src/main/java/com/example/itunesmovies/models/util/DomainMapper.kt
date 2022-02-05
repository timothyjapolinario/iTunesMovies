package com.example.itunesmovies.models.util

interface DomainMapper<T, DomainModel> {

    //Takes an entity then outputs a domain model
    fun mapToDomainModel(model: T): DomainModel

    //Takes a domain model then outputs an entity.
    //use only when uploading is a feature
    //fun mapFromDomainModel(domainModel: DomainModel):T
}