package com.wilsofts.editor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentManager
import java.util.*

class InsertTableDialogFragment : AppCompatDialogFragment() {
    private var listener: OnInsertClickListener? = null
    internal fun setOnInsertClickListener(listener: OnInsertClickListener) {
        this.listener = listener
    }

    override fun show(manager: FragmentManager, tag: String) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (this.dialog.window != null) {
            this.dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(this.context).inflate(R.layout.dialog_insert_table, null)
        val columnCountEditText = view.findViewById<AppCompatEditText>(R.id.column_count)
        val rowCountEditText = view.findViewById<AppCompatEditText>(R.id.row_count)

        val dialog = AlertDialog.Builder(Objects.requireNonNull(this.activity))
        dialog.setTitle(R.string.title_insert_table)
        dialog.setView(view)
        dialog.setPositiveButton(R.string.insert) { _, _ ->
            val colCount = Objects.requireNonNull(columnCountEditText.text).toString().trim { it <= ' ' }
            val rowCount = Objects.requireNonNull(rowCountEditText.text).toString().trim { it <= ' ' }

            if (this.listener != null) {
                this.listener!!.onInsertClick(Integer.valueOf(colCount), Integer.valueOf(rowCount))
            }
        }
        dialog.setNegativeButton(android.R.string.cancel) { dialog1, which -> dialog1.cancel() }

        return dialog.create()
    }

    interface OnInsertClickListener {
        fun onInsertClick(colCount: Int, rowCount: Int)
    }

    companion object {
        internal fun newInstance(): InsertTableDialogFragment {
            return InsertTableDialogFragment()
        }
    }
}