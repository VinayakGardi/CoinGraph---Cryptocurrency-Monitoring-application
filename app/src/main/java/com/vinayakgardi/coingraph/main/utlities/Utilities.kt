package com.vinayakgardi.coingraph.main.utlities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vinayakgardi.coingraph.R

object Utilities {

    fun roundToTwoDecimals(number: Double): Double {
        return String.format("%.2f", number).toDouble()
    }

    fun loadFromGlide(source : String  , View : ImageView , context : Context){
        Glide.with(context).load(source)
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(View)
    }
    
}