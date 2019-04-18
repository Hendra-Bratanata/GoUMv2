package go.id.kominfo.ACTIVITY

import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import com.google.gson.Gson
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import go.id.kominfo.ADAPTER.BannerAdapter
import go.id.kominfo.ADAPTER.KatagoryAdapter
import go.id.kominfo.ADAPTER.PromoAdapter
import go.id.kominfo.ApiRepository.ApiReposirtory
import go.id.kominfo.INTERFACE.MainView
import go.id.kominfo.ITEM.Pria
import go.id.kominfo.POJO.Banner
import go.id.kominfo.POJO.Produk
import go.id.kominfo.PRESENTER.PromoPresenter
import go.id.kominfo.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity


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

    override fun showDataMinuman(listProduk: List<Produk>) {
        listMinuman.clear()
        listMinuman.addAll(listProduk)
        katagoriAdapterMinuman.notifyDataSetChanged()
    }

    override fun showDataPria(listProduk: List<Produk>) {

        listPria = mutableListOf()
        listPria.clear()
        listPria.addAll(listProduk)
        listPria.map {
            group.add(Pria(it){
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
    lateinit var list: MutableList<Produk>
    lateinit var listBanner: MutableList<Banner>
    lateinit var listPria: MutableList<Produk>
    lateinit var listWanita: MutableList<Produk>
    lateinit var listMinuman: MutableList<Produk>
    lateinit var presenter: PromoPresenter
    lateinit var bannerAdapter: BannerAdapter
    lateinit var katagoriAdapterPria: KatagoryAdapter
    lateinit var katagoriAdapterMinuman: KatagoryAdapter
    lateinit var katagoriAdapterWanita: KatagoryAdapter
    lateinit var adapter: PromoAdapter
    lateinit var gson: Gson
    lateinit var apiReposirtory: ApiReposirtory
    var group : GroupAdapter<ViewHolder> = GroupAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow()
            window.statusBarColor = this.resources.getColor(R.color.colorDarkPurple)
        }
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        list = mutableListOf()
        adapter = PromoAdapter(list,{
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

        listBanner = mutableListOf()
        bannerAdapter = BannerAdapter(listBanner, {
            startActivity<DetailBannerActivity>("banner" to it)
        })

        rv_pria.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter = group
        }



        rv_promo.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_promo.adapter = adapter



        rv_wanita.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_wanita.adapter = katagoriAdapterWanita

        rv_electronik.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_electronik.adapter = katagoriAdapterMinuman

        rv_banner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_banner.adapter = bannerAdapter

        gson = Gson()
        apiReposirtory = ApiReposirtory()

        presenter = PromoPresenter(this, gson, apiReposirtory)
        presenter.getPromo()
        presenter.getFashionPria()
        presenter.getFashionWanita()
        presenter.getMinuman()
        presenter.getBenner()


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
                startActivity<SalesOrderActivity>()
            }
        }


        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
