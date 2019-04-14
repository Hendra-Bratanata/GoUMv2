package go.id.kominfo.INTERFACE

import go.id.kominfo.POJO.Produk

interface MainView{
fun showData(listProduk: List<Produk>)
   fun showDataPria(listProduk: List<Produk>)
   fun showDataWanita(listProduk: List<Produk>)
   fun showDataMinuman(listProduk: List<Produk>)

}