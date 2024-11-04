package com.example.lab10.data.ktor

interface Apilab12 {
    suspend fun getCharacters(): Result<CharacterListDto, NetworkError>
    suspend fun getLocations(): Result<LocationListDto, NetworkError>
    suspend fun getCharacter(id: Int): Result<CharacterDto, NetworkError>
    suspend fun getLocation(id: Int): Result<LocationDto, NetworkError>
}