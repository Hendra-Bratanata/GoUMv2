package go.id.kominfo.ACTIVITY

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_pembeli_data.*

class PembeliDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembeli_data)
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
                val intenKembali = Intent()
                intenKembali.putExtra("nama",nama)
                intenKembali.putExtra("tlp",tlp)
                intenKembali.putExtra("alamat",alamat)
                setResult(Activity.RESULT_OK,intenKembali)
                finish()
            }
        }
    }
}
