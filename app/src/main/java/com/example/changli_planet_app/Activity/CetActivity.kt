package com.example.changli_planet_app.Activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.changli_planet_app.Core.FullScreenActivity
import com.example.changli_planet_app.R
import com.example.changli_planet_app.databinding.ActivityCetBinding
import com.google.android.material.snackbar.Snackbar


class CetActivity : FullScreenActivity() {
    private lateinit var binding: ActivityCetBinding
    private val webView: WebView by lazy { binding.webView }
    private val back:ImageView by lazy { binding.back }
    private val progressBar: ProgressBar by lazy { binding.progressBar }
    private val initialUrl = "https://cjcx.neea.edu.cn/html1/folder/21033/653-1.htm"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar){ view, windowInsets->
            val insets=windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                view.paddingLeft,
                insets.top,
                view.paddingRight,
                view.paddingBottom
            )
            WindowInsetsCompat.CONSUMED
        }
        back.setOnClickListener{finish()}
        setupWebView()
    }

    private fun setupWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return false
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressBar.visibility = View.INVISIBLE
                } else {
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                }
            }
        }
        webView.loadUrl(initialUrl)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.setDownloadListener { url, _, _, _, _ ->
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                showMessage("正在跳转到系统浏览器...")
            } catch (e: Exception) {
                showMessage("无法打开系统浏览器: ${e.message}")
            }
        }
    }


    private fun showMessage(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(                                        R.color.score_bar))
            .setTextColor(Color.BLACK)
        val snackerView = snackbar.view

        snackerView.layoutParams = (snackerView.layoutParams as FrameLayout.LayoutParams).apply {
            width = FrameLayout.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
            bottomMargin = 70
        }

        snackerView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            textSize = 16f
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setPadding(40, 8, 40, 8)
        }
        snackbar.show()
    }


}