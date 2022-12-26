package com.augray.siftandroid

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfKeyPoint
import org.opencv.features2d.Features2d
import org.opencv.features2d.SIFT
import org.opencv.imgproc.Imgproc


class MainActivity : AppCompatActivity() {
    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
            System.loadLibrary("opencv_java4")
        }
    }

    private var imageView: ImageView? = null

    // make bitmap from image resource
    private var inputImage: Bitmap? = null
    private val sift = SIFT.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputImage = BitmapFactory.decodeResource(resources, R.drawable.test)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        detectAndDrawKeypoints()
    }

    fun detectAndDrawKeypoints() {
        val rgba = Mat()
        Utils.bitmapToMat(inputImage, rgba)
        val keyPoints = MatOfKeyPoint()
        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY)
        sift.detect(rgba, keyPoints)
        Features2d.drawKeypoints(rgba, keyPoints, rgba)
        Utils.matToBitmap(rgba, inputImage)
        imageView!!.setImageBitmap(inputImage)
    }

}