package com.example.roadmap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roadmap.data.model.ActionPointEntity
import com.example.roadmap.data.model.RoadmapEntity

@Database(
    entities = [RoadmapEntity::class, ActionPointEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RoadmapDatabase : RoomDatabase() {

    abstract fun createDao(): RoadmapDao

    companion object {
        private var Instance: RoadmapDatabase? = null

        fun getDatabase(context: Context): RoadmapDatabase {
            return Instance ?: synchronized(RoadmapDatabase::class) {
                if (Instance == null) {
                    Instance = Room
                        .databaseBuilder(
                            context = context,
                            klass = RoadmapDatabase::class.java,
                            name = "roadmap_db"
                        )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }

                return@synchronized Instance!!
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(sql = """
                    ALTER TABLE action_points 
                    ADD COLUMN link TEXT DEFAULT NULL""")
            }
        }
    }
}
