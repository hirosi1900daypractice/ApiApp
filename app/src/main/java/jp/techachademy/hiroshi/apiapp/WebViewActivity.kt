package jp.techachademy.hiroshi.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.annotation.ContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.fragment_api.*
import kotlinx.android.synthetic.main.fragment_api.recyclerView
import kotlinx.android.synthetic.main.fragment_favorite.*


class WebViewActivity: AppCompatActivity(){
    var isFavorite = null as? Boolean
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
//        webView.loadUrl(intent.getStringExtra(KEY_Shop).toString())
        val shop = intent.getSerializableExtra(KEY_Shop) as? Shop
        val favoriteShop = intent.getSerializableExtra(KEY_favoriteShop) as? FavoriteShop
        if(shop != null) {
            Log.d("url2","${shop.couponUrls.toString()}")
            var url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
            isFavorite = FavoriteShop.findBy(shop.id) != null
            webView.loadUrl(url)
            favoriteWebImageView.apply {
                setImageResource(if (isFavorite as Boolean) R.drawable.ic_star else R.drawable.ic_star_border) // Picassoというライブラリを使ってImageVIewに画像をはめ込む
                setOnClickListener {
                    if (isFavorite as Boolean) {
                        FavoriteShop.insert(FavoriteShop().apply {
                            id = shop.id
                            name = shop.name
                            imageUrl = shop.logoImage
                            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
                            Log.d("object1","object1")
                        })
                        setImageResource(R.drawable.ic_star)
                        isFavorite = false

                    } else {
                        FavoriteShop.delete(shop.id)
                        Log.d("object3","object3")
                        setImageResource(R.drawable.ic_star_border)
                        isFavorite = true
                    }
                    Log.d("object2","${FavoriteShop}")
                }
            }
        }
        else{
            if (favoriteShop != null) {
                Log.d("urlFavorite","${favoriteShop.url}")
                isFavorite = true
                webView.loadUrl(favoriteShop.url)
                favoriteWebImageView.apply {
                    setImageResource(if (isFavorite as Boolean) R.drawable.ic_star else R.drawable.ic_star_border) // Picassoというライブラリを使ってImageVIewに画像をはめ込む
                    setOnClickListener {
                        if (isFavorite as Boolean) {
                            FavoriteShop.insert(FavoriteShop().apply {
                                id = favoriteShop.id
                                name = favoriteShop.name
                                imageUrl = favoriteShop.imageUrl
                                url = favoriteShop.url
                                Log.d("object1", "object1")
                            })
                            setImageResource(R.drawable.ic_star)
                            isFavorite = false

                        } else {
                            FavoriteShop.delete(favoriteShop.id)
                            Log.d("object3", "object3")
                            setImageResource(R.drawable.ic_star_border)
                            isFavorite = true
                        }
                        Log.d("object2", "${FavoriteShop}")
                    }
                }
            }
        }
    }
        //お気に入りの追加削除




//    companion object {
//        private const val KEY_Shop = "key_shop"
//        fun start(activity: Activity, url: String) {
//            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_Shop, url))
//        }
//    }
    companion object {
         private const val KEY_Shop = "key_shop"
         private const val KEY_favoriteShop = "key_favoriteShop"
         fun start(activity: Activity, shop: Shop) {
             activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_Shop, shop))
         }
         fun startFavorite(activity: Activity, favoriteShop: FavoriteShop) {
             activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_favoriteShop, favoriteShop))
         }
    }

}