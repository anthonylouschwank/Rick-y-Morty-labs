package com.example.lab10.data.ktor

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ApiKtorLab12(
    private val httpClient: HttpClient
): Apilab12 {
    override suspend fun getCharacters(): Result<CharacterListDto, NetworkError> {
        return safeCall<CharacterListDto> {
            httpClient.get(
                "https://rickandmortyapi.com/api/character"
            )
        }
    }

    override suspend fun getLocations(): Result<LocationListDto, NetworkError> {
        return safeCall<LocationListDto> {
            httpClient.get(
                "https://rickandmortyapi.com/api/location"
            )
        }
    }

    override suspend fun getCharacter(id: Int): Result<CharacterDto, NetworkError> {
        return safeCall<CharacterDto> {
            httpClient.get(
                "https://rickandmortyapi.com/api/character/{id}"
            )
        }
    }

    override suspend fun getLocation(id: Int): Result<LocationDto, NetworkError> {
        return safeCall<LocationDto> {
            httpClient.get(
                "https://rickandmortyapi.com/api/location/{id}"
            )
        }
    }
}
