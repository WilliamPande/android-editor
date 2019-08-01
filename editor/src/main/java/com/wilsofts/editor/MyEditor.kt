package com.wilsofts.editor

import androidx.fragment.app.FragmentActivity
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jkcarino.rtexteditorview.RTextEditorButton
import com.jkcarino.rtexteditorview.RTextEditorToolbar
import com.jkcarino.rtexteditorview.RTextEditorView

class MyEditor(private val activity: FragmentActivity, val editor: RTextEditorView, toolbar: RTextEditorToolbar) :
    ColorPickerDialogListener {
    private val DIALOG_TEXT_FORE_COLOR_ID = 0
    private val DIALOG_TEXT_BACK_COLOR_ID = 1

    init {
        toolbar.setEditorView(editor)


    }

    fun insertLink(insertLinkButton: RTextEditorButton): MyEditor {
        insertLinkButton.setOnClickListener {
            val dialog = InsertLinkDialogFragment.newInstance()
            dialog.setOnInsertClickListener(object : InsertLinkDialogFragment.OnInsertClickListener {
                override fun onInsertClick(title: String, url: String) {
                    editor.insertLink(title, url)
                }
            })
            dialog.show(this.activity.supportFragmentManager, "insert-link-dialog")
        }
        return this
    }

    fun setTextColor(textForeColorButton: RTextEditorButton): MyEditor {
        textForeColorButton.setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogId(DIALOG_TEXT_FORE_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity)
        }
        return this
    }

    fun setFillColor(textBackColorButton: RTextEditorButton): MyEditor {
        textBackColorButton.setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogId(DIALOG_TEXT_BACK_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_back_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity)
        }
        return this
    }

    fun insertTable(insertTableButton: RTextEditorButton): MyEditor {
        insertTableButton.setOnClickListener {
            val dialog = InsertTableDialogFragment.newInstance()
            dialog.setOnInsertClickListener(object : InsertTableDialogFragment.OnInsertClickListener {
                override fun onInsertClick(colCount: Int, rowCount: Int) {
                    editor.insertTable(colCount, rowCount)
                }
            })
            dialog.show(this.activity.supportFragmentManager, "insert-table-dialog")
        }
        return this
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        if (dialogId == DIALOG_TEXT_FORE_COLOR_ID) {
            this.editor.setTextColor(color)
        } else if (dialogId == DIALOG_TEXT_BACK_COLOR_ID) {
            this.editor.setTextBackgroundColor(color)
        }
    }

    override fun onDialogDismissed(dialogId: Int) {

    }
}