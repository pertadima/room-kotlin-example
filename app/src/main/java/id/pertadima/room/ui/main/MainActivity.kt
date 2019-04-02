package id.pertadima.room.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.core.commons.DiffCallback
import id.co.core.commons.GeneralRecyclerView
import id.pertadima.room.R
import id.pertadima.room.base.BaseActivity
import id.pertadima.room.room.entity.Note
import id.pertadima.room.ui.add.AddNoteActivity
import id.pertadima.room.ui.add.AddNoteActivity.Companion.DESC_NOTE_TAG
import id.pertadima.room.ui.add.AddNoteActivity.Companion.TITLE_NOTE_TAG
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import kotlinx.android.synthetic.main.viewholder_note.view.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        const val REQUEST_ADD = 1
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var diffCallback: DiffCallback

    private val notesAdapter by lazy {
        GeneralRecyclerView<Note>(
            diffCallback = diffCallback,
            holderResId = R.layout.viewholder_note,
            onBind = { model, view ->
                setupNotes(view, model)
            },
            itemListener = { model, _, _ ->

            }
        )
    }

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
        initRecyclerView()
        fab_add.setOnClickListener {
            startActivityForResult(Intent(this@MainActivity, AddNoteActivity::class.java), REQUEST_ADD)
        }
    }

    private fun initRecyclerView() {
        with(rv_notes) {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        with(mainViewModel) {
            getAllNotes().onResult {
                notesAdapter.setData(it)
            }
        }
    }

    private fun setupNotes(view: View, model: Note) {
        with(view) {
            tv_note_title.text = model.title
            tv_note_desc.text = model.description
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
        }

    }
}
