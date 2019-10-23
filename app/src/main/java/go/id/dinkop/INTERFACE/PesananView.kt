package go.id.dinkop.INTERFACE
import go.id.dinkop.POJO.Penjualan

interface PesananView{
    fun showData( list:List<Penjualan>)
}