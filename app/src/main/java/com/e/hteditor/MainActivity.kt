package com.e.hteditor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.wilsofts.editor.MyEditor

class MainActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var editor: MyEditor
    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        editor.onColorSelected(dialogId, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editor = MyEditor(
            this,
            arrayOf(getString(R.string.des_list))
        )
    }
}
