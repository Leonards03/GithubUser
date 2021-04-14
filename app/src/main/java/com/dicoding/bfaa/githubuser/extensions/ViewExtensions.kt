package com.dicoding.bfaa.githubuser.extensions

import android.view.View


/** makes visible a view. */
fun View.visible() {
    visibility = View.VISIBLE
}

/** makes view invinsible **/
fun View.invisible(){
    visibility = View.GONE
}