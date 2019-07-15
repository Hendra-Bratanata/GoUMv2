package go.id.diskominfo.INTERFACE

import go.id.diskominfo.POJO.Daftar

interface DaftarView{
    fun showData( list:List<Daftar>)
}