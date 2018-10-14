package com.zapir.ariadne.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.zapir.ariadne.R
//import com.zapir.ariadne.ui.map.MapView
import com.zapir.ariadne.ui.search.SearchFragment
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zapir.ariadne.model.mock.RouterApiMock
//import com.zapir.ariadne.ui.map.MapLayer
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.map.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

//        mapview.setImage("https://geology.com/world/world-map.gif")
//        RouterApiMock().getPoints()
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(
//                                        {
//                                            mapview.loadPoints(it)
//                                        }
//                                        ,
//                                            {
//                                            }
//
//                                )



//        Picasso.get()
//                .load("https://upload.wikimedia.org/wikipedia/ru/thumb/5/5f/Original_Doge_meme.jpg/300px-Original_Doge_meme.jpg")
//                .into(object: com.squareup.picasso.Target {
//                    override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
//                        Log.e("djfkdf", "dkfjfd")
//
//                    }
//
//                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//
//                    }
//
//                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                        mapview.loadMap(bitmap!!)
//                        RouterApiMock().getPoints()
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(
//                                        {
//                                            mapview.loadPoints(it)
//                                        }
//                                        ,
//                                            {
//                                            }
//
//                                )
//                    }
//                })



        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SearchFragment())
                .commitNow()
    }
}
