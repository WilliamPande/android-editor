package com.wilsofts.editor

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import java.util.Objects

class InsertLinkDialogFragment : AppCompatDialogFragment() {
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
        val view = LayoutInflater.from(this.context).inflate(R.layout.dialog_insert_link, null)

        val textToDisplayEditText = view.findViewById<AppCompatEditText>(R.id.text_to_display)
        val linkToEditText = view.findViewById<AppCompatEditText>(R.id.link_to)

        val dialog = AlertDialog.Builder(this.activity!!)
        dialog.setTitle(R.string.title_insert_link)
        dialog.setView(view)
        dialog.setPositiveButton(R.string.insert) { _, _ ->
            val title = textToDisplayEditText.text.toString().trim { it <= ' ' }
            val url = linkToEditText.text.toString().trim { it <= ' ' }

            if (this.listener != null) {
                this.listener!!.onInsertClick(title, url)
            }
        }
        dialog.setNegativeButton(android.R.string.cancel) { dialog1, _ -> dialog1.cancel() }

        return dialog.create()
    }

    interface OnInsertClickListener {
        fun onInsertClick(title: String, url: String)
    }

    companion object {
        internal fun newInstance(): InsertLinkDialogFragment {
            return InsertLinkDialogFragment()
        }
    }
}