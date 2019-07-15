package go.id.diskominfo.INTERFACE
import go.id.diskominfo.POJO.Penjualan

interface PesananView{
    fun showData( list:List<Penjualan>)
}