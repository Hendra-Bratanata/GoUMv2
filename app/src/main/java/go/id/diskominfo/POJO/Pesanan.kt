package go.id.diskominfo.POJO

data class Pesanan(
        val idPesana:Long,
        val id: String?,
        val kdUmkm: String?,
        val nama: String?,
        val harga: Int?,
        val gambar: String?,
        val jumlah : Int?
) {
    companion object {
        const val Table_Pesanan: String = "TABLE_PESANAN"
        const val Id_Pesanan: String = "ID_PESANAN"
        const val Id: String = "ID"
        const val kdUmkm: String = "KDUMKM"
        const val Nama: String = "NAMA"
        const val Harga: String = "HARGA"
        const val Gambar: String = "GAMBAR"
        const val jumlah : String = "JUMLAH"

    }
}