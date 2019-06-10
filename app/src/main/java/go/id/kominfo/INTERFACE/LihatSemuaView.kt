package go.id.kominfo.INTERFACE

import go.id.kominfo.POJO.Produk

interface LihatSemuaView {
    fun showData(list:List<Produk>)
}