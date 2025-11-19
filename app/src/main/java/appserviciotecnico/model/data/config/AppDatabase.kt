package appserviciotecnico.model.data.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import appserviciotecnico.model.data.dao.FormularioServicioDao
import appserviciotecnico.model.data.dao.SolicitudDao
import appserviciotecnico.model.data.entities.FormularioServicioEntity
import appserviciotecnico.model.data.entities.SolicitudEntity

// Base de datos Room para la aplicación
@Database(
    entities = [
        FormularioServicioEntity::class,
        SolicitudEntity::class
    ],
    version = 2,  // 🔥 Incrementado a 2 para forzar recreación
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

        abstract fun formularioServicioDao(): FormularioServicioDao
        abstract fun solicitudDao(): SolicitudDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_servicio_tecnico_db"
                )
                    .fallbackToDestructiveMigration() // 🔥 Permite recrear la DB si hay cambios de esquema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}