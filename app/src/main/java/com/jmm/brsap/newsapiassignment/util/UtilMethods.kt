package com.jmm.brsap.newsapiassignment.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jmm.brsap.newsapiassignment.R


fun ImageView.setImageWithGlide(url: Any) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_error)
        .into(this)
}
