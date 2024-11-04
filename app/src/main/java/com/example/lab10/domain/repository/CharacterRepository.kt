package com.example.lab10.domain.repository
import com.example.lab10.data.model.Character


interface CharacterRepository {
    suspend fun initialSync(): Boolean
    suspend fun getCharacters(): List<Character>
    suspend fun getCharacterById(id: Int): Character
}