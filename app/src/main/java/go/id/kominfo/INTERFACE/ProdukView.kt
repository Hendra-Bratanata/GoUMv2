package go.id.kominfo.INTERFACE

import go.id.kominfo.POJO.Produk

interface ProdukView {
    fun showDataProduk(listProduk: List<Produk>)
}