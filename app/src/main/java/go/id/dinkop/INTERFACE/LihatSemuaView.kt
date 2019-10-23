package go.id.dinkop.INTERFACE

import go.id.dinkop.POJO.Produk

interface LihatSemuaView {
    fun showData(list:List<Produk>)
}