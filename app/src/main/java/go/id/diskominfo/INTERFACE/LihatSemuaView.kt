package go.id.diskominfo.INTERFACE

import go.id.diskominfo.POJO.Produk

interface LihatSemuaView {
    fun showData(list:List<Produk>)
}