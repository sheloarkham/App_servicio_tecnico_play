package appserviciotecnico.model.data

import androidx.room.Database
import androidx.room.RoomDatabase
import appserviciotecnico.model.entities.FormularioServicioEntity

// 🗄️ Base de datos Room para la aplicación
@Database(
    entities = [FormularioServicioEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // DAO para acceder a los formularios de servicio
    abstract fun formularioServicioDao(): FormularioServicioDao
}

