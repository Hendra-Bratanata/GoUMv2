package go.id.kominfo.ITEM

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import go.id.kominfo.POJO.Pesanan
import org.jetbrains.anko.db.*

class PesananDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Pesanan.db", null, 1) {

    companion object {
        private var instance: PesananDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): PesananDatabaseOpenHelper {
            if (instance == null) {
                instance = PesananDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as PesananDatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                Pesanan.Table_Pesanan, true,
                Pesanan.Id_Pesanan to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Pesanan.Id to TEXT + UNIQUE,
                Pesanan.Nama to TEXT,
                Pesanan.Harga to INTEGER,
                Pesanan.Gambar to TEXT,
                Pesanan.jumlah to INTEGER
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Pesanan.Table_Pesanan, true)

    }

}
val Context.database: PesananDatabaseOpenHelper
    get() = PesananDatabaseOpenHelper.getInstance(applicationContext)