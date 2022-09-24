package com.nilsnahooy.a7minuteworkout.workoutHistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.nilsnahooy.a7minuteworkout.R
import com.nilsnahooy.a7minuteworkout.databinding.ItemWorkoutHistoryBinding

class HistoryAdapter(private val workoutList: ArrayList<WorkoutEntity>,
                     private val deleteListener: (id:Int) -> Unit)
    :RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

        inner class ViewHolder(b: ItemWorkoutHistoryBinding): RecyclerView.ViewHolder(b.root){
            val clItem = b.clItemWorkout
            val ivCompleted = b.ivItemCompleted
            val tvStart = b.tvItemStartDt
            val tvEnd = b.tvItemEndDt
            val tvSkipped = b.tvSkippedNumber
            val ivDelete = b.ivButtonDelete
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWorkoutHistoryBinding.inflate(LayoutInflater.from(parent.context),
        parent, false))
    }

    override fun onBindViewHolder(h: ViewHolder, position: Int) {
        val c = h.itemView.context
        val item = workoutList[position]
        h.clItem.setBackgroundColor(
            if (position % 2 == 0){
                c.getColor(R.color.white)
            } else {
                c.getColor(R.color.grey)
            }
        )
        h.ivCompleted.setImageDrawable(
            if (item.isCompleted){
                AppCompatResources.getDrawable(c, R.drawable.ic_baseline_check_24)
            }else{
                AppCompatResources.getDrawable(c, R.drawable.ic_baseline_close_24)
            }
        )
        h.tvStart.text = item.startDateTime
        h.tvEnd.text = item.endDateTime
        h.tvSkipped.text = item.skipped.toString()

        h.ivDelete.setOnClickListener {
            deleteListener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }
}