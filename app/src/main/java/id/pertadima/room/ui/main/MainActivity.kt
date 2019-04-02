package id.pertadima.room.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.core.commons.DiffCallback
import id.co.core.commons.GeneralRecyclerView
import id.co.core.commons.showDialog
import id.pertadima.room.R
import id.pertadima.room.base.BaseActivity
import id.pertadima.room.room.entity.Note
import id.pertadima.room.ui.add.AddNoteActivity
import id.pertadima.room.ui.add.AddNoteActivity.Companion.DEFAULT_INT_VALUE
import id.pertadima.room.ui.add.AddNoteActivity.Companion.DESC_NOTE_TAG
import id.pertadima.room.ui.add.AddNoteActivity.Companion.ID_NOTE_TAG
import id.pertadima.room.ui.add.AddNoteActivity.Companion.TITLE_NOTE_TAG
import id.pertadima.swipecontrol.SwipeControl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import kotlinx.android.synthetic.main.viewholder_note.view.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        const val REQUEST_ADD = 1
        const val REQUEST_EDIT = 2
    }

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var diffCallback: DiffCallback

    private val noteList = mutableListOf<Note>()
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
        scrollRv()
        fab_add.setOnClickListener {
            startActivityForResult(
                Intent(this@MainActivity, AddNoteActivity::class.java),
                REQUEST_ADD
            )
        }
    }

    private fun initRecyclerView() {
        with(rv_notes) {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

            val swipeHandler = object : SwipeControl(
                this@MainActivity,
                R.drawable.ic_edit,
                R.drawable.ic_delete
            ) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    when (direction) {
                        SWIPE_RIGHT -> {
                            editItem(viewHolder)
                        }
                        SWIPE_LEFT -> {
                            deleteItem(viewHolder)
                        }
                    }
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun editItem(viewHolder: RecyclerView.ViewHolder) {
        val note = noteList[viewHolder.adapterPosition]
        showDialog(
            message = getString(R.string.text_are_you_sure_to_edit, note.title),
            cancelable = false,
            positiveButton = getString(R.string.text_ok),
            positiveAction = {
                startActivityForResult(
                    Intent(this@MainActivity, AddNoteActivity::class.java).apply {
                        putExtra(ID_NOTE_TAG, note.id)
                        putExtra(TITLE_NOTE_TAG, note.title)
                        putExtra(DESC_NOTE_TAG, note.description)
                    }, REQUEST_EDIT
                )
            },
            negativeButton = getString(R.string.text_cancel),
            negativeAction = {
                notesAdapter.notifyDataSetChanged()
            })
    }

    private fun deleteItem(viewHolder: RecyclerView.ViewHolder) {
        val note = noteList[viewHolder.adapterPosition]
        showDialog(
            message = getString(R.string.text_are_you_sure_to_delete, note.title),
            cancelable = false,
            positiveButton = getString(R.string.text_ok),
            positiveAction = {
                mainViewModel.removeNote(note)
            },
            negativeButton = getString(R.string.text_cancel),
            negativeAction = {
                notesAdapter.notifyDataSetChanged()
            })
    }

    private fun scrollRv() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> fab_add.show()
                    else -> fab_add.hide()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        }
        with(rv_notes) {
            clearOnScrollListeners()
            addOnScrollListener(scrollListener)
        }
    }

    private fun observeViewModel() {
        with(mainViewModel) {
            getAllNotes().onResult {
                noteList.clear()
                noteList.addAll(it)
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
        when (resultCode) {
            Activity.RESULT_OK -> {
                data?.let {
                    val note = Note(
                        it.getStringExtra(TITLE_NOTE_TAG),
                        it.getStringExtra(DESC_NOTE_TAG)
                    )
                    if (requestCode == REQUEST_EDIT) {
                        note.id = it.getIntExtra(ID_NOTE_TAG, DEFAULT_INT_VALUE)
                        mainViewModel.updateNote(note)
                    } else if (requestCode == REQUEST_ADD) {
                        mainViewModel.insertNoteList(note)
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                if (requestCode == REQUEST_EDIT) {
                    notesAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
