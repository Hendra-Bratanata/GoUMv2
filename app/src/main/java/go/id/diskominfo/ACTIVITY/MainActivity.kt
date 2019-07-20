package go.id.diskominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.firebase.jobdispatcher.*
import com.google.gson.Gson

import go.id.diskominfo.ACTIVITY.DetailActivity.DetailBannerActivity
import go.id.diskominfo.ACTIVITY.DetailActivity.DetailProductActivity
import go.id.diskominfo.ADAPTER.BannerAdapter
import go.id.diskominfo.ADAPTER.KatagoryAdapter
import go.id.diskominfo.ADAPTER.PromoAdapter
import go.id.diskominfo.ApiRepository.ApiReposirtory
import go.id.diskominfo.INTERFACE.MainView
import go.id.diskominfo.ITEM.JobService
import go.id.diskominfo.ITEM.SharedPreference
import go.id.diskominfo.POJO.Banner
import go.id.diskominfo.POJO.Produk
import go.id.diskominfo.PRESENTER.PromoPresenter
import go.id.diskominfo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.yesButton


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {
    override fun showDataJasa(listJASA: List<Produk>) {
        listJasa.clear()
        listJasa.addAll(listJASA)
        katagoriAdapterJasa.notifyDataSetChanged()
    }

    override fun showDataPria(listProduk: List<Produk>) {
        listPria.clear()
        listPria.addAll(listProduk)
        katagoriAdapterPria.notifyDataSetChanged()
    }

    override fun showDataRumah(listRumah: List<Produk>) {
        listRumahTangga.clear()
        listRumahTangga.addAll(listRumah)
        katagoriAdapterRumah.notifyDataSetChanged()
    }

    override fun showDataBanner(listBann: List<Banner>) {
        listBanner.clear()
        listBanner.addAll(listBann)
        bannerAdapter.notifyDataSetChanged()

    }


    override fun showDataWanita(listProduk: List<Produk>) {
        listCraft.clear()
        listCraft.addAll(listProduk)
        katagoriAdapterCraft.notifyDataSetChanged()
    }

    override fun showDataMinuman(listProduk: List<Produk>, kode: String) {
        swpMain.isRefreshing = false
        conn = 1
        if (kode.equals("minuman", true)) {
            listMinuman.clear()
            listMinuman.addAll(listProduk)
            katagoriAdapterMinuman.notifyDataSetChanged()
            loading.visibility = View.INVISIBLE
        }
        if (kode.equals("kuliner", true)) {
            listKuliner.clear()
            listKuliner.addAll(listProduk)
            katagoriAdapterKuliner.notifyDataSetChanged()
            loading.visibility = View.INVISIBLE
        }

        viewVisible()

    }


    override fun showData(listProduk: List<Produk>) {
        list.clear()
        list.addAll(listProduk)
        adapter.notifyDataSetChanged()

    }

    internal lateinit var window: Window
    lateinit var loading: ImageView
    lateinit var list: MutableList<Produk>
    lateinit var listBanner: MutableList<Banner>
    lateinit var listPria: MutableList<Produk>
    lateinit var listCraft: MutableList<Produk>
    lateinit var listMinuman: MutableList<Produk>
    lateinit var listKuliner: MutableList<Produk>
    lateinit var listRumahTangga: MutableList<Produk>
    lateinit var listJasa : MutableList<Produk>
    lateinit var listLainya: MutableList<Produk>
    lateinit var presenter: PromoPresenter
    lateinit var bannerAdapter: BannerAdapter
    lateinit var katagoriAdapterPria: KatagoryAdapter
    lateinit var katagoriAdapterMinuman: KatagoryAdapter
    lateinit var katagoriAdapterKuliner: KatagoryAdapter
    lateinit var katagoriAdapterCraft: KatagoryAdapter
    lateinit var katagoriAdapterRumah: KatagoryAdapter
    lateinit var katagoriAdapterJasa: KatagoryAdapter
    lateinit var katagoryAdapterLainya: KatagoryAdapter
    lateinit var adapter: PromoAdapter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    lateinit var mDispatcher:FirebaseJobDispatcher

    lateinit var sharedPreferences: SharedPreference
    var LOGIN = false
    var NOHP = ""
    var noHpPemebeli = ""
    var alamatPemebeli =""
    var namaPembeli = "Tamu"

    lateinit var swpMain: SwipeRefreshLayout
    var conn = 0


    var KDUMKM: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = this.resources.getColor(R.color.colorDarkPurple)
        }
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        loading = imgLoadingCart
        Glide.with(this)
                .load(R.drawable.loading_cart)
                .into(loading)


        swpMain = swpfMain

        viewGone()



        sharedPreferences = SharedPreference(this)
        LOGIN = sharedPreferences.getValueBoolien("LOGIN", false)
        NOHP = sharedPreferences.getValueString("noHP").toString()
        KDUMKM = sharedPreferences.getValueString("kd_umkm").toString()
        noHpPemebeli = sharedPreferences.getValueString("noHpPembeli").toString()
        alamatPemebeli = sharedPreferences.getValueString("alamatPembeli").toString()
        namaPembeli = sharedPreferences.getValueString("namaPembeli").toString()
        Log.d("LOGIN", "$LOGIN")
        Log.d("No HP", "$NOHP")
        Log.d("No HP pembeli", "$noHpPemebeli")
        if (noHpPemebeli.isNotEmpty()){
            val nav: NavigationView = findViewById(R.id.nav_view)
            val M: Menu = nav.menu
            val akun = M.findItem(R.id.nav_akun)
            akun.setVisible(true)
            akun.setTitle(namaPembeli)
        }
        if(noHpPemebeli.equals("null")){
            val nav: NavigationView = findViewById(R.id.nav_view)
            val M: Menu = nav.menu
            val akun = M.findItem(R.id.nav_akun)
            akun.setVisible(true)
            akun.setTitle("Tamu")
        }
        if (LOGIN) {
            Log.d("in LOGIN ", "$LOGIN")
            Log.d("in No HP", "$NOHP")
            val nav: NavigationView = findViewById(R.id.nav_view)
            val M: Menu = nav.menu
            var nav_login = M.findItem(R.id.nav_login)
            var nav_toko = M.findItem(R.id.nav_toko)
            var nav_stat = M.findItem(R.id.nav_statistik)
            var nav_salesOrder = M.findItem(R.id.nav_sales_order)

            val logOut = M.findItem(R.id.nav_logOut)
            val akun = M.findItem(R.id.nav_akun)


            nav_login.setVisible(false)

            akun.setVisible(LOGIN)
            akun.setTitle(namaPembeli)
            nav_toko.setVisible(LOGIN)

            nav_salesOrder.setVisible(LOGIN)
//            nav_stat.setVisible(LOGIN)
            logOut.setVisible(LOGIN)
            nav_login.setOnMenuItemClickListener {
                false
            }

            nav.setNavigationItemSelectedListener(this)

        }
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        list = mutableListOf()
        adapter = PromoAdapter(list, {
            startActivity<DetailProductActivity>("detail" to it)
        })
        listRumahTangga = mutableListOf()
        katagoriAdapterRumah = KatagoryAdapter(listRumahTangga, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listCraft = mutableListOf()
        katagoriAdapterCraft = KatagoryAdapter(listCraft, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listMinuman = mutableListOf()
        katagoriAdapterMinuman = KatagoryAdapter(listMinuman, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listKuliner = mutableListOf()
        katagoriAdapterKuliner = KatagoryAdapter(listKuliner, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listBanner = mutableListOf()
        bannerAdapter = BannerAdapter(listBanner, {
            startActivity<DetailBannerActivity>("banner" to it)
        })

        listPria = mutableListOf()
        katagoriAdapterPria = KatagoryAdapter(listPria,{
            startActivity<DetailProductActivity>("detail" to it)
        })

        listJasa = mutableListOf()
        katagoriAdapterJasa = KatagoryAdapter(listJasa,{
            startActivity<DetailProductActivity>("detail" to it)
        })

//        listLainya = mutableListOf()
//        katagoryAdapterLainya = KatagoryAdapter(listLainya,{
//            startActivity<DetailProductActivity>("detail" to it)
//        })







        rv_promo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_promo.adapter = adapter

        rv_fashion.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_fashion.adapter = katagoriAdapterPria

        rv_craft.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_craft.adapter = katagoriAdapterCraft

        rv_kuliner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_kuliner.adapter = katagoriAdapterKuliner

        rv_makanan_minuman.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_makanan_minuman.adapter = katagoriAdapterMinuman

        rv_banner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_banner.adapter = bannerAdapter

        rv_rumah_tangga.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_rumah_tangga.adapter = katagoriAdapterRumah

        rv_jasa.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        rv_jasa.adapter = katagoriAdapterJasa

        gson = Gson()
        apiReposirtory = ApiReposirtory()
        presenter = PromoPresenter(this, gson, apiReposirtory)

        requestData()
        swpMain.onRefresh {
            swpMain.isRefreshing = true
            conn = 0
            requestData()
        }
        tv_lihat_semua_fashion.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "fashion")
        }
        tv_lihat_semua_craft.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "craft")
        }
        tv_lihat_semua_kuliner.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "kuliner")
        }
        tv_lihat_semua_makanan_minuman.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "minuman")
        }
        tv_lihat_semua_rumah_tangga.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "rumah")
        }
        tv_lihat_semua_jasa.setOnClickListener {
            startActivity<LihatSemuaActivity>("kode" to "jasa")
        }
        edtCari.setOnClickListener {
            startActivity<CariActivity>()
        }

        mDispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        starJob()
    }

    private fun requestData() {


        presenter.getPromo()
        presenter.getFashion()
        presenter.getCraft()
        presenter.getKuliner()
        presenter.getBenner()
        presenter.getMinuman()
        presenter.getRumahTangga()
        presenter.getJasa()
//        presenter.getLainLain()

        if (conn == 0) {
            Thread(Runnable {
                Thread.sleep(5000)
                requestData()
                println("req Runnable")

            }).start()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            alert("Ingin Keluar dari aplikasi?") {
                yesButton {
                    super.onBackPressed()
                    finish()

                }
                noButton {

                }

            }.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)



        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId



        return if (id == R.id.action_cart) {
            startActivity<KeranjangActivity>()
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            //test
            R.id.nav_login -> {
                startActivity<LoginActivity>()
            }
            R.id.nav_register -> {
                startActivity<RegisterActivity>()
            }
            R.id.nav_keranjang -> {
                startActivity<KeranjangActivity>()
            }
            R.id.nav_statistik -> {
                startActivity<StatistikActivity>()
            }
            R.id.nav_toko -> {
                startActivity<TokoActivity>("kodeRequest" to 1001)
            }
            R.id.nav_logOut -> {
                alert("Keluar Dari Akun?") {
                    yesButton { sharedPreferences.clearSharedPreference()
                        startActivity<MainActivity>()
                    }
                    noButton {  }
                }
                        .show()
            }
            R.id.nav_sales_order -> {
                startActivity<PenjualanActivity>()
            }
            R.id.nav_promo -> {
                startActivity<PembelianActivity>()
            }
        }


        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun viewGone() {

        tvPromoMain.visibility = View.GONE
        tvStoreMain.visibility = View.GONE
        tvFashionMain.visibility = View.GONE
        tvCraftMain.visibility = View.GONE
        tvKulinerMain.visibility = View.GONE
        tvMakananMinumanMain.visibility = View.GONE
        tvRumahTanggaMain.visibility = View.GONE
        tvJasaMain.visibility = View.GONE


        tv_lihat_semua_rumah_tangga.visibility = View.GONE
        tv_lihat_semua_makanan_minuman.visibility = View.GONE
        tv_lihat_semua_kuliner.visibility = View.GONE
        tv_lihat_semua_craft.visibility = View.GONE
        tv_lihat_semua_fashion.visibility = View.GONE
        tv_lihat_semua_jasa.visibility = View.GONE


    }

    fun viewVisible() {
        tvPromoMain.visibility = View.VISIBLE
        tvStoreMain.visibility = View.VISIBLE
        tvFashionMain.visibility = View.VISIBLE
        tvCraftMain.visibility = View.VISIBLE
        tvKulinerMain.visibility = View.VISIBLE
        tvMakananMinumanMain.visibility = View.VISIBLE
        tvRumahTanggaMain.visibility = View.VISIBLE
        tvJasaMain.visibility = View.VISIBLE



        tv_lihat_semua_rumah_tangga.visibility = View.VISIBLE
        tv_lihat_semua_makanan_minuman.visibility = View.VISIBLE
        tv_lihat_semua_kuliner.visibility = View.VISIBLE
        tv_lihat_semua_craft.visibility = View.VISIBLE
        tv_lihat_semua_fashion.visibility = View.VISIBLE
        tv_lihat_semua_jasa.visibility = View.VISIBLE

    }

    fun starJob()
    {
        print("start job main \n")
        val myJob = mDispatcher.newJobBuilder()
                .setService(JobService::class.java)
                .setTag("myJob")
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .build()
        mDispatcher.mustSchedule(myJob)


    }


}
