package appserviciotecnico.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import appserviciotecnico.model.entities.FormularioServicioEntity
import appserviciotecnico.model.entities.SolicitudEntity

// 🗄️ Base de datos Room para la aplicación
@Database(
    entities = [
        FormularioServicioEntity::class,
        SolicitudEntity::class
    ],
    version = 1,
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
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
