package go.id.diskominfo.ACTIVITY

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.UmkmView
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.Umkm
import go.id.diskominfo.PRESENTER.UmkmPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.edit_toko.*

class EditTokoActivity : AppCompatActivity(),UmkmView {
    override fun showDataUmkm(listUmkm: List<Umkm>) {
        umkm = listUmkm[0]
        edt_deskripsi_edit_toko.setText(umkm.ket_toko)
        edt_nama_pemilik_edit_toko.setText(umkm.nama)
        edt_catatan_toko_edit_toko.setText(umkm.email)
    }

    lateinit var pref : SharedPreference
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var presenter : UmkmPresenter
    lateinit var umkm: Umkm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_toko)
        pref = SharedPreference(this)

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = UmkmPresenter(apiReposirtory,gson,this)
        pref.getValueString("kd_umkm")?.let { presenter.getUmkmDataKdUmkm(it) }
    }
}
