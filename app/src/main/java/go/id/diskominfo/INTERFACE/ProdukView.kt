package go.id.diskominfo.INTERFACE

import go.id.diskominfo.POJO.Produk

interface ProdukView {
    fun showDataProduk(listProduk: List<Produk>)
}