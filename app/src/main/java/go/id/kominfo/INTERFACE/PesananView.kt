package go.id.kominfo.INTERFACE
import go.id.kominfo.POJO.Penjualan

interface PesananView{
    fun showData( list:List<Penjualan>)
}