package jp.techachademy.hiroshi.apiapp

import android.content.Context

interface FragmentCallback {
        // Itemを押したときの処理
        fun onClickItem(shop: Shop)
        //お気に入りitemを押した時の処理
        fun onClickItemFavorite(favoriteShop: FavoriteShop)

        // お気に入り追加時の処理
        fun onAddFavorite(shop: Shop)
        // お気に入り削除時の処理
        fun onDeleteFavorite(id: String)

}