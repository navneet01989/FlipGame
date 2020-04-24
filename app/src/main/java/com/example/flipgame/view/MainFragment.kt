package com.example.flipgame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flipgame.R
import com.example.flipgame.utils.GridItemDecoration
import com.example.flipgame.view.adapters.DataAdapter
import com.example.flipgame.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var adapter: DataAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.generateNumbers()
        btnResetCount.setOnClickListener {
            viewModel.generateNumbers()
        }
        adapter = DataAdapter { numberAtPosition, position, itemView ->
            if(!viewModel.isCardPendingToClose.value!!) {
                viewModel.increaseStep(position)
                flipViewAnimation(itemView, numberAtPosition)
            }
        }
        viewModel.pairsDone.observe(viewLifecycleOwner, Observer {
            if(viewModel.getNumberOfPairs() == it!!) {
                AlertDialog.Builder(context!!)
                    .setCancelable(false)
                    .setTitle(getString(R.string.congratulation_msg))
                    .setMessage(getString(R.string.won_msg, txtStepCounts.text))
                    .setPositiveButton(android.R.string.yes)
                { _, _ -> viewModel.generateNumbers() }.show()
            }
        })
        viewModel.isCardPendingToClose.observe(viewLifecycleOwner, Observer {
            if(!it!!) {
                val viewHolderCurrent = recyclerView.findViewHolderForAdapterPosition(viewModel.companionClickPosition.value!!) as? DataAdapter.ViewHolder
                val viewHolderPrevious = recyclerView.findViewHolderForAdapterPosition(viewModel.previousClickPosition.value!!) as? DataAdapter.ViewHolder
                if(viewHolderCurrent != null && viewHolderPrevious != null) {
                    reverseViewAnimation(viewHolderCurrent.itemView)
                    reverseViewAnimation(viewHolderPrevious.itemView)
                }
            }
        })
        recyclerView.layoutManager = GridLayoutManager(context,3)
        recyclerView.addItemDecoration(GridItemDecoration(10, 3))
        recyclerView.adapter = adapter
        viewModel.listNumbers.observe(viewLifecycleOwner, Observer {
            adapter.addData(it)
            adapter.notifyDataSetChanged()
        })
        viewModel.step.observe(viewLifecycleOwner, Observer {
            txtStepCounts.text = getString(R.string.steps, it)
        })
    }
    private fun flipViewAnimation(itemView: View, numberAtPosition: Int) {
        itemView.animate().withLayer().rotationY(-90f).setDuration(300).withEndAction {
            run {
                itemView.findViewById<TextView>(R.id.item_number)?.text = numberAtPosition.toString()
                itemView.setBackgroundResource(R.drawable.round_corner_white)
                itemView.rotationY = 90f
                itemView.animate().withLayer().rotationY(0f).setDuration(300).withEndAction { adapter.notifyDataSetChanged() }.start()
            }
        }.start()
    }
    private fun reverseViewAnimation(itemView: View) {
        itemView.animate().withLayer().rotationY(90f).setDuration(300).withEndAction {
            run {
                itemView.findViewById<TextView>(R.id.item_number)?.text = "?"
                itemView.setBackgroundResource(R.drawable.round_corner_blue)
                itemView.rotationY = -90f
                itemView.animate().withLayer().rotationY(0f).setDuration(300).withEndAction { adapter.notifyDataSetChanged() }.start()
            }
        }.start()
    }
}
