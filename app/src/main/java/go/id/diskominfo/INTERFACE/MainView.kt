package go.id.diskominfo.INTERFACE

import go.id.diskominfo.POJO.Banner
import go.id.diskominfo.POJO.Produk

interface MainView{
fun showData(listProduk: List<Produk>)
   fun showDataPria(listProduk: List<Produk>)
   fun showDataWanita(listProduk: List<Produk>)
   fun showDataMinuman(listProduk: List<Produk>,kode:String)
   fun showDataBanner(listBanner: List<Banner>)
   fun showDataRumah(listRumah: List<Produk>)

}