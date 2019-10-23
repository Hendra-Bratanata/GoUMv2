package go.id.dinkop.INTERFACE

import go.id.dinkop.POJO.Produk

interface ProdukView {
    fun showDataProduk(listProduk: List<Produk>)
}