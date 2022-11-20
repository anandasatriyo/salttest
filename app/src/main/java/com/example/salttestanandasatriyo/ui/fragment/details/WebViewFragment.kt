package com.example.salttestanandasatriyo.ui.fragment.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.salttestanandasatriyo.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment(R.layout.fragment_web_view) {
    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.webViewUrl)
            settings.javaScriptEnabled=true
            webChromeClient = object : WebChromeClient() {
                override fun getDefaultVideoPoster(): Bitmap? {
                    return Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
                }
            }
        }
    }
}



