package go.id.diskominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_pembeli_data.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class IsiDataPembeliActivity : AppCompatActivity() {
    lateinit var pref: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = SharedPreference(this)
        setContentView(R.layout.activity_pembeli_data)
        val namaLama = pref.getValueString("namaPembeli")
        val alamatLama = pref.getValueString("alamatPembeli")
        val tlpLama = pref.getValueString("noHpPembeli")
        edtAlamatPembeli.setText(alamatLama)
        edtNamaPemebeli.setText(namaLama)
        edtTlpPembeli.setText(tlpLama)
        if (edtTlpPembeli.text.isNotEmpty()) {


            edtTlpPembeli.isEnabled = false
            edtNamaPemebeli.isEnabled = false

            this.alert("Anda sudah memiliki data, anda hanya dapat merubah alamat ") {
           yesButton {  }
            }.show()
        }

        btnSelesaiDataDiriPembeli.setOnClickListener {

            val nama = edtNamaPemebeli.text.toString()
            val tlp = edtTlpPembeli.text.toString()
            val alamat = edtAlamatPembeli.text.toString()



            if (nama.isEmpty()){
                edtNamaPemebeli.setError("Nama Wajib Di isi")
            }
            if(tlp.isEmpty()){
                edtTlpPembeli.setError("Nomor Kontak Wajib di isi")
            }
            if(alamat.isEmpty()){
                edtAlamatPembeli.setError("Alaat Tidak Boleh Kosong")
            }
            else{
                alert("Apakah Data sudah benar?") {
                    yesButton {  selesai(nama,alamat,tlp) }
                    noButton {  }

                }.show()

            }
        }

    }
    fun selesai(nama: String, alamat: String, tlp: String) {
        val intenKembali = Intent()
        intenKembali.putExtra("nama",nama)
        intenKembali.putExtra("tlp",tlp)
        intenKembali.putExtra("alamat",alamat)
        pref.save("namaPembeli",nama)
        pref.save("noHpPembeli",tlp)
        pref.save("alamatPembeli",alamat)
        setResult(Activity.RESULT_OK,intenKembali)
        finish()
    }
}
