package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_pembeli_data.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class PembeliDataActivity : AppCompatActivity() {
    lateinit var pref: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembeli_data)
        btnSelesaiDataDiriPembeli.setOnClickListener {

            val nama = edtNamaPemebeli.text.toString()
            val tlp = edtTlpPembeli.text.toString()
            val alamat = edtAlamatPembeli.text.toString()
            pref = SharedPreference(this)


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
                    yesButton {  val intenKembali = Intent()
                        intenKembali.putExtra("nama",nama)
                        intenKembali.putExtra("tlp",tlp)
                        intenKembali.putExtra("alamat",alamat)
                        pref.save("namaPembeli",nama)
                        pref.save("noHpPembeli",tlp)
                        pref.save("alamatPembeli",alamat)
                        setResult(Activity.RESULT_OK,intenKembali)
                        finish() }
                    noButton {  }

                }.show()

            }
        }
    }
}
