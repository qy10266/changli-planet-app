package com.example.changli_planet_app.Cache.Room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.changli_planet_app.Cache.Room.entity.SomethingItemEntity
import com.example.changli_planet_app.Cache.Room.entity.TopCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountBookDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertOrUpdateTopCard(topCard: TopCardEntity)


    @Query("SELECT * FROM top_card WHERE username = :username")
    fun getTopCardByUserName(username: String): TopCardEntity?

    @Query("SELECT * FROM something_items WHERE username = :username")
    fun getSomethingItemsByUsername(username: String): List<SomethingItemEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertOrUpdateSomethingItems(items: SomethingItemEntity)

    @Query("SELECT id FROM something_items WHERE name = :name AND totalMoney = :totalMoney AND startTime = :startTime  LIMIT 1")
    fun findIdByAttributes(name: String, totalMoney: Double, startTime: String): Int?

    @Query("SELECT totalMoney FROM something_items WHERE id = :itemId")
    fun findPriceById(itemId: Int): Double?

    @Query("SELECT * FROM something_items WHERE id = :itemId")
    fun findItemById(itemId: Int):SomethingItemEntity?

    @Query("SELECT * FROM something_items")
    fun getAllSomethingItems(): List<SomethingItemEntity>

    @Query("DELETE FROM something_items WHERE id = :itemId")
    fun deleteSomethingItem(itemId: Int)
}