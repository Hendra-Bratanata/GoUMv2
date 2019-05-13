package go.id.kominfo.ITEM

import java.util.*
class AmbilTanggal{
    fun ambilTanggalLengkap(): String {
        val tahun = Calendar.getInstance().get(Calendar.YEAR)
        val bulan = Calendar.getInstance().get(Calendar.MONTH + 1)
        val tanggal = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val menit = Calendar.getInstance().get(Calendar.MINUTE)
        val detik = Calendar.getInstance().get(Calendar.SECOND)
        var Invoice = "$tahun$bulan$tanggal$jam$menit$detik"

        return Invoice
    }
   fun ambilTanggal(): String {
        val tahun = Calendar.getInstance().get(Calendar.YEAR)
        val bulan = Calendar.getInstance().get(Calendar.MONTH + 1)
        val tanggal = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        var Invoice = "$tahun$bulan$tanggal"

        return Invoice
    }
}
