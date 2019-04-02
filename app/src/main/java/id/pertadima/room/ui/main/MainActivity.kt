package id.pertadima.room.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import id.pertadima.room.R
import id.pertadima.room.base.BaseActivity
import id.pertadima.room.room.entity.Note
import id.pertadima.room.ui.add.AddNoteActivity
import id.pertadima.room.ui.add.AddNoteActivity.Companion.DESC_NOTE_TAG
import id.pertadima.room.ui.add.AddNoteActivity.Companion.TITLE_NOTE_TAG
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        const val REQUEST_ADD = 1
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        setupToolbarProperties(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.app_name
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        observeViewModel()
        fab_add.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, AddNoteActivity::class.java), REQUEST_ADD)
        }
    }

    private fun observeViewModel() {
        with(mainViewModel) {
            observeNoteList().onResult {

            }
            getNoteList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            data?.let {
                mainViewModel.insertNoteList(
                    Note(
                        it.getStringExtra(TITLE_NOTE_TAG),
                        it.getStringExtra(DESC_NOTE_TAG)
                    )
                )
            }
            mainViewModel.getNoteList()
        }

    }
}
