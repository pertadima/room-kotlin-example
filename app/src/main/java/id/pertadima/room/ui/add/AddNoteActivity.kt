package id.pertadima.room.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import id.co.core.commons.showDialog
import id.co.core.commons.showToast
import id.pertadima.room.R
import id.pertadima.room.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.default_toolbar.view.*

/**
 * Created by pertadima on 01,April,2019
 */

class AddNoteActivity : BaseActivity() {
    companion object {
        const val TITLE_NOTE_TAG = "TITLE_NOTE"
        const val DESC_NOTE_TAG = "DESC_NOTE"
    }

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_add)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.text_add_notes
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        if (!et_title.text.isNullOrEmpty() || !et_description.text.isNullOrEmpty()) {
            showDialog(
                message = getString(R.string.text_success_add_note),
                cancelable = false,
                positiveButton = getString(R.string.text_ok)
            ) {
                val data = Intent().apply {
                    putExtra(TITLE_NOTE_TAG, et_title.text.toString())
                    putExtra(DESC_NOTE_TAG, et_description.text.toString())
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        } else {
            showToast(getString(R.string.text_not_null))
        }
    }
}