package com.e.hteditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wilsofts.editor.MyEditor

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editor = MyEditor()
    }
}
