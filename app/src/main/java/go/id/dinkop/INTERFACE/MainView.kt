package go.id.dinkop.INTERFACE

import go.id.dinkop.POJO.Banner
import go.id.dinkop.POJO.Produk

interface MainView{
   fun showData(listProduk: List<Produk>)
   fun showDataPria(listProduk: List<Produk>)
   fun showDataWanita(listProduk: List<Produk>)
   fun showDataMinuman(listProduk: List<Produk>,kode:String)
   fun showDataBanner(listBanner: List<Banner>)
   fun showDataRumah(listRumah: List<Produk>)
   fun showDataJasa(listJASA: List<Produk>)

}