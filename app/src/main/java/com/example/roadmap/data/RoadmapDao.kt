package com.example.roadmap.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roadmap.data.model.ActionPointEntity
import com.example.roadmap.data.model.RoadmapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoadmapDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoadmaps(roadmaps: List<RoadmapEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActionPoints(actionPoints: List<ActionPointEntity>)

    @Query("SELECT * FROM roadmaps")
    fun getRoadmaps(): Flow<List<RoadmapEntity>>

    @Query("SELECT * FROM action_points")
    fun getActionPoints(): Flow<List<ActionPointEntity>>

    @Insert
    suspend fun insertActionPoint(actionPoint: ActionPointEntity)
}
