package com.wilsofts.editor

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.FragmentActivity
import com.google.android.flexbox.FlexboxLayout
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jkcarino.rtexteditorview.RTextEditorButton
import com.jkcarino.rtexteditorview.RTextEditorToolbar
import com.jkcarino.rtexteditorview.RTextEditorView

class MyEditor(private val activity: FragmentActivity, descriptions: Array<String>) :
    ColorPickerDialogListener {
    private val DIALOG_TEXT_FORE_COLOR_ID = 0
    private val DIALOG_TEXT_BACK_COLOR_ID = 1
    private var editor_view: RTextEditorView

    init {
        val editor_toolbar: RTextEditorToolbar = activity.findViewById(R.id.editor_toolbar)
        val editor_flex: FlexboxLayout = activity.findViewById(R.id.editor_flex)
        this.editor_view = activity.findViewById(R.id.editor_view)
        editor_toolbar.setEditorView(editor_view)

        for (description in descriptions) {
            val list = arrayListOf<View>()
            editor_flex.findViewsWithText(list, description, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)
            Toast.makeText(activity, list.size.toString(), Toast.LENGTH_LONG).show()
            list.forEach { button ->
                button.setOnClickListener { v: View ->
                    v.visibility = View.VISIBLE
                }
            }
        }

        activity.findViewById<RTextEditorButton>(R.id.undo_button).setOnClickListener {
            editor_view.undo()
        }

        activity.findViewById<RTextEditorButton>(R.id.redo_button).setOnClickListener {
            editor_view.redo()
        }

        activity.findViewById<RTextEditorButton>(R.id.clear_button).setOnClickListener {
            editor_view.clear()
        }

        activity.findViewById<RTextEditorButton>(R.id.bold_button).setOnClickListener {
            editor_view.setBold()
        }
        activity.findViewById<RTextEditorButton>(R.id.italic_button).setOnClickListener {
            editor_view.setItalic()
        }

        activity.findViewById<RTextEditorButton>(R.id.underline_button).setOnClickListener {
            editor_view.setUnderline()
        }

        activity.findViewById<RTextEditorButton>(R.id.strike_through_button).setOnClickListener {
            editor_view.setStrikeThrough()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_remove_format).setOnClickListener {
            editor_view.removeFormat()
        }

        val heading_spinner: AppCompatSpinner = activity.findViewById(R.id.heading_spinner)
        val payment_adapter = ArrayAdapter(
            this.activity.applicationContext, R.layout.editor_spinner_layout,
            arrayOf("Select heading", "Heading 1", "Heading 2", "Heading 3", "Heading 4", "Heading 5", "Heading 6")
        )
        payment_adapter.setDropDownViewResource(R.layout.editor_spinner_dropdown)
        heading_spinner.adapter = payment_adapter
        heading_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position > 0){
                    editor_view.setHeading(position)
                }
            }
        }
        heading_spinner.setSelection(0, true)

        activity.findViewById<RTextEditorButton>(R.id.button_superscript).setOnClickListener {
            editor_view.setSuperscript()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_subscript).setOnClickListener {
            editor_view.setSubscript()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_text_color).setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogId(DIALOG_TEXT_FORE_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity)
        }

        activity.findViewById<RTextEditorButton>(R.id.button_fill_color).setOnClickListener {
            ColorPickerDialog.newBuilder()
                .setDialogId(DIALOG_TEXT_BACK_COLOR_ID)
                .setDialogTitle(R.string.dialog_title_text_back_color)
                .setShowAlphaSlider(false)
                .setAllowCustom(true)
                .show(this.activity)
        }

        activity.findViewById<RTextEditorButton>(R.id.button_block_quote).setOnClickListener {
            editor_view.setBlockCode()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_ordered).setOnClickListener {
            editor_view.setOrderedList()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_unordered).setOnClickListener {
            editor_view.setUnorderedList()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_align_center).setOnClickListener {
            editor_view.setAlignCenter()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_align_justify).setOnClickListener {
            editor_view.setAlignJustify()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_align_left).setOnClickListener {
            editor_view.setAlignLeft()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_align_right).setOnClickListener {
            editor_view.setAlignRight()
        }
        activity.findViewById<RTextEditorButton>(R.id.button_indent).setOnClickListener {
            editor_view.setIndent()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_out_dent).setOnClickListener {
            editor_view.setOutdent()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_horizontal_rule).setOnClickListener {
            editor_view.insertHorizontalRule()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_unlink).setOnClickListener {
            editor_view.setUnlink()
        }

        activity.findViewById<RTextEditorButton>(R.id.button_link).setOnClickListener {
            val dialog = InsertLinkDialogFragment.newInstance()
            dialog.setOnInsertClickListener(object : InsertLinkDialogFragment.OnInsertClickListener {
                override fun onInsertClick(title: String, url: String) {
                    editor_view.insertLink(title, url)
                }
            })
            dialog.show(this.activity.supportFragmentManager, "insert-link-dialog")
        }

        activity.findViewById<RTextEditorButton>(R.id.button_table).setOnClickListener {
            val dialog = InsertTableDialogFragment.newInstance()
            dialog.setOnInsertClickListener(object : InsertTableDialogFragment.OnInsertClickListener {
                override fun onInsertClick(colCount: Int, rowCount: Int) {
                    editor_view.insertTable(colCount, rowCount)
                }
            })
            dialog.show(this.activity.supportFragmentManager, "insert-table-dialog")
        }


        /*unused*/
        activity.findViewById<RTextEditorButton>(R.id.bold_button).setOnClickListener {
           // editor_view.focus()
        }

        activity.findViewById<RTextEditorButton>(R.id.bold_button).setOnClickListener {
           // editor_view.setNormal()
        }

        activity.findViewById<RTextEditorButton>(R.id.bold_button).setOnClickListener {
           // editor_view.enable()
        }

        activity.findViewById<RTextEditorButton>(R.id.bold_button).setOnClickListener {
            //editor_view.disable()
        }
    }

    public fun insertText(text: String) {
        editor_view.insertText(text)
    }

    public fun setHtML(text: String) {
        editor_view.html = text
    }

    public fun getHtML(): String {
        return editor_view.html
    }

    fun setFontSize() {
        editor_view.setFontSize(12)
    }

    fun setLineHeigt() {
        editor_view.setLineHeight(14)
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        if (dialogId == DIALOG_TEXT_FORE_COLOR_ID) {
            this.editor_view.setTextColor(color)
        } else if (dialogId == DIALOG_TEXT_BACK_COLOR_ID) {
            this.editor_view.setTextBackgroundColor(color)
        }
    }

    override fun onDialogDismissed(dialogId: Int) {

    }
}