package go.id.kominfo.ACTIVITY

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
import android.widget.ProgressBar
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import go.id.kominfo.ADAPTER.BannerAdapter
import go.id.kominfo.ADAPTER.KatagoryAdapter
import go.id.kominfo.ADAPTER.PromoAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.ITEM.Pria
import go.id.kominfo.ITEM.SharedPreference
import go.id.kominfo.POJO.Banner
import go.id.kominfo.POJO.Produk
import go.id.kominfo.PRESENTER.PromoPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {
    override fun showDataBanner(listBann: List<Banner>) {
        listBanner.clear()
        listBanner.addAll(listBann)
        bannerAdapter.notifyDataSetChanged()

    }


    override fun showDataWanita(listProduk: List<Produk>) {
        listWanita.clear()
        listWanita.addAll(listProduk)
        katagoriAdapterWanita.notifyDataSetChanged()
    }

    override fun showDataMinuman(listProduk: List<Produk>,kode: String) {
        swpMain.isRefreshing = false
        conn = 1
        if(kode.equals("minuman" ,true)){
            listMinuman.clear()
            listMinuman.addAll(listProduk)
            katagoriAdapterMinuman.notifyDataSetChanged()
            loading.visibility = View.INVISIBLE
        }
        if(kode.equals("kuliner",true)){
            listKuliner.clear()
            listKuliner.addAll(listProduk)
            katagoriAdapterKuliner.notifyDataSetChanged()
            loading.visibility = View.INVISIBLE
        }

        viewVisible()

    }

    override fun showDataPria(listProduk: List<Produk>) {

        listPria = mutableListOf()
        listPria.clear()
        listPria.addAll(listProduk)
        listPria.map {
            group.add(Pria(it) {
                startActivity<DetailProductActivity>("detail" to it)
            })
        }
    }

    override fun showData(listProduk: List<Produk>) {
        list.clear()
        list.addAll(listProduk)
        adapter.notifyDataSetChanged()

    }

    internal lateinit var window: Window
    lateinit var loading: ProgressBar
    lateinit var list: MutableList<Produk>
    lateinit var listBanner: MutableList<Banner>
    lateinit var listPria: MutableList<Produk>
    lateinit var listWanita: MutableList<Produk>
    lateinit var listMinuman: MutableList<Produk>
    lateinit var listKuliner: MutableList<Produk>
    lateinit var presenter: PromoPresenter
    lateinit var bannerAdapter: BannerAdapter
    lateinit var katagoriAdapterPria: KatagoryAdapter
    lateinit var katagoriAdapterMinuman: KatagoryAdapter
    lateinit var katagoriAdapterKuliner: KatagoryAdapter
    lateinit var katagoriAdapterWanita: KatagoryAdapter
    lateinit var adapter: PromoAdapter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    var group: GroupAdapter<ViewHolder> = GroupAdapter()
    lateinit var sharedPreferences: SharedPreference
    var LOGIN = false
    var NOHP = ""
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
        loading = progressBarMain
        swpMain = swpfMain

        viewGone()



        sharedPreferences = SharedPreference(this)
        LOGIN = sharedPreferences.getValueBoolien("LOGIN", false)
        NOHP = sharedPreferences.getValueString("noHP").toString()
        KDUMKM = sharedPreferences.getValueString("kd_umkm").toString()
        Log.d("LOGIN", "$LOGIN")
        Log.d("No HP", "$NOHP")

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
            akun.setTitle(NOHP)
            nav_toko.setVisible(LOGIN)

            nav_salesOrder.setVisible(LOGIN)
            nav_stat.setVisible(LOGIN)
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


        listWanita = mutableListOf()
        katagoriAdapterWanita = KatagoryAdapter(listWanita, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listMinuman = mutableListOf()
        katagoriAdapterMinuman = KatagoryAdapter(listMinuman, {
            startActivity<DetailProductActivity>("detail" to it)
        })

        listKuliner = mutableListOf()
        katagoriAdapterKuliner = KatagoryAdapter(listKuliner,{
            startActivity<DetailProductActivity>("detail" to it)
        })

        listBanner = mutableListOf()
        bannerAdapter = BannerAdapter(listBanner, {
            startActivity<DetailBannerActivity>("banner" to it)
        })

        rv_pria.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = group
        }



        rv_promo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_promo.adapter = adapter



        rv_wanita.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_wanita.adapter = katagoriAdapterWanita

        rv_electronik.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_electronik.adapter = katagoriAdapterKuliner

        rv_makanan_minuman.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_makanan_minuman.adapter = katagoriAdapterMinuman

        rv_banner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_banner.adapter = bannerAdapter

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
        edtCari.setOnClickListener {
            startActivity<CariActivity>()
        }





    }

    private fun requestData() {

        presenter.getPromo()
        presenter.getFashion()
        presenter.getCraft()
        presenter.getKuliner()
        presenter.getBenner()
        presenter.getMinuman()

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
            super.onBackPressed()
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
                sharedPreferences.clearSharedPreference()
                startActivity<MainActivity>()
            }
            R.id.nav_sales_order ->{
                startActivity<SalesOrderActivity>()
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
    }

    fun viewVisible() {
        tvPromoMain.visibility = View.VISIBLE
        tvStoreMain.visibility = View.VISIBLE
        tvFashionMain.visibility = View.VISIBLE
        tvCraftMain.visibility = View.VISIBLE
        tvKulinerMain.visibility = View.VISIBLE
    }


}
